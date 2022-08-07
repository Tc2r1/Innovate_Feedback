package com.tc2r1.innovate.pilotfeedback.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * response
 * Pilot Flying J
 *
 * Created by Nudennie White aka Tc2r on 2022...
 * Copyright © 2022 Pilot Flying J. All rights reserved.
 *
 */
@Serializable
data class FeedBackResponse(
    @SerialName("application_id")
    val applicationId: String, // gljdk9dsa7
    @SerialName("datetime")
    val datetime: Double, // 1659582200.0
    @SerialName("feedback_category")
    val feedbackCategory: String, // Store Showers
    @SerialName("feedback_good")
    val feedbackGood: Boolean, // false
    @SerialName("feedback_rating")
    val feedbackRating: Int, // 3
    @SerialName("feedback_text")
    val feedbackText: String, // Lorem ipsum...
    @SerialName("loyalty_id")
    val loyaltyId: Int, // 12345678
    @SerialName("store_id")
    val storeId: Int // 219
)