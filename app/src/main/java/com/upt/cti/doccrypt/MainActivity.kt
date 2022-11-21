package com.upt.cti.doccrypt

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.text)

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"  //http://192.168.0.107:8000/

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                text.text = response
            },
            { text.text = "That didn't work!" })

        queue.add(stringRequest)
    }
}