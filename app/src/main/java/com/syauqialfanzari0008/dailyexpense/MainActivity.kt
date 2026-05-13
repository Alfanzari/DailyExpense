package com.syauqialfanzari0008.dailyexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.syauqialfanzari0008.dailyexpense.ui.screen.AppNavigation
import com.syauqialfanzari0008.dailyexpense.ui.theme.DailyExpenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyExpenseTheme {
                AppNavigation()
            }
        }
    }
}