package pl.salo.stoneglish.presentation.core.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pl.salo.stoneglish.data.model.home.Topic
import pl.salo.stoneglish.databinding.CardTopicItemBinding

class HomeVerticalTopicsAdapter(
    private val context: Context
) : RecyclerView.Adapter<HomeVerticalTopicsAdapter.HomeTopicsHolder>() {
    var topicsList: List<Topic> = listOf()
    var onTopicClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopicsHolder {
        val binding = CardTopicItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return HomeTopicsHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTopicsHolder, position: Int) {
        with(holder.binding) {
            with(topicsList[position]) {
                topicCardTitle.text = this.title
                topicCardDescription.text = this.text

                Glide.with(context)
                    .load(this.imgUrl)
                    .transform(RoundedCorners(30))
                    .into(topicImage)

                topicCardLayout.setOnClickListener {
                    onTopicClick?.invoke(this.title)
                }
            }
        }
    }

    override fun getItemCount() = topicsList.size

    inner class HomeTopicsHolder(val binding: CardTopicItemBinding) : RecyclerView.ViewHolder(binding.root)
}