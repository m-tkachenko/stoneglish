package pl.salo.stoneglish.presentation.auth.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentWelcomeBinding
import pl.salo.stoneglish.util.navigator

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initNavButtons()
    }

    private fun initViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.attachTo(binding.viewPager)
    }

    private fun initNavButtons() {

        binding.createAccountButton.setOnClickListener {
            navigator().goToSignUp()
        }

        binding.existAccountButton.setOnClickListener {
            navigator().goToSignIn()
        }
    }
}