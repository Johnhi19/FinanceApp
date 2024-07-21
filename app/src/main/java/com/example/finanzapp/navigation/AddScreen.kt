package com.example.finanzapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finanzapp.R
import com.example.finanzapp.objects.OutgoingsEntry
import java.util.Calendar
import kotlin.math.pow
import kotlin.math.round

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController){
    val kc = LocalSoftwareKeyboardController.current
    var description by remember { mutableStateOf("") }
    var outgoing by remember { mutableStateOf("") }
    var totalOutgoings by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("") }
    val callback = {
        result = try {
            val num = outgoing.toFloat()
            num.pow(2.0F).toString()
        } catch (ex: NumberFormatException) {
            ""
        }
        kc?.hide()
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.total_outgoings) + " " + totalOutgoings + "€")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = outgoing,
                onValueChange = { text ->
                    if (text.matches(Regex("^[1-9][0-9]*(\\.\\d{0,2}|,\\d{0,2})?$"))) {
                        outgoing = text
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        callback()
                    }
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { text ->
                    description = text
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        callback()
                    }
                ),
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (outgoing.contains(",")) {
                    outgoing = outgoing.replace(",", ".")
                }
                if (outgoing.isNotBlank()) {
                    totalOutgoings =
                        (round(totalOutgoings.toDouble() + outgoing.toDouble() * 100) / 100).toString()
                }
                navController.navigate(Screen.MainScreen.withArgs(outgoing, description))
                outgoing = ""
                description = ""
            }) {
                Text(text = "Add")
            }
        }
    }
}