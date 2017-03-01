package com.cs478.main.fragmentproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Varshini on 3/16/2016.
 */

//This class receives broadcasted intents and calls the corresponding activity
public class chicagoHandler extends BroadcastReceiver {
    public void onReceive(Context context,Intent intent)    {
        System.out.println("Received!!");
        //check if the extra "value" has chicago
        if(intent.getExtras().get("value").equals("Chicago")) {
            System.out.println("Received chicago");
            //start the ChicagoActivity
            Intent startAct = new Intent();
            startAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startAct.setClass(context.getApplicationContext(), MainActivity.class);
            context.startActivity(startAct);
        }
        else {
            System.out.println("Received Indiana");
            //start the IndianaActivity
            Intent startAct = new Intent();
            startAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startAct.setClass(context.getApplicationContext(), IndianaActivity.class);
            context.startActivity(startAct);
        }
    }
}
