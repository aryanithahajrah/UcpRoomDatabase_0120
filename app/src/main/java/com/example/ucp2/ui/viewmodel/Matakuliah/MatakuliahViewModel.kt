package com.example.ucp2.ui.viewmodel.Matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.repository.RepositoryMatakuliah
import kotlinx.coroutines.launch

class MatakuliahViewModel (private val repositoryMatakuliah: RepositoryMatakuliah) : ViewModel(){

    var uiState by mutableStateOf(MatkulUIState())

    fun updateState(matakuliahEvent: MatakuliahEvent) {
        uiState = uiState.copy(
            matakuliahEvent = matakuliahEvent,
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.matakuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Kelas tidak boleh kosong",
            dosenpengampu = if (event.dosenpengampu.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //menyimpan data ke repository
    fun saveData(){
        val currentEvent = uiState.matakuliahEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMatakuliah.insertMatakuliah(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        matakuliahEvent = MatakuliahEvent(), // reset input form
                        isEntryValid = FormErrorState()//reset error state
                    )
                } catch (e:Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else{
            uiState = uiState.copy(
                snackBarMessage = "Data tidak valid. Periksa kembali data anda"
            )
        }
    }
    //reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(
            snackBarMessage = null )
    }
}

data class MatkulUIState(
    val matakuliahEvent: MatakuliahEvent = MatakuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage:String? = null,
)

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenpengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null && semester == null && jenis == null && dosenpengampu == null
    }
}

fun MatakuliahEvent.toMatakuliahEntity(): Matakuliah = Matakuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenpengampu
)

//data class variabel yang menyimpan data input form
data class MatakuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenpengampu: String = ""
)