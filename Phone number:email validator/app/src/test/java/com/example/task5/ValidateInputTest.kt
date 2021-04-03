package com.example.task5

import org.junit.Assert.*
import org.junit.Test

class MainActivityUnitTest {
    @Test
    fun test_phoneNumber_startingWithZero_isValid() {
        val phone = "07056446785"
        assertTrue(validNumber(phone))
    }

    @Test
    fun test_phoneNumber_startingWith234_isValid() {
        val phone = "2347056446785"
        assertTrue(validNumber(phone))
    }

    @Test
    fun test_phoneNumber_NotStartingWithZeroOr234_isInvalid() {
        var phone = "67056446785"
        assertFalse(validNumber(phone))
    }

    @Test
    fun test_phoneNumber_startingWithZero_but_lessThanElevenDigits_isInvalid() {
        val phone = "07056446"
        assertFalse(validNumber(phone))
    }

    @Test
    fun test_phoneNumber_startingWith234_but_lessThanThirteenDigits_isInvalid() {
        val phone = "234705644690"
        assertFalse(validNumber(phone))
    }

    @Test
    fun test_email_isValid() {
        val email = "augustine@mail.com"
        assertTrue(validEmail(email))
    }


}