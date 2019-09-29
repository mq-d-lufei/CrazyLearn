package com.crazy.crazylearn.manager.background.binder.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.crazy.crazylearn.IMusicService;
import com.crazy.crazylearn.manager.background.binder.MyMusicService;

import static android.content.Context.BIND_AUTO_CREATE;

public class MyMusicClient {

    private ServiceConnection connectionService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMusicService iMusicService = IMusicService.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onBindingDied(ComponentName name) {

        }

        @Override
        public void onNullBinding(ComponentName name) {

        }
    };

    public void onBindMusicServer(Context context) {

        Intent intent = new Intent(context, MyMusicService.class);

        context.startService(intent);

        context.bindService(intent, connectionService, BIND_AUTO_CREATE);
    }


}
