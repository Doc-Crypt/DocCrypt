package com.upt.cti.doccrypt.interfaceActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.adapters.NotaryNotificationAdapter
import com.upt.cti.doccrypt.adapters.NotaryPersonalFoldersAdapter
import com.upt.cti.doccrypt.adapters.NotaryPublicFoldersAdapter
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager
import com.upt.cti.doccrypt.authentication_manager.CurrentNotary
import com.upt.cti.doccrypt.databinding.ActivityNotaryInterfaceBinding
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.entity.FileStatus
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus
import com.upt.cti.doccrypt.loginActivity.Login


class  NotaryInterface : DrawerBase() {
    private lateinit var activityNotaryInterfaceBinding: ActivityNotaryInterfaceBinding

    private lateinit var noFoldersText : TextView
    private lateinit var folderRV : RecyclerView
    private lateinit var personalBtn : Button
    private lateinit var publicBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNotaryInterfaceBinding = ActivityNotaryInterfaceBinding.inflate(layoutInflater)
        setContentView(activityNotaryInterfaceBinding.root)

        drawerLeft.menu.clear()
        drawerLeft.inflateMenu(R.menu.nav_menu_notary)
        drawerLeft.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_log_out_notary -> {
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
                    AuthenticationManager.logout()
                    CurrentNotary.clearCurrentUser()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        val notifications = getListOfNotaryNotifications()

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
                notificationRV.adapter = NotaryNotificationAdapter(notifications)
                notificationRV.setHasFixedSize(true)
            }
            if (drawerLayout.isDrawerOpen(drawerLeft)) {
                drawerLayout.closeDrawer(drawerLeft)
            }
        }

        noFoldersText = findViewById(R.id.notary_no_folders)
        folderRV = findViewById(R.id.notary_folder_recycler)
        personalBtn = findViewById(R.id.personal_btn)
        publicBtn = findViewById(R.id.public_btn)

        var notaryFolders = getListOfPersonalFolders()
        folderRV.adapter = NotaryPersonalFoldersAdapter(notaryFolders)
        getFolderRecyclerContent(notaryFolders)

        personalBtn.setOnClickListener {
            notaryFolders = getListOfPersonalFolders()
            folderRV.adapter = NotaryPersonalFoldersAdapter(notaryFolders)
            getFolderRecyclerContent(notaryFolders)

            personalBtn.setBackgroundColor(Color.BLACK)
            personalBtn.setTextColor(Color.LTGRAY)
            publicBtn.setBackgroundColor(Color.LTGRAY)
            publicBtn.setTextColor(Color.BLACK)
        }

        publicBtn.setOnClickListener {
            notaryFolders = getListOfPublicFolders()
            folderRV.adapter = NotaryPublicFoldersAdapter(notaryFolders)
            getFolderRecyclerContent(notaryFolders)
            personalBtn.setBackgroundColor(Color.LTGRAY)
            personalBtn.setTextColor(Color.BLACK)
            publicBtn.setBackgroundColor(Color.BLACK)
            publicBtn.setTextColor(Color.LTGRAY)
        }
    }

    private fun getFolderRecyclerContent(notaryFolders : ArrayList<Folder>) {
        if (notaryFolders.size == 10) noFoldersText.visibility = View.VISIBLE
        else noFoldersText.visibility = View.INVISIBLE
        folderRV.layoutManager = LinearLayoutManager(this)
        folderRV.setHasFixedSize(true)
    }

    private fun getListOfPersonalFolders(): ArrayList<Folder>{
        return CurrentNotary.personalFolders
    }

    private fun getListOfPublicFolders(): ArrayList<Folder>{
        return CurrentNotary.publicFolders
    }

    private fun getListOfNotaryNotifications(): ArrayList<Notification>{
        val list =  ArrayList<Notification>()
        for (i in 0 .. 5){
            val notifStatus: NotificationStatus =  NotificationStatus.PENDING

            list.add(Notification("Notary Notification $i", notifStatus))
        }
        return list
    }
}