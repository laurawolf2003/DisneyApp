package com.example.disney.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.disney.databinding.FragmentCharacterDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = args.characterId
        viewModel.loadCharacter(characterId)
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.character.collect { character ->
                    character?.let { char ->
                        char.imageUrl?.let { imageUrl ->
                            Glide.with(requireContext())
                                .load(imageUrl)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .error(android.R.drawable.ic_menu_report_image)
                                .into(binding.characterImage)
                        } ?: binding.characterImage.setImageResource(android.R.drawable.ic_menu_gallery)

                        binding.characterName.text = char.name

                        // Nur die Werte, KEINE Kategorie davor!
                        binding.films.text = if (char.films.isNullOrEmpty()) "Keine Filme vorhanden."
                        else char.films.joinToString(separator = "\n")
                        binding.tvShows.text = if (char.tvShows.isNullOrEmpty()) "Keine TV-Shows vorhanden."
                        else char.tvShows.joinToString(separator = "\n")
                        binding.videoGames.text = if (char.videoGames.isNullOrEmpty()) "Keine Videospiele vorhanden."
                        else char.videoGames.joinToString(separator = "\n")
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    binding.contentGroup.visibility = if (isLoading) View.GONE else View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}