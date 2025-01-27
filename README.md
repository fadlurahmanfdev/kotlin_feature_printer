# Description

Library to handle bluetooth thermal printer

## Key Features

### Get list already paired bluetooth devices

Get list paired bluetooth device that later will be connected

```kotlin
val featureBluetooth:FeatureBluetoothPrinterRepository = FeatureBluetoothPrinter()
fun screenFunction() {
    val pairedBluetoothDevices = featureBluetooth.getAlreadyPairedBluetoothDevice()
    // process list paired bluetooth devices
}
```

### Connect

Connect app to a bluetooth printer device using parameter mac address.

```kotlin
val featureBluetooth:FeatureBluetoothPrinterRepository = FeatureBluetoothPrinter()
fun screenFunction() {
    val isConnected = featureBluetooth.connect(macAddress = "{mac-address}")
    // process connected bluetooth printer
}
```

### Initialize Printer

initialize printer using custom specification bluetooth printer

#### Example Spec Bluetooth Printer
<img src="https://raw.githubusercontent.com/fadlurahmanfdev/kotlin_feature_printer/master/assets/example_thermal_printer_spec.png"/>

#### Calculate DPI
<img src="https://raw.githubusercontent.com/fadlurahmanfdev/kotlin_feature_printer/master/assets/calculate_dpi.png"/>

```kotlin
val featureBluetooth:FeatureBluetoothPrinterRepository = FeatureBluetoothPrinter()
fun screenFunction() {
    featureBluetooth.initializePrinter(
        printerDpi = 203,
        printerWidthMM = 48f,
        printerNbrCharactersPerLine = 32,
    )
    // process initialized printer
}
```