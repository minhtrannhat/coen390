package com.example.coen390_app.Views;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
public class NotificationsUtils {
    private static final String TAG = "NotificationUtils";

    @SuppressLint("ObsoleteSdkInt")
    public static boolean areNotificationsEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                return notificationManagerCompat.areNotificationsEnabled();
            }
        } else {
            // On versions before Oreo, we can't check per channel, so we check global setting.
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        return false;
    }

    public static void requestNotificationPermission(Context context) {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        context.startActivity(intent);
    }

    public static void checkAndRequestNotificationPermission(Context context) {
        if (!areNotificationsEnabled(context)) {
            Log.d(TAG, "Notifications are not enabled. Requesting permission...");
            Toast.makeText(context, "You must enable notifications for real time parking lot updates", Toast.LENGTH_SHORT).show();
            requestNotificationPermission(context);
        } else {
            Log.d(TAG, "Notifications are enabled.");
        }
    }
}
