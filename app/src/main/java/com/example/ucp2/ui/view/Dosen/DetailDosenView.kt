package com.example.ucp2.ui.view.Dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.Dosen.DetailDosenViewModel
import com.example.ucp2.ui.viewmodel.Dosen.DetailUiState
import com.example.ucp2.ui.viewmodel.Dosen.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.Dosen.toDosenEntity

@Composable
fun DetailDosenView(
    modifier: Modifier = Modifier,
    viewModel: DetailDosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF09381F)),
        topBar = {
            TopAppBar(
                judul = "Detail Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF09381F))
        ) {
            val detailUiState by viewModel.detailUiState.collectAsState()
            BodyDetailDosen(modifier = Modifier, detailUiState = detailUiState)
        }
    }
}

@Composable
fun BodyDetailDosen(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState()
) {
    when {
        detailUiState.isLoading -> {  }
        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailDosen(
                    dosen = detailUiState.detailUiEvent.toDosenEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
        detailUiState.isUiEventEmpty -> { }
    }
}

@Composable
fun ItemDetailDosen(
    modifier: Modifier = Modifier,
    dosen: Dosen
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFE6D4E6), shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color(0xFF09381F)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetaiDosen(judul = "NIDN", isinya = dosen.nidn)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetaiDosen(judul = "Nama", isinya = dosen.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetaiDosen(judul = "Jenis Kelamin", isinya = dosen.jenisKelamin)
        }
    }
}

@Composable
fun ComponentDetaiDosen(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF09381F)
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF09381F)
        )
    }
}