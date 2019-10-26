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
package com.smarttech.helpme.data.network;

import com.smarttech.helpme.data.network.model.requests.LoginRequest;
import com.smarttech.helpme.data.network.model.requests.RegisterRequest;
import com.smarttech.helpme.data.network.model.responses.LoginResponse;
import com.smarttech.helpme.data.network.model.responses.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * API model interface
 *
 * @category   Data
 * @package    Network
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public interface ApiHelper {

    /**
     * Api login
     *
     * @param loginRequest login request
     *
     * @return login response
     */
    @POST("/api/login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    /**
     * Api register
     *
     * @param registerRequest register request
     *
     * @return register response
     */
    @POST("/api/register")
    Observable<RegisterResponse> register(@Body RegisterRequest registerRequest);

}
