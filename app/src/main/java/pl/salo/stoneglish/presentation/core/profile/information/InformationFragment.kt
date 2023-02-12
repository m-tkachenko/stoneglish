package pl.salo.stoneglish.presentation.core.profile.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.salo.stoneglish.databinding.FragmentInformationBinding
import pl.salo.stoneglish.util.coreNavigator

class InformationFragment : Fragment() {
    lateinit var binding: FragmentInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.backArrow.setOnClickListener {
            coreNavigator().goBack()
        }
    }

}