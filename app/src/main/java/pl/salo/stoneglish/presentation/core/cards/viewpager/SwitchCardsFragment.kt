package pl.salo.stoneglish.presentation.core.cards.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.salo.stoneglish.databinding.FragmentSwitchCardsBinding

class SwitchCardsFragment : Fragment() {
    lateinit var binding: FragmentSwitchCardsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSwitchCardsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        arguments
            ?.takeIf {
                it.containsKey(CARD_NAME)
                it.containsKey(CARD_POSITION)
                it.containsKey(CARDS_QUANTITY)
            }
            ?.apply {
                setText(getString(CARD_NAME))
                setNumber(
                    cardNumber = getInt(CARD_POSITION),
                    cardsQuantity = getInt(CARDS_QUANTITY)
                )
            }
    }

    private fun setText(cardText: String?) {
        binding.cardSwitcherText.text = cardText
    }

    private fun setNumber(cardNumber: Int, cardsQuantity: Int) {
        binding.cardSwitcherNumber.text = "${cardNumber + 1}/${cardsQuantity}"
    }
}