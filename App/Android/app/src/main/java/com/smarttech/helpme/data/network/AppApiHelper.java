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
package com.smarttech.helpme.data.network;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smarttech.helpme.utils.AppConstants.API_BASE_URL;

/**
 * API app helper
 *
 * @category   Data
 * @package    Network
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class AppApiHelper {

    /**
     * Api helper
     */
    private static ApiHelper sApiInterface = null;

    /**
     * Create retrofit adapter
     *
     * @return retrofit adapter
     */
    @NonNull
    private static ApiHelper createRetrofitAdapter() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();

        // Set data converter
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(ApiHelper.class);
    }

    /**
     * Singleton for ApiInterface
     *
     * @return ApiInterface instance
     */
    public static ApiHelper getRetrofitAdapter() {
        if (sApiInterface == null)
            sApiInterface = createRetrofitAdapter();
        return sApiInterface;
    }

}
