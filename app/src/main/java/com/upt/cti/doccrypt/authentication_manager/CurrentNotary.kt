package com.upt.cti.doccrypt.authentication_manager

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.upt.cti.doccrypt.entity.Document
import com.upt.cti.doccrypt.entity.FileStatus
import com.upt.cti.doccrypt.entity.Folder
import org.json.JSONArray
import org.json.JSONObject

object CurrentNotary {
    var currentFolderId: Int = 0
    var currentPublicFolderId: Int = 0
    var isPersonalFolder: Boolean = false
    var currentFolder: Int = 0
    var publicFolders = ArrayList<Folder>()
    var personalFolders = ArrayList<Folder>()

    fun getFolders(context: Context, username: String){
        var resultPersonalFolders: JSONArray? = null
        var resultPublicFolders: JSONArray? = null
        val params = HashMap<String, String>()
        params["username"] = username

        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/admin/notary/allFolders",
            jsonRequest,
            { response ->
//                Log.d(ContentValues.TAG, "Response: %s".format(response.toString()))
//              ------------------------------------------------------------------------
                var publicFoldersTmp = ArrayList<Folder>()
                var personalFoldersTmp = ArrayList<Folder>()
                resultPersonalFolders = JSONObject(response.toString()).get("publicFolder") as JSONArray?
                Log.d(ContentValues.TAG, resultPersonalFolders.toString())
                var length = resultPersonalFolders?.length()?.minus(1)
                for (i in 0 ..length!!){
                    var folder = JSONObject(resultPersonalFolders?.get(i).toString())
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
                    publicFoldersTmp.add(Folder(folderName, fileStatus, id, documentList))
                    if(publicFolders.isEmpty()){
                        publicFolders = publicFoldersTmp;
                    }
                }
                Log.d(ContentValues.TAG, publicFolders.toString())
//              ------------------------------------------------------------------------
                resultPublicFolders = JSONObject(response.toString()).get("personalFolder") as JSONArray?
                Log.d(ContentValues.TAG, resultPublicFolders.toString())
                length = resultPublicFolders?.length()?.minus(1)
                for (i in 0 ..length!!){
                    var folder = JSONObject(resultPublicFolders?.get(i).toString())
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

                    personalFoldersTmp.add(Folder(folderName, fileStatus, id, documentList))
                    if(personalFolders.isEmpty()){
                        personalFolders = personalFoldersTmp;
                    }
                }
                Log.d(ContentValues.TAG, personalFolders.toString())
            },
            { error ->
                Log.e(ContentValues.TAG, "Error: %s".format(error.toString()))
            }
        )
        queue.add(jsonObjectRequest)
    }

    fun getFolderToPersonalList(context: Context){
        val params = HashMap<String, String>()
        params["id"] = currentPublicFolderId.toString()
        params["ownerUsername"] = AuthenticationManager.getUsername()!!

        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/admin/notary/addToPersonal",
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

    fun approveFolder(context: Context, status: String) {
        val params = HashMap<String, String>()
        params["fileStatus"] = status
        params["id"] = currentFolderId.toString()

        val queue = Volley.newRequestQueue(context)
        var jsonRequest = JSONObject(params.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            "$BASE_URL/api/v1/admin/notary/approveFolder",
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
        currentFolderId = 0
        isPersonalFolder = false
        currentFolder = 0
        publicFolders = ArrayList<Folder>()
        personalFolders = ArrayList<Folder>()
    }
}