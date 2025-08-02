package ru.vafeen.presentation.ui.screens.quiz_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vafeen.domain.models.QuizQuestion
import ru.vafeen.domain.network.ResponseResult
import ru.vafeen.domain.network.usecase.GetQuizUseCase
import javax.inject.Inject

@HiltViewModel
internal class QuizViewModel @Inject constructor(
    private val getQuizUseCase: GetQuizUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<QuizState>(QuizState.Start)
    val state = _state.asStateFlow()
    fun handleIntent(intent: QuizIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (intent) {
                QuizIntent.BeginQuiz -> beginQuiz()
                QuizIntent.NavigateToHistory -> navigateToHistory()
                QuizIntent.ReturnToBeginning -> returnToBeginning()
                is QuizIntent.ChoseAnswer -> choseAnswer(intent.answer)
                QuizIntent.ConfirmChosenAnswer -> confirmAnswer()
                QuizIntent.TryAgain -> beginQuiz()
            }
        }
    }

    private fun confirmAnswer() {
        val state = _state.value
        if (state is QuizState.Quiz) {
            val currentQuestion = state.currentQuestion
            // в вопрос кладем выбранный ответ, чтобы показать, что пользователь сделал выбор
            if (currentQuestion != null) {
                if (currentQuestion.chosenAnswer == null) {
                    _state.update {
                        state.copy(currentQuestion = currentQuestion.copy(chosenAnswer = state.chosenAnswer))
                    }
                } else {
                    // если выбор уже сделан, показываем следующий вопрос
                    val newStateWithNewQuestions = state.copy(
                        questions = state.questions.filter {
                            it.question != state.currentQuestion.question
                        },
                        passedQuestions = state.currentQuestion.let { state.passedQuestions.plus(it) },
                    )
                    val newCurrentQuestion = newStateWithNewQuestions.questions.firstOrNull()
                    val newStateWithNewQuestionsAndAnswer = newStateWithNewQuestions.copy(
                        currentQuestion = newCurrentQuestion,
                        chosenAnswer = null
                    )
                    _state.update { newStateWithNewQuestionsAndAnswer }
                    if (newCurrentQuestion == null) {
                        // значит, вопросы закончились
                        val countOfRightQuestions = newStateWithNewQuestionsAndAnswer
                            .passedQuestions
                            .count { question -> question.chosenAnswer == question.correctAnswer }

                        // todo сделать сохранение результатов

                        _state.update {
                            QuizState.Result(
                                quizResult = QuizResult.getByCount(countOfRightQuestions)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun choseAnswer(answer: String) {
        val state = _state.value
        if (state is QuizState.Quiz) {
            _state.update { state.copy(chosenAnswer = answer) }
        }
    }

    private fun returnToBeginning() {
        _state.update { QuizState.Start }
    }

    private fun navigateToHistory() {
        // TODO (add navigation to history)
    }

    private suspend fun beginQuiz() {
        _state.update { QuizState.Loading }
//        delay(2000)
        when (val quizzes = getQuizUseCase.invoke()) {
            is ResponseResult.Success<List<QuizQuestion>> -> _state.update {
                QuizState.Quiz(
                    questions = quizzes.data,
                    currentQuestion = quizzes.data.firstOrNull()
                )
            }

            is ResponseResult.Error -> _state.update { QuizState.Error }
        }
    }
}