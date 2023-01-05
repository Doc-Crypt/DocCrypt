package com.upt.cti.doccrypt.interfaceActivity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.adapters.NotificationDrawerAdapter
import com.upt.cti.doccrypt.adapters.UserRecyclerAdapter
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus


class UserInterface : AppCompatActivity() {
    private lateinit var noFilesText : TextView
    private lateinit var noNotifications : TextView
    private lateinit var docRV : RecyclerView
    private lateinit var notificationRV : RecyclerView
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var menuBtn : ImageButton
    private lateinit var notificationBtn : ImageButton
    private lateinit var drawerLeft : NavigationView
    private lateinit var drawerRight : NavigationView
    private lateinit var notificationCnt : TextView
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interface)

        // Drawer realated --------------------------------

        drawerLayout = findViewById(R.id.drawer_layout)

        // frame pt a putea schimba activitatea interface in activitatea drawer
        frameLayout = findViewById(R.id.container)
        layoutInflater.inflate(R.layout.content_user_interface, frameLayout)


        menuBtn = findViewById(R.id.menu_btn)
        notificationBtn = findViewById(R.id.notification_btn)

        drawerLeft = findViewById(R.id.nav_menu)
        drawerRight = findViewById(R.id.nav_notification)


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
        // Drawer related ------------------------------------------



        // File Recycler related -----------------------------
        noFilesText = findViewById(R.id.user_no_files)
        docRV = findViewById(R.id.user_doc_recycler)


        val filesAndFolders = getListOfDoc()

        if (filesAndFolders.size == 0) noFilesText.visibility = View.VISIBLE
        else noFilesText.visibility = View.INVISIBLE

        docRV.adapter = UserRecyclerAdapter(filesAndFolders)
        docRV.layoutManager = GridLayoutManager(this, 3)
        docRV.setHasFixedSize(true)
//        // File Recycler related ----------------------------
    }

    private fun getListOfDoc(): ArrayList<DocFile>{
        val list =  ArrayList<DocFile>()
        for (i in 0 .. 30){
            val docStatus: DocFileStatus = when (i % 3) {
                0 -> DocFileStatus.CHECKED
                1 -> DocFileStatus.DENIED
                else -> DocFileStatus.PENDING
            }

            list.add(DocFile("new Folder $i", docStatus))
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