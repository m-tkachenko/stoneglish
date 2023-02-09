package pl.salo.stoneglish.presentation.core.cards.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.Keyword
import pl.salo.stoneglish.databinding.FragmentCardsBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.TextToSpeechResult
import pl.salo.stoneglish.presentation.core.cards.CardsViewModel
import pl.salo.stoneglish.presentation.core.cards.adapters.CardTestsAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.KeywordsAdapter
import pl.salo.stoneglish.util.Utils.isAbsoluteTrue
import pl.salo.stoneglish.util.Utils.ninja
import pl.salo.stoneglish.util.coreNavigator

const val TAG = "CardsFragment"

@AndroidEntryPoint
class CardsFragment : Fragment() {
    private lateinit var binding: FragmentCardsBinding
    private val cardsViewModel: CardsViewModel by activityViewModels()

    lateinit var testAdapter: CardTestsAdapter
    private val keywordsAdapter = KeywordsAdapter()

    private var isKeywordsSpeakingBlocked = false
    private var isLSSpeakingBlocked = false

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
        cardsViewModel.downloadTests()



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
                when (tests) {
                    is Resource.Success -> {
                        Log.d(TAG, "TestsDownload : Success")

                        testAdapter = CardTestsAdapter(
                            tests = tests.data!!
                        )

                        binding.testsRecyclerviewInCards.adapter = testAdapter
                        testsDownloaded = true

                        testAdapter.onItemClick = {
                            coreNavigator().goToTest(it.name)
                        }
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
        cardsViewModel.cardsDownloadState.observe(viewLifecycleOwner) { cards ->
            when (cards) {
                is Resource.Success -> {
                    Log.d(TAG, "CardsDownload : Success")

                    val notNullCards = cards.data!!
                    keywordsAdapter.items = notNullCards.toKeywords()

                    with(binding) {
                        cardsRecyclerviewInCards.adapter = keywordsAdapter

                        cardsView.setUpCardsAdapter(
                            fragment = this@CardsFragment,
                            cards = notNullCards
                        )
                    }
                    keywordsAdapter.onItemClick = { keyword ->
                        if (!isLSSpeakingBlocked) {
                            val items = keywordsAdapter.items.toMutableList()
                            isKeywordsSpeakingBlocked = true
                            coreNavigator().speakWithFlow(keyword.word).onEach { res ->
                                when (res) {
                                    is TextToSpeechResult.Loading -> {
                                        items.forEach { it.isSpeaking = false }
                                        val index = items.indexOf(keyword)
                                        keyword.isSpeaking = true
                                        items[index] = keyword

                                        keywordsAdapter.items = items
                                        keywordsAdapter.notifyDataSetChanged()
                                    }
                                    else -> {

                                        items.forEach { it.isSpeaking = false }
                                        keywordsAdapter.items = items
                                        keywordsAdapter.notifyDataSetChanged()
                                        isKeywordsSpeakingBlocked = false
                                    }
                                }
                            }.launchIn(MainScope())
                        }
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

    private fun loadingUiUpdate() {
        with(binding) {
            cardsLoadingLayout.ninja(!downloaded.isAbsoluteTrue())
            cardsDownloadedLayout.ninja(downloaded.isAbsoluteTrue())
        }
    }
}

fun List<Card>.toKeywords(): List<Keyword> {
    return this.map {
        Keyword(word = it.word, translate = it.firstTranslation)
    }
}