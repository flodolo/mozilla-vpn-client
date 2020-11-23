/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.mozilla.vpn

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationUtil() {
    companion object{
        var sCurrentText =  "Starting..."
        var sCurrentTitle = "Mozilla VPN"
        var sCurrentSec = 0;
        var sCurrentContext : Context? = null;
        private var sNotificationBuilder : NotificationCompat.Builder? = null;

        val NOTIFICATION_CHANNEL_ID = "com.mozilla.vpnNotification"
        val CONNECTED_NOTIFICATION_ID = 1337

        // Gets called from AndroidNotificationHandler.cpp
        fun update(title: String, message: String, sec: Int){
            sCurrentTitle = title;
            sCurrentText = message;
            sCurrentSec = sec
            update();
        }

        fun update(){
            if(sCurrentContext == null) return;
            val notificationManager: NotificationManager = sCurrentContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            sNotificationBuilder?.let {
                it.setContentTitle(sCurrentTitle)
                .setContentText(sCurrentText)
                notificationManager.notify(CONNECTED_NOTIFICATION_ID, it.build())
            }

        }

        /*
        * Creates a new Notification using the current set of Strings
        * Shows the notification in the given {context}
        */
        fun show(service :VPNService){
            sNotificationBuilder = NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID);
            sCurrentContext = service;
            val notificationManager: NotificationManager = sCurrentContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // From Oreo on we need to have a "notification channel" to post to.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "vpn"
                val descriptionText = "  "
                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                notificationManager.createNotificationChannel(channel)
            }
            // Create the Intent that Should be Fired if the User Clicks the notification
            val mainActivityName = "org.qtproject.qt5.android.bindings.QtActivity";
            val activity = Class.forName(mainActivityName);
            val intent = Intent(service, activity)
            val pendingIntent = PendingIntent.getActivity(service, 0, intent, 0)
            // Build our notification
            sNotificationBuilder?.let {
                it.setSmallIcon(com.mozilla.vpn.R.drawable.ic_logo_on)
                    .setContentTitle(sCurrentTitle)
                    .setContentText(sCurrentText)
                    .setOnlyAlertOnce(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)

                service.startForeground(CONNECTED_NOTIFICATION_ID, it.build())
            }
        }
    }
}