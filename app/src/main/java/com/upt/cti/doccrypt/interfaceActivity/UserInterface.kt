package com.upt.cti.doccrypt.interfaceActivity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus
import com.upt.cti.doccrypt.entity.DocFileStatus.*

class UserInterface : AppCompatActivity() {
    private lateinit var noFilesText : TextView
    private lateinit var docRV : RecyclerView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_interface)

        // Navdrawer related
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout_left)
        val navView : NavigationView = findViewById(R.id.nav_menu_left)

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_upload_doc-> Toast.makeText(applicationContext, "Click upload", Toast.LENGTH_SHORT).show()
                R.id.menu_take_photo-> Toast.makeText(applicationContext, "Click photo", Toast.LENGTH_SHORT).show()
                R.id.menu_send_doc-> Toast.makeText(applicationContext, "Click sebd", Toast.LENGTH_SHORT).show()
            }
            true
        }

        // Recycler related
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListOfDoc(): ArrayList<DocFile>{
        val list =  ArrayList<DocFile>()
        for (i in 0 .. 30){
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