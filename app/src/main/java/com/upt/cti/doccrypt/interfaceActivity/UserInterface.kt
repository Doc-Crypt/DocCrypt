package com.upt.cti.doccrypt.interfaceActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.AuthenticationManager
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus
import com.upt.cti.doccrypt.entity.DocFileStatus.PENDING
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus
import com.upt.cti.doccrypt.loginActivity.Login

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interface)

        // Drawer realated --------------------------------

        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        menuBtn = findViewById<View>(R.id.menu_btn) as ImageButton
        notificationBtn = findViewById<View>(R.id.notification_btn) as ImageButton

        drawerLeft = findViewById<View>(R.id.nav_menu) as NavigationView
        drawerRight = findViewById<View>(R.id.nav_notification) as NavigationView

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

        notificationBtn.setOnClickListener {
            if (drawerLayout.isDrawerOpen(drawerRight)) {

                drawerLayout.closeDrawer(drawerRight)
            } else if (!drawerLayout.isDrawerOpen(drawerRight)) {
                drawerLayout.openDrawer(drawerRight)
                noNotifications = findViewById(R.id.no_notifications)
                notificationRV = findViewById(R.id.notification_recycler)
                val notifications = getListOfNotifications()

                if (notifications.size == 0) {
                    noFilesText.visibility = View.VISIBLE
                }
                noFilesText.visibility = View.INVISIBLE

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
                R.id.logout_button -> {
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
                    AuthenticationManager.logout()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)

                }
            }
            true
        }

        // Drawer related ------------------------------------------



        // File Recycler related -----------------------------
        noFilesText = findViewById(R.id.no_files)
        docRV = findViewById(R.id.doc_recycler)


        val filesAndFolders = getListOfDoc()

        if (filesAndFolders.size == 0) {
            noFilesText.visibility = View.VISIBLE
        }
        noFilesText.visibility = View.INVISIBLE

        docRV.adapter = RecyclerAdapter(filesAndFolders)
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
                else -> PENDING
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