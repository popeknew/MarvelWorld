package com.example.marvelworld.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelworld.adapter.CharactersAdapter
import com.example.marvelworld.databinding.FragmentCharactersBinding
import com.example.marvelworld.ext.empty
import com.example.marvelworld.ext.setRightDrawableOnTouchListener
import com.example.marvelworld.model.Character
import com.example.marvelworld.repository.RepositoryRetrofit
import com.example.marvelworld.utility.EndlessRecyclerViewScrollListener
import com.example.marvelworld.vm.CharactersViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_characters.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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
    private var isUsingGetCharacterWithName = false
    private val lastSuggestionsList = mutableListOf<String>()

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
        initializeEndlessScrollListener()
        setupRecyclerView()
        openCharacterDetails()

        with(binding.mainSearchText) {
            setRightDrawableOnTouchListener {
                text?.clear()
                charactersAdapter.clearList()
                pageOffset = 0
                getCharactersFromServer()
            }
            addTextChangedListener(searchTextChangeListener)
        }

        with(binding.materialSearchBar) {
            setHint("Tap character")
            setCardViewElevation(10)
            addTextChangeListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    if (s.isNullOrEmpty().not()) {
                        isUsingGetCharacterWithName = true
                        viewModel.setLoadingState(true)
                        GlobalScope.launch {
                            withContext(Dispatchers.Main) {
                                startSearch(s.toString()).collect { value ->
                                    characterList.clear()
                                    swapCharacterList(value)
                                    viewModel.setLoadingState(false)
                                }
                            }
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val suggest = ArrayList<String>()
                    for (search in lastSuggestionsList) {
                        if (search.toLowerCase().contains(materialSearchBar.text.toLowerCase())) {
                            suggest.add(search)
                            materialSearchBar.lastSuggestions = suggest
                        }
                    }
                }
            })
//            setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
//                override fun onButtonClicked(buttonCode: Int) {
//                    if (buttonCode == 0) {
//                        println("$text -----------------------------------------------------------")
//                    }
//                }
//
//                override fun onSearchStateChanged(enabled: Boolean) {
//                    if (!enabled) {
//                        binding.recyclerView.adapter = charactersAdapter
//                    }
//                }
//
//                override fun onSearchConfirmed(text: CharSequence?) {
//                    //after ok click
//                }
//            })
        }
    }

    fun startSearch(text: String) = flow {
        val characterList = repository.getCharacterNameStartsWith(text).data.results
        emit(characterList)
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
        isUsingGetCharacterWithName = false
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

    private fun getCharacterFromServerNameStartsWith(text: String) {
        isUsingGetCharacterWithName = true
        viewModel.setLoadingState(true)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                characterList.clear()
                val response = repository.getCharacterNameStartsWith(text).data.results
                swapCharacterList(response)
                viewModel.setLoadingState(false)
            }
        }
    }

    private fun sendCharacterListToRecycler(list: List<Character>) {
        binding.recyclerView.scheduleLayoutAnimation()
        charactersAdapter.addToList(list)
    }

    private fun initializeEndlessScrollListener() {
        endlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    if (!isUsingGetCharacterWithName) {
                        pageOffset += pageLimit
                        getCharactersFromServer()
                    }
                }
            }
    }

    private fun turnOffEndlessScrollListener() {
        endlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {}
            }
    }

    private fun swapCharacterList(list: List<Character>) {
        binding.recyclerView.scheduleLayoutAnimation()
        charactersAdapter.swapList(list)
    }

    private fun openCharacterDetails() {
        pageOffset = 0
        charactersAdapter.onRowClicked = { selectedCharacterId ->
            val action = CharactersFragmentDirections.actionNavCharactersToCharacterDetailsFragment(
                selectedCharacterId
            )
            view?.findNavController()?.navigate(action)
        }
    }

    private val searchTextChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (count >= 3 && !isUsingGetCharacterWithName) {
                turnOffEndlessScrollListener()
                binding.recyclerView.scheduleLayoutAnimation()
                getCharacterFromServerNameStartsWith(s.toString())
            } else if (count <= 1) {
                initializeEndlessScrollListener()
                charactersAdapter.clearList()
                pageOffset = 0
                getCharactersFromServer()
            }
        }
    }
}