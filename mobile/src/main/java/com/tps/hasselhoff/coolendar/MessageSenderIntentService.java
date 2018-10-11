package com.tps.hasselhoff.coolendar;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessageSenderIntentService extends IntentService {


    private static final String START_ACTIVITY_PATH = "/start-activity";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MessageSenderIntentService() {
        super("MessageSenderService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int notificationId = intent.getIntExtra(CalendarContract.Events._ID,-1);
        String title = intent.getStringExtra(CalendarContract.Events.TITLE);
        Log.i("Reçu intent service",title);
        NotificationManager mNotificationManager =
                (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationId);
        this.sendMessage(title);
    }

    /**
     * Methode used to retrieve all the node for broadcasting message
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Collection<String> getNodes() throws ExecutionException, InterruptedException {
        HashSet<String> results = new HashSet<>();
        List<Node> nodes =
                Tasks.await(Wearable.getNodeClient(getBaseContext()).getConnectedNodes());
        for (Node node : nodes) {
            results.add(node.getId());
        }
        return results;
    }
    private void sendMessage(String title){
        byte[] bytes = title.getBytes(StandardCharsets.UTF_8);
        try {
            for(String transcriptionNodeId : getNodes())
                Wearable.getMessageClient(getBaseContext()).sendMessage(transcriptionNodeId, START_ACTIVITY_PATH, bytes);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
