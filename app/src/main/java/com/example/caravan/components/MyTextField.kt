package com.example.caravan.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MyTextField(state: MutableState<String>, s: String) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp, top = 0.dp),
        value = state.value,
        label = { Text(s) },
        singleLine = true,
        onValueChange = {
            state.value = it
        }
    )
}