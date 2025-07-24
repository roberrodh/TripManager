package com.example.tripmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.tripmanager.viewmodel.ExpenseViewModel
import com.example.tripmanager.data.Expense
import com.example.tripmanager.data.ExpenseSection
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddExpenseScreen(expenseViewModel)
        }
    }
}

@Composable
fun AddExpenseScreen(expenseViewModel: ExpenseViewModel) {
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var sectionName by remember { mutableStateOf(TextFieldValue("")) }
    var selectedSection by remember { mutableStateOf<ExpenseSection?>(null) }
    var expenses by remember { mutableStateOf(listOf<Expense>()) }
    var sections by remember { mutableStateOf(listOf<ExpenseSection>()) }
    var showError by remember { mutableStateOf(false) }

    // Función para añadir el gasto
    fun addExpense() {
        val amountValue = amount.text.toDoubleOrNull()
        if (amountValue != null && description.text.isNotBlank() && selectedSection != null) {
            expenseViewModel.addExpense(amountValue, description.text, selectedSection!!.id)
            // Limpiar los campos
            amount = TextFieldValue("")
            description = TextFieldValue("")
            showError = false
        } else {
            showError = true // Mostrar el error si los datos no son válidos
        }
    }

    // Función para eliminar un gasto
    fun deleteExpense(expense: Expense) {
        expenseViewModel.deleteExpense(expense)
    }

    // Función para añadir una nueva sección
    fun addSection() {
        if (sectionName.text.isNotBlank()) {
            expenseViewModel.addSection(sectionName.text)
            sectionName = TextFieldValue("") // Limpiar el campo de texto
        }
    }

    // Función para eliminar una sección
    fun deleteSection(section: ExpenseSection) {
        expenseViewModel.deleteSection(section)
    }

    // Recoger la lista de gastos
    LaunchedEffect(selectedSection) {
        selectedSection?.let { section ->
            expenseViewModel.getExpensesBySection(section.id).collect { list ->
                expenses = list
            }
        }
    }

    // Recoger la lista de secciones
    LaunchedEffect(Unit) {
        expenseViewModel.getAllSections().collect { list ->
            sections = list
        }
    }

    // Calcular el total de los gastos
    val totalAmount = expenses.sumOf { it.amount }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            // Entrada para el nombre de la nueva sección
            TextField(
                value = sectionName,
                onValueChange = { sectionName = it },
                label = { Text("Nombre de la nueva sección") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            // Botón para añadir la nueva sección
            Button(
                onClick = { addSection() },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Añadir Sección")
            }

            // Selector de sección
            sections.forEach { section ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { selectedSection = section }, modifier = Modifier.weight(1f)) {
                        Text(section.name)
                    }
                    IconButton(onClick = { deleteSection(section) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar Sección")
                    }
                }
            }

            // Entrada para el monto
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            // Entrada para la descripción
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Botón para añadir el gasto
            Button(
                onClick = { addExpense() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir Gasto")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Mostrar lista de gastos añadidos con LazyColumn para el scroll
        items(expenses) { expense ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Mostrar los detalles de cada gasto
                Text(text = "Cantidad: ${expense.amount}, Descripción: ${expense.description}")

                // Icono de eliminar para cada gasto
                IconButton(onClick = { deleteExpense(expense) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar Gasto")
                }
            }
        }

        item {
            // Mostrar el total de los gastos
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total: $totalAmount",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        }
    }
}
