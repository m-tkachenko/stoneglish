package pl.salo.stoneglish.presentation.core.cards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.databinding.CardTranslationItemBinding
import pl.salo.stoneglish.domain.model.card.Card

class CardsTranslationsAdapter(private val cards: List<Card>)
    : RecyclerView.Adapter<CardsTranslationsAdapter.CardsViewHolder>() {
    var onItemClick: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val binding = CardTranslationItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        with(holder.binding) {
            with(cards[position]) {
                originWord.text = word
                translatedWord.text = firstTranslation


            }
        }
    }

    override fun getItemCount() = cards.size

    inner class CardsViewHolder(val binding: CardTranslationItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val currentItem = cards[adapterPosition]
                onItemClick?.invoke(currentItem.word)
            }
        }
    }
}