package com.upt.cti.doccrypt.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.entity.FolderStatus.PUBLIC


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
        when(filesAndFolders[position].folderStatus){
            PUBLIC -> holder.folderName.text = filesAndFolders[position].folderName
            else -> {}
        }
    }

    override fun getItemCount() = filesAndFolders.size
}