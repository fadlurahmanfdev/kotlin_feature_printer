package com.fadlurahmanfdev.example.presentation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.fadlurahmanfdev.example.R
import com.fadlurahmanfdev.example.data.FeatureModel
import com.fadlurahmanfdev.feature_printer.FeatureBluetoothPrinter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ListExampleAdapter.Callback {
    lateinit var featureBluetooth: FeatureBluetoothPrinter

    private val features: List<FeatureModel> = listOf<FeatureModel>(
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "List Bluetooth Connection",
            desc = "List Bluetooth Connection",
            enum = "LIST_BLUETOOTH_CONNECTION"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Connect to bluetooth device",
            desc = "Connect to bluetooth device",
            enum = "CONNECT_BLUETOOTH_DEVICE"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Initialize Printer",
            desc = "Initialize Printer",
            enum = "INITIALIZE_PRINTER"
        ),
        FeatureModel(
            featureIcon = R.drawable.baseline_developer_mode_24,
            title = "Print Text",
            desc = "Print Text",
            enum = "PRINT_TEXT"
        ),
    )

    private lateinit var rv: RecyclerView
    private lateinit var main: ConstraintLayout

    private lateinit var adapter: ListExampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rv = findViewById<RecyclerView>(R.id.rv)
        main = findViewById(R.id.main)

        rv.setItemViewCacheSize(features.size)
        rv.setHasFixedSize(true)

        adapter = ListExampleAdapter()
        adapter.setCallback(this)
        adapter.setList(features)
        adapter.setHasStableIds(true)
        rv.adapter = adapter

        featureBluetooth = FeatureBluetoothPrinter()

    }

    @SuppressLint("MissingPermission")
    override fun onClicked(item: FeatureModel) {
        when (item.enum) {
            "LIST_BLUETOOTH_CONNECTION" -> {
                val bluetoothDevices = featureBluetooth.getAlreadyPairedBluetoothDevice()
                Log.d(
                    this::class.java.simpleName,
                    "total bluetooth connection: ${bluetoothDevices.size}"
                )
                bluetoothDevices.forEach {
                    Log.d(
                        this::class.java.simpleName,
                        "bluetooth connection: ${it.device.name} & ${it.device.address}"
                    )
                }
            }

            "CONNECT_BLUETOOTH_DEVICE" -> {
                val isConnected = featureBluetooth.connect("DC:0D:51:F5:7E:AD")
                Log.d(this::class.java.simpleName, "is connected: $isConnected")
                if (isConnected) {
                    val snackbar = Snackbar.make(
                        main.rootView,
                        "Successfully connected",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                } else {
                    val snackbar =
                        Snackbar.make(main.rootView, "Unable to connect", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }

            "INITIALIZE_PRINTER" -> {
                featureBluetooth.initializePrinter(203, 48f, 32)
                val snackbar = Snackbar.make(
                    main.rootView,
                    "Successfully initialize printer",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }

            "PRINT_TEXT" -> {
//                featureBluetooth.printText(  "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(featureBluetooth.printer, this.applicationContext.getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM))+"</img>\n" +
//                        "[L]\n" +
//                        "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
//                        "[L]\n" +
//                        "[C]================================\n" +
//                        "[L]\n" +
//                        "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
//                        "[L]  + Size : S\n" +
//                        "[L]\n" +
//                        "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
//                        "[L]  + Size : 57/58\n" +
//                        "[L]\n" +
//                        "[C]--------------------------------\n" +
//                        "[R]TOTAL PRICE :[R]34.98e\n" +
//                        "[R]TAX :[R]4.23e\n" +
//                        "[L]\n" +
//                        "[C]================================\n" +
//                        "[L]\n" +
//                        "[L]<font size='tall'>Customer :</font>\n" +
//                        "[L]Raymond DUPONT\n" +
//                        "[L]5 rue des girafes\n" +
//                        "[L]31547 PERPETES\n" +
//                        "[L]Tel : +33801201456\n" +
//                        "[L]\n" +
//                        "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
//                        "[C]<qrcode size='20'>https://dantsu.com/</qrcode>"
//                )
                ////

                featureBluetooth.printText(
                    "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                        featureBluetooth.printer,
                        this.applicationContext.resources.getDrawableForDensity(
                            R.drawable.monalisa_dither,
                            DisplayMetrics.DENSITY_MEDIUM
                        )
                    ) + "</img>\n" +
                            "[L]\n" +
                            "[C]Taufik Fadlurahman Fajari\n" +
                            "[C]+6281283602320\n" +
                            "[C]<u>linkedin.com/taufikfadlurahmanfajari</u>\n" +
                            "[C]Jakarta Selatan, Indonesia\n" +
                            "[L]\n" +
                            "[C]================================\n" +
                            "[L]\n" +
                            "[L]1 Bebek Madura[R]30.000\n" +
                            "[L]1 Nasi Goreng[R]20.000\n" +
                            "[L]1 Asam Black Tea[R]26.000\n" +
                            "[L]1 Es Tebu[R]18.000\n" +
                            "[L]\n" +
                            "[C]--------------------------------\n" +
                            "[R]TOTAL HARGA :[R]94.000\n" +
                            "[L]\n" +
                            "[L]\n" +
                            "[C]T H A N K  Y O U\n" +
                            "[L]\n" +
                            "[L]\n" +
                            "[C]<qrcode size='15'>https://www.linkedin.com/in/taufikfadlurahmanfajari/</qrcode>\n" +
                            "[L]\n" +
                            "[L]\n" +
                            "[L]\n"
                )
            }
        }
    }
}