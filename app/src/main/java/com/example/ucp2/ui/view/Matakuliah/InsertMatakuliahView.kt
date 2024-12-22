package com.example.ucp2.ui.view.Matakuliah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.Dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.Dosen.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.FormErrorState
import com.example.ucp2.ui.viewmodel.Matakuliah.MatakuliahEvent
import com.example.ucp2.ui.viewmodel.Matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.MatkulUIState
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.ucp2.ui.viewmodel.Dosen.DsnUIState


object DestinasiInsertMatakuliah : AlamatNavigasi {
    override val route: String = "insert_matkul"
}

@Composable
fun InsertMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF09381F))
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah",
                modifier = modifier,
            )
            InsertBodyMatkul(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatkul(
    modifier: Modifier = Modifier,
    onValueChange: (MatakuliahEvent) -> Unit,
    uiState: MatkulUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF09381F)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matakuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan", color = Color(0xFFE6D4E6))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMatakuliah(
    matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    onValueChange: (MatakuliahEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    DosenViewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val DsnUIState by DosenViewModel.DsnUIState.collectAsState()
    val listDosen = DsnUIState.listDosen.map { it.nama }
    val jenisMatakuliah = listOf("Wajib", "Peminatan")
    val semester = listOf("Genap", "Ganjil")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF09381F))
            .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label = { Text("Kode", color = Color(0xFFE6D4E6)) },
            isError = errorState.kode != null,
            placeholder = { Text("Masukan kode matakuliah", color = Color(0xFFE6D4E6)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFE6D4E6))
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(nama = it))
            },
            label = { Text("Nama", color = Color(0xFFE6D4E6)) },
            isError = errorState.nama != null,
            placeholder = { Text("Masukan nama matakuliah", color = Color(0xFFE6D4E6)) },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFE6D4E6))
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.sks,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(sks = it))
            },
            label = { Text("SKS", color = Color(0xFFE6D4E6)) },
            isError = errorState.sks != null,
            placeholder = { Text("Masukan jumlah SKS", color = Color(0xFFE6D4E6)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFE6D4E6))
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Semester", color = Color(0xFFE6D4E6))
        Row(modifier = Modifier.fillMaxWidth()) {
            semester.forEach { semester ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.semester == semester,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(semester = semester))
                        },
                    )
                    Text(
                        text = semester,
                        color = Color(0xFFE6D4E6)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Matakuliah", color = Color(0xFFE6D4E6))
        Row(modifier = Modifier.fillMaxWidth()) {
            jenisMatakuliah.forEach { jenis ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenis == jenis,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(jenis = jenis))
                        },
                    )
                    Text(
                        text = jenis,
                        color = Color(0xFFE6D4E6)
                    )
                }
            }
        }

        DropdownMenuField(
            label = "Nama Dosen Pengampu",
            options = listDosen,
            selectedOption = matakuliahEvent.dosenpengampu,
            onOptionSelected = { selectedDosen ->
                onValueChange(matakuliahEvent.copy(dosenpengampu = selectedDosen))
            },
            isError = errorState.dosenpengampu != null,
            errorMessage = errorState.dosenpengampu
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedOption) }


    Column {
        OutlinedTextField (
            value = currentSelection,
            onValueChange = {},
            readOnly = true,
            label = { Text (label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}