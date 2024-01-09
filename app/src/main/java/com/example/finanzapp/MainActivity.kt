package com.example.finanzapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finanzapp.objects.OutgoingsEntry
import com.example.finanzapp.ui.theme.FinanzAppTheme
import java.time.LocalDate
import java.util.Calendar
import kotlin.math.pow
import kotlin.math.round

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanzAppTheme {
                val kc = LocalSoftwareKeyboardController.current
                var description by remember { mutableStateOf("") }
                var outgoing by remember { mutableStateOf("") }
                var outgoings by remember { mutableStateOf(listOf<OutgoingsEntry>()) }
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
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = outgoing,
                            onValueChange = {text ->
                                if (text.matches(Regex("^[1-9][0-9]*(\\.\\d{0,2}|,\\d{0,2})?$"))){
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
                        Button(onClick = {
                            if (outgoing.contains(",")){
                                outgoing = outgoing.replace(",", ".")
                            }
                            if(outgoing.isNotBlank()) {
                                outgoings = outgoings + OutgoingsEntry(outgoing, description, Calendar.getInstance())
                                totalOutgoings = (round(totalOutgoings.toDouble() + outgoing.toDouble()*100)/100).toString()
                                outgoing = ""
                            }
                        }) {
                            Text(text = "Add")
                        }
                    }
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
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(name) }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

 @Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinanzAppTheme {
        Greeting("Android")
    }
}