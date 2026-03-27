package com.atruedev.bletoolkit.detail

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValueFormatterTest {

    @Test
    fun formatHexDisplaysCorrectly() {
        val data = byteArrayOf(0x01, 0xFF.toByte(), 0xA3.toByte())
        val result = ValueFormatter.format(data, DisplayFormat.HEX)
        assertEquals("01 FF A3", result)
    }

    @Test
    fun formatUtf8DisplaysCorrectly() {
        val data = "Hello".encodeToByteArray()
        val result = ValueFormatter.format(data, DisplayFormat.UTF8)
        assertEquals("Hello", result)
    }

    @Test
    fun formatDecimalDisplaysCorrectly() {
        val data = byteArrayOf(0x01, 0x0A, 0xFF.toByte())
        val result = ValueFormatter.format(data, DisplayFormat.DECIMAL)
        assertEquals("1 10 255", result)
    }

    @Test
    fun formatBinaryDisplaysCorrectly() {
        val data = byteArrayOf(0x01, 0xFF.toByte())
        val result = ValueFormatter.format(data, DisplayFormat.BINARY)
        assertEquals("00000001 11111111", result)
    }

    @Test
    fun parseHexValid() {
        val result = ValueFormatter.parseHex("01 FF A3")
        assertNotNull(result)
        assertEquals(3, result.size)
        assertEquals(0x01.toByte(), result[0])
        assertEquals(0xFF.toByte(), result[1])
        assertEquals(0xA3.toByte(), result[2])
    }

    @Test
    fun parseHexWithoutSpaces() {
        val result = ValueFormatter.parseHex("01FFA3")
        assertNotNull(result)
        assertEquals(3, result.size)
    }

    @Test
    fun parseHexWithColons() {
        val result = ValueFormatter.parseHex("01:FF:A3")
        assertNotNull(result)
        assertEquals(3, result.size)
    }

    @Test
    fun parseHexInvalidReturnsNull() {
        val result = ValueFormatter.parseHex("GG")
        assertNull(result)
    }

    @Test
    fun parseHexOddLengthReturnsNull() {
        val result = ValueFormatter.parseHex("01F")
        assertNull(result)
    }

    @Test
    fun formatEmptyByteArray() {
        val result = ValueFormatter.format(byteArrayOf(), DisplayFormat.HEX)
        assertEquals("", result)
    }
}
