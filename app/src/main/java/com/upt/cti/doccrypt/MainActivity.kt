package com.upt.cti.doccrypt

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.text)

        val queue = Volley.newRequestQueue(this)

        val url = "http://192.168.0.107:8075/api/v1/auth/login"  //http://192.168.0.107:8000/ //http://localhost:8075/api/v1/auth/login

//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response ->
//                text.text = response
//            },
//            { text.text = "That didn't work!" })
//        val requestBody = "username=ion2" + "&password=test"

        val params = HashMap<String, String>()
        params["username"] = "ion2"
        params["password"] = "test"

        var jsonRequest = JSONObject(params.toString())


//        val stringReq : StringRequest =
//            object : StringRequest(Method.POST, url,
//                Response.Listener { response ->
//                    // response
//                    var strResp = response.toString()
//                    Log.d(TAG, "Super merge")
//                    Log.d(TAG, "API $strResp")
//                },
//                Response.ErrorListener { error ->
//                    Log.d(TAG, "TEST")
//                    Log.d(TAG, "API error => $error")
//                }
//            ){
//                override fun getBody(): ByteArray? {
//                    Log.e(TAG, "Input")
//                    Log.d(TAG, params.toString())
//                    return params.toString().toByteArray(Charsets.UTF_8)
//                }
//
//                override fun getBodyContentType(): String {
//                    return "application/json;"
//                }
//            }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonRequest,
            { response ->
                Log.e(TAG, "Response: %s".format(response.toString()))
            },
            { _ ->
                // TODO: Handle error
            }
        )
        queue.add(jsonObjectRequest)




    }
}