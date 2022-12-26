package pl.salo.stoneglish.presentation.core.home.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.DialogAddNewCardBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.home.HomeViewModel
import pl.salo.stoneglish.util.Utils.visible
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class AddNewCardDialog(
    private val selectedCard: Card
): DialogFragment() {
    private lateinit var binding: DialogAddNewCardBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private var isLoading = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddNewCardBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        homeViewModel.downloadModules()

        modulesStateObserver()
        addCardStateObserver()
    }

    private fun modulesStateObserver() {
        homeViewModel.modulesState.observe(viewLifecycleOwner) { modulesResult ->
            modulesResult.getContentIfNotHandled()?.let { modules ->
                when(modules) {
                    is Resource.Success -> {
                        Log.d(TAG, "ModulesDownload : Success")

                        binding.modulesRecyclerViewDialog.adapter =
                            NewCardModulesAdapter(
                                modulesList = modules.data ?: listOf()
                            ) { moduleName ->
                                homeViewModel.addNewCard(
                                    newCard = selectedCard,
                                    moduleName = moduleName
                                )
                            }

                        isLoading = false
                        updateLoadingLayout()
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "ModulesDownload : Failure : Error = ${modules.message}")
                        isLoading = false
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "ModulesDownload : Loading")

                        isLoading = true
                        updateLoadingLayout(getString(R.string.card_dialog_modules_loading_text))
                    }
                }
            }
        }
    }

    private fun addCardStateObserver() {
        homeViewModel.addCardState.observe(viewLifecycleOwner) { addCardResult ->
            addCardResult.getContentIfNotHandled()?.let { status ->
                when(status) {
                    is Resource.Success -> {
                        Log.d(TAG, "AddCard : Success")

                        isLoading = false
                        updateLoadingLayout()

                        coreNavigator().makeSnack("Card successfully uploaded")

                        dialog?.dismiss()
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "AddCard : Failure : Error = ${status.message}")
                        isLoading = false
                    }
                    is Resource.Loading -> {
                        Log.d(TAG, "AddCard : Loading")

                        isLoading = true
                        updateLoadingLayout(getString(R.string.card_dialog_add_card_loading_text))
                    }
                }
            }
        }
    }

    private fun updateLoadingLayout(loadingText: String = "") {
        with(binding) {
            dialogLoadingLayout.visible(isLoading)
            dialogLoadedLayout.visible(!isLoading)

            if (isLoading)
                dialogLoadingText.text = loadingText
        }
    }

    val TAG = "AddNewCardDialog"
}