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
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zircon.app.R;
import com.zircon.app.ui.login.SplashActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getData().get(notificationPanel.IARGS.BODY));
        Bitmap image = null;
        String messageimage = remoteMessage.getData().get(notificationPanel.IARGS.IMAGE);
        String messagetitle = remoteMessage.getData().get(notificationPanel.IARGS.TITLE);
        String messagebody = remoteMessage.getData().get(notificationPanel.IARGS.BODY);
        String messageicon = remoteMessage.getData().get(notificationPanel.IARGS.ICON);
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
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this);
        Intent resultIntent;
        if (!TextUtils.isEmpty(messageimage)) {
            android.support.v4.app.NotificationCompat.BigPictureStyle notiStyle = new android.support.v4.app.NotificationCompat.BigPictureStyle();
            notiStyle.setSummaryText(messagebody);
            notiStyle.bigPicture(image);
            mBuilder.setStyle(notiStyle);
            resultIntent = new Intent(this, notificationPanel.class);
            resultIntent.putExtra(notificationPanel.IARGS.BODY, messagebody);
            resultIntent.putExtra(notificationPanel.IARGS.TITLE, messagetitle);
            resultIntent.putExtra(notificationPanel.IARGS.IMAGE, messageimage);
        } else {
            resultIntent = new Intent(this, SplashActivity.class);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messagetitle)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent)
                .setContentText(messagebody);

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

}
