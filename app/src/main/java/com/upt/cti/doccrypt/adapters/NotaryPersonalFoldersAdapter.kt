package com.upt.cti.doccrypt.adapters
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.CurrentNotary
import com.upt.cti.doccrypt.authentication_manager.CurrentUser
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.entity.FileStatus.*
import com.upt.cti.doccrypt.interfaceActivity.OpenUserFolder


class NotaryPersonalFoldersAdapter(private val filesAndFolders: ArrayList<Folder>) : RecyclerView.Adapter<NotaryPersonalFoldersAdapter.PersonalFolderHolder>() {

    class PersonalFolderHolder(view: View) : RecyclerView.ViewHolder(view) {
        var folderName: TextView
        var folderStatus: ImageView

        init {
            folderStatus = view.findViewById(R.id.notary_personal_folder_status)
            folderName = view.findViewById(R.id.notary_personal_folder_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalFolderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.personal_folder_recycler, parent, false)
        return PersonalFolderHolder(view)
    }

    override fun onBindViewHolder(holder: PersonalFolderHolder, position: Int) {
        holder.folderName.text = filesAndFolders[position].folderName
        when(filesAndFolders[position].fileStatus){
            CHECKED -> {
                holder.folderStatus.setImageResource(R.drawable.checked)
                holder.folderStatus.setBackgroundResource(R.color.green)
            }
            DENIED -> {
                holder.folderStatus.setImageResource(R.drawable.denied)
                holder.folderStatus.setBackgroundResource(R.color.red)
            }
            PENDING -> {
                holder.folderStatus.setImageResource(R.drawable.pending)
                holder.folderStatus.setBackgroundResource(R.color.yellow)
            }
            else -> {}
        }
        holder.itemView.setOnClickListener {
            CurrentNotary.isPersonalFolder = true
            CurrentNotary.currentFolder = position
            CurrentNotary.currentFolderId = filesAndFolders[position].folderId
            val intent = Intent(holder.itemView.context, OpenUserFolder::class.java)
            intent.putExtra("Folder_Name", filesAndFolders[position].folderName)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int{
        Log.e(TAG, filesAndFolders.size.toString())
        return filesAndFolders.size
    }
}