package com.upt.cti.doccrypt.interfaceActivity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus
import com.upt.cti.doccrypt.entity.DocFileStatus.*
import java.io.File


class UserInterface : AppCompatActivity() {
    private lateinit var noFilesText : TextView
    private lateinit var docRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interface)

        noFilesText = findViewById(R.id.no_files)
        docRV = findViewById(R.id.doc_recycler)


        val filesAndFolders = getListOfDoc()

        if (filesAndFolders.size == 0) {
            noFilesText.visibility = View.VISIBLE
            return
        }
        noFilesText.visibility = View.INVISIBLE

        docRV.adapter = UserInterfaceAdapter(filesAndFolders)
        docRV.layoutManager = GridLayoutManager(this, 3)
        docRV.setHasFixedSize(true)


    }
    private fun getListOfDoc(): ArrayList<DocFile>{
        val list =  ArrayList<DocFile>()
        for (i in 0 .. 100){
            val status: DocFileStatus = when (i % 3) {
                0 -> CHECKED
                1 -> DENIED
                else -> PENDING
            }

            list.add(DocFile("new Folder $i", status))
        }
        return list
    }
}