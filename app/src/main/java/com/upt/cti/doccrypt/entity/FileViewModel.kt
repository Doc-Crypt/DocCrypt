package com.upt.cti.doccrypt.entity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upt.cti.doccrypt.repository.FileRepository
import kotlinx.coroutines.launch
import java.io.File

class FileViewModel(
    private val repository: FileRepository = FileRepository()
): ViewModel() {

    fun uploadFile(file: File, username: String, email: String, password: String, firstName: String, lastName: String){
        viewModelScope.launch {
           repository.uploadFile(file, username, email, password, firstName, lastName)
        }
    }
}