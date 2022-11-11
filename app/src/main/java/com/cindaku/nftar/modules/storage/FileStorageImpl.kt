package com.cindaku.nftar.modules.storage

import android.content.Context
import android.os.Environment
import com.cindaku.nftar.APP_NAME
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileStorageImpl @Inject constructor(private val context: Context): FileStorage {
    override fun newMovie(fileName: String): File {
        val root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        val dir = File(root!!.path, APP_NAME)
        return dir.run {
            if(!this.exists()){
                this.mkdirs()
            }
            val file=File(
                dir,
                fileName
            )
            file
        }
    }

    override fun newData(fileName: String): File {
        val dir = context.getExternalFilesDir(null)
        return dir.run {
            if(!this!!.exists()){
                this.mkdirs()
            }
            val file=File(
                dir,
                fileName
            )
            file
        }
    }
}