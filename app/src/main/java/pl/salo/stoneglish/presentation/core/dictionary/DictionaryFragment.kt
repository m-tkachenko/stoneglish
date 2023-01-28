package pl.salo.stoneglish.presentation.core.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentDictionaryBinding
import pl.salo.stoneglish.util.Constants
import pl.salo.stoneglish.util.hideKeyboard
import java.util.*

@AndroidEntryPoint
class DictionaryFragment : Fragment() {
    lateinit var binding: FragmentDictionaryBinding
    private val viewModel: DictionaryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerAdapter = PagerAdapter(this)
        binding.dictionaryViewPager.adapter = pagerAdapter

        binding.audioActionButton.setOnClickListener {
            viewModel.runAudio()
        }

        TabLayoutMediator(
            binding.dictionaryTabLayout,
            binding.dictionaryViewPager
        ) { tab, position ->
            val tabNames = listOf("Noun", "Verb")
            tab.text = tabNames[position]
        }.attach()
        observeWordData()
        observePlayingAudio()
        setUpSearch()

    }

    private fun observePlayingAudio() {
        viewModel.playingAudio.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading -> {
                    binding.audioActionButton.visibility = View.GONE
                    binding.audioActionProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.audioActionButton.visibility = View.VISIBLE
                    binding.audioActionProgressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.audioActionButton.apply {
                        setImageResource(R.drawable.ic_alert_circle_outline)
                        visibility = View.VISIBLE
                        isClickable = false
                    }

                    binding.audioActionProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun observeWordData() {
        viewModel.wordData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.dictionaryProgressBar.visibility = View.GONE
                    binding.blankContainer.visibility = View.GONE
                    binding.dictionaryContentContainer.visibility = View.VISIBLE

                    binding.word.text = resource.data?.word?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }

                    binding.phonetic.text = resource.data?.phonetic
                }
                is Resource.Error -> {
                    binding.dictionaryContentContainer.visibility = View.GONE
                    binding.dictionaryProgressBar.visibility = View.GONE

                    binding.blankContainer.visibility = View.VISIBLE

                    binding.dictionaryTextBlank.apply {
                        visibility = View.VISIBLE
                        text = Constants.SOMETHING_WENT_WRONG
                    }
                }
                is Resource.Loading -> {
                    binding.dictionaryProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpSearch() {
        binding.dictionarySearchField.setOnEditorActionListener { _, query, _ ->
            if (query == EditorInfo.IME_ACTION_GO) {

                val word = binding.dictionarySearchField.text.toString()
                viewModel.getWordData(word)

                binding.dictionarySearchField.clearFocus()

                hideKeyboard()
                updateUI()
            }
            false
        }

        binding.dictionarySearchField.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                binding.dictionarySearchField.text.clear()
            }
        }
    }

    private fun updateUI() {
        binding.audioActionButton.apply {
            setImageResource(R.drawable.ic_play)
            isClickable = true
        }
    }
}