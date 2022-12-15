package com.upt.cti.doccrypt.authentication_manager

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


const val BASE_URL = "http://192.168.0.107:8075"
object AuthenticationManager {
    private var authenticationToken:String? = null
    private var username:String? = null
    private var isLoggedIn: Boolean = false

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
                this.username = result!!.get("username") as String
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
        username = null
        isLoggedIn = false
    }
    fun getToken() = authenticationToken
    fun getUserName() = username
    fun getIsLoggedIn() = isLoggedIn

}

