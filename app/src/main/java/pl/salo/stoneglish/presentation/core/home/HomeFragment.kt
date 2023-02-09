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
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.data.model.home.HorizontalGroup
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.data.model.home.TopicType
import pl.salo.stoneglish.databinding.ChoiceChipBinding
import pl.salo.stoneglish.databinding.FragmentHomeBinding
import pl.salo.stoneglish.presentation.core.home.adapters.HomeHorizontalTopicsAdapter
import pl.salo.stoneglish.presentation.core.home.adapters.HomeVerticalTopicsAdapter
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

    private lateinit var verticalTopicsAdapter: HomeVerticalTopicsAdapter
    private lateinit var horizontalTopicsAdapter: HomeHorizontalTopicsAdapter

    private var interestedTopics = listOf<TopicType>()

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

        homeViewModel.readVerticalTopics()
        verticalTopicsObserver()

        homeViewModel.readHorizontalGroups()
        horizontalGroupsObserver()

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

                        binding.verticalTopicsGridRecycler.adapter = verticalTopicsAdapter
                        updateVerticalTopics(
                            topics = homeViewModel.getVerticalTopicsAllTypes(
                                verticalTopics.data!!
                            )
                        )
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "VerticalTopicsDownload : Failure : Error = ${verticalTopics.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "VerticalTopicsDownload : Loading")
                    }
                }
            }
        }
    }

    private fun horizontalGroupsObserver() {
        homeViewModel.horizontalGroups.observe(viewLifecycleOwner) { horizontalGroupsResult ->
            horizontalGroupsResult.getContentIfNotHandled()?.let { horizontalGroups ->
                when(horizontalGroups) {
                    is Resource.Success -> {
                        Log.d(TAG, "HorizontalGroupsDownload : Success : Data = ${horizontalGroups.data}")

                        binding.topicsHorizontalRecycler.adapter = horizontalTopicsAdapter
                        updateHorizontalGroup(
                            horizontalGroup = homeViewModel
                                .getInterestedHorizontalGroup(
                                    interestedTopicTypes = interestedTopics,
                                    horizontalGroups = horizontalGroups.data!!
                                )
                        )

                        binding.topicVerticalLoading.ninja(false)
                        binding.verticalTopicsGridRecycler.ninja(true)
                        binding.topicsHorizontalRecycler.ninja(true)
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "HorizontalGroupsDownload : Failure : Error = ${horizontalGroups.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "HorizontalGroupsDownload : Loading")

                        binding.topicVerticalLoading.ninja(true)
                        binding.verticalTopicsGridRecycler.ninja(false)
                        binding.topicsHorizontalRecycler.ninja(false)
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

            favouriteTopic.text = choosedTopic.name
            favouriteTopic.textSize = 14F

            favouriteTopic.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    updateHomeTopicsByType(choosedTopic)
                else
                    updateHomeTopicsToPrevious()
            }

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
        verticalTopicsAdapter = HomeVerticalTopicsAdapter(requireContext())
        horizontalTopicsAdapter = HomeHorizontalTopicsAdapter(requireContext())
    }

    private fun updateHomeTopicsToPrevious() {
        updateVerticalTopicsAllType()
        updateHorizontalGroupToInterested()
    }
    private fun updateHomeTopicsByType(chosenType: TopicType) {
        updateVerticalTopicsByType(chosenType)
        updateHorizontalGroupByType(chosenType)
    }

    private fun updateVerticalTopicsAllType() {
        updateVerticalTopics(
            homeViewModel.getTopicsAllTypes()
        )
    }
    private fun updateVerticalTopicsByType(chosenType: TopicType) {
        updateVerticalTopics(
            homeViewModel.getTopicsByChosenType(chosenType)
        )
    }
    private fun updateVerticalTopics(topics: List<Topic>) {
        verticalTopicsAdapter.verticalTopicsList = topics
        verticalTopicsAdapter.onVerticalTopicClick = { position ->
            homeViewModel.setTopic(
                topicToShow = topics[position]
            )
            homeViewModel.setTopicList(
                topicsList = topics
            )

            coreNavigator().goToTopicFragment()
        }

        verticalTopicsAdapter.notifyDataSetChanged()
    }

    private fun updateHorizontalGroupToInterested() {
        updateHorizontalGroup(
            homeViewModel.getInterestedHorizontalGroup()
        )
    }
    private fun updateHorizontalGroupByType(chosenType: TopicType) {
        updateHorizontalGroup(
            homeViewModel.getHorizontalGroupByType(chosenType)
        )
    }
    private fun updateHorizontalGroup(horizontalGroup: HorizontalGroup) {
        horizontalTopicsAdapter.horizontalTopics = horizontalGroup.topics
        horizontalTopicsAdapter.onHorizontalTopicClick = { position ->
            homeViewModel.setTopic(
                topicToShow = horizontalGroup.topics[position]
            )
            homeViewModel.setTopicList(
                topicsList = horizontalGroup.topics
            )

            coreNavigator().goToTopicFragment()
        }

        binding.topicHorizontalTitle.text = horizontalGroup.horizontalGroupTitle

        horizontalTopicsAdapter.notifyDataSetChanged()
    }

    val TAG = "HomeFragment"
}