package com.crazy.crazylearn.manager.background.binder;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CustomMusicService extends Binder {

    /**
     * @param code  客户端与服务端统一的标识，客户端期望服务端调用哪个函数
     * @param data  客户端传递数据
     * @param reply 服务端返回数据
     * @param flags 两种：双向:0 服务端执行完后会返回数据， 1:单向 不返回任何数据
     * @return
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {

        boolean result;

        switch (code) {
            case 100:
                data.enforceInterface("CustomMusicService");
                result = play(data.readString());
                if (null != reply)
                    reply.writeString("123");
                break;
            case 200:
                result = play("");
                break;
            default:
                result = super.onTransact(code, data, reply, flags);
        }

        return result;
    }

    public boolean play(String fileName) {
        return true;
    }

    public boolean stop() {
        return true;
    }

}
