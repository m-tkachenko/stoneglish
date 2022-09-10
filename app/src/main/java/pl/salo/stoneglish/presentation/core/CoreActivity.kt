package pl.salo.stoneglish.presentation.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.ActivityCoreBinding
import pl.salo.stoneglish.presentation.auth.AuthViewModel
import pl.salo.stoneglish.presentation.core.cards.CardsFragment
import pl.salo.stoneglish.presentation.core.dictionary.DictionaryFragment
import pl.salo.stoneglish.presentation.core.home.HomeFragment
import pl.salo.stoneglish.presentation.core.profile.ProfileFragment
import pl.salo.stoneglish.util.CoreNavigator

@AndroidEntryPoint
class CoreActivity : AppCompatActivity(), CoreNavigator {

    lateinit var binding: ActivityCoreBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.dictionary -> {
                    replaceFragment(DictionaryFragment())
                    true
                }
                R.id.cards -> {
                    replaceFragment(CardsFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.home

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.coreFragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() = goBack()


    override fun goBack() {
        if (supportFragmentManager.backStackEntryCount != 0) supportFragmentManager.popBackStack() else finish()
    }

}