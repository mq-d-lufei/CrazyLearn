// IMusicService.aidl
package com.crazy.crazylearn;

// Declare any non-default types here with import statements

interface IMusicService {
    /**
     * Demonstrates some basic types that you can uonTransactse as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void start(String filePath);

    void setBinder(IBinder binder);

    void stop();
}
