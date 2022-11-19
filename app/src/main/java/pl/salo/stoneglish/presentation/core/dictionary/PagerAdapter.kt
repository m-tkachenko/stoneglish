package pl.salo.stoneglish.presentation.core.dictionary

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(dictionaryFragment: DictionaryFragment): FragmentStateAdapter(dictionaryFragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> {NounFragment()}
            else -> {VerbFragment()}
        }
    }
}