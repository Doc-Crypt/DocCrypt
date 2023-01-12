package com.upt.cti.doccrypt.interfaceActivity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager
import com.upt.cti.doccrypt.authentication_manager.CurrentUser

class UploadDocument : AppCompatActivity() {
    private lateinit var upload : Button
    private lateinit var file_name : TextView
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    private lateinit var data: Intent
//    private lateinit var pdfViewer: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notary_verification)

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
            if (ActivityCompat.checkSelfPermission(this@UploadDocument, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@UploadDocument, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)}
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
                Log.e(ContentValues.TAG, "Size of file " + byteArray.size)
                val username = AuthenticationManager.getUsername()
                CurrentUser.sendDocumentInFolder(byteArray, this, username!!, filename!!,  CurrentUser.currentFolderId)
            //                AuthenticationManager.sendProveFile(byteArray, this, fullName!!, email!!, password!!, firstName!!, lastName!!)
                val intent = Intent(this, OpenUserFolder::class.java)
                startActivity(intent)
            }else{
                Log.e(ContentValues.TAG, "Empty")
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
            Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}