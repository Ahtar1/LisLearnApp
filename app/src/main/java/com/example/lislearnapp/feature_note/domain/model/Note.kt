package com.example.lislearnapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lislearnapp.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val language: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
        val languages = listOf("gb","germany","france","italy","china")
    }

}

class InvalidNoteException(message: String): Exception(message)
