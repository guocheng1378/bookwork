package com.novelreader.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast

object ClipboardHelper {

    fun copyText(context: Context, text: String, label: String = "novel_text") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.setPrimaryClip(ClipData.newPlainText(label, text))
        triggerHaptic(context)
    }

    fun copyTitle(context: Context, title: String) {
        copyText(context, title, "chapter_title")
        Toast.makeText(context, "标题已复制", Toast.LENGTH_SHORT).show()
    }

    fun copyContent(context: Context, content: String, wordCount: Int) {
        copyText(context, content, "chapter_content")
        Toast.makeText(context, "内容已复制（约${wordCount}字）", Toast.LENGTH_SHORT).show()
    }

    private fun triggerHaptic(context: Context) {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(VibrationEffect.createOneShot(50, 30))
                } else {
                    @Suppress("DEPRECATION")
                    it.vibrate(50)
                }
            }
        } catch (_: Exception) {}
    }
}
