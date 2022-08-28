package pl.salo.stoneglish.presentation.auth.sign_up

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.activityViewModels
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignUpStepOneBinding
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.util.ProgressDialogState
import pl.salo.stoneglish.util.navigator
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource

@AndroidEntryPoint
class SignUpStepOneFragment : Fragment() {
    private lateinit var binding: FragmentSignUpStepOneBinding

    private val RC_SIGN_IN: Int = 3
    private val TAG = "SignUpStepOneFragment"

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var googleSignInClient: SignInClient
    private lateinit var googleSignInRequest: BeginSignInRequest

    private val facebookCallbackManager = CallbackManager.Factory.create()

    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                facebookCallbackManager.onActivityResult(
                    requestCode = activityResult.resultCode,
                    resultCode = activityResult.resultCode,
                    data = activityResult.data
                )

                try {
                    val account = GoogleSignIn
                        .getSignedInAccountFromIntent(activityResult.data)
                        .getResult(ApiException::class.java)

                    authViewModel.signInUsingGoogle(account)
                } catch (e: ApiException) {
                    Log.e(TAG, "GoogleSignIn : Failure : Error = $e")
                }
            }
        }

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

        createGoogleSignInRequest()
        with(binding) {
            signUpBackArrow.setOnClickListener {
                navigator().goBack()
            }

            signUpBtn.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()

                authViewModel.signUpUsingEmailAndPassword(email, password)
            }

            signUpWithGoogle.setOnClickListener {
                beginGoogelSignIn()
            }

            signUpWithFacebook.setPermissions("email", "public_profile")
            signUpWithFacebook.setFragment(this@SignUpStepOneFragment)
            signUpWithFacebook.registerCallback(facebookCallbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d(TAG, "FacebookSignIn : Success")
                    authViewModel.signInUsingFacebook(result.accessToken)
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

    private fun createGoogleSignInRequest() {
        googleSignInClient = Identity.getSignInClient(requireContext())
        googleSignInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun beginGoogelSignIn() {
        googleSignInClient.beginSignIn(googleSignInRequest)
            .addOnSuccessListener(requireActivity()) { signInResult ->
                try {
                    activityResultLauncher.launch(
                        IntentSenderRequest
                            .Builder(signInResult.pendingIntent.intentSender)
                            .build()
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                Log.e(TAG, "GoogleSignIn : Failure : Error = $e");
            }
    }

    private fun authStateObserver() {
        authViewModel.authState.observe(viewLifecycleOwner) { authResult ->
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