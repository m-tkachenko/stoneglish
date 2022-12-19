package pl.salo.stoneglish.presentation.auth.sign_up

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentSignUpStepFourBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.presentation.auth.sign_up.adapter.SignUpCategoryAdapter
import pl.salo.stoneglish.util.authNavigator

@AndroidEntryPoint
class SignUpStepFourFragment : BaseAuthFragment<FragmentSignUpStepFourBinding>(
    FragmentSignUpStepFourBinding::inflate
) {

    private val adapterCategory = SignUpCategoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        onSwitchFragmentListener { authNavigator().goToCoreActivity() }

        with(binding) {
            signUpBackArrow.setOnClickListener { authNavigator().goBack() }
            signUpSkipBtn.setOnClickListener { viewModel.signUpUsingEmailAndPassword() }
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

            adapterCategory.onItemClick = {
                viewModel.setCategoryState(it)
            }
        }
    }
}