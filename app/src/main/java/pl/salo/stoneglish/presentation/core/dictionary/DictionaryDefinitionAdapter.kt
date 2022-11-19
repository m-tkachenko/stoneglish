package pl.salo.stoneglish.presentation.core.dictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.data.model.dictionary.Definition
import pl.salo.stoneglish.databinding.WordDefinitionItemBinding

class DictionaryDefinitionAdapter : RecyclerView.Adapter<DictionaryDefinitionAdapter.ViewHolder>() {

    var onItemClick: (() -> Unit)? = null

    inner class ViewHolder(val binding: WordDefinitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
              //TODO
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Definition>() {
        override fun areItemsTheSame(
            oldItem: Definition,
            newItem: Definition
        ): Boolean {
            return oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(
            oldItem: Definition,
            newItem: Definition
        ): Boolean {
            return newItem == oldItem
        }


    }

    lateinit var context: Context
    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<Definition>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            WordDefinitionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            wordDefinition.text = item.definition
        }

    }

    override fun getItemCount() = items.size

}