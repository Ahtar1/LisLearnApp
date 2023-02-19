package com.example.lislearnapp.feature_note.presentation.notes

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lislearnapp.feature_note.domain.model.Note
import com.example.lislearnapp.feature_note.domain.use_case.NoteUseCases
import com.example.lislearnapp.feature_note.domain.util.NoteOrder
import com.example.lislearnapp.feature_note.domain.util.OrderType
import com.example.lislearnapp.feature_note.presentation.add_edit_note.PlayBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private val _playBoxState= mutableStateOf(PlayBoxState())
    val playBoxState: State<PlayBoxState> = _playBoxState

    private var textToSpeech: TextToSpeech? = null

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    fun textToSpeech(context: Context,content:String, language: String) {
        _playBoxState.value = playBoxState.value.copy(
            isButtonEnabled = false,
        )
        textToSpeech = TextToSpeech(
            context
        ) {

            var lang= Locale.UK
            when(language){
                "france"-> lang= Locale.FRANCE
                "gb"-> lang= Locale.UK
                "germany"-> lang= Locale.GERMANY
                "italy"-> lang= Locale.ITALY
                "china"-> lang= Locale.CHINA
            }
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = lang
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        content,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }
            }

            _playBoxState.value = playBoxState.value.copy(
                isButtonEnabled = true
            )
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}
