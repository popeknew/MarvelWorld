package com.example.marvelworld.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelworld.databinding.RowCharacterBinding
import com.example.marvelworld.model.Character

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private val characterList = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            RowCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = characterList.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) = holder.bind(characterList[position])

    inner class CharactersViewHolder(private val binding: RowCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.character = character
            binding.executePendingBindings()
        }
    }

    fun swapList(list: List<Character>) {
        characterList.clear()
        characterList.addAll(list)
        notifyDataSetChanged()
    }
}