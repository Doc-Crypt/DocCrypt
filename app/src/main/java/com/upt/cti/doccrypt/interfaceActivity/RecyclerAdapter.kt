package com.upt.cti.doccrypt.interfaceActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus.*


class RecyclerAdapter(private val filesAndFolders: ArrayList<DocFile>) : RecyclerView.Adapter<RecyclerAdapter.DocFileHolder>() {

    class DocFileHolder(view: View) : RecyclerView.ViewHolder(view) {
        var folderName: TextView
        var folderStatus: ImageView

        init {
            folderStatus = view.findViewById(R.id.folderStatus)
            folderName = view.findViewById(R.id.folder_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocFileHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doc_recycler, parent, false)
        return DocFileHolder(view)
    }

    override fun onBindViewHolder(holder: DocFileHolder, position: Int) {
        holder.folderName.text = filesAndFolders[position].folderName
        when(filesAndFolders[position].folderStatus){
            CHECKED -> holder.folderStatus.setImageResource(R.drawable.checked)
            DENIED ->  holder.folderStatus.setImageResource(R.drawable.denied)
            PENDING -> holder.folderStatus.setImageResource(R.drawable.pending)
        }
    }

    override fun getItemCount() = filesAndFolders.size
}