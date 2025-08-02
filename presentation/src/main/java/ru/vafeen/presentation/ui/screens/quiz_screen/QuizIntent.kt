package ru.vafeen.presentation.ui.screens.quiz_screen

/**
 * Стейт-интенты для экрана викторины.
 *
 * Представляет все возможные пользовательские действия (интенты),
 * которые могут быть отправлены на экране викторины.
 */
internal sealed class QuizIntent {
    /**
     * Интент начала викторины.
     * Отправляется при нажатии на кнопку "Начать викторину".
     */
    data object BeginQuiz : QuizIntent()

    /**
     * Интент перехода к экрану истории.
     * Отправляется при нажатии на кнопку перехода к истории викторин.
     */
    data object NavigateToHistory : QuizIntent()
}