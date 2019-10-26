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
package com.smarttech.helpme.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * API Location model
 *
 * @category   Data
 * @package    Network
 * @subpackage Model
 * @author     Dmitry Abakumov <killerinshadow2@gmail.com>
 */
public class Location {

    /**
     * Lat
     */
    @SerializedName("lat")
    private Double mLat;

    /**
     * Long
     */
    @SerializedName("lng")
    private Double mLng;

    /**
     * Radius
     */
    @SerializedName("radius")
    private Double mRadius;

    /**
     * Location
     *
     * @param lat    lat
     * @param lng    long
     * @param radius radius
     */
    public Location(Double lat, Double lng, Double radius) {
        mLat = lat;
        mLng = lng;
        mRadius = radius;
    }

}
