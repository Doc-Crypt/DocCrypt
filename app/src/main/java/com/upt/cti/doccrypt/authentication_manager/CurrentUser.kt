package com.upt.cti.doccrypt.authentication_manager

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.upt.cti.doccrypt.entity.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object CurrentUser {
    var currentFolderId: Int = 0
    var userFullName:String? = null
    var currentFolder: Int = 0
    var folders = ArrayList<Folder>()

    fun getFolders(context: Context, username: String){
//        folders = ArrayList<Folder>()
        var foldersTmp = ArrayList<Folder>()
        var result: JSONArray? = null
        val params = HashMap<String, String>()
        params["username"] = username

        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/admin/customer/folders/",
            jsonRequest,
            { response ->
                Log.d(ContentValues.TAG, "Response: %s".format(response.toString()))
                result = JSONObject(response.toString()).get("folders") as JSONArray?
                Log.d(ContentValues.TAG, result.toString())
                val length = result?.length()?.minus(1)
                for (i in 0 ..length!!){
                    var folder = JSONObject(result?.get(i).toString())
                    val folderName = folder.get("fileName").toString()
                    val fileStatus: FileStatus = when(folder.get("fileStatus")){
                        "CHECKED" -> FileStatus.CHECKED
                        "DENIED" -> FileStatus.DENIED
                        else -> FileStatus.PENDING
                    }
                    val documentList = ArrayList<Document>()
                    try {
                        val documentListJSON = JSONArray(folder.get("documentDtoList").toString())
                        var len = documentListJSON.length() - 1

                        for (j in 0 ..len!!){
                            val doc = JSONObject(documentListJSON?.get(j).toString())
                            val docName = doc.get("fileName").toString()
                            val docStatus: FileStatus = when(doc.get("fileStatus")){
                                "CHECKED" -> FileStatus.CHECKED
                                "DENIED" -> FileStatus.DENIED
                                else -> FileStatus.PENDING
                            }

                            documentList.add(Document(docName, docStatus, BASE_URL + doc.get("file").toString()))
                        }
                    }catch (e: Exception){
                        Log.e(ContentValues.TAG, "Field doesn't exist")
                    }
                    val id = folder.get("id").toString().toInt()
                    foldersTmp.add(Folder(folderName, fileStatus, id, documentList))
                }
                Log.e(ContentValues.TAG, foldersTmp.toString())
                if(folders.isEmpty()){
                    folders = foldersTmp;
                }
            },
            { error ->
                Log.e(ContentValues.TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun createFolder(context: Context, folderName: String){
        val params = HashMap<String, String>()
        params["fileName"] = folderName
        params["ownerUsername"] = AuthenticationManager.getUsername()!!

        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/admin/customer_creation_folder",
            jsonRequest,
            { response ->
                Log.d(ContentValues.TAG, "Response: %s".format(response.toString()))
            },
            { error ->
                Log.e(ContentValues.TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clearCurrentUser(){
        userFullName = ""
        folders = ArrayList<Folder>()
    }

    @Throws(IOException::class)
    fun sendDocumentInFolder(input: ByteArray?, context: Context, username: String, fileName: String, folderId: Int): String? {
        val path = context.filesDir
        val letDirectory = File(path, "LET")
        letDirectory.mkdirs()
        val file = File(letDirectory, "Records.pdf")
        FileOutputStream(file).use {
            it.write(input)
        }
        val viewModel = DocumentViewModel()
        return viewModel.uploadFile(file, username, fileName, folderId).toString()
    }

    fun postFolder(context: Context){
        val params = HashMap<String, String>()
        params["id"] = currentFolderId.toString()
        params["ownerUsername"] = AuthenticationManager.getUsername()!!

        Log.d(ContentValues.TAG, params.toString())

        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/admin/customer_post_Folder",
            jsonRequest,
            { response ->
                Log.d(ContentValues.TAG, "Response: %s".format(response.toString()))
            },
            { error ->
                Log.e(ContentValues.TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
    }
}