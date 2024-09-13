package com.example.finanzapp.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OutgoingsEntry(
    val value: String,
    val description: String,
    val date: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

