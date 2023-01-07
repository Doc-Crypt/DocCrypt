package com.upt.cti.doccrypt.interfaceActivity

import android.annotation.SuppressLint
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.upt.cti.doccrypt.R

open class DrawerBase : AppCompatActivity() {
    protected lateinit var drawerLayout : DrawerLayout
    private lateinit var frameLayout: FrameLayout

    private lateinit var menuBtn : ImageButton
    protected lateinit var notificationBtn : ImageButton

    protected lateinit var drawerLeft : NavigationView
    protected lateinit var drawerRight : NavigationView

    protected lateinit var noNotifications : TextView
    protected lateinit var notificationRV : RecyclerView
    protected lateinit var notificationCnt : TextView

    @SuppressLint("InflateParams")
    override fun setContentView(view: View?) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer_base,null) as DrawerLayout
        frameLayout = drawerLayout.findViewById(R.id.container)
        frameLayout.addView(view)
        super.setContentView(drawerLayout)

        menuBtn = drawerLayout.findViewById(R.id.menu_btn)

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

        notificationBtn = drawerLayout.findViewById(R.id.notification_btn)

        drawerLeft = drawerLayout.findViewById(R.id.nav_menu)
        drawerRight = drawerLayout.findViewById(R.id.nav_notification)

        noNotifications = findViewById(R.id.no_notifications)
        notificationRV = findViewById(R.id.notification_recycler)
        notificationCnt = findViewById(R.id.notification_count)
    }
 }