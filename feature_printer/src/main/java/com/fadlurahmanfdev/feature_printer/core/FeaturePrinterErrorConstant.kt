package com.fadlurahmanfdev.feature_printer.core

import com.fadlurahmanfdev.feature_printer.exception.FeaturePrinterException

object FeaturePrinterErrorConstant {
    val NO_BLUETOOTH_DEVICE_CONNECTED = FeaturePrinterException(code = "NO_BLUETOOTH_DEVICE_CONNECTED", message = "no bluetooth device connected yet")
    val PRINTER_NOT_INITIALIZED = FeaturePrinterException(code = "PRINTER_NOT_INITIALIZED", message = "printer not initialized yet")
}