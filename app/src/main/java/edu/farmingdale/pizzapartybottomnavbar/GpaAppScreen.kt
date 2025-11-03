package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.DarkViolet
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Indigo
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Mauve
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Orchid
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.RebeccaPurple
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.TruePurple

@Preview
@Composable
fun GpaAppScreen() {

    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    // Declare variables for GPA result - no custom background color needed
    var gpa by remember { mutableStateOf("") }

    // Enable scrolling for landscape mode
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)  // Theme-aware background
            .verticalScroll(scrollState)  // Enable vertical scrolling
            .padding(44.dp), // Padding around the column
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //ToDo 4: fix formatting.
        TextField(
            value = grade1,
            onValueChange = { grade1 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                ),
            label = { Text("Course 1 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        TextField(
            value = grade2,
            onValueChange = { grade2 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                ),
            label = { Text("Course 2 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        TextField(
            value = grade3,
            onValueChange = { grade3 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                ),
            label = { Text("Course 3 Grade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Compute GPA Button
        Button(
            onClick = {
                val gpaVal = calGPA(grade1, grade2, grade3)
                if (gpaVal != null) {
                    gpa = gpaVal.toString()
                } else {
                    gpa = "Invalid input"
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Compute GPA")
        }

        // ToDo 5: add clear button
        Button(
            onClick = {
                // Clear all input fields
                grade1 = ""
                grade2 = ""
                grade3 = ""
                gpa = ""
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text("Clear")
        }

        if (gpa.isNotEmpty()) {
            Text(
                text = "GPA: $gpa",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

fun calGPA(grade1: String, grade2: String, grade3: String): Double? {
    return try {
        val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
        grades.average()
    } catch (e: NumberFormatException) {
        null
    }
}