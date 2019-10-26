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
package com.smarttech.helpme.ui.splash;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.smarttech.helpme.R;
import com.smarttech.helpme.data.hardware.AppHardwareHelper;
import com.smarttech.helpme.data.hardware.model.Permissions;
import com.smarttech.helpme.ui.base.BaseActivity;
import com.smarttech.helpme.ui.login.LoginActivity;
import com.smarttech.helpme.ui.main.MainActivity;

import static com.smarttech.helpme.data.hardware.model.Permissions.PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE;

/**
 * Hardware model interface
 *
 * @category   Data
 * @package    Hardware
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class SplashActivity extends BaseActivity {

    /**
     * App hardware helper
     */
    AppHardwareHelper mAppHardwareHelper;

    /**
     * On create activity
     *
     * @param savedInstanceState bundle saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAppHardwareHelper = new AppHardwareHelper(getApplicationContext());
        nextActivity();
        checkPermissions();
    }

    /**
     * Check permissions
     */
    private void checkPermissions() {
        // Check permissions
        if (!mAppHardwareHelper.checkPermissions()) {
            mAppHardwareHelper.requestPermissions(this);
        }
    }

    // TODO: Close app if user didn't grant permissions
    // TODO: Check permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // TODO: Show message, why user have to grant permission
        switch(requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION_REQUEST_CODE:
            case Permissions.PERMISSION_ACCESS_BACKGROUND_LOCATION_REQUEST_CODE:
                if (true || grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    nextActivity();
                    break;
            default:
                break;
        }
    }

    /**
     * Start next activity
     */
    private void nextActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }

}
