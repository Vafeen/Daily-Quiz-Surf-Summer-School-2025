package ru.vafeen.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Кастомная кнопка с закругленными углами.
 *
 * Является оберткой над стандартной Button из Material3 с предустановленными скругленными углами.
 *
 * @property onClick Лямбда-функция, вызываемая при нажатии на кнопку
 * @property modifier Модификатор для настройки внешнего вида и поведения кнопки
 * @property content Содержимое кнопки (обычно текст и/или иконка)
 */
@Composable
internal fun RounderCornerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        content = content
    )
}