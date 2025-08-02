package ru.vafeen.presentation.ui.screens.quiz_screen

import ru.vafeen.domain.models.QuizQuestion

internal sealed class QuizState {
    data object Start : QuizState()
    data object Loading : QuizState()
    data object Error : QuizState()
    data class Success(
        val questions: List<QuizQuestion> = listOf(),
        val passedQuestions: List<QuizQuestion> = listOf(),
    ) : QuizState()
}