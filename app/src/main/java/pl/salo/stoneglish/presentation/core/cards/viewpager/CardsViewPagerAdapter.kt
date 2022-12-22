package pl.salo.stoneglish.presentation.core.cards.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.salo.stoneglish.domain.model.card.Card

const val CARD_POSITION = "CARD_POSITION"
const val CARDS_QUANTITY = "CARDS_QUANTITY"
const val CARD_NAME = "CARD_NAME"

class CardsViewPagerAdapter(
    fragment: Fragment,
    private val cardsList: List<Card>
) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        val fragment = SwitchCardsFragment()

        fragment.arguments = Bundle().apply {
            putInt(CARD_POSITION, position)
            putInt(CARDS_QUANTITY, itemCount)
            putString(CARD_NAME, cardsList[position].word)
        }

        return fragment
    }

    override fun getItemCount(): Int = cardsList.size
}