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
import pl.salo.stoneglish.util.Utils.isAbsoluteTrue
import pl.salo.stoneglish.util.Utils.visible
import pl.salo.stoneglish.util.coreNavigator

const val TAG = "CardsFragment"
@AndroidEntryPoint
class CardsFragment : Fragment() {
    private lateinit var binding: FragmentCardsBinding
    private val cardsViewModel: CardsViewModel by viewModels()

    private var downloaded = Pair(false, false)

    private var testsDownloaded = false
        set(value) {
            field = value
            downloaded = Pair(cardsDownloaded, testsDownloaded)

            if (value)
                loadingUiUpdate()
        }
    private var cardsDownloaded = false
        set(value) {
            field = value
            downloaded = Pair(cardsDownloaded, testsDownloaded)

            if (value)
                loadingUiUpdate()
        }

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

        val moduleName = arguments?.getString("ModuleName") ?: ""

        cardsViewModel.downloadCards(moduleName)
        cardsViewModel.downloadTests(moduleName)

        testsStateObserver()
        downloadCardsStateObserver()

        with(binding) {
            signInBackArrow.setOnClickListener {
                this@CardsFragment.coreNavigator().goBack()
            }
        }
    }

    private fun testsStateObserver() {
        cardsViewModel.testsState.observe(viewLifecycleOwner) { testsResult ->
            testsResult.getContentIfNotHandled()?.let { tests ->
                when(tests) {
                    is Resource.Success -> {
                        Log.d(TAG, "TestsDownload : Success")

                        binding.testsRecyclerviewInCards.adapter = CardTestsAdapter(
                            tests = tests.data!!
                        )
                        testsDownloaded = true
                    }
                    is Resource.Error -> {
                        testsDownloaded = false
                        Log.d(TAG, "TestsDownload : Failure : Error = ${tests.message}")
                    }
                    is Resource.Loading -> {
                        testsDownloaded = false
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

                        val notNullCards = cards.data!!

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

                        cardsDownloaded = true
                    }
                    is Resource.Error -> {
                        cardsDownloaded = false
                        Log.d(TAG, "CardsDownload : Failure : Error = ${cards.message}")
                    }
                    is Resource.Loading -> {
                        cardsDownloaded = false
                        Log.d(TAG, "CardsDownload : Loading")
                    }
                }
            }
        }
    }

    private fun loadingUiUpdate() {
        with(binding) {
            cardsLoadingLayout.visible(!downloaded.isAbsoluteTrue())
            cardsDownloadedLayout.visible(downloaded.isAbsoluteTrue())
        }
    }
}