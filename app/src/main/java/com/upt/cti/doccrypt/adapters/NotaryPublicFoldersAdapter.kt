package com.upt.cti.doccrypt.adapters
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.CurrentNotary
import com.upt.cti.doccrypt.authentication_manager.CurrentUser
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.entity.FileStatus.PUBLIC
import com.upt.cti.doccrypt.interfaceActivity.OpenUserFolder


class NotaryPublicFoldersAdapter(private val filesAndFolders: ArrayList<Folder>) : RecyclerView.Adapter<NotaryPublicFoldersAdapter.PublicFolderHolder>() {

    class PublicFolderHolder(view: View) : RecyclerView.ViewHolder(view) {
        var folderName: TextView

        init {
            folderName = view.findViewById(R.id.notary_public_folder_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicFolderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.public_folder_recycler, parent, false)
        return PublicFolderHolder(view)
    }

    override fun onBindViewHolder(holder: PublicFolderHolder, position: Int) {
        when(filesAndFolders[position].fileStatus){
            PUBLIC -> holder.folderName.text = filesAndFolders[position].folderName
            
            else -> {}
        }

        holder.itemView.setOnClickListener{
            CurrentNotary.isPersonalFolder = false
            CurrentNotary.currentFolder = position
            CurrentNotary.currentPublicFolderId = filesAndFolders[position].folderId
            val intent = Intent(holder.itemView.context, OpenUserFolder::class.java)
            intent.putExtra("Folder_Name", filesAndFolders[position].folderName)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = filesAndFolders.size
}