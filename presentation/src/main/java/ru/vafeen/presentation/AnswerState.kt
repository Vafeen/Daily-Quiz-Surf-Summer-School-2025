package ru.vafeen.presentation

import ru.vafeen.presentation.AnswerState.Chosen
import ru.vafeen.presentation.AnswerState.Correct
import ru.vafeen.presentation.AnswerState.Free
import ru.vafeen.presentation.AnswerState.Incorrect


/**
 * Перечисление состояний ответа в викторине.
 *
 * @property Free Ответ ещё не выбран пользователем.
 * @property Chosen Ответ выбран пользователем, но ещё не проверен.
 * @property Correct Ответ выбран и подтверждён как правильный.
 * @property Incorrect Ответ выбран и подтверждён как неправильный.
 */
internal enum class AnswerState {
    Free,
    Chosen,
    Correct,
    Incorrect
}
