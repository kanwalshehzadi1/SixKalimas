package com.example.sixkalimas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TranslationFragment : Fragment() {
    private var kalimaNumber: Int = 1

    companion object {
        fun newInstance(kalimaNumber: Int): TranslationFragment {
            val fragment = TranslationFragment()
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
        val view = inflater.inflate(R.layout.fragment_translation, container, false)
        val englishTextView = view.findViewById<TextView>(R.id.textView5)
        val urduTextView = view.findViewById<TextView>(R.id.textView4)

        // Load Kalima data
        val kalima = (activity as DisplayActivity).getKalima(kalimaNumber)
        if (kalima != null) {
            englishTextView.text = kalima.getString("english")
            urduTextView.text = kalima.getString("urdu")
            Log.d("TranslationFragment", "English: ${kalima.getString("english")}, Urdu: ${kalima.getString("urdu")}")
        } else {
            Log.e("TranslationFragment", "Kalima data is null!")
        }

        return view
    }

}