package pl.salo.stoneglish.presentation.core.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.data.model.home.*
import pl.salo.stoneglish.databinding.FragmentAddTopicBinding
import pl.salo.stoneglish.presentation.core.home.HomeViewModel
import pl.salo.stoneglish.util.Utils.ninja
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class AddTopicFragment : Fragment() {
    private lateinit var binding: FragmentAddTopicBinding

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val addTopicViewModel: AddTopicViewModel by viewModels()

    private val listeningSpeakingList = mutableListOf<ListeningSpeaking>()
    private val keywordsList = mutableListOf<Keyword>()
    private val similarTopicList = mutableListOf<SimilarTopic>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTypeChipGroup()
        setUpEngLevelChipGroup()

        with(binding) {
            showCreatedTopic.setOnClickListener {
                if (!allEditTextNotBlank() && !allListsNotEmpty()) {
                    homeViewModel.setTopic(addTopicViewModel.exampleTopic)
                    coreNavigator().goToTopicFragment()
                }
                else
                    coreNavigator().makeToast("Please add all information")
            }

            addNewListening.setOnClickListener {
                if (newListeningInput.text.isNotBlank() && newListeningTranslationInput.text.isNotBlank())
                    listeningSpeakingList.add(
                        ListeningSpeaking(
                            text = newListeningInput.text.toString(),
                            translatedText = newListeningTranslationInput.text.toString()
                        )
                    )
                else
                    coreNavigator().makeToast("Add listening and translation")

                newListeningInput.setText("")
                newListeningTranslationInput.setText("")
            }

            addSimilarTopics.setOnClickListener {
                if (similarTopicInput.text.isNotBlank() && similarTopicImgInput.text.isNotBlank())
                    similarTopicList.add(
                        SimilarTopic(
                            imgUrl = similarTopicImgInput.text.toString(),
                            title = similarTopicInput.text.toString()
                        )
                    )
                else
                    coreNavigator().makeToast("Add similar topic info")

                similarTopicInput.setText("")
                similarTopicImgInput.setText("")
            }

            horizontalViewTypeChip.setOnCheckedChangeListener { _, checked ->
                topicGroupTitleInput.ninja(checked)
            }

            showText.setOnClickListener {
                topicText.text = topicTextInput.text

                topicText.visibility = View.VISIBLE
                topicTextInput.visibility = View.GONE

                it.visibility = View.GONE
                editText.visibility = View.VISIBLE

                coreNavigator().setClickableWords(topicText.text.toString(), topicText)
                coreNavigator().setKeywordAddAction { newKeyword ->
                    if(!keywordsList.contains(newKeyword))
                        keywordsList.add(newKeyword)
                }
            }
            editText.setOnClickListener {
                topicText.visibility = View.GONE
                topicTextInput.visibility = View.VISIBLE

                it.visibility = View.GONE
                showText.visibility = View.VISIBLE
            }
        }
    }

    private fun getTopicModel(): Topic {
        return with(binding) {
            Topic(
                title = topicTitleInput.text.toString(),
                horizontalGroupTitle = topicGroupTitleInput.text.toString(),
                imgUrl = topicImgUrlInput.text.toString(),
                text = topicTextInput.text.toString(),
                type = getTopicTypeList(),
                eng_level = getTopicEngLevelList(),
                exercises = null,
                listeningAndSpeaking = listeningSpeakingList.toList(),
                keywords = keywordsList.toList(),
                similarTopics = similarTopicList.toList()
            )
        }
    }

    private fun getTopicTypeList(): List<TopicType> {
        val typeList: MutableList<TopicType> = mutableListOf()
        val chipsQuantity = binding.topicTypesChipGroup.childCount

        for (index in 0..chipsQuantity) {
            val type = binding.topicTypesChipGroup.getChildAt(index) as Chip?

            if (type?.isChecked == true)
                typeList.add(
                    TopicType.valueOf(type.text.toString())
                )
        }

        return typeList.toList()
    }

    private fun getTopicEngLevelList(): List<EngLevel> {
        val engLevelList: MutableList<EngLevel> = mutableListOf()
        val chipsQuantity = binding.topicEngLevelsChipGroup.childCount

        for (index in 0..chipsQuantity) {
            val level = binding.topicEngLevelsChipGroup.getChildAt(index) as Chip?

            if (level?.isChecked == true)
                engLevelList.add(
                    EngLevel.valueOf(level.text.toString())
                )
        }

        return engLevelList
    }

    override fun onDetach() {
        super.onDetach()
        homeViewModel.isNotPreview = true
    }

    private fun setUpTypeChipGroup() {
        for (type in TopicType.values()) {
            val chip = Chip(requireContext())

            chip.text = type.name
            chip.isCheckable = true
            chip.textSize = 20F

            binding.topicTypesChipGroup.addView(chip)
        }
    }

    private fun setUpEngLevelChipGroup() {
        for (level in EngLevel.values()) {
            val chip = Chip(requireContext())

            chip.text = level.name
            chip.isCheckable = true
            chip.textSize = 20F

            binding.topicEngLevelsChipGroup.addView(chip)
        }
    }

    private fun allListsNotEmpty() = getTopicTypeList().isNotEmpty() &&
            getTopicEngLevelList().isNotEmpty() &&
            listeningSpeakingList.isNotEmpty() &&
            keywordsList.isNotEmpty() &&
            similarTopicList.isNotEmpty()

    private fun allEditTextNotBlank() = listOf(
        binding.topicTextInput,
        binding.topicTitleInput,
        binding.topicImgUrlInput
    ).all {
        it.text.isNotBlank()
    }
}