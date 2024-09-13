package com.example.finanzapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzapp.objects.OutgoingsDao
import com.example.finanzapp.objects.OutgoingsEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ContactViewModel(
    private val dao: OutgoingsDao
): ViewModel() {

    val _state = MutableStateFlow(ContactState())
    val _outgoings = dao.getAll()

    fun onEvent(event: ContactEvent){
        when(event){
            is ContactEvent.DeleteOutgoing -> {
                viewModelScope.launch {
                    dao.deleteOutgoing(event.outgoingsEntry)
                }
            }
            ContactEvent.HideDialog -> {
                _state.value = _state.value.copy(
                    isDialogVisible = false
                )
            }
            ContactEvent.ShowDialog -> {
                _state.value = _state.value.copy(
                    isDialogVisible = true
                )
            }
            ContactEvent.SaveOutgoingsEntry -> {
                val value = _state.value.value
                val description = _state.value.description
                val date = _state.value.date

                if (!value.matches(Regex("^[1-9][0-9]*(\\.\\d{0,2}|,\\d{0,2})?$"))) {
                    return
                }

                val outgoingsEntry = OutgoingsEntry(
                    value = value,
                    description = description,
                    date = date
                )

                viewModelScope.launch {
                    dao.insertOutgoing(outgoingsEntry)
                    _state.value = _state.value.copy(
                        value = "",
                        description = "",
                        date = "",
                        isDialogVisible = false
                    )
                }
            }
            is ContactEvent.SetValue -> {
                _state.value = _state.value.copy(
                    value = event.value
                )
            }
            is ContactEvent.SetDescription -> {
                _state.value = _state.value.copy(
                    description = event.description
                )
            }
            is ContactEvent.SetDate -> {
                _state.value = _state.value.copy(
                    date = event.date
                )
            }
        }
    }
}