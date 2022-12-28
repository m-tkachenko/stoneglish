package pl.salo.stoneglish.presentation.core.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.data.model.home.EngLevel
import pl.salo.stoneglish.databinding.FragmentAddTopicBinding

@AndroidEntryPoint
class AddTopicFragment : Fragment() {
    private lateinit var binding: FragmentAddTopicBinding
    private val viewModel: AddTopicViewModel by viewModels()

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

        setUpChipGroup()
    }

    private fun setUpChipGroup() {
        for (level in EngLevel.values()) {
            val chip = Chip(requireContext())

            chip.text = level.name
            chip.isCheckable = true
            chip.textSize = 20F

            binding.topicEngLevelsChipGroup.addView(chip)
        }
    }
}