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
package com.smarttech.helpme.data.hardware;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.smarttech.helpme.data.hardware.model.Location;
import com.smarttech.helpme.data.hardware.model.Permissions;

import io.reactivex.Observable;

/**
 * App hardware helper
 *
 * @category   Data
 * @package    Hardware
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class AppHardwareHelper implements HardwareHelper {

    /**
     * Context
     */
    private Context mContext;

    /**
     * Permission model
     *
     * @see Permissions
     */
    private Permissions mPermissions;

    /**
     * Location model
     *
     * @see Location
     */
    private Location mLocation;

    /**
     * Location constructor
     *
     * @param context context
     */
    public AppHardwareHelper(Context context) {

        // Context
        mContext = context;

        // Setup models
        setupModels();

    }

    /**
     * Setup models
     */
    private void setupModels() {

        // Permission model
        mPermissions = new Permissions(mContext);

        // Location model
        mLocation = new Location(mContext);
    }

    /**
     * Check permissions
     *
     * @return has required permissions
     */
    public boolean checkPermissions() {
        return mPermissions.checkPermissions();
    }

    /**
     * Request permissions
     *
     * @param activity activity where show dialog
     */
    public void requestPermissions(AppCompatActivity activity) {
        mPermissions.requestPermissions(activity);
    }

    /**
     * Get location observable
     *
     * @return location observable
     */
    public Observable<android.location.Location> getLocation() {
        return mLocation.getLocation();
    }

}
