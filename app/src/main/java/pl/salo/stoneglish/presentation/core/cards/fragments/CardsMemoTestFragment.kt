package pl.salo.stoneglish.presentation.core.cards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentCardsTestBinding
import pl.salo.stoneglish.presentation.core.cards.CardsTestViewModel
import pl.salo.stoneglish.presentation.core.cards.CardsViewModel
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class CardsMemoTestFragment : Fragment() {

    private lateinit var binding: FragmentCardsTestBinding
    private val viewModel: CardsTestViewModel by viewModels()
    private val cardsViewModel: CardsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardsTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            coreNavigator().goBack()
        }
        viewModel.wordCounterState.observe(viewLifecycleOwner) {
            binding.testWordsCount.text = it
        }

        viewModel.onFinish.observe(viewLifecycleOwner) {
            binding.finishText.text = it
            binding.flowContainer.visibility = View.GONE
            binding.finishContainer.visibility = View.VISIBLE
        }

        binding.finishBtn.setOnClickListener {
            coreNavigator().goBack()
        }

        viewModel.onButtonsColorUpdated.observe(viewLifecycleOwner) {
            with(binding) {
                val colorWhite =
                    ContextCompat.getColorStateList(requireContext(), R.color.background_card)
                val colorGreen = requireContext().getColor(R.color.text_green)
                firstWordContainer.backgroundTintList = colorWhite
                firstWord.setTextColor(colorGreen)
                secondWordContainer.backgroundTintList = colorWhite
                secondWord.setTextColor(colorGreen)
                thirdWordContainer.backgroundTintList = colorWhite
                thirdWord.setTextColor(colorGreen)
            }
        }

        cardsViewModel.cardsDownloadState.observe(viewLifecycleOwner) {
            if (it is Resource.Success) viewModel.initCards(
                it.data ?: emptyList()
            ) else coreNavigator().goBack()
        }

        viewModel.progressBarState.observe(viewLifecycleOwner) {
            val progressVisibility = if (it) View.VISIBLE else View.GONE
            val flowVisibility = if (!it) View.VISIBLE else View.GONE
            binding.flowContainer.visibility = flowVisibility
            binding.progressBarContainer.visibility = progressVisibility
        }

        viewModel.onTestDataChanged.observe(viewLifecycleOwner) { viewData ->
            binding.testWord.text = viewData.word
            val buttonList = listOf(
                binding.firstWord,
                binding.secondWord,
                binding.thirdWord
            ).shuffled()

            buttonList[0].text = viewData.correctTranslation
            buttonList[1].text = viewData.incorrectTranslations.first()
            buttonList[2].text = viewData.incorrectTranslations.last()

            val listener = OnClickListener {
                val button = (it as TextView)
                val isCorrect = button.text.toString() == viewData.correctTranslation
                val color = if (isCorrect) R.color.main_green else R.color.main_red
                (button.parent as MaterialCardView).backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), color)
                button.setTextColor(requireContext().getColor(R.color.white))

                viewModel.onNext(isCorrect)
            }

            buttonList.forEach {
                it.setOnClickListener(listener)
            }
        }

    }

}