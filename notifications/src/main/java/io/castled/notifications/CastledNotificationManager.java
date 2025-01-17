package io.castled.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.castled.notifications.consts.NotificationEventType;
import io.castled.notifications.consts.NotificationFields;
import io.castled.notifications.logger.CastledLogger;
import io.castled.notifications.service.models.NotificationEvent;

public class CastledNotificationManager {

    public static boolean isCastledNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        if (data.containsKey(NotificationFields.CASTLED_KEY)) {
            return true;
        }
        CastledLogger.getInstance().debug("Push message not from Castled!");
        return false;
    }

    public static boolean handleNotification(Context context, RemoteMessage remoteMessage) {

        if (!CastledNotificationManager.isCastledNotification(remoteMessage)) {
            return false;
        }
        // Payload from Castled server
        CastledLogger.getInstance().debug("handling castled notification...");

        CastledNotificationEventBuilder eventBuilder = new CastledNotificationEventBuilder(context);
        NotificationEvent event = eventBuilder.buildEvent(remoteMessage.getData());

        if(CastledNotifications.getInstance().isAppInForeground()) {
            //Ignore the notification, mark event as foreground and report!
            event.setEventType(NotificationEventType.FOREGROUND);
        } else {
            CastledNotificationBuilder notificationBuilder = new CastledNotificationBuilder(context);
            Notification notification = notificationBuilder.buildNotification(remoteMessage.getData(), event.clickEvent());

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(event.notificationId, notification);
        }
        reportNotificationEvent(event);
        return true;
    }

    public static void reportNotificationEvent(NotificationEvent event) {
        CastledNotifications.getInstance().reportNotificationEvent(event);
    }

    public static String getOrCreateNotificationChannel(Context context, String channelId, String channelName, String channelDesc) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                channel.setDescription(channelDesc);
                notificationManager.createNotificationChannel(channel);
            }
        }

        return channelId;
    }

}
