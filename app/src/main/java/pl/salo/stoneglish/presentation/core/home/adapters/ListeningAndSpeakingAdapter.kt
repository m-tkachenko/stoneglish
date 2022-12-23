package pl.salo.stoneglish.presentation.core.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.R
import pl.salo.stoneglish.data.model.home.ListeningSpeaking
import pl.salo.stoneglish.databinding.ListeningSpeakingItemBinding

class ListeningAndSpeakingAdapter : RecyclerView.Adapter<ListeningAndSpeakingAdapter.ViewHolder>() {
    var onItemClick: ((ListeningSpeaking) -> Unit)? = null

    inner class ViewHolder(val binding: ListeningSpeakingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val listener = View.OnClickListener {
                startSpeaking()
            }

            binding.audioActionButton.setOnClickListener(listener)
            binding.originText.setOnClickListener(listener)
        }

        private fun startSpeaking(){
            val currentItem = items[adapterPosition]
            onItemClick?.invoke(currentItem)
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<ListeningSpeaking>() {
        override fun areItemsTheSame(
            oldItem: ListeningSpeaking,
            newItem: ListeningSpeaking
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: ListeningSpeaking,
            newItem: ListeningSpeaking
        ): Boolean {
            return newItem == oldItem
        }


    }

    lateinit var context: Context
    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<ListeningSpeaking>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ListeningSpeakingItemBinding.inflate(
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
            originText.setTextColor(textColor)

            originText.text = item.text
            translatedText.text = item.translatedText
        }
    }

    override fun getItemCount() = items.size
}