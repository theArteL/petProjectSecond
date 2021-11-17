package com.artelsv.petprojectsecond.utils

import org.junit.Assert.*

import org.junit.Test

class EmptyCheckerTest {

    @Test
    fun isStringEmpty() {
        assertEquals(false, EmptyChecker.isStringEmpty("a"))
        assertEquals(false, EmptyChecker.isStringEmpty("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"))
        assertEquals(true, EmptyChecker.isStringEmpty(null))
        assertEquals(false, EmptyChecker.isStringEmpty("\uD83D\uDE0B\uD83D\uDE0B\uD83D\uDE0B\uD83D\uDE0B"))
    }
}