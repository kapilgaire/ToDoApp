package com.example.todo.repo

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.todo.dao.NoteDao
import com.example.todo.model.Note

class NoteRepository(private val noteDao: NoteDao) {


    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note) {

        noteDao.insertNote(note)
    }

    @WorkerThread
    suspend fun update(noteContent: String, timeDateStamp: String, id: Int) {
        noteDao.updateNote(noteContent, timeDateStamp, id)
    }

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }


}