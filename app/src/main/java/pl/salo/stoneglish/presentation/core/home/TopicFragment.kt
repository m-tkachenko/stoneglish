package pl.salo.stoneglish.presentation.core.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pl.salo.stoneglish.databinding.FragmentTopicBinding
import pl.salo.stoneglish.presentation.core.home.adapters.KeywordsAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.ListeningAndSpeakingAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.SimilarTopicsAdapter
import pl.salo.stoneglish.util.coreNavigator

class TopicFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentTopicBinding

    private val keywordsAdapter = KeywordsAdapter()
    private val listeningAndSpeakingAdapter = ListeningAndSpeakingAdapter()
    private val similarTopicsAdapter = SimilarTopicsAdapter()

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
        viewModel.getTopic()

        viewModel.selectedTopic.observe(viewLifecycleOwner) {
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


    private fun initAdapters() {
        binding.topicKeywordsRecycler.apply {
            adapter = keywordsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.topicListeningAndSpeakingRecycler.apply {
            adapter = listeningAndSpeakingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.similarTopicsRecycler.apply {
            adapter = similarTopicsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

}