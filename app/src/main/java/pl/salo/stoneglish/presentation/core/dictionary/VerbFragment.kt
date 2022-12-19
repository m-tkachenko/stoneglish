package pl.salo.stoneglish.presentation.core.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentVerbBinding
import pl.salo.stoneglish.util.getVerb

class VerbFragment : Fragment() {

    lateinit var binding: FragmentVerbBinding
    private val viewModel: DictionaryViewModel by viewModels({ requireParentFragment() })
    lateinit var dictionaryAdapter: DictionaryDefinitionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dictionaryAdapter = DictionaryDefinitionAdapter()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.verbRecyclerView.apply {
            adapter = dictionaryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            viewModel.wordData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        dictionaryAdapter.items = it.data?.meanings?.getVerb() ?: listOf()
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

}