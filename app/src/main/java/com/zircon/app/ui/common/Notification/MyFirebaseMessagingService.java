package com.zircon.app.ui.common.Notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zircon.app.R;
import com.zircon.app.ui.common.Notification.notificationPanel;

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

        android.support.v4.app.NotificationCompat.BigPictureStyle notiStyle = new android.support.v4.app.NotificationCompat.BigPictureStyle();
        notiStyle.setSummaryText(messagebody);
        notiStyle.bigPicture(image);
        android.support.v7.app.NotificationCompat.Builder mBuilder =
                (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setContentTitle(messagetitle).setSound(defaultSoundUri)
                        .setContentText(messagebody).setStyle(notiStyle);

        Intent resultIntent = new Intent(this, notificationPanel.class);
        resultIntent.putExtra("msgbody", messagebody);
        resultIntent.putExtra("msgtitle", messagetitle);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
//*

/*
        //It is optional
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getData().get("title"));

        //Calling method to generate notification
//        sendNotification(remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getData().get("main_picture"), remoteMessage.getData().get("body"),
                remoteMessage.getData().get("title"), remoteMessage.getData().get("icon"));

        sendNotification(remoteMessage.getData().get("main_picture"), remoteMessage.getNotification().getBody(),
                remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getIcon());
*/

    }

    //This method is only generating push notification
    //It is same as we did in earlier posts 
    private void sendNotification(String PicUrl, String messageBody, String Title, String icon) {
        Intent intent = new Intent(this, notificationPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap image = null;
        URL url = null;
        try {
            url = new URL(PicUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.e("Bitmap", " map: " + image.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        notiStyle.setSummaryText(messageBody);
        notiStyle.bigPicture(image);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent).setStyle(notiStyle);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
