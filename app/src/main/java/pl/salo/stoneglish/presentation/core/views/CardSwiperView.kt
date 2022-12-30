package pl.salo.stoneglish.presentation.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.ViewCardsSwipeBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.cards.viewpager.CardsViewPagerAdapter
import pl.salo.stoneglish.util.Utils.ninja

class CardSwiperView(
    context: Context,
    attributeSet: AttributeSet
): ConstraintLayout(context, attributeSet) {
    private val binding = ViewCardsSwipeBinding.inflate(LayoutInflater.from(context), this, true)

    private var showTitle = false
    private var showPlusButton = false
    private var viewPagerHeight: Float = 300F

    var addCardClicked: (() -> Unit) = {}
    var cardForAdd: Card? = null

    init {
        context
            .theme
            .obtainStyledAttributes(
                attributeSet, R.styleable.CardSwiperView, 0, 0
            ).apply {
                try {
                    showTitle = getBoolean(R.styleable.CardSwiperView_showCardsTitle, false)
                    showPlusButton = getBoolean(R.styleable.CardSwiperView_showAddWordButton, false)
                    viewPagerHeight = getDimension(R.styleable.CardSwiperView_viewPagerHeight, 300F)
                } finally {
                    recycle()
                }
            }
    }

    fun setUpCardsAdapter(
        fragment: Fragment,
        cards: List<Card>
    ) {
        val cardAdapter = CardsViewPagerAdapter(fragment)
        cardAdapter.cardItems = cards

        with(binding) {
            cardViewTitle.ninja(showTitle)

            addWordToCardsButton.ninja(showPlusButton)
            addWordToCardsButton.setOnClickListener {
                cardForAdd = cardAdapter.card
                addCardClicked.invoke()
            }

            cardsViewPager.layoutParams.height = viewPagerHeight.toInt()
            cardsViewPager.adapter = cardAdapter
            cardsWormDotsIndicator.attachTo(cardsViewPager)
        }
    }
}