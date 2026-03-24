package com.atruedev.bletoolkit.scanner

import com.atruedev.kmpble.scanner.uuidFrom
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class DeviceCategoryClassifierTest {

    @Test
    fun heartRateServiceClassifiesCorrectly() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("180d")))
        assertEquals(DeviceCategory.HEART_RATE, result)
    }

    @Test
    fun batteryServiceClassifiesCorrectly() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("180f")))
        assertEquals(DeviceCategory.BATTERY, result)
    }

    @Test
    fun bloodPressureServiceClassifiesCorrectly() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("1810")))
        assertEquals(DeviceCategory.BLOOD_PRESSURE, result)
    }

    @Test
    fun cyclingSpeedServiceClassifiesCorrectly() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("1816")))
        assertEquals(DeviceCategory.CYCLING, result)
    }

    @Test
    fun cyclingPowerServiceClassifiesCorrectly() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("1818")))
        assertEquals(DeviceCategory.CYCLING, result)
    }

    @Test
    fun deviceInfoServiceClassifiesCorrectly() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("180a")))
        assertEquals(DeviceCategory.DEVICE_INFO, result)
    }

    @Test
    fun emptyListClassifiesAsUnknown() {
        val result = DeviceCategoryClassifier.classify(emptyList())
        assertEquals(DeviceCategory.UNKNOWN, result)
    }

    @Test
    fun unknownServiceClassifiesAsUnknown() {
        val result = DeviceCategoryClassifier.classify(listOf(uuidFrom("ffff")))
        assertEquals(DeviceCategory.UNKNOWN, result)
    }

    @Test
    fun heartRateTakesPriorityOverBattery() {
        val result = DeviceCategoryClassifier.classify(
            listOf(uuidFrom("180f"), uuidFrom("180d"))
        )
        assertEquals(DeviceCategory.HEART_RATE, result)
    }
}
