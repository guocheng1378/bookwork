package com.novelreader.data.parser

import java.io.InputStream
import java.util.zip.ZipInputStream

object ZipParser {

    data class ZipEntry(
        val name: String,
        val inputStreamProvider: () -> InputStream
    )

    /**
     * Scan a zip file and return all supported book files (txt, epub) inside it.
     * Each entry provides a lazy InputStream provider so we don't load everything at once.
     */
    fun scan(inputStream: InputStream): List<ZipEntry> {
        val entries = mutableListOf<ZipEntry>()
        // First pass: collect all bytes
        val data = mutableMapOf<String, ByteArray>()
        val zis = ZipInputStream(inputStream)
        var entry = zis.nextEntry
        while (entry != null) {
            if (!entry.isDirectory) {
                val name = entry.name
                val lowerName = name.lowercase()
                if (lowerName.endsWith(".txt") || lowerName.endsWith(".epub")) {
                    // Skip macOS metadata and hidden files
                    if (!name.startsWith("__MACOSX") && !name.substringAfterLast('/').startsWith(".")) {
                        data[name] = zis.readBytes()
                    }
                }
            }
            entry = zis.nextEntry
        }
        zis.close()

        for ((name, bytes) in data) {
            entries.add(ZipEntry(
                name = name.substringAfterLast('/'),
                inputStreamProvider = { bytes.inputStream() }
            ))
        }
        return entries
    }
}
