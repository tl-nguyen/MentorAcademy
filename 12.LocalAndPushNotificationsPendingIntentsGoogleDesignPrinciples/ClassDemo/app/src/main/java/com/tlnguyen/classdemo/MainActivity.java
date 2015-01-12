package com.tlnguyen.classdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RemoteViews;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simpleNotification(View view) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Simple Notification")
                .setContentText("Hello world")
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("NOTIFICATION", "Simple Notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }

    public void customNotification(View view) {
        Bitmap large_icon = BitmapFactory.decodeResource(getResources(), R.drawable.robot_expanded);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Custom Notification")
                .setContentText("Hello From Custom Notification")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(large_icon)
                .setPriority(BIND_ABOVE_CLIENT);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("NOTIFICATION", "Custom Notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(2, notification);
    }

    public void megaCustomNotification(View view) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher);

        Intent secondIntent = new Intent(this, SecondActivity.class);
        secondIntent.putExtra("NOTIFICATION", "Mega Custom Notification");
        PendingIntent pendingIntentToSecondActivity = PendingIntent.getActivity(this, 0, secondIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent googleIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        PendingIntent pendingIntentToGoogle = PendingIntent.getActivity(this, 0, googleIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationBuilder.setContentIntent(pendingIntentToSecondActivity);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);

        contentView.setTextViewText(R.id.tvTitle, "Mega Custom Notification");
        contentView.setTextViewText(R.id.tvDescription, "Hello to Mega Custom Notification");

        RemoteViews contentViewExpand = new RemoteViews(getPackageName(), R.layout.custom_notification_expand);
        contentViewExpand.setTextViewText(R.id.tvTitle, "Mega Custom Notification");
        contentViewExpand.setTextViewText(R.id.tvDescription, "Hello to Mega Custom Notification");
        contentViewExpand.setOnClickPendingIntent(R.id.btnSecondActivity, pendingIntentToSecondActivity);
        contentViewExpand.setOnClickPendingIntent(R.id.btnGoogle, pendingIntentToGoogle);

        Notification notification = notificationBuilder.build();
        notification.contentView = contentView;

        if (Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = contentViewExpand;
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(3, notification);

    }
}
