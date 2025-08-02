package ru.vafeen.presentation.ui.screens.quiz_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuizViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<QuizState>(QuizState.Start)
    val state = _state.asStateFlow()
    fun handleIntent(intent: QuizIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (intent) {
                QuizIntent.BeginQuiz -> beginQuiz()
                QuizIntent.NavigateToHistory -> navigateToHistory()
            }
        }
    }

    private fun navigateToHistory() {
        // TODO (add navigation to history)
    }

    private suspend fun beginQuiz() {
        _state.update { QuizState.Loading }
        delay(2000)
        _state.update { QuizState.Error }
    }
}