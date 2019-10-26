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

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationRequest;
import com.smarttech.helpme.utils.AppConstants;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * App hardware helper
 *
 * @category   Data
 * @package    Hardware
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class Location {

    /**
     * Context
     *
     * @see Context
     */
    private Context mContext;

    /**
     * Location request
     *
     * @see LocationRequest
     */
    private LocationRequest mLocationRequest;

    /**
     * Location manager
     */
    private LocationManager mLocationManager;

    /**
     * Latest location
     */
    private final PublishSubject<android.location.Location> mLocation = PublishSubject.create();

    /**
     * Location listener
     *
     * @see android.location.LocationListener
     */
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            mLocation.onNext(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }

        @Override
        public void onProviderEnabled(String s) { }

        @Override
        public void onProviderDisabled(String s) { }
    };

    /**
     * Location model constructor
     *
     * @param context context
     */
    public Location(Context context) {
        mContext = context;
        setupLocationManager();
        setupLocationRequest();
    }

    /**
     * Setup location request
     */
    private void setupLocationRequest() {
        mLocationRequest = LocationRequest
                .create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(AppConstants.SCANNING_LOCATION_PERIOD);
    }

    private void setupLocationManager() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Get location observable
     *
     * @return location observable
     */
    public Observable<android.location.Location> getLocation() {

        String locationProvider;

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            locationProvider = LocationManager.PASSIVE_PROVIDER;
        }

        locationProvider = LocationManager.NETWORK_PROVIDER;

        mLocationManager.requestLocationUpdates(locationProvider, AppConstants.SCANNING_LOCATION_PERIOD, 0, mLocationListener);
        return mLocation;
    }

}
