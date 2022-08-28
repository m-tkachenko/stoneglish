package pl.salo.stoneglish.presentation.auth.sign_up

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
    lateinit var binding:FragmentSignUpStepOneBinding

    private val RC_SIGN_IN: Int = 3
    private val TAG = "SignUpStepOneFragment"

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions : GoogleSignInOptions

    private val facebookCallbackManager = CallbackManager.Factory.create()

    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            facebookCallbackManager.onActivityResult(
                requestCode = activityResult.resultCode,
                resultCode = activityResult.resultCode,
                data = activityResult.data
            )

            if (activityResult.resultCode == RC_SIGN_IN) {
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
                navigator().setProgressDialog(ProgressDialogState.SHOW, "Signing up")
            }

            // here onButton click listener {
            googleSignInIntent()
            // }

//        buttonFacebookLogin.setReadPermissions("email", "public_profile")
//        buttonFacebookLogin.registerCallback(facebookCallbackManager, object :
//            FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult) {
//                Log.d(TAG, "FacebookSignIn : Success")
//                authViewModel.signInUsingFacebook(result.accessToken)
//            }
//
//            override fun onCancel() {
//                Log.d(TAG, "FacebookSignIn : Cancel")
//            }
//
//            override fun onError(error: FacebookException) {
//                Log.e(TAG, "FacebookSignIn : Failure : Error = $error")
//            }
//        })
        }

    }

    private fun createGoogleSignInRequest() {
        googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    private fun googleSignInIntent() {
        val googleSignInIntent = googleSignInClient.signInIntent
        activityResultLauncher.launch(googleSignInIntent)
    }

    private fun authStateObserver() {
        authViewModel.authState.observe(viewLifecycleOwner) { authResult ->
            when(authResult) {
                is Resource.Success -> Log.d(TAG, "BlaBla")
                is Resource.Error -> Log.d(TAG, "RumRum : ${authResult.message}")
                is Resource.Loading -> Log.d(TAG, "LudLud")
            }
        }
    }
}