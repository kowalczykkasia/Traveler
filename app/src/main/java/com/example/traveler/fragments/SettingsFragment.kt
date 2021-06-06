package com.example.traveler.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import com.example.traveler.*
import com.example.traveler.database.Shared
import kotlinx.android.synthetic.main.settings_layout.*
import kotlinx.android.synthetic.main.settings_layout.view.*

class SettingsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_layout, container, false).apply {
            initView(this)
        }
    }

    private fun initView(rootView: View) {
        rootView.apply{
            if(Shared.sharedPrefs?.getBoolean(NOTIFICATIONS, true) == true) switcher.performClick()
            (Shared.sharedPrefs?.getInt(DISTANCE, 1))?.let {
                seekBar.progress = it
                tvDistance.text = String.format("%d km", it)
            }
            val fontSize = Shared.sharedPrefs?.getInt(FONT_SIZE, R.dimen.textSize_20) ?: R.dimen.textSize_20
            radioGroup.findViewById<RadioButton>(getIdBasedOnSize(fontSize)).isChecked = true
            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                Shared.sharedPrefs?.edit()?.putInt(FONT_SIZE, getSizeBasedOnId(i))?.apply()
            }
            switcher.setOnCheckedChangeListener { _, isChecked ->
                Shared.sharedPrefs?.edit()?.putBoolean(NOTIFICATIONS, isChecked)?.apply()
                if(isChecked) (activity as? MainActivity)?.listenerForLocationUpdates()
                else (activity as? MainActivity)?.removeLocationCallback()
            }
            seekBar.setOnSeekBarChangeListener(this@SettingsFragment)
        }
    }

    private fun getIdBasedOnSize(size: Int) = when(size){
        R.dimen.textSize_10 -> R.id.rb10
        R.dimen.textSize_35 -> R.id.rb35
        R.dimen.textSize_40 -> R.id.rb40
        else -> R.id.rb20
    }

    private fun getSizeBasedOnId(id: Int) = when(id){
        R.id.rb10 -> R.dimen.textSize_10
        R.id.rb35 ->R.dimen.textSize_35
        R.id.rb40 -> R.dimen.textSize_40
        else -> R.dimen.textSize_20
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        tvDistance.text = String.format("%d km", p1)
        Shared.sharedPrefs?.edit()?.putInt(DISTANCE, p1)?.apply()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
       //nothing to do
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        //nothing to do
    }
}