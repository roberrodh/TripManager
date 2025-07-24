package com.example.tripmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripmanager.data.Expense
import com.example.tripmanager.data.ExpenseSection
import com.example.tripmanager.data.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val expenseDao = db.expenseDao()

    // Función para añadir un gasto
    fun addExpense(amount: Double, description: String, sectionId: Int) {
        viewModelScope.launch {
            val expense = Expense(amount = amount, description = description, sectionId = sectionId)
            expenseDao.insertExpense(expense)
        }
    }

    // Función para borrar un gasto
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.delete(expense)
        }
    }

    // Función para obtener todos los gastos de una sección
    fun getExpensesBySection(sectionId: Int): Flow<List<Expense>> = expenseDao.getExpensesBySection(sectionId)

    // Función para añadir una nueva sección
    fun addSection(name: String) {
        viewModelScope.launch {
            val section = ExpenseSection(name = name)
            expenseDao.insertSection(section)
        }
    }

    // Función para borrar una sección
    fun deleteSection(section: ExpenseSection) {
        viewModelScope.launch {
            expenseDao.deleteSection(section)
        }
    }

    // Función para obtener todas las secciones
    fun getAllSections(): Flow<List<ExpenseSection>> = expenseDao.getAllSections()
}

