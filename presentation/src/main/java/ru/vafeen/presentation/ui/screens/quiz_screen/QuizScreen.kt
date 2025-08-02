package ru.vafeen.presentation.ui.screens.quiz_screen

import RadialGradientLoader
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.vafeen.presentation.R
import ru.vafeen.presentation.ui.components.RounderCornerButton

@Composable
internal fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state is QuizState.Start ||
                    state is QuizState.Error
                ) {
                    Button(onClick = {
                        viewModel.handleIntent(QuizIntent.NavigateToHistory)
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                        Text(
                            stringResource(R.string.history),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(R.drawable.history),
                            contentDescription = stringResource(R.string.history),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            if (state is QuizState.Start ||
                state is QuizState.Error ||
                state is QuizState.Loading
            ) {
                Icon(
                    painter = painterResource(R.drawable.daily_quiz),
                    contentDescription = stringResource(R.string.daily_quiz)
                )
            }
            if (state is QuizState.Error || state is QuizState.Start) {
                Welcome {
                    viewModel.handleIntent(QuizIntent.BeginQuiz)
                }
            }
            if (state is QuizState.Error) {
                Error()
            }
            if (state is QuizState.Loading) {
                LoadingQuiz()
            }
        }
    }
}

@Composable
private fun LoadingQuiz() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(121.dp))
        RadialGradientLoader()
    }
}

@Composable
private fun Error() {
    Text(
        modifier = Modifier.padding(top = 24.dp),
        text = stringResource(R.string.error_try_again), fontSize = 20.sp,
        color = Color.White
    )
}

@Composable
private fun Welcome(onBegin: () -> Unit) {
    Column(modifier = Modifier.padding(top = 40.dp)) {
        Card(shape = RoundedCornerShape(46.dp)) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.welcome_to_dailyquiz),
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                RounderCornerButton(onClick = onBegin) {
                    Text(
                        stringResource(R.string.start_the_quiz).uppercase(),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}