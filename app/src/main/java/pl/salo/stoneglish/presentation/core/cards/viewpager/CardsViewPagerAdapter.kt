package pl.salo.stoneglish.presentation.core.cards.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import pl.salo.stoneglish.domain.model.card.Card

const val CARD_POSITION = "CARD_POSITION"
const val CARDS_QUANTITY = "CARDS_QUANTITY"
const val CARD_NAME = "CARD_NAME"

class CardsViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    var cardItems: List<Card> = listOf()
    var card: Card? = null

    override fun createFragment(position: Int): Fragment {
        val fragment = SwitchCardsFragment()

        fragment.arguments = Bundle().apply {
            putInt(CARD_POSITION, position)
            putInt(CARDS_QUANTITY, itemCount)
            putString(CARD_NAME, cardItems[position].word)
        }

        return fragment
    }

    override fun getItemId(position: Int): Long {
        card = cardItems[position]
        return super.getItemId(position)
    }

    override fun getItemCount(): Int = cardItems.size
}