package com.example.task6

import org.junit.Assert.*
import org.junit.Test
import com.example.task6.validName


class UnitTests {
    @Test
    fun test_name_is_valid() {
        assertTrue(validName("Mr Kelvin"))
    }

    @Test
    fun test_name_isNot_valid() {
        assertFalse(validName(""))
    }

    @Test
    fun test_number_is_valid() {
        assertTrue(validName("08094577382"))
    }

    @Test
    fun test_number_isNot_valid() {
        assertFalse(validName(""))
    }
}