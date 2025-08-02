package ru.vafeen.presentation.ui.screens.quiz_screen

internal sealed class QuizIntent {
    data object BeginQuiz : QuizIntent()
    data object NavigateToHistory : QuizIntent()
}