package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepOneBinding
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.util.ProgressDialogState
import pl.salo.stoneglish.util.navigator

@AndroidEntryPoint
class SignUpStepOneFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()
    lateinit var binding:FragmentSignUpStepOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpStepOneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authStateObserver()
        with(binding) {
            signUpBackArrow.setOnClickListener {
                navigator().goBack()
            }

            signUpBtn.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()
                viewModel.register(email, password)
                navigator().setProgressDialog(ProgressDialogState.SHOW, "Signing up")
            }
        }

    }

    private fun authStateObserver() {
        viewModel.authState.observe(viewLifecycleOwner) {
            navigator().setProgressDialog(ProgressDialogState.HIDE)
        }
    }
}