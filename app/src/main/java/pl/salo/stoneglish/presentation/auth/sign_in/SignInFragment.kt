package pl.salo.stoneglish.presentation.auth.sign_in

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentSignInBinding
import pl.salo.stoneglish.presentation.auth.BaseAuthFragment
import pl.salo.stoneglish.util.navigator

private const val TAG = "SignInFragment"

@AndroidEntryPoint
class SignInFragment : BaseAuthFragment<FragmentSignInBinding>(
    FragmentSignInBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSwitchFragmentListener { navigator().goToCoreActivity() }

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
        }
    }
}