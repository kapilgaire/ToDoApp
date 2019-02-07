package com.example.todo

import java.text.SimpleDateFormat

fun String.formatDate(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val date = sdf.parse(this)
    val fmtOut = SimpleDateFormat("MMM d")
    return fmtOut.format(date)

}

