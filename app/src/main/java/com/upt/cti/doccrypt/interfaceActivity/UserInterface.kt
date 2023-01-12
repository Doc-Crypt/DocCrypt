package com.upt.cti.doccrypt.interfaceActivity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.adapters.UserNotificationAdapter
import com.upt.cti.doccrypt.adapters.UserRecyclerAdapter
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager
import com.upt.cti.doccrypt.authentication_manager.CurrentUser
import com.upt.cti.doccrypt.databinding.ActivityUserInterfaceBinding
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus
import com.upt.cti.doccrypt.loginActivity.Login


open class UserInterface : DrawerBase(), UserRecyclerAdapter.OnItemClickListener {
    private lateinit var activityUserInterfaceBinding: ActivityUserInterfaceBinding

    private lateinit var noFilesText : TextView
    private lateinit var user_full_name : TextView
    private lateinit var folderName : EditText
    private lateinit var addNewFolder : ConstraintLayout
    private lateinit var addNewFolderButton : MaterialButton
    private lateinit var folderList : ConstraintLayout
    private lateinit var userInfo : ConstraintLayout
    private lateinit var docRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUserInterfaceBinding = ActivityUserInterfaceBinding.inflate(layoutInflater)
        setContentView(activityUserInterfaceBinding.root)

        user_full_name = findViewById(R.id.user_full_name)
        user_full_name.text = AuthenticationManager.getUserFullName()

        drawerLeft.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_create_new_folder-> {
                    addNewFolder.visibility = View.VISIBLE
                    folderList.visibility = View.INVISIBLE
                    userInfo.visibility = View.INVISIBLE

                }
                R.id.menu_log_out_user-> {
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
                    AuthenticationManager.logout()
                    CurrentUser.clearCurrentUser()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)

                }
            }
            true
        }

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

        noFilesText = findViewById(R.id.no_files)
        folderName = findViewById(R.id.new_folder_name)
        addNewFolderButton = findViewById(R.id.create_new_folder)
        addNewFolder = findViewById(R.id.add_new_folder_form)
        folderList = findViewById(R.id.folder_list)
        userInfo = findViewById(R.id.user_info_constrain)
        addNewFolder.visibility = View.INVISIBLE

        docRV = findViewById(R.id.doc_recycler)

        addNewFolderButton.setOnClickListener {

            val folderNameText = folderName.text
            Toast.makeText(applicationContext, "Folder: $folderNameText, was created", Toast.LENGTH_SHORT).show()
            CurrentUser.createFolder(this, folderNameText.toString());
            CurrentUser.clearCurrentUser();
            AuthenticationManager.getUsername()?.let { it1 -> CurrentUser.getFolders(this, it1) };
            addNewFolder.visibility = View.INVISIBLE
            folderList.visibility = View.VISIBLE
            userInfo.visibility = View.VISIBLE
        }

        Log.e(TAG, CurrentUser.folders.toString())
        val filesAndFolders = CurrentUser.folders

        if (filesAndFolders.size == 0) noFilesText.visibility = View.VISIBLE
        else noFilesText.visibility = View.INVISIBLE

        docRV.adapter = UserRecyclerAdapter(filesAndFolders, this)
        docRV.layoutManager = GridLayoutManager(this, 3)
        docRV.setHasFixedSize(true)
    }



    protected fun getListOfUserNotifications(): ArrayList<Notification>{
        val list =  ArrayList<Notification>()
        for (i in 0 .. 3){
            val notifStatus: NotificationStatus = when (i % 2) {
                0 -> NotificationStatus.CHECKED
                else -> NotificationStatus.DENIED
            }

            list.add(Notification("User Notification $i", notifStatus))
        }
        return list
    }

    override fun onDocFileClick(position: Int) {
        val intent = Intent(this, OpenUserFolder::class.java)
        Toast.makeText(applicationContext, "Folder: $position", Toast.LENGTH_SHORT).show()
        intent.putExtra("folderPosition", position)
        intent.putExtra("folderId", 1)
        intent.putExtra("Folder_Name", CurrentUser.folders[position].folderName)
        CurrentUser.currentFolder = position
        CurrentUser.currentFolderId = CurrentUser.folders[position].folderId

        startActivity(intent)
    }
}