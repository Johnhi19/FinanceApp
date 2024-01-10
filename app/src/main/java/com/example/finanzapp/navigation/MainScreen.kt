package com.example.finanzapp.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finanzapp.objects.OutgoingsEntry
import java.util.Calendar

@Composable
fun MainScreen(navController: NavController, outgoing: String, description: String) {
    var outgoings by remember { mutableStateOf(listOf<OutgoingsEntry>()) }
    outgoings = outgoings + OutgoingsEntry(outgoing, description, Calendar.getInstance())
    LazyColumn {
        items(outgoings) { currentOutgoing ->
            Text(
                text = currentOutgoing.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Divider()
        }
    }
}