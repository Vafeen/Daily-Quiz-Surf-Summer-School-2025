package ru.vafeen.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.vafeen.presentation.AnswerState
import ru.vafeen.presentation.R
import ru.vafeen.presentation.ui.screens.quiz_screen.QuizState
import ru.vafeen.presentation.ui.theme.QuestionNumberColor

@Composable
internal fun Question(
    state: QuizState.Quiz,
    chooseAnswer: (String) -> Unit,
    confirmAnswer: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            state.currentQuestion?.let { currentQuestion ->
                val passedSize = state.passedQuestions.size
                Text(
                    text = "Вопрос ${passedSize + 1} из ${passedSize + state.questions.size}",
                    color = QuestionNumberColor
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = currentQuestion.question,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                currentQuestion.allAnswers.forEach { answer ->
                    Answer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        text = answer, answerState = when {
                            answer == state.chosenAnswer && currentQuestion.chosenAnswer == null
                                -> AnswerState.Chosen

                            currentQuestion.chosenAnswer != null &&
                                    answer == currentQuestion.chosenAnswer &&
                                    answer == currentQuestion.correctAnswer
                                -> AnswerState.Correct

                            currentQuestion.chosenAnswer != null &&
                                    answer == currentQuestion.chosenAnswer &&
                                    answer != currentQuestion.correctAnswer
                                -> AnswerState.Incorrect

                            else -> AnswerState.Free
                        }, onClick = if (state.currentQuestion.chosenAnswer == null) {
                            { chooseAnswer(answer) }
                        } else null
                    )
                }
                RounderCornerButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = confirmAnswer,
                    enabled = state.chosenAnswer != null
                ) {
                    Text(
                        text = if (state.questions.size == 1)
                            stringResource(R.string.complete)
                        else stringResource(R.string.onward)
                    )
                }
            }
        }
    }
}