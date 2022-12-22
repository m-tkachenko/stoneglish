package pl.salo.stoneglish.presentation.core.cards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.databinding.CardsTestItemBinding
import pl.salo.stoneglish.domain.model.card.TestForCards
import pl.salo.stoneglish.util.Utils.getTopicIcon

class CardTestsAdapter(private val tests: List<TestForCards>)
    : RecyclerView.Adapter<CardTestsAdapter.TestsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        val binding = CardsTestItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return TestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        with(holder.binding) {
            with(tests[position]) {
                testName.text = name
                testDescription.text = description

                testImageView.setImageResource(name.getTopicIcon())

                testItem.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount() = tests.size

    inner class TestsViewHolder(val binding: CardsTestItemBinding) : RecyclerView.ViewHolder(binding.root)
}