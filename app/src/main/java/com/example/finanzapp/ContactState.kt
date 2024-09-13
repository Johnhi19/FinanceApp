package com.example.finanzapp

import com.example.finanzapp.objects.OutgoingsEntry
import java.util.Calendar

data class ContactState(
    val outgoings: List<OutgoingsEntry> = emptyList(),
    val value: String = "",
    val description: String = "",
    val date: String = "",
    val isDialogVisible: Boolean = false,

)
