package com.example.marvelworld.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.marvelworld.R
import com.example.marvelworld.vm.SeriesViewModel

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        seriesViewModel =
            ViewModelProviders.of(this).get(SeriesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_series, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        seriesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}