package ru.vafeen.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vafeen.presentation.AnswerState
import ru.vafeen.presentation.R

@Composable
internal fun Answer(
    modifier: Modifier = Modifier,
    text: String, answerState: AnswerState,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(
                    id = when (answerState) {
                        AnswerState.Free -> R.drawable.free_circle
                        AnswerState.Chosen -> R.drawable.chosen_circle
                        AnswerState.Correct -> R.drawable.correct_circle
                        AnswerState.Incorrect -> R.drawable.incorrect_circle
                    }
                ),
                contentDescription = stringResource(R.string.answer_state)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, fontSize = 14.sp)
        }
    }
}

