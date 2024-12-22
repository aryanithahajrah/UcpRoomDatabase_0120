package com.example.ucp2.ui.view.Dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier

@Composable
fun HomeMenuView(
    onDosenClick: () -> Unit,
    onMatkulClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF09381F))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onDosenClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE6D4E6)
            )
        ) {
            Text(
                text = "Menu Dosen",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF09381F)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onMatkulClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE6D4E6)
            )
        ) {
            Text(
                text = "Menu Matakuliah",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF09381F)
            )
        }
    }
}