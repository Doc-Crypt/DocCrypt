package com.upt.cti.doccrypt.requests

import com.upt.cti.doccrypt.authentication_manager.BASE_URL
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {
    @Multipart
    @POST("$BASE_URL/api/v1/reg/registration/notary/")
    suspend fun uploadFile(@Part file: MultipartBody.Part,
                           @Part username: MultipartBody.Part,
                           @Part email: MultipartBody.Part,
                           @Part password: MultipartBody.Part)

    companion object{
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("$BASE_URL/api/v1/reg/registration/notary/")
                .build()
                .create(FileApi::class.java)
        }
    }
}