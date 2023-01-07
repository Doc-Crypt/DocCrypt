package com.upt.cti.doccrypt.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.entity.DocFile
import com.upt.cti.doccrypt.entity.DocFileStatus.*


class UserRecyclerAdapter(private val filesAndFolders: ArrayList<DocFile>, private val listener: OnItemClickListener) : RecyclerView.Adapter<UserRecyclerAdapter.DocFileHolder>() {
    inner class DocFileHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var folderName: TextView
        var folderStatus: ImageView

        init {
            folderStatus = view.findViewById(R.id.user_folder_status)
            folderName = view.findViewById(R.id.user_folder_name)

            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onDocFileClick(position)
            }
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

    interface OnItemClickListener {
        fun onDocFileClick(position: Int)
    }
}