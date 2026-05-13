package com.syauqialfanzari0008.dailyexpense.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.syauqialfanzari0008.dailyexpense.data.model.Expense
import com.syauqialfanzari0008.dailyexpense.ui.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(
    expenseId: Int,
    onBack: () -> Unit,
    viewModel: ExpenseViewModel = viewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    val expense = expenses.find { it.id == expenseId }

    var title by remember { mutableStateOf(expense?.title ?: "") }
    var amount by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(expense?.category ?: "") }
    var note by remember { mutableStateOf(expense?.note ?: "") }

    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }
    var categoryError by remember { mutableStateOf(false) }

    LaunchedEffect(expense) {
        expense?.let {
            title = it.title
            amount = it.amount.toString()
            category = it.category
            note = it.note
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Pengeluaran") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false
                },
                label = { Text("Judul") },
                isError = titleError,
                supportingText = { if (titleError) Text("Judul tidak boleh kosong") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    amountError = false
                },
                label = { Text("Nominal (Rp)") },
                isError = amountError,
                supportingText = { if (amountError) Text("Nominal tidak valid") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = {
                    category = it
                    categoryError = false
                },
                label = { Text("Kategori") },
                isError = categoryError,
                supportingText = { if (categoryError) Text("Kategori tidak boleh kosong") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Catatan (opsional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    titleError = title.isBlank()
                    amountError = amount.isBlank() || amount.toDoubleOrNull() == null
                    categoryError = category.isBlank()

                    if (!titleError && !amountError && !categoryError) {
                        expense?.let {
                            viewModel.updateExpense(
                                it.copy(
                                    title = title.trim(),
                                    amount = amount.toDouble(),
                                    category = category.trim(),
                                    note = note.trim()
                                )
                            )
                        }
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}