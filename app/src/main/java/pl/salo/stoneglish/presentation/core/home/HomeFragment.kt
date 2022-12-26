package pl.salo.stoneglish.presentation.core.home

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.FragmentHomeBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.home.dialog.AddNewCardDialog
import pl.salo.stoneglish.util.Utils.visible
import pl.salo.stoneglish.util.coreNavigator
import pl.salo.stoneglish.util.hideKeyboard
import pl.salo.stoneglish.util.showKeyboard

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var topicsAdapter: HomeTopicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()

        with(binding) {
            openSearchButton.setOnClickListener { openSearch() }
            closeOpenedSearchButton.setOnClickListener { closeSearch() }

            wordsOfTheDayCards.setUpCardsAdapter(
                fragment = this@HomeFragment,
                cards = listOf(Card(word = "Nu"), Card(word="Sho"), Card("ty?"))
            ) { word ->
                coreNavigator().showAddCardDialog(
                    AddNewCardDialog(
                        selectedWord = word
                    )
                )
            }


            topicsAdapter.topicsList = listOf("Buuu", "Uuuuu", "Aaaaa", "Paaaaa", "Waaaa", "Tatatata")
            topicsRecycler.adapter = topicsAdapter

            startLearnImage.setImageResource(R.drawable.me)
            startLearnCardTitle.text = "To moje zdjęcie jest na dole piękneee. Polecam ten przycisk naciśnąc i zobaczysz"
        }
    }

    private fun openSearch() {
        with(binding) {
            searchInput.setText("")
            searchInput.showKeyboard(requireContext())

            searchOpenedView visible true

            val circularReveal = ViewAnimationUtils.createCircularReveal(
                searchOpenedView,
                (openSearchButton.right + openSearchButton.left) / 2,
                (openSearchButton.top + openSearchButton.bottom) / 2,
                0f,
                searchOpenedView.width.toFloat()
            )

            circularReveal.duration = 300
            circularReveal.start()
        }
    }

    private fun closeSearch() {
        with(binding) {
            val circularConceal = ViewAnimationUtils.createCircularReveal(
                searchOpenedView,
                (openSearchButton.right + openSearchButton.left) / 2,
                (openSearchButton.top + openSearchButton.bottom) / 2,
                searchOpenedView.width.toFloat(),
                0f
            )
            circularConceal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) = Unit
                override fun onAnimationCancel(animation: Animator?) = Unit
                override fun onAnimationStart(animation: Animator?) = Unit
                override fun onAnimationEnd(animation: Animator?) {
                    searchOpenedView.visibility = View.INVISIBLE
                    circularConceal.removeAllListeners()
                }
            })

            hideKeyboard()

            circularConceal.duration = 300
            circularConceal.start()
        }
    }

    private fun initAdapters() {
        topicsAdapter = HomeTopicsAdapter()
    }
}