package com.example.habittracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment

class ColorSelectionDialogFragment: DialogFragment() {

    private var onColorSelectedListener: OnColorSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.color_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayout>(R.id.color_buttons).touchables.forEach { button ->
            button.setOnClickListener {
                sendColor(it as Button)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onColorSelectedListener = context as OnColorSelectedListener?
    }

    private fun sendColor(button: Button) {
        val background = button.currentHintTextColor
        onColorSelectedListener?.onColorSelected(background)
        dismiss()
    }
}