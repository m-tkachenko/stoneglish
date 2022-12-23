package pl.salo.stoneglish.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import pl.salo.stoneglish.databinding.ViewCardsSwipeBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.cards.viewpager.CardsViewPagerAdapter
import pl.salo.stoneglish.util.Utils.ninja

class CardSwiperView(
    context: Context,
    attributeSet: AttributeSet
): ConstraintLayout(context, attributeSet) {
    private val binding = ViewCardsSwipeBinding.inflate(LayoutInflater.from(context), this, true)

    fun setUpCardsAdapter(fragment: Fragment, cards: List<Card>, withTitle: Boolean, height: Int) {
        val cardAdapter = CardsViewPagerAdapter(fragment)
        cardAdapter.cardItems = cards

        with(binding) {
            cardViewTitle.ninja(withTitle)

            cardsViewPager.layoutParams.height = height
            cardsViewPager.adapter = cardAdapter
            cardsWormDotsIndicator.attachTo(cardsViewPager)
        }
    }
}