package pl.salo.stoneglish.presentation.core.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.data.model.home.EngLevel
import pl.salo.stoneglish.data.model.home.ListeningSpeaking
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.databinding.FragmentAddTopicBinding
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class AddTopicFragment : Fragment() {
    private lateinit var binding: FragmentAddTopicBinding
    private val viewModel: AddTopicViewModel by viewModels()

    private val listeningSpeakingList = mutableListOf<ListeningSpeaking>()

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
                if (allEditTextNotBlank() &&
                    getTopicTypeList().isNotEmpty() &&
                    getTopicEngLevelList().isNotEmpty() &&
                    listeningSpeakingList.isNotEmpty())
                    viewModel.sendTopic(getTopicModel())
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

            showText.setOnClickListener {
                topicText.text = topicTextInput.text

                topicText.visibility = View.VISIBLE
                topicTextInput.visibility = View.GONE

                it.visibility = View.GONE
                editText.visibility = View.VISIBLE
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
                type = getTopicTypeList(),
                eng_level = getTopicEngLevelList(),
                imgUrl = topicImgUrlInput.text.toString(),
                text = topicTextInput.text.toString(),
                exercises = Any(),
                listeningAndSpeaking = listeningSpeakingList,
                keywords = listOf(),
                similarTopics = listOf()
            )
        }
    }

    private fun getTopicTypeList(): List<TopicType> {
        val typeList: MutableList<TopicType> = mutableListOf()
        val checkedIds = binding.topicTypesChipGroup.checkedChipIds

        for (checked in checkedIds) {
            typeList.add(
                TopicType.values()[checked-1]
            )
        }

        return typeList.toList()
    }

    private fun getTopicEngLevelList(): List<EngLevel> {
        val engLevelList: MutableList<EngLevel> = mutableListOf()

        for (level in binding.topicEngLevelsChipGroup.checkedChipIds) {
            engLevelList.add(
                EngLevel.values()[level-15]
            )
        }

        return engLevelList
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

    private fun allEditTextNotBlank(): Boolean {
        return listOf(
            binding.topicTextInput,
            binding.topicTitleInput,
            binding.topicImgUrlInput
        ).all {
            it.text.isNotBlank()
        }
    }
}