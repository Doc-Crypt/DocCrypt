package com.upt.cti.doccrypt.interfaceActivity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import java.io.File


class UserInterface : AppCompatActivity() {
    private lateinit var noFilesText : TextView
    private lateinit var docRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interface)

        noFilesText = findViewById(R.id.no_files)
        docRV = findViewById(R.id.doc_recycler)

        val path = intent.getStringExtra("path")
        val root = path?.let { File(it) }
        val filesAndFolders = root?.listFiles()

        if (filesAndFolders == null || filesAndFolders.size == 0) {
            noFilesText.visibility = View.VISIBLE
            return
        }

        noFilesText.visibility = View.INVISIBLE

        docRV.setLayoutManager(LinearLayoutManager(this))
//        docRV.setAdapter(UserInterfaceAdapter(applicationContext, filesAndFolders))
    }
}