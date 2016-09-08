package drumm.sangsu.drumm;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.Request;

/**
 * Implementation of App Widget functionality.
 */
public class DrumWidget extends AppWidgetProvider {

    private static final String ACTION_DRUM_ON ="drumm.sangsu.drumm.ON";
    private static final String ACTION_DRUM_OFF ="drumm.sangsu.drumm.OFF";
    private ComponentName drumWidget;
    private RemoteViews views = null;




    public static int drumControl = 0;
    private Intent intent;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        drumWidget = new ComponentName(context, DrumWidget.class);
        views = new RemoteViews(context.getPackageName(), R.layout.drum_widget);

        if(drumControl == 0){ //  수신 불가능

            views.setImageViewResource(R.id.drum_btn, R.drawable.off);

            intent = new Intent(ACTION_DRUM_ON);
            Log.e("Widget State", ""+drumControl);

        }else if(drumControl == 1){  //  수신 가능

            views.setImageViewResource(R.id.drum_btn, R.drawable.on);

            intent = new Intent(ACTION_DRUM_OFF);
            Log.e("Widget State",""+drumControl);
        }

        // Flash Intent
        PendingIntent onPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.drum_btn, onPendingIntent);

        appWidgetManager.updateAppWidget(drumWidget, views);
    }


    @Override
    public void onEnabled(Context context) { // 처음 위젯 생성시 token 받고 서버로 전송
        super.onEnabled(context);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String token =  FirebaseInstanceId.getInstance().getToken();
        Log.e("token", token);
        NetworkManager.getInstance().send_token(MyApplication.getContext(), token, new NetworkManager.OnResultListener<SendTokenResult>() {
            @Override
            public void onSuccess(Request request, SendTokenResult result) { // 성공시
                Log.e("success", "success");
            }

            @Override
            public void onFail(Request request, IOException exception) { // 실패시
                Log.e("fail", exception.toString());
            }
        });

    }



    @Override
    public void onDisabled(Context context) {
        Log.e("disable", "disable");
        // Enter relevant functionality for when the last widget is disabled
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        String action = intent.getAction();

        if(action.equals(ACTION_DRUM_ON)){

            try{

                drumControl=1;
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, DrumWidget.class)));

            }catch (Exception e) {
                // TODO: handle exception
                Log.e("Drum state", "Drum ON Exception");
            }
        }else if(action.equals(ACTION_DRUM_OFF)){

            try{
                drumControl=0;
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, DrumWidget.class)));

            }catch (Exception e) {
                // TODO: handle exception
                Log.e("Drum state", "Drum OFF Exception");
            }
        }else{
            super.onReceive(context, intent);
        }
    }
}

