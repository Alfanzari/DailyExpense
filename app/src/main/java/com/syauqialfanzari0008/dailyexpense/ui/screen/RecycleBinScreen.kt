package com.syauqialfanzari0008.dailyexpense.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.syauqialfanzari0008.dailyexpense.data.model.Expense
import com.syauqialfanzari0008.dailyexpense.ui.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    onBack: () -> Unit,
    viewModel: ExpenseViewModel = viewModel()
) {
    val deletedExpenses by viewModel.deletedExpenses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        @Suppress("DEPRECATION")
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (deletedExpenses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Recycle Bin kosong")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(deletedExpenses) { expense ->
                    RecycleBinCard(
                        expense = expense,
                        onRestore = { viewModel.restoreExpense(expense.id) },
                        onHardDelete = { viewModel.hardDeleteExpense(expense) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RecycleBinCard(
    expense: Expense,
    onRestore: () -> Unit,
    onHardDelete: () -> Unit
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))
    val showDeleteDialog = remember { mutableStateOf(false) }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("Hapus Permanen") },
            text = { Text("Data ini akan dihapus permanen dan tidak bisa dikembalikan. Lanjutkan?") },
            confirmButton = {
                TextButton(onClick = {
                    onHardDelete()
                    showDeleteDialog.value = false
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog.value = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(expense.title, fontWeight = FontWeight.Bold)
            Text(expense.category, style = MaterialTheme.typography.bodySmall)
            Text(
                formatter.format(expense.amount),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onRestore) {
                    Text("Pulihkan")
                }
                Button(onClick = { showDeleteDialog.value = true }) {
                    Text("Hapus Permanen")
                }
            }
        }
    }
}