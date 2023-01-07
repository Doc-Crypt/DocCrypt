package com.upt.cti.doccrypt.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.entity.FolderStatus.*


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
        when(filesAndFolders[position].folderStatus){
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
    }

    override fun getItemCount() = filesAndFolders.size
}