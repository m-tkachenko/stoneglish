package pl.salo.stoneglish.presentation.core.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.databinding.FragmentTopicBinding
import pl.salo.stoneglish.presentation.core.TextToSpeechResult
import pl.salo.stoneglish.presentation.core.home.adapters.KeywordsAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.ListeningAndSpeakingAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.SimilarTopicsAdapter
import pl.salo.stoneglish.util.Utils.ninja
import pl.salo.stoneglish.util.Utils.notSame
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class TopicFragment : Fragment() {
    private lateinit var binding: FragmentTopicBinding

    private val topicViewModel: TopicViewModel by activityViewModels()

    private val keywordsAdapter = KeywordsAdapter()
    private val listeningAndSpeakingAdapter = ListeningAndSpeakingAdapter()
    private val similarTopicsAdapter = SimilarTopicsAdapter()

    private var isKeywordsSpeakingBlocked = false
    private var isLSSpeakingBlocked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        observeAddTopicState()

        binding.topicIsLoading.ninja(false)
        binding.topicIsDownloaded.ninja(true)

        setUpTopicScreen(topicViewModel.topicForShow)

        similarTopicsAdapter.onItemClick = { similarTopic ->
            val prepareTopic = topicViewModel.topicsListFowShow.find { thisTopic ->
                thisTopic.title == similarTopic.title
            } ?: Topic()

            if (prepareTopic notSame Topic()) {
                topicViewModel.setTopic(
                    topicToShow = prepareTopic
                )
                coreNavigator().goToTopicFragment()
            }
        }

        binding.addTopicToDBLayout.ninja(!topicViewModel.isNotPreview)
        binding.addTopicToDB.setOnClickListener {
            topicViewModel.addNewTopic(topicViewModel.topicForShow)
        }
    }

    private fun observeAddTopicState() {
        topicViewModel.newTopicUploadState.observe(viewLifecycleOwner) { uploadResult ->
            uploadResult.getContentIfNotHandled()?.let { result ->
                when(result) {
                    is Resource.Success -> {
                        Log.d(TAG, "UploadNewTopic : Success")

                        coreNavigator().showAddedTopicDialog()
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "UploadNewTopic : Failure : Error = ${result.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "UploadNewTopic : Loading")

                        coreNavigator().makeToast("Loading...")
                    }
                }
            }
        }
    }

    private fun setUpTopicScreen(topic: Topic) {
        keywordsAdapter.items = topic.keywords
        similarTopicsAdapter.items = topic.similarTopics ?: emptyList()
        listeningAndSpeakingAdapter.items = topic.listeningAndSpeaking

        binding.topicText.text = topic.text

        coreNavigator().setClickableWords(topic.text, binding.topicText)

        binding.topicTitle.text = topic.title
        binding.topicEngLevel.text = topic.eng_level.first().name

        binding.signInBackArrow.setOnClickListener {
            coreNavigator().goBack()
        }

        Glide.with(requireContext())
            .load(topic.imgUrl)
            .transform(RoundedCorners(30))
            .into(binding.topicImg)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapters() {
        binding.topicKeywordsRecycler.apply {
            adapter = keywordsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            keywordsAdapter.onItemClick = { keyword ->
                if (!isLSSpeakingBlocked) {
                    val items = keywordsAdapter.items.toMutableList()
                    isKeywordsSpeakingBlocked = true
                    coreNavigator().speakWithFlow(keyword.word).onEach { res ->
                        when (res) {
                            is TextToSpeechResult.Loading -> {
                                items.forEach { it.isSpeaking = false }
                                val index = items.indexOf(keyword)
                                keyword.isSpeaking = true
                                items[index] = keyword

                                keywordsAdapter.items = items
                                keywordsAdapter.notifyDataSetChanged()
                            }
                            else -> {

                                items.forEach { it.isSpeaking = false }
                                keywordsAdapter.items = items
                                keywordsAdapter.notifyDataSetChanged()
                                isKeywordsSpeakingBlocked = false
                            }
                        }
                    }.launchIn(MainScope())
                }
            }
        }

        binding.topicListeningAndSpeakingRecycler.apply {
            adapter = listeningAndSpeakingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            listeningAndSpeakingAdapter.onItemClick = { listeningSpeaking ->
                if (!isKeywordsSpeakingBlocked) {
                    val items = listeningAndSpeakingAdapter.items.toMutableList()
                    isLSSpeakingBlocked = true
                    coreNavigator().speakWithFlow(listeningSpeaking.text).onEach { res ->
                        when (res) {
                            is TextToSpeechResult.Loading -> {
                                items.forEach { it.isSpeaking = false }
                                val index = items.indexOf(listeningSpeaking)
                                listeningSpeaking.isSpeaking = true
                                items[index] = listeningSpeaking

                                listeningAndSpeakingAdapter.items = items
                                listeningAndSpeakingAdapter.notifyDataSetChanged()
                            }
                            else -> {
                                items.forEach { it.isSpeaking = false }
                                listeningAndSpeakingAdapter.items = items
                                listeningAndSpeakingAdapter.notifyDataSetChanged()
                                isLSSpeakingBlocked = false
                            }
                        }
                    }.launchIn(MainScope())
                }
            }
        }

        binding.similarTopicsRecycler.apply {
            adapter = similarTopicsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    companion object {
        const val TAG = "TopicFragment"
    }
}