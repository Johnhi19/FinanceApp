package com.example.finanzapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.ContactEvent
import com.example.finanzapp.ContactState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ContactEvent.HideDialog) },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Button(onClick = {
                    onEvent(ContactEvent.SaveOutgoingsEntry)
                }) {
                    Text(text = "Save Outgoing")
                }
            }
        },
        title = {
            Text(text = "Add new outgoing")
        },
        text = {
           Column(
               verticalArrangement = Arrangement.spacedBy(16.dp),
           ) {
                TextField(
                    value = state.value,
                    onValueChange = {
                        onEvent(ContactEvent.SetValue(it))
                    },
                    placeholder = { Text("Amount") },
                )
               TextField(
                   value = state.description,
                   onValueChange = {
                       onEvent(ContactEvent.SetDescription(it))
                   },
                   placeholder = { Text("Description") },
               )
               TextField(
                   value = state.date,
                   onValueChange = {
                       onEvent(ContactEvent.SetDate(it))
                   },
                   placeholder = { Text("Date") },
               )
           }
        },
    )
}