package com.upt.cti.doccrypt.interfaceActivity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.adapters.NotaryFoldersAdapter
import com.upt.cti.doccrypt.adapters.NotificationDrawerAdapter
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.entity.FolderStatus
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus


class  NotaryInterface : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private lateinit var noFoldersText : TextView
    private lateinit var folderRV : RecyclerView
    private lateinit var personalBtn : Button
    private lateinit var publicBtn : Button

    private lateinit var noNotifications : TextView
    private lateinit var notificationRV : RecyclerView
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var menuBtn : ImageButton
    private lateinit var notificationBtn : ImageButton
    private lateinit var drawerLeft : NavigationView
    private lateinit var drawerRight : NavigationView
    private lateinit var notificationCnt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interface)


        // Drawer realated --------------------------------   !!!  TEMPORAR !!!

        drawerLayout = findViewById(R.id.drawer_layout)
        frameLayout = findViewById(R.id.container)

        menuBtn = findViewById(R.id.menu_btn)
        notificationBtn = findViewById(R.id.notification_btn)

        drawerLeft = findViewById(R.id.nav_menu)
        drawerRight = findViewById(R.id.nav_notification)

        layoutInflater.inflate(R.layout.content_notary_interface, frameLayout)

        menuBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(drawerLeft)) {
                drawerLayout.closeDrawer(drawerLeft)
            } else if (!drawerLayout.isDrawerOpen(drawerLeft)) {
                drawerLayout.openDrawer(drawerLeft)
            }
            if (drawerLayout.isDrawerOpen(drawerRight)) {
                drawerLayout.closeDrawer(drawerRight)
            }
        }

        noNotifications = findViewById(R.id.no_notifications)
        notificationRV = findViewById(R.id.notification_recycler)
        notificationCnt = findViewById(R.id.notification_count)

        val notifications = getListOfNotifications()

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
                notificationRV.adapter = NotificationDrawerAdapter(notifications)
                notificationRV.setHasFixedSize(true)
            }
            if (drawerLayout.isDrawerOpen(drawerLeft)) {
                drawerLayout.closeDrawer(drawerLeft)
            }
        }

        drawerLeft.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_create_new_folder-> Toast.makeText(applicationContext, "Create folder", Toast.LENGTH_SHORT).show()
            }
            true
        }

        // Notary buttons -----------------------------------

        personalBtn = findViewById(R.id.personal_btn)
        publicBtn = findViewById(R.id.public_btn)

        noFoldersText = findViewById(R.id.notary_no_folders)
        folderRV = findViewById(R.id.notary_folder_recycler)

        var notaryFolders = getListOfPersonalFolders()
        getFolderRecyclerContent(notaryFolders)

        personalBtn.setOnClickListener {
            notaryFolders = getListOfPersonalFolders()
            getFolderRecyclerContent(notaryFolders)

            personalBtn.setBackgroundColor(Color.BLACK)
            personalBtn.setTextColor(Color.LTGRAY)
            publicBtn.setBackgroundColor(Color.LTGRAY)
            publicBtn.setTextColor(Color.BLACK)
        }

        publicBtn.setOnClickListener {
            notaryFolders = getListOfPublicFolders()
            getFolderRecyclerContent(notaryFolders)

            personalBtn.setBackgroundColor(Color.LTGRAY)
            personalBtn.setTextColor(Color.BLACK)
            publicBtn.setBackgroundColor(Color.BLACK)
            publicBtn.setTextColor(Color.LTGRAY)
        }
        // Notary buttons -----------------------------------]
    }

    private fun getFolderRecyclerContent(notaryFolders : ArrayList<Folder>) {
        if (notaryFolders.size == 10) noFoldersText.visibility = View.VISIBLE
        else noFoldersText.visibility = View.INVISIBLE
        folderRV.adapter = NotaryFoldersAdapter(notaryFolders)
        folderRV.layoutManager = LinearLayoutManager(this)
        folderRV.setHasFixedSize(true)
    }

    private fun getListOfPersonalFolders(): ArrayList<Folder>{
        val list =  ArrayList<Folder>()
        for (i in 0 .. 8){
            val folderStatus: FolderStatus = when (i % 3) {
                0 -> FolderStatus.CHECKED
                1 -> FolderStatus.DENIED
                else -> FolderStatus.PENDING
            }

            list.add(Folder("Personal Folder $i", folderStatus))
        }
        return list
    }

    private fun getListOfPublicFolders(): ArrayList<Folder>{
        val list =  ArrayList<Folder>()
        for (i in 0 .. 8){
            val folderStatus: FolderStatus = when (i % 3) {
                0 -> FolderStatus.CHECKED
                1 -> FolderStatus.DENIED
                else -> FolderStatus.PENDING
            }

            list.add(Folder("Public Folder $i", folderStatus))
        }
        return list
    }

    private fun getListOfNotifications(): ArrayList<Notification>{
        val list =  ArrayList<Notification>()
        for (i in 0 .. 5){
            val notifStatus: NotificationStatus = when (i % 2) {
                0 -> NotificationStatus.CHECKED
                else -> NotificationStatus.DENIED
            }

            list.add(Notification("Notification $i", notifStatus))
        }
        return list
    }
}