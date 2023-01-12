package com.upt.cti.doccrypt.interfaceActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.upt.cti.doccrypt.R
import java.io.ByteArrayOutputStream
import java.io.InputStream


class DocumentView : AppCompatActivity() {
    private lateinit var storage: StorageReference
    private lateinit var database: DatabaseReference

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_view)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("http://192.168.0.101:8075/api/v1/admin/candidate/doc/1"),"application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
        startActivity(intent);

    }
}
