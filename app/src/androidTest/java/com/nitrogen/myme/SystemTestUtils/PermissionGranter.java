package com.nitrogen.myme.SystemTestUtils;

import android.Manifest;
import android.os.Build;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class PermissionGranter {

    public static boolean allowPermissionsIfNeeded() {

        boolean permissionsNeeded = false;

        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = device.findObject(new UiSelector().text("Allow"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                    permissionsNeeded = true;
                } catch (UiObjectNotFoundException e) {
                   System.out.print("There is no permissions dialog to interact with ");
                }
                finally{
                    return permissionsNeeded;
                }
            }
        }
        return permissionsNeeded;
    }


}

