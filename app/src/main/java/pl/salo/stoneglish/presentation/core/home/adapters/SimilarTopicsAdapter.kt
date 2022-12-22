package pl.salo.stoneglish.presentation.core.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.salo.stoneglish.data.model.home.SimilarTopic
import pl.salo.stoneglish.databinding.SimilarTopicsItemBinding

class SimilarTopicsAdapter : RecyclerView.Adapter<SimilarTopicsAdapter.ViewHolder>() {
    var onItemClick: ((SimilarTopic) -> Unit)? = null

    inner class ViewHolder(val binding: SimilarTopicsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val currentItem = items[adapterPosition]
                onItemClick?.invoke(currentItem)
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<SimilarTopic>() {
        override fun areItemsTheSame(
            oldItem: SimilarTopic,
            newItem: SimilarTopic
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: SimilarTopic,
            newItem: SimilarTopic
        ): Boolean {
            return newItem == oldItem
        }


    }

    lateinit var context: Context
    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<SimilarTopic>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            SimilarTopicsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            topicTitle.text = item.title
            Glide.with(context)
                .load(item.imgUrl)
                .centerCrop()
                .into(topicImage)
        }
    }

    override fun getItemCount() = items.size
}