package com.example.freecalcaccessibility

import android.os.Bundle
import android.widget.GridLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.example.freecalcaccessibility.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var mode: Int = 1
    var panels = emptyArray<GridLayout>()
    var decAccu = 5
    var deg = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.modeSlider.addOnChangeListener { _, value, _ ->
            mode = value.toInt()
            binding.modeSlider.contentDescription = when (mode) {
                1 -> getString(R.string.mode_slider_1)
                2 -> getString(R.string.mode_slider_2)
                3 -> getString(R.string.mode_slider_3)
                4 -> getString(R.string.mode_slider_4)
                5 -> getString(R.string.mode_slider_5)
                6 -> getString(R.string.mode_slider_6)
                else -> getString(R.string.mode_slider_7)
            }
            SwitchMode()
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
        radio_rad.setText(getString(R.string.radian_text))
        radio_deg.setText(getString(R.string.degree_text))
        radModeSelect.addView(radio_rad)
        radModeSelect.addView(radio_deg)
        radModeSelect.check(radio_rad.id)
        radModeSelect.setOnCheckedChangeListener { _, id ->
            deg = id == radio_deg.id
        }
        panels[0].addView(radModeSelect)
        val slider_accu = Slider(this)
        slider_accu.contentDescription = getString(R.string.dec_accu)
        slider_accu.valueFrom = 0f
        slider_accu.valueTo = 15f
        slider_accu.stepSize = 1f
        slider_accu.value = 5f
        slider_accu.addOnChangeListener { _, value, _ ->
            decAccu = value.toInt()
        }
        panels[0].addView(slider_accu)
        val btn_about = MaterialButton(this)
        btn_about.text = getString(R.string.btn_about)
        panels[0].addView(btn_about)
        val layout: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout.topToBottom = R.id.mode_slider
        panels[0].layoutParams = layout
        binding.mainLayout.addView(panels[0])

        // mode 2 Actions
        panels[1].rowCount = 6
        panels[1].columnCount = 1

        // mode 3 Memory

        // mode 4 Numbers

        // mode 5 Operators

        // mode 6 Functions

        // mode 7 Constants

    }

    private fun SwitchMode() {
        for (panel in panels) {
            if (!panel.isGone) {
                panel.isGone = true
                break
            }
        }
        panels[mode-1].isGone = false
    }
}
