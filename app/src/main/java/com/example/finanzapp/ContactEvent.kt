package com.example.finanzapp

import com.example.finanzapp.objects.OutgoingsEntry

sealed interface ContactEvent {
    object SaveOutgoingsEntry: ContactEvent
    data class SetValue(val value: String): ContactEvent
    data class SetDescription(val description: String): ContactEvent
    data class SetDate(val date: String): ContactEvent
    object ShowDialog: ContactEvent
    object HideDialog: ContactEvent
    data class DeleteOutgoing(val outgoingsEntry: OutgoingsEntry): ContactEvent
}