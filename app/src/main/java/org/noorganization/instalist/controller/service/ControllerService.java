package org.noorganization.instalist.controller.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * The Service to handle all write and update actions related to the database.
 * This will be executed in the service thread to prevent the UI from struggeling.
 * Created by Tino on 16.10.2015.
 */
public class ControllerService extends IntentService {

    public ControllerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


}
