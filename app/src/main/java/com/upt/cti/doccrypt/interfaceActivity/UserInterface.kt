package com.upt.cti.doccrypt.interfaceActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.adapters.UserNotificationAdapter
import com.upt.cti.doccrypt.adapters.UserRecyclerAdapter
import com.upt.cti.doccrypt.databinding.ActivityUserInterfaceBinding
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus
import com.upt.cti.doccrypt.entity.Notification
import com.upt.cti.doccrypt.entity.NotificationStatus


open class UserInterface : DrawerBase(), UserRecyclerAdapter.OnItemClickListener {
    private lateinit var activityUserInterfaceBinding: ActivityUserInterfaceBinding

    private lateinit var noFilesText : TextView
    private lateinit var docRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUserInterfaceBinding = ActivityUserInterfaceBinding.inflate(layoutInflater)
        setContentView(activityUserInterfaceBinding.root)

        drawerLeft.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_create_new_folder-> {
                    //startActivity(Intent(this, Create))
                }
                R.id.menu_log_out_user-> Toast.makeText(applicationContext, "Log out", Toast.LENGTH_SHORT).show()
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
        docRV = findViewById(R.id.doc_recycler)

        val filesAndFolders = getListOfDoc()

        if (filesAndFolders.size == 0) noFilesText.visibility = View.VISIBLE
        else noFilesText.visibility = View.INVISIBLE

        docRV.adapter = UserRecyclerAdapter(filesAndFolders, this)
        docRV.layoutManager = GridLayoutManager(this, 3)
        docRV.setHasFixedSize(true)
    }

    private fun getListOfDoc(): ArrayList<DocFile>{
        val list =  ArrayList<DocFile>()
        for (i in 0 .. 7){
            val docStatus: DocFileStatus = when (i % 3) {
                0 -> DocFileStatus.CHECKED
                1 -> DocFileStatus.DENIED
                else -> DocFileStatus.PENDING
            }

            list.add(DocFile("new Folder $i", docStatus))
        }
        return list
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
        startActivity(intent)
    }
}