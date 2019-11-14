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
import com.example.marvelworld.vm.CharactersViewModel

class CharactersFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        charactersViewModel =
            ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_characters, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        charactersViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}