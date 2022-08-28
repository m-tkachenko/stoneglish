package pl.salo.stoneglish.presentation.auth.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentSignInBinding
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.util.ProgressDialogState
import pl.salo.stoneglish.util.navigator

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authStateObserver()

        with(binding) {
            signInBackArrow.setOnClickListener {
                navigator().goBack()
            }

            signInBtn.setOnClickListener {
                val email = signInEmail.text.toString()
                val password = signInPassword.text.toString()

                viewModel.signInUsingEmailAndPassword(email, password)
            }
        }

    }

    private fun authStateObserver() {
        viewModel.authState.observe(viewLifecycleOwner) { authResult ->
            when(authResult) {
                is Resource.Success -> {
                    navigator().setProgressDialog(ProgressDialogState.HIDE)
                    Toast.makeText(requireContext(), "Upipi piramambi", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    navigator().setProgressDialog(ProgressDialogState.HIDE)
                    Toast.makeText(requireContext(), "${authResult.message}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading ->
                    navigator().setProgressDialog(ProgressDialogState.SHOW, "Signing up")
            }
        }
    }

}