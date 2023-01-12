package com.upt.cti.doccrypt.requests

import com.upt.cti.doccrypt.authentication_manager.BASE_URL
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DocumentApi {
    @Multipart
    @POST("$BASE_URL/api/v1/admin/customer_add_Document_to_Folder/")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Part username: MultipartBody.Part,
        @Part filename: MultipartBody.Part,
        @Part folderId: MultipartBody.Part,
    )

    companion object{
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("$BASE_URL/api/v1/admin/customer_add_Document_to_Folder/")
                .build()
                .create(DocumentApi::class.java)
        }
    }
}