package ru.alxr.serviceexp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
		      Intent serviceLauncher = new Intent(context, MyServ.class);
		      context.startService(serviceLauncher);
		    }

	}

}
