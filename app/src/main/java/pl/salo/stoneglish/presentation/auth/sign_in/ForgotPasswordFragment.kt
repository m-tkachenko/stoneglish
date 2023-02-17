package pl.salo.stoneglish.presentation.auth.sign_in

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentForgotPasswordBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.util.authNavigator

@AndroidEntryPoint
class ForgotPasswordFragment : BaseAuthFragment<FragmentForgotPasswordBinding>(
    FragmentForgotPasswordBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            authNavigator().goBack()
        }

        binding.resetPassBtn.setOnClickListener {
            viewModel.forgotPassword(binding.forgotPassEmail.text.toString())
        }

        viewModel.emailIsSent.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.resetPassContainer.visibility = View.VISIBLE
                    binding.viewContainer.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE

                    authNavigator().makeToast(it.message.toString())
                }
            }
        }
        binding.okResetPassBtn.setOnClickListener {
            authNavigator().goBack()
        }
    }

}