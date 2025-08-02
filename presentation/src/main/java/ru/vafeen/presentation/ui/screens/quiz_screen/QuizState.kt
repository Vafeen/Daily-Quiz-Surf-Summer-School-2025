package ru.vafeen.presentation.ui.screens.quiz_screen

import ru.vafeen.domain.models.QuizQuestion

/**
 * Состояния экрана викторины.
 *
 * Представляет все возможные состояния UI экрана викторины.
 * Используется в сочетании с MVI-паттерном для управления отображением экрана.
 */
internal sealed class QuizState {
    /**
     * Начальное состояние экрана перед стартом викторины.
     * Отображает стартовый экран с кнопкой начала.
     */
    data object Start : QuizState()

    /**
     * Состояние загрузки данных викторины.
     * Отображает индикатор загрузки.
     */
    data object Loading : QuizState()

    /**
     * Состояние ошибки при загрузке викторины.
     * Отображает сообщение об ошибке и возможность повторить загрузку.
     */
    data object Error : QuizState()

    /**
     * Успешное состояние с загруженными вопросами викторины.
     *
     * @property questions Список всех вопросов викторины
     * @property passedQuestions Список уже пройденных вопросов
     */
    data class Quiz(
        val questions: List<QuizQuestion> = listOf(),
        val passedQuestions: List<QuizQuestion> = listOf(),
    ) : QuizState()
}