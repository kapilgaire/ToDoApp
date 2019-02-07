package com.example.todo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName ="note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val note: String,

    val timeStamp: String

)