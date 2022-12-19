package pl.salo.stoneglish.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.util.ProgressDialogState
import pl.salo.stoneglish.util.authNavigator

abstract class BaseAuthFragment<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    val viewModel: AuthViewModel by viewModels()
    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null) throw IllegalArgumentException("Binding cannot be null")
        return binding.root
    }

    fun onSwitchFragmentListener(fragment: () -> Unit) {
        viewModel.authState.observe(viewLifecycleOwner) { authResult ->

            authResult.getContentIfNotHandled()?.let {
                when (it) {
                    is Resource.Success -> {
                        authNavigator().setProgressDialog(ProgressDialogState.HIDE)
                        viewModel.authState.removeObservers(viewLifecycleOwner)
                        fragment()
                    }
                    is Resource.Error -> {
                        authNavigator().setProgressDialog(ProgressDialogState.HIDE)
                        authNavigator().makeSnack("${it.message}")
                    }
                    is Resource.Loading ->
                        authNavigator().setProgressDialog(ProgressDialogState.SHOW, "Signing up")
                }
            }

        }
    }
}