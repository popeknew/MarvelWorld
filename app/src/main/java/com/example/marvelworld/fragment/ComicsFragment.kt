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
import com.example.marvelworld.vm.ComicsViewModel

class ComicsFragment : Fragment() {

    private lateinit var comicsViewModel: ComicsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        comicsViewModel =
            ViewModelProviders.of(this).get(ComicsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_comics, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        comicsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}