package com.atruedev.bletoolkit.peripheral.server

sealed interface ServerState {
    data object Stopped : ServerState
    data object Running : ServerState
    data class Error(val message: String) : ServerState
}

enum class CharProperty { READ, WRITE, NOTIFY }

data class ServerCharConfig(
    val uuid: String = "",
    val properties: Set<CharProperty> = emptySet(),
    val initialValue: String = "",
)

data class ServerServiceConfig(
    val uuid: String = "",
    val characteristics: List<ServerCharConfig> = emptyList(),
)

data class GattServerUiState(
    val state: ServerState = ServerState.Stopped,
    val services: List<ServerServiceConfig> = emptyList(),
    val connectedClients: Int = 0,
    val error: String? = null,
)

enum class ServerPreset(
    val label: String,
    val services: List<ServerServiceConfig>,
) {
    HEART_RATE_SENSOR(
        label = "Heart Rate Sensor",
        services = listOf(
            ServerServiceConfig(
                uuid = "0000180d-0000-1000-8000-00805f9b34fb",
                characteristics = listOf(
                    ServerCharConfig(
                        uuid = "00002a37-0000-1000-8000-00805f9b34fb",
                        properties = setOf(CharProperty.NOTIFY),
                    ),
                    ServerCharConfig(
                        uuid = "00002a38-0000-1000-8000-00805f9b34fb",
                        properties = setOf(CharProperty.READ),
                    ),
                ),
            ),
        ),
    ),
    BATTERY_SERVICE(
        label = "Battery Service",
        services = listOf(
            ServerServiceConfig(
                uuid = "0000180f-0000-1000-8000-00805f9b34fb",
                characteristics = listOf(
                    ServerCharConfig(
                        uuid = "00002a19-0000-1000-8000-00805f9b34fb",
                        properties = setOf(CharProperty.READ, CharProperty.NOTIFY),
                    ),
                ),
            ),
        ),
    ),
    UART_SERVICE(
        label = "Nordic UART (NUS)",
        services = listOf(
            ServerServiceConfig(
                uuid = "6e400001-b5a3-f393-e0a9-e50e24dcca9e",
                characteristics = listOf(
                    ServerCharConfig(
                        uuid = "6e400002-b5a3-f393-e0a9-e50e24dcca9e",
                        properties = setOf(CharProperty.WRITE),
                    ),
                    ServerCharConfig(
                        uuid = "6e400003-b5a3-f393-e0a9-e50e24dcca9e",
                        properties = setOf(CharProperty.NOTIFY),
                    ),
                ),
            ),
        ),
    ),
    DEVICE_INFO(
        label = "Device Information",
        services = listOf(
            ServerServiceConfig(
                uuid = "0000180a-0000-1000-8000-00805f9b34fb",
                characteristics = listOf(
                    ServerCharConfig(
                        uuid = "00002a29-0000-1000-8000-00805f9b34fb",
                        properties = setOf(CharProperty.READ),
                    ),
                    ServerCharConfig(
                        uuid = "00002a24-0000-1000-8000-00805f9b34fb",
                        properties = setOf(CharProperty.READ),
                    ),
                ),
            ),
        ),
    ),
}
