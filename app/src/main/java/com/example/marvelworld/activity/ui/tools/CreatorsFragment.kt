package com.example.marvelworld.activity.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.marvelworld.R

class CreatorsFragment : Fragment() {

    private lateinit var creatorsViewModel: CreatorsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        creatorsViewModel =
            ViewModelProviders.of(this).get(CreatorsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_creators, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        creatorsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}