package ru.vafeen.presentation.ui.screens.quiz_info_screen

internal sealed class QuizSessionResultIntent {
    data object Back : QuizSessionResultIntent()
    data object StartQuiz : QuizSessionResultIntent()
}