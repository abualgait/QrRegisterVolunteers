/*
 * Created by  Mobile Dev Team  on 1/12/20 12:10 PM
 * Copyright (c) Resala Charity Organization. All rights reserved.
 */

package com.resala.mobile.qrregister.shared.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.resala.mobile.qrregister.R
import java.util.*


class DialogScanSuccessFragment : DialogFragment() {

    private var root_view: View? = null
    private var NAME = ""
    private var EVENT = ""
    private var QRCODE = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            NAME = arguments?.getString("NAME")!!
            EVENT = arguments?.getString("EVENT")!!
            QRCODE = arguments?.getString("QRCODE")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root_view = inflater.inflate(R.layout.dialog_scan_success, container, false)
        (root_view!!.findViewById<View>(R.id.fab) as FloatingActionButton).setOnClickListener { dismiss() }

        return root_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event = root_view!!.findViewById<TextView>(R.id.event)
        val volunteer = root_view!!.findViewById<TextView>(R.id.volunteer)
        val qrcode = root_view!!.findViewById<TextView>(R.id.qrcode)
        val qrcodeimage = root_view!!.findViewById<ImageView>(R.id.qrcodeimage)

        event.text = EVENT
        volunteer.text = NAME
        qrcode.text = QRCODE

        val multiFormatWriter = MultiFormatWriter()
        val hintMap: MutableMap<EncodeHintType, Any> =
            EnumMap<EncodeHintType, Any>(
                EncodeHintType::class.java
            )

        hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hintMap[EncodeHintType.MARGIN] = 1 /* default = 4 */
        hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L


        try {
            val bitMatrix =
                multiFormatWriter.encode(QRCODE, BarcodeFormat.QR_CODE, 600, 600,hintMap)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qrcodeimage.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


}