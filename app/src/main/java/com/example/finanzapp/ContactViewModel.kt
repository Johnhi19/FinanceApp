package com.example.finanzapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzapp.objects.OutgoingsDao
import com.example.finanzapp.objects.OutgoingsEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class ContactViewModel(
    private val dao: OutgoingsDao
): ViewModel() {

    private val _state = MutableStateFlow(ContactState())
    private val _outgoings = dao.getAll()
    val state = combine(_state, _outgoings) { state, outgoings ->
        state.copy(
            outgoings = outgoings
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ContactState()
    )

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
                val value = state.value.value
                val description = state.value.description
                val date = state.value.date

                if (value.isEmpty() || description.isEmpty() || date.isEmpty()) {
                    return
                } else if (!value.matches(Regex("^[1-9][0-9]*(\\.\\d{0,2}|,\\d{0,2})?$"))) {
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