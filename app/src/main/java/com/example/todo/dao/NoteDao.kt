package com.example.todo.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.todo.model.Note
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Query("update note Set note=:noteContent , timeStamp = :timeDateStamp where id=:id ")
    fun updateNote(noteContent: String ,timeDateStamp:String, id:Int)

    @Delete
    fun deleteNote(note: Note)

    @Query("Select * From Note")
    fun getAllNotes(): LiveData<List<Note>>


}