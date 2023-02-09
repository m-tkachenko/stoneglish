package pl.salo.stoneglish.presentation.core.profile

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentChangeFieldBinding
import pl.salo.stoneglish.util.coreNavigator

class ChangeFieldFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    lateinit var binding: FragmentChangeFieldBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeFieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.changeBtn.setOnClickListener {
            val newFieldText = binding.fieldText.text.toString()
            viewModel.changeField(newFieldText)
        }

        binding.backArrow.setOnClickListener {
            coreNavigator().goBack()
        }

        viewModel.fieldToChange.observe(viewLifecycleOwner) {
            val text = when (it) {
                Field.NAME -> {
                    binding.changeType.text = it.text
                    viewModel.currentUser.value?.data?.username
                }
                Field.AGE -> {
                    binding.fieldText.inputType = InputType.TYPE_CLASS_NUMBER
                    binding.changeType.text = it.text
                    viewModel.currentUser.value?.data?.age.toString()
                }
                else -> {
                    null
                }
            }
            binding.fieldText.setText(text ?: "Something is wrong")
        }

        viewModel.onUserTextChanged.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Resource.Loading -> {
                        binding.changeFieldProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        binding.changeFieldProgressBar.visibility = View.GONE
                        coreNavigator().makeToast("Something is wrong")
                    }
                    is Resource.Success -> {
                        binding.changeFieldProgressBar.visibility = View.GONE
                        coreNavigator().goBack()
                    }
                }
            }
        }

    }


}