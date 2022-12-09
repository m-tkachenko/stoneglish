package pl.salo.stoneglish.presentation.core.cards.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentCardsBinding
import pl.salo.stoneglish.presentation.core.cards.CardsViewModel
import pl.salo.stoneglish.presentation.core.cards.adapters.CardsTranslationsAdapter
import pl.salo.stoneglish.presentation.core.cards.adapters.CardTestsAdapter
import pl.salo.stoneglish.presentation.core.cards.viewpager.CardsViewPagerAdapter

const val TAG = "CardsFragment"
@AndroidEntryPoint
class CardsFragment : Fragment() {
    private lateinit var binding: FragmentCardsBinding

    private val cardsViewModel: CardsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardsViewModel.downloadCards(moduleName = "Food")
        cardsViewModel.downloadTests()

        testsStateObserver()
        downloadCardsStateObserver()
    }

    private fun testsStateObserver() {
        cardsViewModel.testsState.observe(viewLifecycleOwner) { testsResult ->
            testsResult.getContentIfNotHandled()?.let { tests ->
                when(tests) {
                    is Resource.Success -> {
                        Log.d(TAG, "TestsDownload : Success")

                        binding.testsRecyclerviewInCards.adapter = CardTestsAdapter(
                            tests = tests.data ?: listOf()
                        )
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "TestsDownload : Failure : Error = ${tests.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "TestsDownload : Loading")
                    }
                }
            }
        }
    }

    private fun downloadCardsStateObserver() {
        cardsViewModel.cardsDownloadState.observe(viewLifecycleOwner) { cardsResult ->
            cardsResult.getContentIfNotHandled()?.let { cards ->
                when(cards) {
                    is Resource.Success -> {
                        Log.d(TAG, "CardsDownload : Success")

                        val notNullCards = cards.data ?: listOf()

                        with(binding) {
                            cardsRecyclerviewInCards.adapter = CardsTranslationsAdapter(
                                cards = notNullCards
                            )

                            cardsViewPager.adapter =
                                CardsViewPagerAdapter(
                                    fragment = this@CardsFragment,
                                    cardsList = notNullCards
                                )
                            cardsWormDotsIndicator.attachTo(cardsViewPager)
                        }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "CardsDownload : Failure : Error = ${cards.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "CardsDownload : Loading")
                    }
                }
            }
        }
    }
}