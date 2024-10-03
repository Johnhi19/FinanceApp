package com.example.finanzapp.navigation

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.finanzapp.ContactEvent
import com.example.finanzapp.ContactState
import com.example.finanzapp.objects.OutgoingsEntry
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun groupOutgoingsByMonth(outgoings: List<OutgoingsEntry>): Map<String, List<OutgoingsEntry>> {
    val formatMonthYear = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return outgoings.groupBy { outgoing ->
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(outgoing.date)
        formatMonthYear.format(date)
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = sdf.format(calendar.time)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ContactEvent.SetDate(currentDate))
                onEvent(ContactEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new outgoing")
            }
        }
    ) { padding ->
        if (state.isDialogVisible) {
            AddScreen(
                state = state,
                onEvent = onEvent
            )
        }
        val groupedOutgoings = groupOutgoingsByMonth(state.outgoings.reversed())
        val listState = rememberLazyListState()
        val total = state.outgoings.sumOf { it.value.toDouble() }
        val formattedTotal = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US)).format(total)

        LazyColumn(
            state = listState,
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            groupedOutgoings.forEach { (month, outgoings) ->
                stickyHeader {
                    Text(
                        text = month + " - " + formattedTotal + "€",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    )
                    Divider(thickness = 3.dp)
                }
                items(state.outgoings.reversed()) { currentOutgoing ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = currentOutgoing.value + "€",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Text(
                                text = currentOutgoing.description,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(0.5f)
                        ) {
                            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                            val date = inputFormat.parse(currentOutgoing.date)
                            val formattedDate = outputFormat.format(date)

                            Text(
                                text = formattedDate,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                        IconButton(onClick = {
                            onEvent(ContactEvent.DeleteOutgoing(currentOutgoing))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete outgoing")
                        }
                    }
                    Divider()
                }
            }
        }
    }
}