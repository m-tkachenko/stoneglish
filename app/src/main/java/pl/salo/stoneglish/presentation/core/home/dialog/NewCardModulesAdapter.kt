package pl.salo.stoneglish.presentation.core.home.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.databinding.ModuleItemInDialogBinding

class NewCardModulesAdapter(
    private val modulesList: List<Pair<Int, String>>,
    private val onAddModuleClicked: (String) -> Unit
) : RecyclerView.Adapter<NewCardModulesAdapter.NewCardModulesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCardModulesHolder {
        val binding = ModuleItemInDialogBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return NewCardModulesHolder(binding)
    }

    override fun onBindViewHolder(holder: NewCardModulesHolder, position: Int) {
        with(holder.binding) {
            with(modulesList[position]) {
                moduleName.text = this.second
            }
        }
    }

    override fun getItemCount() = modulesList.size

    inner class NewCardModulesHolder(val binding: ModuleItemInDialogBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onAddModuleClicked(modulesList[layoutPosition].second)
            }
        }
    }
}