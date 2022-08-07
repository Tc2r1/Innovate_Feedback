package com.tc2r1.innovate.pilotfeedback.repository



import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * service
 * Pilot Flying J
 *
 * Created by Nudennie White aka Tc2r on 2022...
 * Copyright © 2022 Pilot Flying J. All rights reserved.
 *
 */
interface RestAPI {
    @POST("guest-feedback")
    @Headers(
        "accept: application/json",
        "Content-type:application/json",
        "x-api-key: b0a8222f-ee6c-bf51-3d87-623cff48a38b"
    )
    fun submitGuestFeedback(@Body request: FeedbackRequestBody): Call<FeedBackResponse>
}