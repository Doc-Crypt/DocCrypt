package com.upt.cti.doccrypt.loginActivity

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.upt.cti.doccrypt.R


class NotaryVerification : AppCompatActivity() {
    private lateinit var upload : Button
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notary_verification)

        upload = findViewById(R.id.upload_button)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            // Initialize result data
            val data: Intent? = result.data
            // check condition
            if (data != null) {
                // When data is not equal to empty
                // Get PDf uri
                val sUri: Uri? = data.data

                // Get PDF path
                val sPath: String? = sUri?.path
                val filename = sPath?.substring(sPath.lastIndexOf("/")+1)
                findViewById<TextView>(R.id.file_name).text = filename
            }
        }

        // Set click listener on button
        // Set click listener on button
        upload.setOnClickListener {
            // check condition
            if (ActivityCompat.checkSelfPermission(this@NotaryVerification, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // When permission is not granted
                // Result permission
                ActivityCompat.requestPermissions(this@NotaryVerification, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)}
            else {
                // When permission is granted
                // Create method
                selectPDF()
            }
        }
    }

    private fun selectPDF() {
        // Initialize intent
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // set type
        intent.type = "application/pdf"
        // Launch intent
        resultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        // check condition
        if (requestCode == 1 && grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            // When permission is granted
            // Call method
            selectPDF()
        } else {
            // When permission is denied
            // Display toast
            Toast.makeText(applicationContext, "Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }
}