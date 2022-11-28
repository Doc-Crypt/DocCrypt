//package com.upt.cti.doccrypt.interfaceActivity
//
//
//import com.upt.cti.doccrypt.R
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.MenuItem
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.PopupMenu
//import android.widget.TextView
//import android.widget.Toast
//
//import java.io.File;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//class UserInterfaceAdapter(context: Context, filesAndFolders: Array<File>) : RecyclerView.Adapter<UserInterfaceAdapter.ViewHolder>() {
//    var context: Context
//    var filesAndFolders: Array<File>
//
//    init {
//        this.context = context
//        this.filesAndFolders = filesAndFolders
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view: View = LayoutInflater.from(context).inflate(R.layout.doc_recycler, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val selectedFile: File = filesAndFolders[position]
//        holder.textView.text = selectedFile.name
//        if (selectedFile.isDirectory) {
//            holder.imageView.setImageResource(R.drawable.folder)
//        } else {
//            holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24)
//        }
//        holder.itemView.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                if (selectedFile.isDirectory) {
//                    val intent = Intent(context, UserInterface::class.java)
//                    val path: String = selectedFile.absolutePath
//                    intent.putExtra("path", path)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    context.startActivity(intent)
//                } else {
//                    //open thte file
//                    try {
//                        val intent = Intent()
//                        intent.action = Intent.ACTION_VIEW
//                        val type = "image/*"
//                        intent.setDataAndType(Uri.parse(selectedFile.absolutePath), type)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        context.startActivity(intent)
//                    } catch (e: Exception) {
//                        Toast.makeText(
//                            context.applicationContext,
//                            "Cannot open the file",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        })
//        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
//            override fun onLongClick(v: View): Boolean {
//                val popupMenu = PopupMenu(context, v)
//                popupMenu.getMenu().add("DELETE")
//                popupMenu.getMenu().add("MOVE")
//                popupMenu.getMenu().add("RENAME")
//                popupMenu.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
//                    PopupMenu.OnMenuItemClickListener {
//                    override fun onMenuItemClick(item: MenuItem): Boolean {
//                        if (item.getTitle().equals("DELETE")) {
//                            val deleted: Boolean = selectedFile.delete()
//                            if (deleted) {
//                                Toast.makeText(
//                                    context.applicationContext,
//                                    "DELETED ",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                v.visibility = View.GONE
//                            }
//                        }
//                        if (item.getTitle().equals("MOVE")) {
//                            Toast.makeText(
//                                context.applicationContext,
//                                "MOVED ",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        if (item.getTitle().equals("RENAME")) {
//                            Toast.makeText(
//                                context.applicationContext,
//                                "RENAME ",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        return true
//                    }
//                })
//                popupMenu.show()
//                return true
//            }
//        })
//    }
//
//    override fun getItemCount(): Int {
//        return filesAndFolders.size
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        var textView: TextView
////        var imageView: ImageView
//
////        init {
////            textView = itemView.findViewById(R.id.file_name_text_view)
////            imageView = itemView.findViewById(R.id.icon_view)
////        }
//    }
//}