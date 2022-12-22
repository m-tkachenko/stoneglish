package pl.salo.stoneglish.presentation.auth.sign_up.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.SignUpCategoryItemBinding
import pl.salo.stoneglish.domain.model.auth.SignUpCategoryItem

class SignUpCategoryAdapter() : RecyclerView.Adapter<SignUpCategoryAdapter.ViewHolder>() {

    var onItemClick: ((SignUpCategoryItem) -> Unit)? = null

    inner class ViewHolder(val binding: SignUpCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val currentItem = items[adapterPosition]
                currentItem.isFavorite = !currentItem.isFavorite
                onItemClick?.invoke(currentItem)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<SignUpCategoryItem>() {
        override fun areItemsTheSame(
            oldItem: SignUpCategoryItem,
            newItem: SignUpCategoryItem
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: SignUpCategoryItem,
            newItem: SignUpCategoryItem
        ): Boolean {
            return newItem == oldItem
        }


    }

    lateinit var context: Context
    private val differ = AsyncListDiffer(this, diffCallback)
    var items: List<SignUpCategoryItem>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            SignUpCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
         //   signUpCategoryImage.setImageResource(item.resource)
            signUpCategoryTitle.text = item.title
        }
        if(item.isFavorite){
            Glide.with(context)
                .load(R.drawable.ic_check)
                .into(holder.binding.signUpCategoryImage)
            holder.binding.signUpCard.setCardBackgroundColor(context.getColor(R.color.background_card_favorite))
        }else{
            Glide.with(context)
                .load(item.resource)
                .into(holder.binding.signUpCategoryImage)
            holder.binding.signUpCard.setCardBackgroundColor(context.getColor(R.color.background_card))
        }

    }

    override fun getItemCount() = items.size

}
