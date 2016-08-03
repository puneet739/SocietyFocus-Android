package com.zircon.app.ui.common.Notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zircon.app.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getData().get("body"));
        Bitmap image = null;
        String messageimage = remoteMessage.getData().get("main_picture");
        String messagetitle = remoteMessage.getData().get("title");
        String messagebody = remoteMessage.getData().get("body");
        String messageicon = remoteMessage.getData().get("icon");
        URL url = null;
        try {
            url = new URL(messageimage);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.e("Bitmap", " map: " + image.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent resultIntent = new Intent(this, notificationPanel.class);
        resultIntent.putExtra("msgbody", messagebody);
        resultIntent.putExtra("msgtitle", messagetitle);
        resultIntent.putExtra("main_picture", messageimage);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        android.support.v4.app.NotificationCompat.BigPictureStyle notiStyle = new android.support.v4.app.NotificationCompat.BigPictureStyle();
        notiStyle.setSummaryText(messagebody);
        notiStyle.bigPicture(image);
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setContentTitle(messagetitle)
                        .setSound(defaultSoundUri)
                        .setContentIntent(resultPendingIntent)
                        .setContentText(messagebody).setStyle(notiStyle);

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

}
