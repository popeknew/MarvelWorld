package com.example.marvelworld.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelworld.adapter.CharactersAdapter
import com.example.marvelworld.databinding.FragmentCharactersBinding
import com.example.marvelworld.model.Character
import com.example.marvelworld.repository.RepositoryRetrofit
import com.example.marvelworld.utility.EndlessRecyclerViewScrollListener
import com.example.marvelworld.vm.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get

class CharactersFragment : Fragment() {

    private val repository: RepositoryRetrofit = get()
    private val viewModel: CharactersViewModel = get()
    private lateinit var binding: FragmentCharactersBinding
    private val charactersAdapter = CharactersAdapter()
    private val characterList = mutableListOf<Character>()
    private var pageOffset = 0
    private var pageLimit = 0
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CharactersFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(context)
        initializeEndlessScrollerListener()
        setupRecyclerView()
        openCharacterDetails()
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = charactersAdapter
            layoutManager = this@CharactersFragment.layoutManager
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }
        getCharactersFromServer()
    }

    private fun getCharactersFromServer() {
        viewModel.setLoadingState(true)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                characterList.clear()
                val responseCharacter = repository.getAllCharacters(pageOffset)
                pageLimit = responseCharacter.data.limit
                characterList.addAll(responseCharacter.data.results)
                sendCharacterListToRecycler(characterList)
                viewModel.setLoadingState(false)
            }
        }
    }

    private fun sendCharacterListToRecycler(list: List<Character>) {
        binding.recyclerView.scheduleLayoutAnimation()
        charactersAdapter.addToList(list)
    }

    private fun initializeEndlessScrollerListener() {
        endlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    pageOffset += pageLimit
                    getCharactersFromServer()
                }
            }
    }

    private fun openCharacterDetails() {
        charactersAdapter.onRowClicked = { selectedCharacterId ->
            val action = CharactersFragmentDirections.actionNavCharactersToCharacterDetailsFragment(
                selectedCharacterId
            )
            view?.findNavController()?.navigate(action)
        }
    }
}