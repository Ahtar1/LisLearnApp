package com.example.lislearnapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import com.example.lislearnapp.feature_note.domain.util.NoteOrder

sealed class AddEditNoteEvent {

    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocused(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocused(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    data class ChangeLanguage(val language: String): AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}