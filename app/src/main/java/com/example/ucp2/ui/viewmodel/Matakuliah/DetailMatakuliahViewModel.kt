package com.example.ucp2.ui.viewmodel.Matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import com.example.ucp2.ui.navigation.DestinasiDetailMatakuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMatakuliahViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah,

    ) : ViewModel() {
    private val kode: String = checkNotNull(savedStateHandle[DestinasiDetailMatakuliah.KODE])

    val detailMatkulUiState: StateFlow<DetailMatkulUiState> = repositoryMatakuliah.getMatakuliah(kode)
        .filterNotNull()
        .map {
            DetailMatkulUiState(
                DetailMatkulUiState = it.toDetailMatkulUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailMatkulUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailMatkulUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailMatkulUiState(
                isLoading = true,
            ),
        )

    fun deleteMatkul() {
        detailMatkulUiState.value.DetailMatkulUiState.toMatakuliahEntity().let {
            viewModelScope.launch {
                repositoryMatakuliah.deleteMatakuliah(it)
            }
        }
    }
}

data class DetailMatkulUiState(
    val DetailMatkulUiState: MatakuliahEvent = MatakuliahEvent (),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = DetailMatkulUiState == MatakuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = DetailMatkulUiState != MatakuliahEvent ()
}

/*Data class untuk menampung data yang akan ditampilkan di UI*/
//memindahkan data dari entity ke ui
fun Matakuliah.toDetailMatkulUiEvent () : MatakuliahEvent {
    return MatakuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenpengampu = dosenPengampu
    )
}