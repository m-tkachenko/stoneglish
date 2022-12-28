package pl.salo.stoneglish.presentation.core.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.CardTopicItemBinding

class HomeTopicsAdapter : RecyclerView.Adapter<HomeTopicsAdapter.HomeTopicsHolder>() {
    var topicsList: List<String> = listOf()
    var onTopicClick: (() -> Unit)? = null

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
                topicCardTitle.text = this
                topicCardDescription.text = "Rok - to muzyka, kor - to kora, ork - to nie człowiek"
                topicImage.setImageResource(R.drawable.me)

                topicCardLayout.setOnClickListener {
                    onTopicClick?.invoke()
                }
            }
        }
    }

    override fun getItemCount() = topicsList.size

    inner class HomeTopicsHolder(val binding: CardTopicItemBinding) : RecyclerView.ViewHolder(binding.root)
}