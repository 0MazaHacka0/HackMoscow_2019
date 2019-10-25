/**
 * Project HelpMe
 *
 * NOTICE OF LICENSE
 *
 * This source file is subject to the Open Software License (OSL 3.0)
 * that is bundled with this package in the file LICENSE.txt.
 * It is also available through the world-wide-web at this URL:
 * http://opensource.org/licenses/osl-3.0.php
 *
 * DISCLAIMER
 *
 * Do not edit or add to this file if you wish to upgrade
 * the HelpMe to newer versions in the future.
 * If you wish to customize the HelpMe for your needs
 * please refer to https://github.com/0MazaHacka0/HackMoscow_2019
 * for more information.
 *
 * @category   UI
 * @package    Base
 * @subpackage None
 * @copyright  Copyright (C) 2019 Project HelpMe (https://github.com/0MazaHacka0/HackMoscow_2019)
 * @license    http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 */
package com.smarttech.helpme.data.hardware.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

/**
 * App hardware helper
 *
 * @category   Data
 * @package    Hardware
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class Permissions {

    /**
     * Permission ACCESS_COARSE_LOCATION request code
     */
    public static final int PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE = 1;

    /**
     * Permission ACCESS_BACKGROUND_LOCATION request code
     */
    public static final int PERMISSION_ACCESS_BACKGROUND_LOCATION_REQUEST_CODE = 2;

    /** @var mContext Context context **/
    private Context mContext;

    /**
     * Permission ACCESS_COARSE_LOCATION status
     */
    private boolean permissionAccessCoarseLocationApproved = false;

    /**
     * Permission ACCESS_BACKGROUND_LOCATION status
     */
    private boolean backgroundLocationPermissionApproved = false;

    /**
     * Permissions class constructor
     *
     * @param context context
     */
    public Permissions(Context context) {
        mContext = context;
    }

    /**
     * Check permissions for location detect
     *
     * @return permission status
     */
    public boolean checkPermissions() {

        permissionAccessCoarseLocationApproved = ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        return permissionAccessCoarseLocationApproved && backgroundLocationPermissionApproved;
    }

    /**
     * Request permissions
     *
     * @param activity activity where show dialog
     */
    public void requestPermissions(Activity activity) {

        // App doesn't have access to the device's location at all. Make full request
        // for permission
        if (!permissionAccessCoarseLocationApproved) {
            ActivityCompat.requestPermissions(activity, new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
            }, PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE);
        }

        // App can only access location in the foreground. Display a dialog
        // warning the user that your app must have all-the-time access to
        // location in order to function properly. Then, request background
        // location
        if (!backgroundLocationPermissionApproved) {
            ActivityCompat.requestPermissions(activity, new String[] {
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    PERMISSION_ACCESS_BACKGROUND_LOCATION_REQUEST_CODE);
        }
    }

}
