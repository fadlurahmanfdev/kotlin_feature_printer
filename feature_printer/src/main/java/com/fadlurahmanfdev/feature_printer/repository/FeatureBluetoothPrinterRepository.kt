package com.fadlurahmanfdev.feature_printer.repository

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection

interface FeatureBluetoothPrinterRepository {
    fun getAlreadyPairedBluetoothDevice(): List<BluetoothConnection>
    fun connect(macAddress: String): Boolean
    fun disconnect()
    fun initializePrinter(
        printerDpi: Int,
        printerWidthMM: Float,
        printerNbrCharactersPerLine: Int
    )
    fun printText(text: String)
}