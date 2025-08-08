package com.avirajsharma.recipeapp.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.regex.Pattern

object YouTubeHelper {

    /**
     * Extracts YouTube video ID from various YouTube URL formats
     */
    fun extractVideoId(url: String): String? {
        val patterns = listOf(
            "(?<=watch\\?v=)[^&\\n]*",       // watch?v=VIDEO_ID
            "(?<=youtu.be/)[^?\\n]*",        // youtu.be/VIDEO_ID
            "(?<=embed/)[^?\\n]*",           // embed/VIDEO_ID
            "(?<=v/)[^?\\n]*",               // v/VIDEO_ID
            "(?<=shorts/)[^?\\n]*"           // shorts/VIDEO_ID
        )

        for (pattern in patterns) {
            val compiledPattern = Pattern.compile(pattern)
            val matcher = compiledPattern.matcher(url)
            if (matcher.find()) {
                return matcher.group()
            }
        }
        return null
    }

    /**
     * Opens YouTube video in YouTube app if available, otherwise opens in browser
     */
    @SuppressLint("QueryPermissionsNeeded")
    fun openYouTubeVideo(context: Context, video: String) {
        val videoUrl = video.trim()

        // Detect if it's a full URL or just an ID
        val finalUrl = if (videoUrl.startsWith("http://") || videoUrl.startsWith("https://")) {
            videoUrl
        } else {
            "https://www.youtube.com/watch?v=$videoUrl"
        }

        val uri = Uri.parse(finalUrl)

        try {
            // Try opening in YouTube app
            context.startActivity(Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.youtube")
            })
        } catch (e: ActivityNotFoundException) {
            // Fallback: open in browser
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
    /**
     * Checks if the URL is a YouTube URL
     */
    fun isYouTubeUrl(url: String): Boolean {
        return url.contains("youtube.com") || url.contains("youtu.be") || url.contains("m.youtube.com")
    }
}