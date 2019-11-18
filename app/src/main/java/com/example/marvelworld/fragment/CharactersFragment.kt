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
import com.example.marvelworld.adapter.CharactersAdapter
import com.example.marvelworld.databinding.RowCharacterBinding
import com.example.marvelworld.model.Character
import com.example.marvelworld.repository.RepositoryRetrofit
import com.example.marvelworld.vm.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get

class CharactersFragment : Fragment() {

    private val repository: RepositoryRetrofit = get()
    private val viewModel: CharactersViewModel = get()
    private val charactersAdapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.adapter = charactersAdapter
        getCharactersFromServer()

    }

    private fun getCharactersFromServer(): List<Character> {
        var list = listOf<Character>()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                list = repository.getAllCharacters().data.results
                createCharacterList(list)
            }
        }
        return list
    }

    private fun createCharacterList(list: List<Character>) {
        recycler_view.scheduleLayoutAnimation()
        charactersAdapter.swapList(list)
    }
}