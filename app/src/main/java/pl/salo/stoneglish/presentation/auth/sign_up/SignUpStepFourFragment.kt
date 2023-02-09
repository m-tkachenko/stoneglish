package pl.salo.stoneglish.presentation.auth.sign_up

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentSignUpStepFourBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.presentation.auth.sign_up.adapter.SignUpCategoryAdapter
import pl.salo.stoneglish.presentation.core.profile.ProfileViewModel
import pl.salo.stoneglish.util.authNavigator
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class SignUpStepFourFragment : BaseAuthFragment<FragmentSignUpStepFourBinding>(
    FragmentSignUpStepFourBinding::inflate
) {

    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val adapterCategory = SignUpCategoryAdapter()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFromProfile = profileViewModel.currentUser.value != null
        if (isFromProfile) {
            binding.screenTitle.text = "Choose your favorite topics"
        }

        initRecyclerView()
        onSwitchFragmentListener { authNavigator().goToCoreActivity() }

        with(binding) {
            signUpBackArrow.setOnClickListener {
                if (isFromProfile) coreNavigator().goBack() else authNavigator().goBack()
            }
            signUpSkipBtn.setOnClickListener {
                if (!isFromProfile) {
                    viewModel.signUpUsingEmailAndPassword()
                } else {
                    Log.d(TAG, "currentUser id: ${profileViewModel.currentUser.value?.data?.id}")
                    val categories = viewModel.getSelectedCategories()

                        Log.d(TAG, "selected categories: $categories")
                        profileViewModel.updateUserCategories(categories)
                        profileViewModel.onUserDataChanged.observe(viewLifecycleOwner) {
                            when (it) {
                                is Resource.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    coreNavigator().goBack()
                                }
                                is Resource.Error -> {
                                    coreNavigator().makeToast("Something went wrong :(")
                                }
                            }
                        }

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        viewModel.getCategories()

        binding.signUpCategories.apply {
            adapter = adapterCategory
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)

            viewModel.categories.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { categories ->
                    when (categories) {
                        is Resource.Success -> {
                            adapterCategory.items = categories.data!!
                            adapterCategory.notifyDataSetChanged()
                        }
                        else -> {}
                    }
                }
            }

            adapterCategory.onItemClick = { item ->
                viewModel.setCategoryState(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        if(profileViewModel.currentUser.value?.data != null){
            profileViewModel.getCurrentUser()
            profileViewModel.clearCategories()
        }
        viewModel.clearData()
    }

    companion object{
        const val TAG = "SignUpStepFourFragment"
    }
}