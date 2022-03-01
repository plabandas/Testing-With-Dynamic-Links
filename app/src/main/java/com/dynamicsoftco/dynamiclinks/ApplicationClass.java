package com.dynamicsoftco.dynamiclinks;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {


    private static final String ONESIGNAL_APP_ID = "fa04c422-d6ac-4797-88fb-b969965f7252";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);


        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }


}
