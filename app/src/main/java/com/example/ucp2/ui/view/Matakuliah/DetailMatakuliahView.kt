package com.example.ucp2.ui.view.Matakuliah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.Dosen.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.DetailMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.DetailMatkulUiState
import com.example.ucp2.ui.viewmodel.Matakuliah.toMatakuliahEntity

@Composable
fun DetailMatakuliahView(
    modifier: Modifier = Modifier,
    onAddMatkul: () -> Unit = { },
    viewModel: DetailMatakuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val kode = viewModel.detailMatkulUiState.value.DetailMatkulUiState.kode
                    onEditClick(kode)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Matakuliah",
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF09381F))
                .padding(innerPadding)
        ) {
            val detailMatkulUiState by viewModel.detailMatkulUiState.collectAsState()

            BodyDetailMatkul(
                modifier = Modifier,
                detailMatkulUiState = detailMatkulUiState,
                onDeleteClick = {
                    viewModel.deleteMatkul()
                    onDeleteClick()
                }
            )
        }
    }
}

@Composable
fun BodyDetailMatkul (
    modifier: Modifier = Modifier,
    detailMatkulUiState: DetailMatkulUiState,
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailMatkulUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailMatkulUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMatkul(
                    matakuliah = detailMatkulUiState.DetailMatkulUiState.toMatakuliahEntity(),
                    modifier = Modifier,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailMatkulUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMatkul(
    modifier: Modifier = Modifier,
    matakuliah: Matakuliah
){
    Card (
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFE6D4E6), shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors (
            containerColor = Color(0xFFE6D4E6),
            contentColor = Color(0xFF09381F)
        )
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            ComponentDetailMatkul(judul = "Kode", isinya = matakuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Nama", isinya = matakuliah.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "SKS", isinya = matakuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Semester", isinya = matakuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Jenis", isinya = matakuliah.jenis)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Dosen Pengampu", isinya = matakuliah.dosenPengampu)
        }
    }
}

@Composable
fun ComponentDetailMatkul(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
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
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF09381F)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog (
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier =
        Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}