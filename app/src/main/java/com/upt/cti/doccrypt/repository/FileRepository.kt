package com.upt.cti.doccrypt.repository

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.upt.cti.doccrypt.requests.FileApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


import java.io.IOException

class FileRepository {
    suspend fun uploadFile(file: File, username: String, email : String, password: String, firstName: String, lastName: String) :Boolean{
        Log.e(TAG, "username $username")
        Log.e(TAG, "email $email")
        Log.e(TAG, "password $password")
        Log.e(TAG, "firstName $firstName")
        Log.e(TAG, "lastName $lastName")
        return try {
            FileApi.instance.uploadFile(
                file = MultipartBody.Part
                    .createFormData("file",
                        file.name,
                        file.asRequestBody()),
                username = MultipartBody.Part.createFormData("username", username),
                email = MultipartBody.Part.createFormData("email", email),
                password = MultipartBody.Part.createFormData("password", password)
                )
            true
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }
}