/*
 * Designed and developed by "옴마야" Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/mash-up-kr/WeQuiz-Android/blob/main/LICENSE
 */

package team.ommaya.wequiz.android.quiz.create.viewholder

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View.GONE
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.launch
import team.ommaya.wequiz.android.databinding.ItemQuizCreateQuizBinding
import team.ommaya.wequiz.android.design.resource.R
import team.ommaya.wequiz.android.quiz.create.Question
import team.ommaya.wequiz.android.quiz.create.QuizCreateViewModel
import team.ommaya.wequiz.android.quiz.create.adapter.AnswerAdapter

class QuestionViewHolder(
    private val binding: ItemQuizCreateQuizBinding,
    private val viewModel: QuizCreateViewModel,
    private val lifecycle: Lifecycle,
    private val context: Context,
    private val onQuestionItemClickListener: (Int, Boolean) -> Unit,
) : ViewHolder(binding.root) {

    private lateinit var answerAdapter: AnswerAdapter

    fun bind(item: Question, position: Int) {
        binding.apply {
            answerAdapter = AnswerAdapter(
                viewModel,
                lifecycle,
                item,
                context,
            ).apply {
                submitList(viewModel.getAnswerList(position))
            }
            rvAnswerList.adapter = answerAdapter
        }

        collectFlows(item)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun collectFlows(item: Question) {
        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.questionList.collect {
                        val currentItem = viewModel.getSyncedQuestion(item)
                        val position = viewModel.getQuestionItemPosition(item)
                        with(binding) {
                            rvAnswerList.isVisible = currentItem.isFocus
                            ivMultipleChoice.isVisible = currentItem.isFocus
                            tvMultipleChoice.isVisible = currentItem.isFocus

                            val multipleChoiceIconRes = if (currentItem.isMultipleChoice) {
                                R.drawable.ic_circle_success
                            } else {
                                R.drawable.ic_checkmark
                            }
                            ivMultipleChoice.setImageDrawable(
                                context.getDrawable(
                                    multipleChoiceIconRes
                                )
                            )
                        }
                        answerAdapter.submitList(viewModel.getAnswerList(position))
                        initListener(item)
                    }
                }
                launch {
                    viewModel.isEditMode.collect { isEditMode ->
                        with(binding) {
                            root.isClickable = !isEditMode
                            etQuizTitle.isEnabled = !isEditMode
                            ivDeleteQuestion.visibility = if (isEditMode) VISIBLE else GONE
                        }
                    }
                }
            }
        }
    }

    private fun initListener(item: Question) {
        with(viewModel) {
            with(binding) {
                with(etQuizTitle) {
                    hint = item.title
                    setOnFocusChangeListener { _, hasFocus ->
                        if (hasFocus) {
                            Log.d(
                                "클릭",
                                "question ${getQuestionItemPosition(getSyncedQuestion(item))} 클릭 "
                            )
                            setQuestionFocus(getQuestionItemPosition(getSyncedQuestion(item)))
                            onQuestionItemClickListener(
                                getQuestionItemPosition(
                                    getSyncedQuestion(
                                        item
                                    )
                                ), true
                            )
                        }
                        ivQuestionTitleDelete.visibility = if (hasFocus) VISIBLE else GONE
                    }
                    doOnTextChanged { text, _, _, _ ->
                    }
                }
                ivQuestionTitleDelete.setOnClickListener {
                    etQuizTitle.text.clear()
                }
                root.setOnClickListener {
                    Log.d("클릭", "question ${getQuestionItemPosition(getSyncedQuestion(item))} 클릭 ")
                    setQuestionFocus(getQuestionItemPosition(getSyncedQuestion(item)), true)
                    onQuestionItemClickListener(
                        getQuestionItemPosition(getSyncedQuestion(item)),
                        false
                    )
                }
                vMultipleChoice.setOnClickListener {
                    setMultipleChoice(getQuestionItemPosition(getSyncedQuestion(item)))
                }
                ivDeleteQuestion.setOnClickListener {
                    deleteQuestion(item)
                }
            }
        }
    }
}
