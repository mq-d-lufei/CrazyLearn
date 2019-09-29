package com.crazy.crazylearn.manager.background.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.crazy.crazylearn.IMusicService;

public class MyMusicService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new IMusicService.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void start(String filePath) throws RemoteException {

            }

            @Override
            public void setBinder(IBinder binder) throws RemoteException {

            }

            @Override
            public void stop() throws RemoteException {

            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //getSystemService();
    }


}
