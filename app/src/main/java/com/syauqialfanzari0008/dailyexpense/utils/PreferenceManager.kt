package com.syauqialfanzari0008.dailyexpense.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferenceManager {

    private val IS_GRID_VIEW = booleanPreferencesKey("is_grid_view")

    fun isGridView(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_GRID_VIEW] ?: false
        }
    }

    suspend fun setGridView(context: Context, isGrid: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_GRID_VIEW] = isGrid
        }
    }
}