package ru.vafeen.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

@Composable
internal fun RounderCornerButton(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Button(onClick = onClick, content = content)
}