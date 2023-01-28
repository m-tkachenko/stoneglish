package pl.salo.stoneglish.presentation.core.cards.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.R
import pl.salo.stoneglish.databinding.CardsTestItemBinding
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.domain.model.card.TestType

class CardTestsAdapter(private val tests: List<TestForCards>) :
    RecyclerView.Adapter<CardTestsAdapter.TestsViewHolder>() {
    var onItemClick: ((TestForCards) -> Unit)? = null
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        context = parent.context
        val binding = CardsTestItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return TestsViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        with(holder.binding) {
            with(tests[position]) {
                testName.text = name.type
                testDescription.text = description

                icon =
                    if (name == TestType.MEMORIZATION) R.drawable.memorization_icon else R.drawable.cards_icon
                backgroundImage =
                    if (name == TestType.MEMORIZATION) R.drawable.memorization_background else R.drawable.cards_background
                testImageView.setImageDrawable(context.getDrawable(icon))
                testContainer.background = ContextCompat.getDrawable(context, backgroundImage)
            }
        }
    }

    override fun getItemCount() = tests.size

    inner class TestsViewHolder(val binding: CardsTestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.testItem.setOnClickListener {
                val currentItem = tests[adapterPosition]
                onItemClick?.invoke(currentItem)
            }
        }
    }
}