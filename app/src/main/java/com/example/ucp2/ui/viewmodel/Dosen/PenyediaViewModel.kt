package com.example.ucp2.ui.viewmodel.Dosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp
import com.example.ucp2.ui.viewmodel.Matakuliah.DetailMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.HomeMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.UpdateMatakuliahViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }
        initializer{
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer{
            DetailDosenViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDosen,
            )
        }

        initializer {
            MatakuliahViewModel(
                krsApp().containerApp.repositoryMatakuliah
            )
        }
        initializer{
            HomeMatakuliahViewModel(
                krsApp().containerApp.repositoryMatakuliah
            )
        }

        initializer{
            DetailMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatakuliah,
            )
        }

        initializer{
            UpdateMatakuliahViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMatakuliah,
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)