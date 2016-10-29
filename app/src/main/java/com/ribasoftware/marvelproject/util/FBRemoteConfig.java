package com.ribasoftware.marvelproject.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

/**
 * Created by ribamar on 28/10/16.
 */

public class FBRemoteConfig {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public FBRemoteConfig(){
        fbRemoteConfig();
        fetchConfig();
    }

    private void fbRemoteConfig(){
        // instance
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
    }

    private void fetchConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
                .isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mFirebaseRemoteConfig.activateFetched();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("RMS", "Error fetching config: " +
                                e.getMessage());
                    }
                });
    }

    public String getKeyValue(String key){
        return mFirebaseRemoteConfig.getString(key);
    }

}
