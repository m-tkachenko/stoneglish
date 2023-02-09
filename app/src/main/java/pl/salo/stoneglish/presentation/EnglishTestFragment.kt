package pl.salo.stoneglish.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.salo.stoneglish.databinding.FragmentEnglishTestBinding
import pl.salo.stoneglish.util.authNavigator
import pl.salo.stoneglish.util.coreNavigator

@AndroidEntryPoint
class EnglishTestFragment : Fragment() {

    private val viewModel: EnglishTestViewModel by viewModels()
    lateinit var binding: FragmentEnglishTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnglishTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.start()
        binding.backArrow.setOnClickListener {
            coreNavigator().goBack()
        }
        viewModel.questionCount.observe(viewLifecycleOwner) {
            binding.testCount.text = "${it.first}/${it.second}"
        }
        viewModel.onQuestionChanged.observe(viewLifecycleOwner) { question ->
            binding.question.text = question.question

            val listener = OnClickListener {
                val selectedAnswer = (it as TextView).text
                viewModel.next(selectedAnswer == question.correctAnswer)
            }

            val buttons = listOf(
                binding.firstAnswer,
                binding.secondAnswer,
                binding.thirdAnswer
            ).shuffled()

            buttons.forEach {
                it.setOnClickListener(listener)
            }

            buttons[0].text = question.correctAnswer
            buttons[1].text = question.firstIncorrectAnswer
            buttons[2].text = question.secondIncorrectAnswer
        }
        viewModel.onTestFinished.observe(viewLifecycleOwner) {
            binding.flowContainer.visibility = View.GONE
            binding.finishContainer.visibility = VISIBLE
            binding.finishText.text = it
        }
        binding.finishBtn.setOnClickListener {
            authNavigator().goToSignUpStepFour()
        }
    }
}