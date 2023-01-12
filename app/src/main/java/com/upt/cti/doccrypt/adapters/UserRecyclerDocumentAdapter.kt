package com.upt.cti.doccrypt.adapters

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.upt.cti.doccrypt.R
import com.upt.cti.doccrypt.authentication_manager.BASE_URL
import com.upt.cti.doccrypt.entity.Document
import com.upt.cti.doccrypt.entity.FileStatus
import com.upt.cti.doccrypt.entity.Folder
import com.upt.cti.doccrypt.interfaceActivity.DocumentView
import com.upt.cti.doccrypt.interfaceActivity.OpenUserFolder

class UserRecyclerDocumentAdapter(private val documentList: ArrayList<Document>, private val listener: UserRecyclerAdapter.OnItemClickListener)
    : RecyclerView.Adapter<UserRecyclerDocumentAdapter.DocFileHolder>(), View.OnClickListener{

    inner class DocFileHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var docName: TextView

        init {
            docName = view.findViewById(R.id.new_doc_name)
            Log.d(ContentValues.TAG, documentList.toString())
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
//                listener.onDocFileClick(position)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocFileHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_folder_doc_recycler, parent, false)
        return DocFileHolder(view)
    }

    override fun onBindViewHolder(holder: DocFileHolder, position: Int) {
        holder.docName.text = documentList[position].Name
        Log.e(ContentValues.TAG, documentList[position].Name)
        var document = documentList[position].fileUrl
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(Uri.parse("http://192.168.0.101:8075/api/v1/admin/candidate/doc/1"),"application/pdf")
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
//        startActivity(intent);
        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(document),"application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = documentList.size



    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}