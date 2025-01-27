package com.fadlurahmanfdev.feature_printer

import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnections
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.fadlurahmanfdev.feature_printer.repository.ThermalPrinterRepository

class ThermalPrinter: ThermalPrinterRepository() {
    fun tes(){
        val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
        BluetoothPrintersConnections().list
    }
}