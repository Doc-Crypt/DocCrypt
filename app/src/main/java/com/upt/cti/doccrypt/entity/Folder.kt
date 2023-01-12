package com.upt.cti.doccrypt.entity

data class Folder(val folderName: String,
                  val fileStatus: FileStatus,
                  val folderId: Int,
                  val documentList: ArrayList<Document> )