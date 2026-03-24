**BLE Toolkit** — A BLE utility app built entirely with [kmp-ble](https://github.com/gary-quinn/kmp-ble).

BLE Toolkit is an open-source Kotlin Multiplatform app (Android + iOS) that provides a full-featured BLE scanner and GATT explorer. It serves as both a practical BLE utility and a reference implementation for the kmp-ble library.

## Features

- **Scanner** — Scan for nearby BLE devices with real-time RSSI updates, name/signal filtering, and automatic device categorization
- **GATT Explorer** — Connect to devices, browse services and characteristics, read/write values, and subscribe to notifications
- **Cross-platform** — Shared UI via Compose Multiplatform, runs on Android and iOS from a single codebase

TODO: add screenshots after Phase 1

## Build

**Android:**
```bash
./gradlew assembleDebug
```

**iOS:**
Open `iosApp/iosApp.xcodeproj` in Xcode, select a physical device target, and run.

> BLE requires a physical device — simulators/emulators do not support Bluetooth.

## Architecture

- Kotlin Multiplatform + Compose Multiplatform for shared UI
- [kmp-ble](https://github.com/gary-quinn/kmp-ble) for all BLE operations
- MVVM with StateFlow-based reactive state
- Structured concurrency with lifecycle-scoped coroutines

## License

See [LICENSE](LICENSE) for details.
