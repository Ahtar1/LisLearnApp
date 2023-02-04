package com.example.lislearnapp.feature_note.presentation.notes

import com.example.lislearnapp.feature_note.domain.model.Note
import com.example.lislearnapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object ToggleOrderSection: NotesEvent()
    object RestoreNote: NotesEvent()

}
