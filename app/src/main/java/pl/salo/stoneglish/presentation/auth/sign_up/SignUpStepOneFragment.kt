package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepOneBinding
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.util.ProgressDialogState
import pl.salo.stoneglish.util.navigator
import pl.salo.stoneglish.common.Resource

private const val TAG = "SignUpStepOneFragment"

@AndroidEntryPoint
class SignUpStepOneFragment : Fragment() {
    private lateinit var binding: FragmentSignUpStepOneBinding
    private val viewModel: AuthViewModel by viewModels()

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

                viewModel.signUpUsingEmailAndPassword(email, password)
            }

            signUpWithGoogle.setOnClickListener {
                navigator().beginGoogleSignIn()
            }

            signUpWithFacebook.setPermissions("email", "public_profile")
            signUpWithFacebook.setFragment(this@SignUpStepOneFragment)
            signUpWithFacebook.registerCallback(
                navigator().getFacebookCallbackManager(),
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        Log.d(TAG, "FacebookSignIn : Success")
                        viewModel.signInUsingFacebook(result.accessToken)
                    }

                    override fun onCancel() {
                        Log.d(TAG, "FacebookSignIn : Cancel")
                    }

                    override fun onError(error: FacebookException) {
                        Log.e(TAG, "FacebookSignIn : Failure : Error = $error")
                    }
            })
        }
    }

    private fun authStateObserver() {
        viewModel.authState.observe(viewLifecycleOwner) { authResult ->
            when(authResult) {
                is Resource.Success -> {
                    navigator().setProgressDialog(ProgressDialogState.HIDE)
                    navigator().makeSnack("Yes!")
                }
                is Resource.Error -> {
                    navigator().setProgressDialog(ProgressDialogState.HIDE)
                    navigator().makeSnack("${authResult.message}")
                }
                is Resource.Loading ->
                    navigator().setProgressDialog(ProgressDialogState.SHOW, "Signing up")
            }
        }
    }
}