package com.example.todo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.example.MyDividerItemDecoration
import com.example.todo.model.Note
import com.example.todo.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.note_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        noteViewModel = ViewModelProviders.of(this@MainActivity).get(NoteViewModel::class.java)


        fab.setOnClickListener {
            showNoteDialog(shouldUpdate = false, note = null)
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        notes_list_rv.layoutManager = layoutManager
        notes_list_rv.itemAnimator = DefaultItemAnimator()
        notes_list_rv.addItemDecoration(MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16))

        noteViewModel.allNotes.observe(this,
            Observer<List<Note>> { noteList ->
                if (noteList != null) {
                    val noteAdapter = NotesAdapter(noteList) { note: Note ->noteItemClicked(note)  }
                    notes_list_rv.adapter = noteAdapter
                }
            })

    }

    private fun showNoteDialog(shouldUpdate: Boolean, note: Note?) {
        val layoutInflater = LayoutInflater.from(applicationContext)
        val view = layoutInflater.inflate(R.layout.note_dialog, null)

        val alertDialogUserInput = AlertDialog.Builder(this@MainActivity)

        alertDialogUserInput.setView(view)
        val title =
            if (!shouldUpdate) getString(R.string.lbl_new_note_title) else getString(R.string.lbl_edit_note_title)
        view.dialog_title.text = title

        val btn = if (shouldUpdate) "Update" else "Save"
        alertDialogUserInput
            .setCancelable(false)
            .setPositiveButton(btn) { dialog, which -> }
            .setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.cancel()
                }
            })

        val alertDialog = alertDialogUserInput.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val inputNote = view.note.text.toString()
            if (TextUtils.isEmpty(inputNote)) {
                Toast.makeText(this@MainActivity, "Enter Note!", Toast.LENGTH_LONG).show()

            } else {
                alertDialog.dismiss()
            }

            if (shouldUpdate && note!=null) {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                noteViewModel.update(inputNote,currentDate, note.id!!)



            } else {


                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())

                noteViewModel.insert(Note(id = null, note = inputNote, timeStamp = currentDate))
                Toast.makeText(this@MainActivity,"inserted", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun noteItemClicked(note:Note){

//        Toast.makeText(this, note.note,Toast.LENGTH_LONG).show()
        showActionAsDialog(note)

    }

    private fun showActionAsDialog(note: Note){
        val colors = arrayOf<CharSequence>("Edit", "Delete")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose option")
        builder.setItems(colors) { dialog, which ->
            if (which == 0) {
                showNoteDialog(true, note)
            } else {
                noteViewModel.delete(note)
            }
        }
        builder.show()
    }

}