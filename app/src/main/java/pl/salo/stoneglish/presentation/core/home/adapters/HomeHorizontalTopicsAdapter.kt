package pl.salo.stoneglish.presentation.core.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.databinding.CardTopicItemBinding

class HomeHorizontalTopicsAdapter(
    val context: Context
): RecyclerView.Adapter<HomeHorizontalTopicsAdapter.HomeHorizontalTopicsHolder>() {
    var horizontalTopics: List<Topic> = listOf()
    var onHorizontalTopicClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHorizontalTopicsHolder {
        val binding = CardTopicItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return HomeHorizontalTopicsHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeHorizontalTopicsHolder, position: Int) {
        with(holder.binding) {
            with(horizontalTopics[position]) {
                topicCardTitle.text = this.title
                topicCardDescription.text = this.text

                Glide.with(context)
                    .load(this.imgUrl)
                    .transform(RoundedCorners(30))
                    .into(topicImage)

                topicCardLayout.setOnClickListener {
                    onHorizontalTopicClick?.invoke(position)
                }
            }
        }
    }

    override fun getItemCount() = horizontalTopics.size

    inner class HomeHorizontalTopicsHolder(val binding: CardTopicItemBinding) : RecyclerView.ViewHolder(binding.root)
}