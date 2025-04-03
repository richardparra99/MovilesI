package com.example.practicacomponentes.ui.Components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import com.example.practicacomponentes.databinding.NumberPickertLayoutBinding


class NumberPicker(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val binding: NumberPickertLayoutBinding
    private var onValueChangeListener: ((Int) -> Unit)? = null

    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = NumberPickertLayoutBinding.inflate(
            inflater,
            this,
            true
        )
        setupEventListerner()
        readXml(attrs)
    }

    private fun setupEventListerner() {
        setupButtonColors(binding.btn1, 1)
        setupButtonColors(binding.btn2, 2)
        setupButtonColors(binding.btn3, 3)
        setupButtonColors(binding.btn4, 4)
        setupButtonColors(binding.btn5, 5)
    }

    var selectBoton: Button? = null
        set(selectBoton) {
            field = selectBoton
            onValueChangeListener?.invoke(selectBoton?.text.toString().toInt())
        }

    private fun setupButtonColors(button: Button){
        button.setOnClickListener {
            selectBoton?.setBackgroundColor(Color.BLUE)
            button.setBackgroundColor(Color.GREEN)
            selectBoton = button
        }
    }

    private fun readXml(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        context.withStyledAttributes(
            attrs,
            com.example.practicacomponentes.R.styleable.NumberPicker
        ) {
            val initialValue =
                getInteger(com.example.practicacomponentes.R.styleable.NumberPicker_initialValue, 0)
            setSelectedValue(initialValue)
            selectBoton?.setBackgroundColor(Color.GREEN)
        }
    }

    fun setSelectedValue(value: Int) {
        selectBoton?.setBackgroundColor(Color.BLUE)
        selectBoton = when (value) {
            1 -> binding.btn1
            2 -> binding.btn2
            3 -> binding.btn3
            4 -> binding.btn4
            5 -> binding.btn5
            else -> null
        }
        selectBoton?.setBackgroundColor(Color.GREEN)
    }

    private fun setupButtonColors(button: Button, value: Int) {
        button.setBackgroundColor(Color.BLUE)
        button.setOnClickListener {
            selectBoton?.setBackgroundColor(Color.BLUE)
            button.setBackgroundColor(Color.GREEN)
            selectBoton = button
        }
    }

    fun setOnValueChangeListener(listener: (Int) -> Unit) {
        onValueChangeListener = listener
    }
}