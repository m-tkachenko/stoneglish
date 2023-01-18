package pl.salo.stoneglish.presentation.core.home

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.databinding.ChoiceChipBinding
import pl.salo.stoneglish.databinding.FragmentHomeBinding
import pl.salo.stoneglish.presentation.core.home.dialog.AddNewCardDialog
import pl.salo.stoneglish.util.Utils.ninja
import pl.salo.stoneglish.util.Utils.visible
import pl.salo.stoneglish.util.coreNavigator
import pl.salo.stoneglish.util.hideKeyboard
import pl.salo.stoneglish.util.showKeyboard

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var topicsAdapter: HomeVerticalTopicsAdapter

    private var interestedTopics = listOf(TopicType.Art, TopicType.Art, TopicType.Art, TopicType.Art)

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

        observeCurrentUser()

        homeViewModel.downloadDailyCards()
        dailyCardsObserver()

        homeViewModel.readVerticalTopics(topicType = interestedTopics[3])
        verticalTopicsObserver()

        with(binding) {
            openSearchButton.setOnClickListener { openSearch() }
            closeOpenedSearchButton.setOnClickListener { closeSearch() }

            addTopicImg.setOnClickListener { coreNavigator().goToAddTopicFragment() }

            wordsOfTheDayCards.addCardClicked = {
                coreNavigator().showAddCardDialog(
                    AddNewCardDialog(
                        selectedCard = wordsOfTheDayCards.cardForAdd!!
                    )
                )
            }
        }
    }

    private fun dailyCardsObserver() {
        homeViewModel.dailyCardState.observe(viewLifecycleOwner) { dailyCardsResult ->
            dailyCardsResult.getContentIfNotHandled()?.let { dailyCards ->
                when(dailyCards) {
                    is Resource.Success -> {
                        Log.d(TAG, "DailyCardsDownload : Success")

                        binding.dailyCardLoading.ninja(false)
                        binding.wordsOfTheDayCards.apply {
                            ninja(true)
                            setUpCardsAdapter(
                                fragment = this@HomeFragment,
                                cards = dailyCards.data ?: listOf()
                            )
                        }
                    }
                    is Resource.Error -> {
                        binding.wordsOfTheDayCards.ninja(true)
                        binding.dailyCardLoading.ninja(false)

                        Log.d(TAG, "DailyCardsDownload : Failure : Error = ${dailyCards.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "DailyCardsDownload : Loading")

                        binding.wordsOfTheDayCards.ninja(false)
                        binding.dailyCardLoading.ninja(true)
                    }
                }
            }
        }
    }

    private fun verticalTopicsObserver() {
        homeViewModel.verticalTopics.observe(viewLifecycleOwner) { verticalTopicsResult ->
            verticalTopicsResult.getContentIfNotHandled()?.let { verticalTopics ->
                when(verticalTopics) {
                    is Resource.Success -> {
                        Log.d(TAG, "VerticalTopicsDownload : Success : Data = ${verticalTopics.data}")

                        topicsAdapter.topicsList = verticalTopics.data ?: listOf()
                        topicsAdapter.onTopicClick = { title ->
                            coreNavigator().goToTopicFragment(
                                type = interestedTopics[3],
                                title = title,
                                isVertical = true
                            )
                        }

                        binding.topicVerticalLoading.ninja(false)
                        binding.verticalTopicsGridRecycler.ninja(true)

                        binding.verticalTopicsGridRecycler.adapter = topicsAdapter
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "VerticalTopicsDownload : Failure : Error = ${verticalTopics.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "VerticalTopicsDownload : Loading")

                        binding.topicVerticalLoading.ninja(true)
                        binding.verticalTopicsGridRecycler.ninja(false)
                    }
                }
            }
        }
    }

    private fun observeCurrentUser() {
        homeViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            when (user) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    interestedTopics = user.data?.interestedTopics?.map { interested ->
                        TopicType.valueOf(interested)
                    } ?: listOf()

                    setUpFavouriteTopics(interestedTopics)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                    homeViewModel.getCurrentUser()
                }
            }
        }
    }

    private fun setUpFavouriteTopics(list: List<TopicType>) {
        for (choosedTopic in list) {
            val favouriteTopic = ChoiceChipBinding.inflate(layoutInflater).root

            favouriteTopic.text = choosedTopic.type
            favouriteTopic.textSize = 14F

            binding.favouriteTopics.addView(favouriteTopic)
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
        topicsAdapter = HomeVerticalTopicsAdapter(requireContext())
    }

    val TAG = "HomeFragment"
}