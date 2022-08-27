package pl.salo.stoneglish.presentation.auth.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.FragmentSwitchItemBinding

class SwitchItemFragment : Fragment() {

    lateinit var binding: FragmentSwitchItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSwitchItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {

            when (getInt(ARG_OBJECT)) {
                FIRST_ITEM -> {
                    setSwitcherText(R.string.text_0)
                }
                SECOND_ITEM -> {
                    setSwitcherText(R.string.text_1)
                }
                else -> {
                    setSwitcherText(R.string.text_2)
                }
            }
        }
    }

    private fun setSwitcherText(resId: Int) {
        binding.authSwitcherText.text = resources.getText(resId)
    }

    companion object {
        const val FIRST_ITEM = 0
        const val SECOND_ITEM = 1
    }
}