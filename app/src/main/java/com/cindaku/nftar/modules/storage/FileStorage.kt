package com.cindaku.nftar.modules.storage

import java.io.File

interface FileStorage {
    fun newMovie(fileName: String): File
    fun newData(fileName: String): File
}