package com.example.freecalcaccessibility

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.example.freecalcaccessibility.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var mode: Int = 1
    var panels = emptyArray<GridLayout>()
    var decAccu = 5
    var deg = false
    var mem = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqForm.inputType = EditorInfo.TYPE_NULL

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
            switchMode()
        }
        val mode_formatter = LabelFormatter {
            when (it) {
                1f -> getString(R.string.mode_slider_1)
                2f -> getString(R.string.mode_slider_2)
                3f -> getString(R.string.mode_slider_3)
                4f -> getString(R.string.mode_slider_4)
                5f -> getString(R.string.mode_slider_5)
                6f -> getString(R.string.mode_slider_6)
                else -> getString(R.string.mode_slider_7)
            }
        }
        binding.modeSlider.setLabelFormatter(mode_formatter)
        panels = Array(7) { GridLayout(this) }
        configurePanels()
    }

    @SuppressLint("SetTextI18n")
    private fun configurePanels() {
        // mode 1 Options
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

        val layout0: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout0.topToBottom = R.id.mode_slider
        panels[0].layoutParams = layout0
        binding.mainLayout.addView(panels[0])

        // mode 2 Actions
        panels[1].rowCount = 6
        panels[1].columnCount = 1
        val btn_read = MaterialButton(this)
        btn_read.text = getString(R.string.btn_read)
        btn_read.setOnClickListener {

        }
        panels[1].addView(btn_read)
        val btn_calc = MaterialButton(this)
        btn_calc.text = getString(R.string.btn_calc)
        btn_calc.setOnClickListener {

        }
        panels[1].addView(btn_calc)
        val btn_clear = MaterialButton(this)
        btn_clear.text = getString(R.string.btn_clear)
        btn_clear.setOnClickListener {
            binding.eqForm.setText("_")
            binding.eqForm.setSelection(0)
        }
        panels[1].addView(btn_clear)
        val btn_back = MaterialButton(this)
        btn_back.text = getString(R.string.btn_backsp)
        btn_back.setOnClickListener {
            val s = binding.eqForm.text.toString()
            val temp = binding.eqForm.selectionStart
            if (temp != 0) {
                binding.eqForm.setText(
                    s.substring(0 until binding.eqForm.selectionStart - 1) + s.substring(
                        binding.eqForm.selectionEnd
                    )
                )
                binding.eqForm.setSelection(temp - 1)
            }
        }
        panels[1].addView(btn_back)
        val btn_lCur = MaterialButton(this)
        btn_lCur.text = getString(R.string.btn_lCur)
        btn_lCur.setOnClickListener {
            val temp = binding.eqForm.selectionStart
            if (temp != 0) {
                val s = binding.eqForm.text.toString()
                binding.eqForm.setText(s.substring(0 until temp-1) + "_" + s[temp-1] + s.substring(temp+1))
                binding.eqForm.setSelection(temp - 1)
            }
        }
        btn_lCur.setOnLongClickListener {
            val temp = binding.eqForm.selectionStart
            if (temp != 0) {
                val s = binding.eqForm.text.toString()
                binding.eqForm.setText("_" + s.substring(0 until temp-1) + s[temp-1] + s.substring(temp+1))
                binding.eqForm.setSelection(0)
            }
            true
        }
        panels[1].addView(btn_lCur)
        val btn_rCur = MaterialButton(this)
        btn_rCur.text = getString(R.string.btn_rCur)
        btn_rCur.setOnClickListener {
            val temp = binding.eqForm.selectionStart
            if (binding.eqForm.length() != 0 && temp != binding.eqForm.length()-1) {
                val s = binding.eqForm.text.toString()
                binding.eqForm.setText(s.substring(0 until temp) + s[temp+1] + "_" + s.substring(temp+2))
                binding.eqForm.setSelection(temp + 1)
            }
        }
        btn_rCur.setOnLongClickListener {
            val temp = binding.eqForm.selectionStart
            if (binding.eqForm.length() != 0 && temp != binding.eqForm.length()-1) {
                val s = binding.eqForm.text.toString()
                binding.eqForm.setText(s.substring(0 until temp) + s[temp+1] + s.substring(temp+2) + "_")
                binding.eqForm.setSelection(binding.eqForm.length() - 1)
            }
            true
        }
        panels[1].addView(btn_rCur)

        val layout1: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout1.topToBottom = R.id.mode_slider
        panels[1].layoutParams = layout1
        panels[1].isGone = true
        binding.mainLayout.addView(panels[1])

        // mode 3 Memory
        panels[2].rowCount = 5
        panels[2].columnCount = 1
        val mem_text = TextView(this)
        mem_text.tag = "mem_text"
        mem_text.text = String.format(getString(R.string.mem_text), mem)
        panels[2].addView(mem_text)
        val btn_mc = MaterialButton(this)
        btn_mc.text = getString(R.string.btn_mc)
        btn_mc.setOnClickListener {
            setM(0.0)
        }
        panels[2].addView(btn_mc)
        val btn_mp = MaterialButton(this)
        btn_mp.text = getString(R.string.btn_mp)
        btn_mp.setOnClickListener {

        }
        panels[2].addView(btn_mp)
        val btn_mm = MaterialButton(this)
        btn_mm.text = getString(R.string.btn_mm)
        btn_mm.setOnClickListener {

        }
        panels[2].addView(btn_mm)
        val btn_mr = MaterialButton(this)
        btn_mr.text = getString(R.string.btn_mr)
        btn_mr.setOnClickListener {

        }
        panels[2].addView(btn_mr)

        val layout2: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout2.topToBottom = R.id.mode_slider
        panels[2].layoutParams = layout2
        panels[2].isGone = true
        binding.mainLayout.addView(panels[2])

        // mode 4 Numbers
        panels[3].rowCount = 4
        panels[3].columnCount = 3
        val btns_nums = Array(10) { MaterialButton(this) }
        val str_nums = "7894561230"
        for ((i, c) in str_nums.withIndex()) {
            btns_nums[i].text = c.toString()
            btns_nums[i].setOnClickListener {
                val s = binding.eqForm.text.toString()
                var temp = binding.eqForm.selectionStart
                binding.eqForm.setText(s.substring(0 until temp) +
                    c +
                    if (s.isEmpty()) { "_" } else { "" } +
                    s.substring(binding.eqForm.selectionEnd))
                temp++
                binding.eqForm.setSelection(temp)
            }
            panels[3].addView(btns_nums[i])
        }
        val layout3: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout3.topToBottom = R.id.mode_slider
        panels[3].layoutParams = layout3
        panels[3].isGone = true
        binding.mainLayout.addView(panels[3])

        // mode 5 Operators
        panels[4].rowCount = 4
        panels[4].columnCount = 3
        val btn_ops = "+-*/^.()%,"
        val desc_op_ids = arrayOf(
            R.string.btn_plus, R.string.btn_minus, R.string.btn_times,
            R.string.btn_div, R.string.btn_pwr, R.string.btn_dot,
            R.string.btn_lPar, R.string.btn_rPar, R.string.btn_mod,
            R.string.btn_comma
        )
        val btns_ops = Array(10) { MaterialButton(this) }
        for ((i, c) in btn_ops.withIndex()) {
            btns_ops[i].text = getString(desc_op_ids[i])
            btns_ops[i].setOnClickListener {
                val s = binding.eqForm.text.toString()
                var temp = binding.eqForm.selectionStart
                binding.eqForm.setText(s.substring(0 until temp) +
                        c +
                        if (s.isEmpty()) { "_" } else { "" } +
                        s.substring(binding.eqForm.selectionEnd))
                temp++
                binding.eqForm.setSelection(temp)
            }
            panels[4].addView(btns_ops[i])
        }
        val layout4: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout4.topToBottom = R.id.mode_slider
        panels[4].layoutParams = layout4
        panels[4].isGone = true
        binding.mainLayout.addView(panels[4])

        // mode 6 Functions
        panels[5].rowCount = 9
        panels[5].columnCount = 1
        val funcs = arrayOf( "sqr", "sin", "cos",
            "tan", "cot", "abs", "cei", "flo", "log" )
        val desc_fn_ids = arrayOf(
            R.string.sqr, R.string.sin, R.string.cos,
            R.string.tan, R.string.cot, R.string.abs,
            R.string.cei, R.string.flo, R.string.log
        )
        val btns_funcs = Array(9) { MaterialButton(this) }
        for ((i, fn) in funcs.withIndex()) {
            btns_funcs[i].text = when (i) {
                0, 5, 6, 7, 8 -> {
                    getString(desc_fn_ids[i])
                }
                else -> {
                    when (deg) {
                        true -> getString(desc_fn_ids[i]) + ", " + getString(R.string.current_mode).format(getString(R.string.degree_text))
                        false -> getString(desc_fn_ids[i]) + ", " + getString(R.string.current_mode).format(getString(R.string.radian_text))
                    }
                }
            }
            btns_funcs[i].setOnClickListener {
                val s = binding.eqForm.text.toString()
                var temp = binding.eqForm.selectionStart
                binding.eqForm.setText(s.substring(0 until temp) +
                    fn +
                    if (s.isEmpty()) { "_" } else { "" } +
                    s.substring(binding.eqForm.selectionEnd))
                temp += 3
                binding.eqForm.setSelection(temp)
            }
            panels[5].addView(btns_funcs[i])
        }
        val layout5: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout5.topToBottom = R.id.mode_slider
        panels[5].layoutParams = layout5
        panels[5].isGone = true
        binding.mainLayout.addView(panels[5])

        // mode 7 Constants
        panels[6].rowCount = 2
        panels[6].columnCount = 1
        val consts = "EP"
        val desc_const_ids = arrayOf(
            R.string.const_e, R.string.const_p
        )
        val btns_consts = Array(2) { MaterialButton(this) }
        for ((i, c) in consts.withIndex()) {
            btns_consts[i].text = getString(desc_const_ids[i])
            btns_consts[i].setOnClickListener {
                val s = binding.eqForm.text.toString()
                var temp = binding.eqForm.selectionStart
                binding.eqForm.setText(s.substring(0 until temp) +
                    c +
                    if (s.isEmpty()) { "_" } else { "" } +
                    s.substring(binding.eqForm.selectionEnd))
                temp++
                binding.eqForm.setSelection(temp)
            }
            panels[6].addView(btns_consts[i])
        }
        val layout6: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(binding.modeSlider.layoutParams)
        layout6.topToBottom = R.id.mode_slider
        panels[6].layoutParams = layout6
        panels[6].isGone = true
        binding.mainLayout.addView(panels[6])
    }

    private fun switchMode() {
        for (panel in panels) {
            if (!panel.isGone) {
                panel.isGone = true
                break
            }
        }
        panels[mode-1].isGone = false
    }

    // TODO: Read expression unfinished
    private fun readExpr() {
        val s = binding.eqForm.text
        loop@ for ((i, c) in s.withIndex()) {
            when (c) {
                in '0'..'9' -> continue@loop
                in 'a'..'z' -> {
                    val funcMap = mapOf(
                        "sqr" to R.string.sqr,
                        "sin" to R.string.sin,
                        "cos" to R.string.cos,
                        "tan" to R.string.tan,
                        "cot" to R.string.cot,
                        "abs" to R.string.abs,
                        "cei" to R.string.cei,
                        "flo" to R.string.flo
                    )
                }
            }
        }
    }

    private fun setM(m: Double) {
        mem = m
        var mem_text = panels[2].findViewWithTag<TextView>("mem_text")
        mem_text.text = getString(R.string.mem_text).format(mem)
    }
}
