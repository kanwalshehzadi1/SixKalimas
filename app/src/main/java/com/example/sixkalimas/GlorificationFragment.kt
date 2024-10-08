package com.example.sixkalimas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class GlorificationFragment : Fragment() {
    private var kalimaNumber: Int = 1

    companion object {
        fun newInstance(kalimaNumber: Int): GlorificationFragment {
            val fragment = GlorificationFragment()
            val args = Bundle()
            args.putInt("kalima_number", kalimaNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kalimaNumber = arguments?.getInt("kalima_number") ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_glorification, container, false)
        val arabicTextView = view.findViewById<TextView>(R.id.textView3)
        val urduTextView = view.findViewById<TextView>(R.id.textView2)

        // Load Kalima data
        val kalima = (activity as DisplayActivity).getKalima(kalimaNumber)
        if (kalima != null) {
            arabicTextView.text = kalima.getString("arabic")
            urduTextView.text = kalima.getString("trans")
            Log.d("GlorificationFragment", "Arabic: ${kalima.getString("arabic")}, Trans: ${kalima.getString("trans")}")
        } else {
            Log.e("GlorificationFragment", "Kalima data is null!")
        }

        return view
    }
    }
