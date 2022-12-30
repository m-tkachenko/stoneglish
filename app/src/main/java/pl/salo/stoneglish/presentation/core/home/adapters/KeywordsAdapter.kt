package pl.salo.stoneglish.presentation.core.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.R
import pl.salo.stoneglish.data.model.home.Keyword
import pl.salo.stoneglish.databinding.KeywordsItemBinding

class KeywordsAdapter : RecyclerView.Adapter<KeywordsAdapter.ViewHolder>() {
    var onItemClick: ((Keyword) -> Unit)? = null

    inner class ViewHolder(val binding: KeywordsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val listener = View.OnClickListener {
                startSpeaking()
            }

            binding.audioActionButton.setOnClickListener(listener)
            binding.originWord.setOnClickListener(listener)
            binding.phoneticWord.setOnClickListener(listener)
        }

        private fun startSpeaking(){
            val currentItem = items[adapterPosition]
            onItemClick?.invoke(currentItem)
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(
            oldItem: Keyword,
            newItem: Keyword
        ): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(
            oldItem: Keyword,
            newItem: Keyword
        ): Boolean {
            return newItem == oldItem
        }


    }

    lateinit var context: Context
    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<Keyword>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            KeywordsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {

            val color = if(item.isSpeaking) R.color.main_orange else R.color.text_color
            val textColor = context.getColor(color)
            originWord.setTextColor(textColor)
            phoneticWord.setTextColor(textColor)

            originWord.text = item.word
            phoneticWord.text = item.phonetic
            translatedWord.text = item.translate
        }
    }

    override fun getItemCount() = items.size
}