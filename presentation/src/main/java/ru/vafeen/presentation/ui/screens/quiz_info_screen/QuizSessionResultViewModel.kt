package ru.vafeen.presentation.ui.screens.quiz_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vafeen.domain.local_database.usecase.GetQuizSessionResultUseCase
import ru.vafeen.domain.models.QuizResult
import ru.vafeen.presentation.navigation.NavRootIntent
import ru.vafeen.presentation.navigation.SendRootIntent

@HiltViewModel(assistedFactory = QuizSessionResultViewModel.Factory::class)
internal class QuizSessionResultViewModel @AssistedInject constructor(
    @Assisted private val sessionId: Long,
    @Assisted private val sendRootIntent: SendRootIntent,
    private val getQuizSessionResultUseCase: GetQuizSessionResultUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(QuizSessionResultState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadResults()
        }
    }

    fun handleIntent(intent: QuizSessionResultIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (intent) {
                QuizSessionResultIntent.Back -> back()
                QuizSessionResultIntent.StartQuiz -> {
                    back()
                    back()
                }
            }
        }
    }

    private fun back() = sendRootIntent(NavRootIntent.Back)

    private suspend fun loadResults() {
        val result = getQuizSessionResultUseCase.invoke(sessionId)
        _state.update {
            it.copy(
                quizResult = result?.let { it1 -> QuizResult.getByCount(it1.countOfRightAnswers) },
                quizSessionResult = result
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            sessionId: Long,
            sendRootIntent: SendRootIntent
        ): QuizSessionResultViewModel
    }
}