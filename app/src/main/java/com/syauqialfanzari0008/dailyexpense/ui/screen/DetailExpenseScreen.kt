package com.syauqialfanzari0008.dailyexpense.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.syauqialfanzari0008.dailyexpense.data.model.Expense
import com.syauqialfanzari0008.dailyexpense.ui.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailExpenseScreen(
    expenseId: Int,
    onBack: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: ExpenseViewModel = viewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    val expense = expenses.find { it.id == expenseId }
    val showDeleteDialog = remember { mutableStateOf(false) }

    val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))
    val dateFormatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID"))

    DeleteConfirmationDialog(
        show = showDeleteDialog.value,
        onConfirm = {
            expense?.let { viewModel.deleteExpense(it.id) }
            showDeleteDialog.value = false
            onBack()
        },
        onDismiss = { showDeleteDialog.value = false }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Pengeluaran") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        @Suppress("DEPRECATION")
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { expense?.let { onEditClick(it.id) } }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog.value = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus")
                    }
                }
            )
        }
    ) { innerPadding ->
        expense?.let {
            DetailContent(
                expense = it,
                innerPadding = innerPadding,
                formatter = formatter,
                dateFormatter = dateFormatter
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Hapus Pengeluaran") },
            text = { Text("Yakin ingin menghapus pengeluaran ini?") },
            confirmButton = {
                TextButton(onClick = onConfirm) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Batal") }
            }
        )
    }
}

@Composable
private fun DetailContent(
    expense: Expense,
    innerPadding: PaddingValues,
    formatter: NumberFormat,
    dateFormatter: SimpleDateFormat
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Judul", style = MaterialTheme.typography.labelMedium)
                Text(expense.title, fontWeight = FontWeight.Bold)

                HorizontalDivider()

                Text("Nominal", style = MaterialTheme.typography.labelMedium)
                Text(
                    formatter.format(expense.amount),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                HorizontalDivider()

                Text("Kategori", style = MaterialTheme.typography.labelMedium)
                Text(expense.category)

                HorizontalDivider()

                Text("Tanggal", style = MaterialTheme.typography.labelMedium)
                Text(dateFormatter.format(Date(expense.date)))

                if (expense.note.isNotBlank()) {
                    HorizontalDivider()
                    Text("Catatan", style = MaterialTheme.typography.labelMedium)
                    Text(expense.note)
                }
            }
        }
    }
}