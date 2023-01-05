package com.upt.cti.doccrypt.loginActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager



class NotaryVerification : AppCompatActivity() {
    private lateinit var upload : Button
    private lateinit var file_name : TextView
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    private lateinit var data: Intent
//    private lateinit var pdfViewer: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notary_verification)

        var fullName = intent.getStringExtra("fullName")
        var password = intent.getStringExtra("password")
        var email = intent.getStringExtra("email")
        var firstName = fullName?.split(" ")?.get(0)
        var lastName = fullName?.split(" ")?.get(1)

        upload = findViewById(R.id.upload_button)
        file_name =  findViewById(R.id.file_name)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            data = result.data!!
            if (data != null) {
                val sUri: Uri? = data.data
                val sPath: String? = sUri?.path
                val filename = sPath?.substring(sPath.lastIndexOf("/")+1)
                file_name.text = filename
            }
        }

        file_name.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this@NotaryVerification, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@NotaryVerification, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)}
            else {
                selectPDF()
            }
        }

        upload.setOnClickListener {
            val sUri: Uri? = data.data
            val sPath: String? = sUri?.path
            val filename = sPath?.substring(sPath.lastIndexOf("/")+1)

            var inputStream = sUri?.let { it1 -> this.contentResolver.openInputStream(it1) }
            var byteArray = inputStream!!.readBytes()

            if (byteArray != null) {
                Log.e(TAG, "Size of file " + byteArray.size)
                AuthenticationManager.sendProveFile(byteArray, this, fullName!!, email!!, password!!, firstName!!, lastName!!)
            }else{
                Log.e(TAG, "Empty")
            }
        }

//        var pdfUrl = "https://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf"
//        pdfViewer = findViewById(R.id.pdfView)
//        RetrievePDFFromURL(pdfViewer).execute(pdfUrl)

    }

    private fun selectPDF() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        resultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        if (requestCode == 1 && grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            selectPDF()
        } else {
            Toast.makeText(applicationContext, "Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }

//    class RetrievePDFFromURL(pdfView: PDFView) :
//        AsyncTask<String, Void, InputStream>() {
//        @SuppressLint("StaticFieldLeak")
//        val mypdfView: PDFView = pdfView
//        override fun doInBackground(vararg params: String?): InputStream? {
//            var inputStream: InputStream? = null
//            try {
//                val url = URL(params.get(0))
//                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
//                if (urlConnection.responseCode == 200) {
//                    inputStream = BufferedInputStream(urlConnection.inputStream)
//                }
//            }
//            catch (e: Exception) {
//                e.printStackTrace()
//                return null;
//            }
//            return inputStream;
//        }
//        override fun onPostExecute(result: InputStream?) {
//            mypdfView.fromStream(result).load()
//
//        }
//    }


}