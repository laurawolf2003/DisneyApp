package com.example.disney.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disney.databinding.ItemCharacterBinding
import com.example.disney.model.DisneyCharacter

class CharacterListAdapter(
    private val onItemClick: (DisneyCharacter) -> Unit
) : ListAdapter<DisneyCharacter, CharacterListAdapter.CharacterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: DisneyCharacter) {
            binding.characterName.text = character.name

            character.imageUrl?.let { imageUrl ->
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .centerCrop()
                    .into(binding.characterImage)
            }

            binding.root.setOnClickListener {
                onItemClick(character)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DisneyCharacter>() {
        override fun areItemsTheSame(oldItem: DisneyCharacter, newItem: DisneyCharacter): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: DisneyCharacter, newItem: DisneyCharacter): Boolean {
            return oldItem == newItem
        }
    }
}