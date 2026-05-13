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
import com.syauqialfanzari0008.dailyexpense.ui.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

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

    var showDeleteDialog by remember { mutableStateOf(false) }

    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    val dateFormatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Pengeluaran") },
            text = { Text("Yakin ingin menghapus pengeluaran ini?") },
            confirmButton = {
                TextButton(onClick = {
                    expense?.let { viewModel.deleteExpense(it.id) }
                    showDeleteDialog = false
                    onBack()
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Pengeluaran") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { expense?.let { onEditClick(it.id) } }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus")
                    }
                }
            )
        }
    ) { innerPadding ->
        expense?.let {
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
                        Text(it.title, fontWeight = FontWeight.Bold)

                        HorizontalDivider()

                        Text("Nominal", style = MaterialTheme.typography.labelMedium)
                        Text(
                            formatter.format(it.amount),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        HorizontalDivider()

                        Text("Kategori", style = MaterialTheme.typography.labelMedium)
                        Text(it.category)

                        HorizontalDivider()

                        Text("Tanggal", style = MaterialTheme.typography.labelMedium)
                        Text(dateFormatter.format(Date(it.date)))

                        if (it.note.isNotBlank()) {
                            HorizontalDivider()
                            Text("Catatan", style = MaterialTheme.typography.labelMedium)
                            Text(it.note)
                        }
                    }
                }
            }
        }
    }
}