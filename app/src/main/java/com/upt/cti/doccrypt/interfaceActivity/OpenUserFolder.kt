package com.upt.cti.doccrypt.interfaceActivity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.upt.cti.doccrypt.adapters.UserNotificationAdapter
import com.upt.cti.doccrypt.databinding.ActivityOpenUserFolderBinding

class OpenUserFolder : UserInterface() {
    private lateinit var activityOpenUserFolderBinding: ActivityOpenUserFolderBinding


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
    }
}