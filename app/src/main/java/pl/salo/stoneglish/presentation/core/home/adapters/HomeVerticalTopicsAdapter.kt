package pl.salo.stoneglish.presentation.core.home.adapters

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
) : RecyclerView.Adapter<HomeVerticalTopicsAdapter.HomeVerticalTopicsHolder>() {
    var verticalTopicsList: List<Topic> = listOf()
    var onVerticalTopicClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVerticalTopicsHolder {
        val binding = CardTopicItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return HomeVerticalTopicsHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeVerticalTopicsHolder, position: Int) {
        with(holder.binding) {
            with(verticalTopicsList[position]) {
                topicCardTitle.text = this.title
                topicCardDescription.text = this.text

                Glide.with(context)
                    .load(this.imgUrl)
                    .transform(RoundedCorners(30))
                    .into(topicImage)

                topicCardLayout.setOnClickListener {
                    onVerticalTopicClick?.invoke(position)
                }
            }
        }
    }

    override fun getItemCount() = verticalTopicsList.size

    inner class HomeVerticalTopicsHolder(val binding: CardTopicItemBinding) : RecyclerView.ViewHolder(binding.root)
}