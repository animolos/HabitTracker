package com.example.habittracker.color_picker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.habittracker.R
import java.lang.ClassCastException

class ColorPickerDialog: DialogFragment() {

    lateinit var buttons: ArrayList<View>
    private lateinit var onInputListener: OnInputListener

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.color_picker, container, false)
        buttons = view.findViewById<LinearLayout>(R.id.color_buttons).touchables
        buttons.forEach { it.setOnClickListener { returnColorCode(it as Button) } }
        return view
    }

    private fun returnColorCode(colorButton: Button){
        val background = colorButton.currentHintTextColor
        onInputListener.sendColor(background)
        dismiss()
    }

    interface OnInputListener {
        fun sendColor(color: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            onInputListener = parentFragment as OnInputListener
        }
        catch (err: ClassCastException) {
            Log.e("color_picker", err.toString())
        }
    }
}