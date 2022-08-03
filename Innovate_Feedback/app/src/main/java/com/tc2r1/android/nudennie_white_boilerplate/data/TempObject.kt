package com.tc2r1.android.nudennie_white_boilerplate.data

import java.time.Year

/** Dessert Data **/

/**
 * Simple data class that represents a data object. Includes a [name]
 * and [birthYear] of the person.
 * [name] - A [String] Variable that stores the Full Name of the person.
 * [birthYear] - A [Year] variable that stores the year the person was born.
 */
data class TempObject(val name: String, val birthYear: Year)