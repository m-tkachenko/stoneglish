package pl.salo.stoneglish.presentation.core.cards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentCreateModuleBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.TranslateViewModel
import pl.salo.stoneglish.presentation.core.cards.CreateModuleViewModel
import pl.salo.stoneglish.presentation.core.cards.adapters.CreateModuleAdapter
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class CreateModuleFragment : Fragment() {

    private val createModuleAdapter = CreateModuleAdapter()
    private val viewModel: CreateModuleViewModel by viewModels()
    private val translateViewModel: TranslateViewModel by viewModels()
    lateinit var binding: FragmentCreateModuleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        translateViewModel.translatedWord.observe(viewLifecycleOwner) {
            viewModel.updateCardData(it)
        }

        observeCards()
        observeLoading()
        observeToast()

        viewModel.finish.observe(viewLifecycleOwner) {
            coreNavigator().goToModules()
        }

        binding.backArrow.setOnClickListener {
            coreNavigator().goBack()
        }

        binding.wordsRecycler.adapter = createModuleAdapter
        createModuleAdapter.insertItem(Card())
        (binding.wordsRecycler.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.doneActionButton.setOnClickListener {
            viewModel.writeModule(createModuleAdapter.items, binding.moduleName.text.toString())
        }

        binding.addWordBtn.setOnClickListener {
            createModuleAdapter.insertItem(Card())
            binding.wordsRecycler.scrollToPosition(createModuleAdapter.items.size - 1)
            binding.appBar.setExpanded(false)
        }

        createModuleAdapter.onItemClick = {
            translateViewModel.translate(it.word)
            viewModel.setUpCard(it)
        }

    }

    private fun observeCards() {
        viewModel.cards.observe(viewLifecycleOwner) {
            createModuleAdapter.updateItem(it)
        }
    }

    private fun observeToast() {
        viewModel.toast.observe(viewLifecycleOwner) {
            coreNavigator().makeToast(it)
        }
    }

    private fun observeLoading() {
        viewModel.loading.observe(viewLifecycleOwner) {
            val visibility = if (it) View.VISIBLE else View.GONE
            binding.progressBar.visibility = visibility
        }
    }

}