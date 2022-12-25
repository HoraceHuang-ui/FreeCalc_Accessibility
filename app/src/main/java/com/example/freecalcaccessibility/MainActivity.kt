package com.example.freecalcaccessibility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.RadioButton
import android.widget.RadioGroup
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

    }

    private fun ConfigurePanels() {
        // mode 1 Options
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
        binding.mainLayout.addView(panels[0])
    }
}
