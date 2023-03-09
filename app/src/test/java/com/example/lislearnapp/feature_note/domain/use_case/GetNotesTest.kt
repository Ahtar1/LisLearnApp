package com.example.lislearnapp.feature_note.domain.use_case

import com.example.lislearnapp.feature_note.data.repository.FakeNoteRepository
import com.example.lislearnapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.lislearnapp.feature_note.domain.model.Note
import com.example.lislearnapp.feature_note.domain.util.NoteOrder
import com.example.lislearnapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest{

    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup(){
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()

        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(Note(
                title = c.toString(),
                content = c.toString(),
                timestamp = index.toLong(),
                color = index,
                language = c.toString()
            ))
        }
        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach {
                fakeNoteRepository.insertNote(it)
            }
        }

    }

    @Test
    fun `get notes, sorted by title ascending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes, sorted by date ascending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
        }
    }

    @Test
    fun `get notes, sorted by color ascending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    fun `get notes, sorted by language ascending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Language(OrderType.Ascending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].language).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes, sorted by title descending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Title(OrderType.Descending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get notes, sorted by date descending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Date(OrderType.Descending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
        }
    }

    @Test
    fun `get notes, sorted by color descending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Color(OrderType.Descending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }

    @Test
    fun `get notes, sorted by language descending`(){
        val notes = runBlocking {
            getNotes(NoteOrder.Language(OrderType.Descending)).first()
        }
        for (i in 0..notes.size - 2){
            assertThat(notes[i].language).isGreaterThan(notes[i + 1].language)
        }
    }
}