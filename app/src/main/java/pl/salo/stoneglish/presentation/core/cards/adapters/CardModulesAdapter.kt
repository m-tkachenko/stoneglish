package pl.salo.stoneglish.presentation.core.cards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.salo.stoneglish.databinding.CardsModuleItemBinding

class CardModulesAdapter(private val modules: List<String>)
    : RecyclerView.Adapter<CardModulesAdapter.ModulesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        val binding = CardsModuleItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ModulesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        with(holder) {
            with(modules[position]) {
                binding.moduleName.text = this
                binding.moduleItem.setOnClickListener {

                }
            }
        }
    }

    override fun getItemCount() = modules.size

    inner class ModulesViewHolder(val binding: CardsModuleItemBinding) : RecyclerView.ViewHolder(binding.root)
}