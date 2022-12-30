package pl.salo.stoneglish.presentation.core.cards.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.databinding.AddCardItemBinding
import pl.salo.stoneglish.domain.model.card.Card

class CreateModuleAdapter : RecyclerView.Adapter<CreateModuleAdapter.ViewHolder>() {

    var onItemClick: ((Card) -> Unit)? = null

    inner class ViewHolder(val binding: AddCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.originWord.setOnFocusChangeListener { _, b ->
                if (!b) {
                    val currentItem = items[adapterPosition]
                    currentItem.word = binding.originWord.text.toString()
                    onItemClick?.invoke(currentItem)
                }

            }
        }
    }


    lateinit var context: Context

    var items: MutableList<Card> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun insertItem(cardData: Card) {
        val index = items.size
        items.add(index, cardData)
        notifyItemInserted(index)
    }

    fun updateItem(cardData: Card) {
        val index = items.indexOf(cardData)
        items[index] = cardData
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            AddCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {

            if (item.word.isNotBlank()) {
                originWord.setText(item.word)
            } else {
                originWord.text.clear()
            }
            if (item.firstTranslation.isBlank()) translatedWord.text.clear()

            val adapter =
                ArrayAdapter(context, android.R.layout.simple_list_item_1, listOf(item.firstTranslation))
            translatedWord.setAdapter(adapter)
            translatedWord.setOnFocusChangeListener { _, b ->
                if (b && item.firstTranslation.isNotBlank()) translatedWord.showDropDown()

                if (!b) {
                    item.firstTranslation = translatedWord.text.toString()
                }
            }

            if (translatedWord.isFocused) {
                if (item.firstTranslation.isNotBlank()) translatedWord.showDropDown()
            }


        }
    }

    override fun getItemCount() = items.size

}
