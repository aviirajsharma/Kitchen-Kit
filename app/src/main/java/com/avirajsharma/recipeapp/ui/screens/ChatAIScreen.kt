package com.avirajsharma.recipeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.avirajsharma.recipeapp.ui.composables.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAIScreen() {
    Scaffold (
        topBar = { TopBar("CHAT AI") }
    ){ paddingValue ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValue), contentAlignment = Alignment.Center) {
            Text("CHAT AI SCREEN")
        }
    }
}