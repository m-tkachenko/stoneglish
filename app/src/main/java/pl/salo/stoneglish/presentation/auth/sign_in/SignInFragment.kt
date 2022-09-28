package pl.salo.stoneglish.presentation.auth.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentSignInBinding
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.util.ProgressDialogState
import pl.salo.stoneglish.util.navigator

private const val TAG = "SignInFragment"

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentSignInBinding

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

            signInWithGoogle.setOnClickListener {
                navigator().beginGoogleSignIn()
            }

//            signInWithFacebook.setPermissions("email", "public_profile")
//            signInWithFacebook.setFragment(this@SignInFragment)
//            signInWithFacebook.registerCallback(
//                navigator().getFacebookCallbackManager(),
//                object : FacebookCallback<LoginResult> {
//                    override fun onSuccess(result: LoginResult) {
//                        Log.d(TAG, "FacebookSignIn : Success")
//                        viewModel.signInUsingFacebook(result.accessToken)
//                    }
//
//                    override fun onCancel() {
//                        Log.d(TAG, "FacebookSignIn : Cancel")
//                    }
//
//                    override fun onError(error: FacebookException) {
//                        Log.e(TAG, "FacebookSignIn : Failure : Error = $error")
//                    }
//                })
        }

    }


    private fun authStateObserver() {
        viewModel.authState.observe(viewLifecycleOwner) { authResult ->
            authResult.getContentIfNotHandled()?.let {
                when(it) {
                    is Resource.Success -> {
                        navigator().setProgressDialog(ProgressDialogState.HIDE)
                        navigator().makeSnack("Yes!")
                    }
                    is Resource.Error -> {
                        navigator().setProgressDialog(ProgressDialogState.HIDE)
                        navigator().makeSnack("${it.message}")
                    }
                    is Resource.Loading ->
                        navigator().setProgressDialog(ProgressDialogState.SHOW, "Signing up")
                }

            }
        }
    }

}