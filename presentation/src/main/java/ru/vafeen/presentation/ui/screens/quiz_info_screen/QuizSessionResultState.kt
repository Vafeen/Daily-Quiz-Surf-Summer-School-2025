package ru.vafeen.presentation.ui.screens.quiz_info_screen

import ru.vafeen.domain.models.QuizResult
import ru.vafeen.domain.models.QuizSessionResult

data class QuizSessionResultState(
    val quizResult: QuizResult? = null,
    val quizSessionResult: QuizSessionResult? = null,
)
