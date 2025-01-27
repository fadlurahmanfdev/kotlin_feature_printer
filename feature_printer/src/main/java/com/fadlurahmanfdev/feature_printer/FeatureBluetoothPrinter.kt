package com.fadlurahmanfdev.feature_printer

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.fadlurahmanfdev.feature_printer.core.FeaturePrinterErrorConstant
import com.fadlurahmanfdev.feature_printer.exception.FeaturePrinterException
import com.fadlurahmanfdev.feature_printer.repository.FeatureBluetoothPrinterRepository

class FeatureBluetoothPrinter : FeatureBluetoothPrinterRepository {
    private val bluetoothPrinterConnection = BluetoothPrintersConnections()

    private var _macAddressConnected: String? = null
    private var _bluetoothConnection: BluetoothConnection? = null
    private var _isConnected: Boolean = false

    var printer: EscPosPrinter? = null

    override fun getAlreadyPairedBluetoothDevice(): List<BluetoothConnection> {
        return bluetoothPrinterConnection.list?.toList() ?: listOf()
    }

    override fun connect(macAddress: String): Boolean {
        if (_isConnected && _macAddressConnected == macAddress && _bluetoothConnection?.isConnected == true) {
            Log.i(this::class.java.simpleName, "already connected to same mac address")
            return true
        }

        if (_isConnected && _macAddressConnected != macAddress && _bluetoothConnection?.isConnected == true) {
            Log.i(
                this::class.java.simpleName,
                "already connected with different mac address, trying to disconnect with existing connection"
            )
            disconnect()
        }

        var device: BluetoothDevice? = null
        for (element in getAlreadyPairedBluetoothDevice()) {
            if (element.device.address == macAddress) {
                device = element.device
                break
            }
        }

        val bluetoothConnection = BluetoothConnection(device)
        try {
            bluetoothConnection.connect()
            _isConnected = true
            _macAddressConnected = macAddress
            _bluetoothConnection = bluetoothConnection
        } catch (e: Throwable) {
            Log.e(this::class.java.simpleName, "failed to connect bluetooth device: ${e.message}")
            _isConnected = false
            _macAddressConnected = null
            _bluetoothConnection = null
        }

        return _isConnected
    }

    override fun disconnect() {
        if (_isConnected && _bluetoothConnection?.isConnected == true) {
            try {
                _bluetoothConnection?.disconnect()
                _isConnected = false
                _bluetoothConnection = null
                Log.i(this::class.java.simpleName, "successfully disconnect bluetooth")
            } catch (e: Throwable) {
                _isConnected = false
                _bluetoothConnection = null
            }
            return
        }

        Log.w(this::class.java.simpleName, "already have connection with older connection")
    }

    override fun initializePrinter(
        printerDpi: Int,
        printerWidthMM: Float,
        printerNbrCharactersPerLine: Int
    ) {
        if (_bluetoothConnection == null) {
            throw FeaturePrinterException(
                code = FeaturePrinterErrorConstant.PRINTER_NOT_INITIALIZED.code,
                message = FeaturePrinterErrorConstant.PRINTER_NOT_INITIALIZED.message
            )
        }

        printer = EscPosPrinter(
            _bluetoothConnection!!,
            printerDpi,
            printerWidthMM,
            printerNbrCharactersPerLine
        )
    }

    override fun printText(text: String) {
        if (printer == null) {
            throw FeaturePrinterException(
                code = FeaturePrinterErrorConstant.PRINTER_NOT_INITIALIZED.code,
                message = FeaturePrinterErrorConstant.PRINTER_NOT_INITIALIZED.message
            )
        }
        printer!!.printFormattedText(text, 0)
    }
}