package com.fadlurahmanfdev.feature_printer.exception

data class FeaturePrinterException(
    val code: String,
    override val message: String?
) : Throwable(message = message)