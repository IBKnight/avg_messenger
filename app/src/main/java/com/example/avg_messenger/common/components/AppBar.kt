package com.example.avg_messenger.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, onClick: () -> Unit) {
    TopAppBar(title = { Text(text = title) }, actions = {
//        IconButton(onClick = onClick) {
//            Icon(Icons.Filled.Settings, contentDescription = "Настройки")
//        }
    })
}