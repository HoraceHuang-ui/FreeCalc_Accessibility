package com.example.freecalcaccessibility

import android.os.Bundle
import android.widget.GridLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.freecalcaccessibility.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var mode: Int = 0
    var panels = emptyArray<GridLayout>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.modeSlider.addOnChangeListener { _, value, _ ->
            mode = value.toInt()
            binding.modeSlider.contentDescription = "Currently on" + mode
        }
        panels = Array(7) { GridLayout(this) }
        ConfigurePanels()
    }

    private fun ConfigurePanels() {
        // mode 1 Options     Not completed
        panels[0].rowCount = 5
        panels[0].columnCount = 1
        val radModeSelect = RadioGroup(this)
        val radio_rad = RadioButton(this)
        val radio_deg = RadioButton(this)
        radio_rad.setText("Radian")
        radio_deg.setText("Degree")
        radModeSelect.addView(radio_rad)
        radModeSelect.addView(radio_deg)
        radModeSelect.setOnCheckedChangeListener { it, id ->
            it.announceForAccessibility("Selected " + id)
        }
        panels[0].addView(radModeSelect)
        var slider_accu = Slider(this)
        slider_accu.contentDescription = "Decimal Accuracy"
        var layout: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout.topToBottom = R.id.mode_slider
        panels[0].layoutParams = layout
        binding.mainLayout.addView(panels[0])

        // mode 2 Actions

        // mode 3 Memory

        // mode 4 Numbers

        // mode 5 Operators

        // mode 6 Functions

        // mode 7 Constants


    }
}
