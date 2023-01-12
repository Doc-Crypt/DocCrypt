package com.upt.cti.doccrypt.interfaceActivity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.adapters.UserNotificationAdapter
import com.upt.cti.doccrypt.adapters.UserRecyclerDocumentAdapter
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager
import com.upt.cti.doccrypt.authentication_manager.CurrentNotary
import com.upt.cti.doccrypt.authentication_manager.CurrentUser
import com.upt.cti.doccrypt.databinding.ActivityOpenUserFolderBinding
import com.upt.cti.doccrypt.entity.Document
import com.upt.cti.doccrypt.entity.FileStatus

class OpenUserFolder : UserInterface() {
    private lateinit var activityOpenUserFolderBinding: ActivityOpenUserFolderBinding
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    private lateinit var open_folder_name : TextView

    private lateinit var addNewDocumentInFolder: ImageButton
    private lateinit var postFolder: ImageButton
    private lateinit var docRV : RecyclerView
    private lateinit var addDocumentButton : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activityOpenUserFolderBinding = ActivityOpenUserFolderBinding.inflate(layoutInflater)
        setContentView(activityOpenUserFolderBinding.root)

        val notifications = getListOfUserNotifications()

        notificationCnt.text = notifications.size.toString()

        notificationBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(drawerRight)) {
                drawerLayout.closeDrawer(drawerRight)
            } else if (!drawerLayout.isDrawerOpen(drawerRight)) {
                drawerLayout.openDrawer(drawerRight)

                if (notifications.size == 0) {
                    noNotifications.visibility = View.VISIBLE
                }
                noNotifications.visibility = View.INVISIBLE

                notificationRV.layoutManager = LinearLayoutManager(this)
                notificationRV.adapter = UserNotificationAdapter(notifications)
                notificationRV.setHasFixedSize(true)
            }
            if (drawerLayout.isDrawerOpen(drawerLeft)) {
                drawerLayout.closeDrawer(drawerLeft)
            }
        }
        var position:Int = 0
        var documentList  = ArrayList<Document>()
        if(AuthenticationManager.getIsNotary()){
            position = CurrentNotary.currentFolder
            Log.e(ContentValues.TAG, position.toString())
            try {
                if(CurrentNotary.isPersonalFolder){
                    documentList = CurrentNotary.personalFolders[position].documentList
                }else{
                    documentList = CurrentNotary.publicFolders[position].documentList
                }
            }catch (e: Exception){
                Log.e(ContentValues.TAG, "java.lang.NullPointerException")
            }
        }else{
            position = CurrentUser.currentFolder
            Log.e(ContentValues.TAG, position.toString())
            try {
                documentList = CurrentUser.folders[position].documentList
            }catch (e: Exception){
                Log.e(ContentValues.TAG, "java.lang.NullPointerException")
            }
        }

        Log.e(ContentValues.TAG, documentList.toString())
        addNewDocumentInFolder = findViewById(R.id.add_new_document_in_folder)
        addNewDocumentInFolder.setOnClickListener {
            if(!AuthenticationManager.getIsNotary()){
                Log.e(ContentValues.TAG, "addNewDocumentInFolder")
                val intent = Intent(this, UploadDocument::class.java)
                startActivity(intent)
            }else{
                if(CurrentNotary.isPersonalFolder){
                    //Todo approve folder, not implemented
                    CurrentNotary.approveFolder(this, "CHECKED")
                    Toast.makeText(this, "Folder is CHECKED", Toast.LENGTH_SHORT).show()
                }else{
                    //Todo  get to personal folders
                    CurrentNotary.getFolderToPersonalList(this)
                }
            }

        }

        docRV = findViewById(R.id.new_folder_doc_recycler)
        docRV.adapter = UserRecyclerDocumentAdapter(documentList, this)
        docRV.layoutManager = GridLayoutManager(this, 1)
        docRV.setHasFixedSize(true)

        postFolder = findViewById(R.id.postFolder)
        open_folder_name = findViewById(R.id.open_folder_name)
        open_folder_name.text = intent.getStringExtra("Folder_Name")

        postFolder = findViewById(R.id.postFolder)

        postFolder.setOnClickListener {
            if(AuthenticationManager.getIsNotary()){
                CurrentNotary.approveFolder(this, "DENIED")
                Toast.makeText(this, "Folder is DENIED", Toast.LENGTH_SHORT).show()
            }else {
                CurrentUser.postFolder(this)
                Toast.makeText(this, "Folder is posted", Toast.LENGTH_SHORT).show()
            }
        }

        if(AuthenticationManager.getIsNotary()){
            if(CurrentNotary.isPersonalFolder){
                addNewDocumentInFolder.setBackgroundResource(R.color.black)
                addNewDocumentInFolder.setImageResource(R.drawable.checked)
                postFolder.setBackgroundResource(R.color.black)
                postFolder.setImageResource(R.drawable.denied)
                postFolder.visibility = View.VISIBLE
            }else{
                addNewDocumentInFolder.setBackgroundResource(R.color.black)
                addNewDocumentInFolder.setImageResource(R.drawable.create_folder)
                postFolder.visibility = View.INVISIBLE
            }
        }

    }

}


