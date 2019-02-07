package com.example.todo.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.todo.database.AppDatabase
import com.example.todo.model.Note
import com.example.todo.repo.NoteRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

 class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val noteRepository: NoteRepository

    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = AppDatabase.getAppDatabase(application)!!.noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.allNotes
    }


    fun insert(note: Note) = scope.launch(Dispatchers.IO) {
        noteRepository.insert(note)
    }

     fun update(noteContent: String ,timeDateStamp:String, id:Int)= scope.launch(Dispatchers.IO){
         noteRepository.update(noteContent,timeDateStamp,id)
     }

     fun delete(note: Note)= scope.launch(Dispatchers.IO){
         noteRepository.delete(note)

     }


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}