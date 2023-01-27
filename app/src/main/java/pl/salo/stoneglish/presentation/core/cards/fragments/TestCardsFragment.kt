package pl.salo.stoneglish.presentation.core.cards.fragments

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.salo.stoneglish.R
import pl.salo.stoneglish.common.Resource
import pl.salo.stoneglish.databinding.FragmentTestCardsBinding
import pl.salo.stoneglish.domain.model.card.Card
import pl.salo.stoneglish.presentation.core.cards.CardsViewModel
import pl.salo.stoneglish.util.coreNavigator

class TestCardsFragment : Fragment() {

    private lateinit var binding: FragmentTestCardsBinding
    private val cardsViewModel: CardsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestCardsBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager2 = binding.viewPager

        cardsViewModel.cardsDownloadState.observe(viewLifecycleOwner) {
            if (it is Resource.Success) {
                val cards = it.data ?: emptyList()
                if (cards.isEmpty()) coreNavigator().goBack()
                val viewPager2Adapter = ViewPager2Adapter(
                    requireContext(), cards
                )
                viewPager2.adapter = viewPager2Adapter
            } else {
                coreNavigator().goBack()
            }
        }

        binding.backArrow.setOnClickListener {
            coreNavigator().goBack()
        }


    }
}

internal class ViewPager2Adapter
    (private val ctx: Context, private val cards: List<Card>) :
    RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {

    private var isAnim = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.card_test_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = cards[position % cards.size]

        holder.front.text = if (holder.isFront) item.word else item.firstTranslation
        holder.back.text = if (!holder.isFront) item.word else item.firstTranslation

        val cardBack = holder.back
        val cardFront = holder.front
        val scale = ctx.applicationContext.resources.displayMetrics.density
        cardBack.cameraDistance = 8000 * scale
        cardFront.cameraDistance = 8000 * scale

        val fAnim = AnimatorInflater.loadAnimator(
            ctx.applicationContext,
            R.animator.front_animator
        ) as AnimatorSet
        val bAnim = AnimatorInflater.loadAnimator(
            ctx.applicationContext,
            R.animator.back_animator
        ) as AnimatorSet

        holder.container.setOnClickListener {
            if (!isAnim) {
                isAnim = true
                holder.isFront = if (holder.isFront) {
                    fAnim.setTarget(cardFront)
                    bAnim.setTarget(cardBack)
                    fAnim.start()
                    bAnim.start()
                    false

                } else {
                    fAnim.setTarget(cardBack)
                    bAnim.setTarget(cardFront)
                    bAnim.start()
                    fAnim.start()
                    true

                }
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    isAnim = false
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var isFront = true
        var front: TextView
        var back: TextView
        var container: ConstraintLayout

        init {
            front = itemView.findViewById(R.id.card_front)
            back = itemView.findViewById(R.id.card_back)
            container = itemView.findViewById(R.id.cardsContainer)
        }
    }
}
