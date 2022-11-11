package com.cindaku.nftar.modules.storage

import java.io.File

interface FileStorage {
    fun newMoview(fileName: String): File
}