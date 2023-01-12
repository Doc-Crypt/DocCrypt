package com.upt.cti.doccrypt.repository

import android.content.ContentValues
import android.util.Log
import com.upt.cti.doccrypt.requests.DocumentApi
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class DocumentRepository {
    suspend fun uploadFile(file: File, username: String,filename: String,  folderId : Int) :Boolean{
        Log.e(ContentValues.TAG, "username $username")
        Log.e(ContentValues.TAG, "folderId $folderId")
        return try {
            DocumentApi.instance.uploadFile(
                file = MultipartBody.Part
                    .createFormData("file",
                        file.name,
                        file.asRequestBody()),
                username = MultipartBody.Part.createFormData("username", username),
                filename = MultipartBody.Part.createFormData("filename", filename),
                folderId = MultipartBody.Part.createFormData("folderId", folderId.toString())
            )
            true
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }
}