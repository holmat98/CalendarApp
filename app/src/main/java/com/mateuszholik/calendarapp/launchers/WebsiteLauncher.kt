package com.mateuszholik.calendarapp.launchers

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

interface WebsiteLauncher {

    fun launchUrl(url: String)

    fun launchMaps(location: String)

    fun launchEmail(email: String)
}

internal class WebsiteLauncherImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : WebsiteLauncher {

    override fun launchUrl(url: String) {
        val correctUrl = if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
            url
        } else {
            "$HTTPS$url"
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(correctUrl)
            flags = FLAG_ACTIVITY_NEW_TASK
        }

        try {
            context.startActivity(intent)
        } catch (exception: Exception) {
            Timber.e(exception, "Error while opening url")
        }
    }

    override fun launchMaps(location: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("http://maps.google.co.in/maps?q=$location")
            flags = FLAG_ACTIVITY_NEW_TASK
        }

        try {
            context.startActivity(intent)
        } catch (exception: Exception) {
            Timber.e(exception, "Error while opening maps")
        }
    }

    override fun launchEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            flags = FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        try {
            context.startActivity(intent)
        } catch (exception: Exception) {
            Timber.e(exception, "Error while opening mail application")
        }
    }

    private companion object {
        const val HTTPS = "https://"
        const val HTTP = "http://"
    }
}
