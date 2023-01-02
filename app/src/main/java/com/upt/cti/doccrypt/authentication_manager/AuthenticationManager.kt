package com.upt.cti.doccrypt.authentication_manager

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Environment
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.upt.cti.doccrypt.entity.FileViewModel
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

const val BASE_URL = "http://192.168.100.31:8075"
object AuthenticationManager {
    private var authenticationToken:String? = null
    private var userFullName:String? = null
    private var isLoggedIn: Boolean = false
    var conn: HttpURLConnection? = null

    fun login(username: String?, password: String?, context: Context): Boolean{
        var result: JSONObject? = null
        val params = HashMap<String, String>()
        params["username"] = username!!
        params["password"] = password!!


        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/auth/login",
            jsonRequest,
            { response ->
                Log.d(TAG, "Response: %s".format(response.toString()))
                result = JSONObject(response.toString())
                authenticationToken = result!!.get("token") as String?;
                this.userFullName = result!!.get("FullName") as String
                this.isLoggedIn = true
            },
            { error ->
                Log.e(TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
        return this.isLoggedIn
    }
    fun logout(){
        authenticationToken = null
        userFullName = null
        isLoggedIn = false
    }
    fun getToken() = authenticationToken
    fun getUserFullName() = userFullName
    fun getIsLoggedIn() = isLoggedIn

    @Throws(IOException::class)
    fun sendProveFile(input: ByteArray?, context: Context, username: String, email: String, password: String, firstName: String, lastName: String): String? {
        val path = context.filesDir
        val letDirectory = File(path, "LET")
        letDirectory.mkdirs()
        val file = File(letDirectory, "Records.pdf")
        FileOutputStream(file).use {
            it.write(input)
        }
        val viewModel = FileViewModel()
        return viewModel.uploadFile(file, username, email, password, firstName, lastName).toString()
    }
}