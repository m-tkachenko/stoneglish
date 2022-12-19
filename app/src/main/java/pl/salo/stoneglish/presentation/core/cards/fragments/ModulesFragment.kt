package pl.salo.stoneglish.presentation.core.cards.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentModulesBinding
import pl.salo.stoneglish.presentation.core.cards.CardsViewModel
import pl.salo.stoneglish.presentation.core.cards.adapters.CardModulesAdapter
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class ModulesFragment : Fragment() {
    private lateinit var binding: FragmentModulesBinding
    private val cardsViewModel: CardsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModulesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardsViewModel.downloadModules()
        modulesStateObserver()
    }

    private fun modulesStateObserver() {
        cardsViewModel.modulesState.observe(viewLifecycleOwner) { modulesResult ->
            modulesResult.getContentIfNotHandled()?.let { modules ->
                when(modules) {
                    is Resource.Success -> {
                        Log.d(TAG, "ModulesDownload : Success")
                        binding.modulesRecyclerView.adapter =
                            CardModulesAdapter(
                                modules = modules.data ?: listOf()
                            ) { module ->
                                this.coreNavigator().goToCard(module)
                            }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "ModulesDownload : Failure : Error = ${modules.message}")
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "ModulesDownload : Loading")
                    }
                }

            }
        }
    }

    val TAG = "ModulesFragment"
}