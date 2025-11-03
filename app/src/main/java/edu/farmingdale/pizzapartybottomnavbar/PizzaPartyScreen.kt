package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Indigo
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Mauve
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.Orchid
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.RebeccaPurple
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.TruePurple

// ToDo 7

@Composable
fun PizzaPartyScreen(
    modifier: Modifier = Modifier,
    viewModel: PizzaPartyViewModel = viewModel()
) {
    // Collect state from ViewModel
    val numPeopleInput by viewModel.numPeopleInput.collectAsState()
    val hungerLevel by viewModel.hungerLevel.collectAsState()
    val totalPizzas by viewModel.totalPizzas.collectAsState()

    // Enable scrolling for landscape mode
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)  // Theme-aware background
            .verticalScroll(scrollState)  // Enable vertical scrolling
            .padding(16.dp)
    ) {
        Text(
            text = "Pizza Party",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,  // Theme-aware primary color
            modifier = modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface  // Theme-aware surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                NumberField(
                    labelText = "Number of people?",
                    textInput = numPeopleInput,
                    onValueChange = { viewModel.updateNumPeople(it) },
                    modifier = modifier.padding(bottom = 16.dp).fillMaxWidth()
                )
            }
        }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface  // Theme-aware surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                //ToDo 6
                RadioGroup(
                    labelText = "How hungry?",
                    radioOptions = listOf("Light", "Medium", "More than medium, but not very", "Very hungry"),
                    selectedOption = hungerLevel,
                    onSelected = { viewModel.updateHungerLevel(it) },
                    modifier = modifier
                )
            }
        }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer  // Theme-aware primary container
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Text(
                text = "Total pizzas: $totalPizzas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,  // Theme-aware text on primary container
                modifier = modifier.padding(16.dp)
            )
        }

        Button(
            onClick = { viewModel.calculateTotalPizzas() },
            modifier = modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,  // Theme-aware primary
                contentColor = MaterialTheme.colorScheme.onPrimary   // Theme-aware text on primary
            )
        ) {
            Text("Calculate", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textInput,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    Column {
        Text(
            text = labelText,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,  // Theme-aware text color
            fontSize = 16.sp
        )
        radioOptions.forEach { option ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    onClick = null,
                    modifier = modifier.padding(end = 8.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,      // Theme-aware selected
                        unselectedColor = MaterialTheme.colorScheme.outline     // Theme-aware unselected
                    )
                )
                Text(
                    text = option,
                    modifier = modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface  // Theme-aware text
                )
            }
        }
    }
}