package pl.salo.stoneglish.presentation.core.home

import android.annotation.SuppressLint
import android.os.Bundle
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
import pl.salo.stoneglish.databinding.FragmentTopicBinding
import pl.salo.stoneglish.presentation.core.TextToSpeechResult
import pl.salo.stoneglish.presentation.core.home.adapters.KeywordsAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.ListeningAndSpeakingAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.SimilarTopicsAdapter
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class TopicFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentTopicBinding

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
        homeViewModel.getTopic()

        homeViewModel.selectedTopic.observe(viewLifecycleOwner) {
            keywordsAdapter.items = it.keywords
            similarTopicsAdapter.items = it.similarTopics ?: emptyList()
            listeningAndSpeakingAdapter.items = it.listeningAndSpeaking

            binding.topicText.text = it.text
            binding.topicTitle.text = it.title
            binding.topicEngLevel.text = it.eng_level.first().name
            binding.signInBackArrow.setOnClickListener {
                coreNavigator().goBack()
            }

            Glide.with(requireContext())
                .load(it.imgUrl)
                .transform(RoundedCorners(30))
                .into(binding.topicImg)
        }

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
                    coreNavigator().speak(keyword.word).onEach { res ->
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
                    coreNavigator().speak(listeningSpeaking.text).onEach { res ->
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

}