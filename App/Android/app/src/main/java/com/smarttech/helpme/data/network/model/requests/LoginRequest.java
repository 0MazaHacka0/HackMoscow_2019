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
 * @category   data
 * @package    network
 * @subpackage model
 * @copyright  Copyright (C) 2019 Project HelpMe (https://github.com/0MazaHacka0/HackMoscow_2019)
 * @license    http://opensource.org/licenses/osl-3.0.php  Open Software License (OSL 3.0)
 */
package com.smarttech.helpme.data.network.model.requests;

import com.google.gson.annotations.SerializedName;

/**
 * API login request model
 *
 * @category   Data
 * @package    Network
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class LoginRequest extends CommonRequest {

    /**
     * Login
     */
    @SerializedName("login")
    String mLogin;

    /**
     * Password
     */
    @SerializedName("password")
    String mPassword;

    /**
     * Login request
     *
     * @param authToken auth token
     * @param login     login
     * @param password  password
     */
    public LoginRequest(String authToken, String login, String password) {
        mAuthToken = authToken;
        mLogin = login;
        mPassword = password;
    }

}
