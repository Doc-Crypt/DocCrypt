package com.upt.cti.doccrypt.entity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upt.cti.doccrypt.repository.DocumentRepository
import kotlinx.coroutines.launch
import java.io.File

class DocumentViewModel(
    private val repository: DocumentRepository = DocumentRepository()
): ViewModel() {

    fun uploadFile(file: File, username: String,filename: String, folderId: Int){
        viewModelScope.launch {
            repository.uploadFile(file, username,filename, folderId)
        }
    }
}