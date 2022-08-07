package com.tc2r1.innovate.pilotfeedback.repository

/** Dessert Data **/

data class FeedbackRequestBody(
    val feedback_category: String,
    val feedback_rating: Int,
    val feedback_text: String,
    val loyalty_id: Int,
    val store_id: Int
)