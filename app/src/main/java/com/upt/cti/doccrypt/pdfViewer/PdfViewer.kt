package com.upt.cti.doccrypt.pdfViewer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.upt.cti.doccrypt.R

class PdfViewer : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
        var pdfViewer = findViewById<WebView>(R.id.webPdf)
        var url = "https://docs.google.com/gview?embedded=true&url=https://www.africau.edu/images/default/sample.pdf"
        pdfViewer.webViewClient = WebViewClient()
        pdfViewer.loadUrl(url)
    }
}