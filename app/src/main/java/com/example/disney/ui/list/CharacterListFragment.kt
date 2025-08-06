package com.example.disney.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disney.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var adapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupFavoritesButton()
        observeViewModel()
        viewModel.loadCharacters()
    }

    private fun setupRecyclerView() {
        adapter = CharacterListAdapter(
            onItemClick = { character ->
                val action = CharacterListFragmentDirections
                    .actionCharacterListFragmentToCharacterDetailFragment(character._id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { character ->
                viewModel.toggleFavorite(character)
            }
        )

        binding.charactersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CharacterListFragment.adapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setSearchQuery(query ?: "")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText ?: "")
                return true
            }
        })

        // Dynamisches Verschieben des Buttons bei Fokus auf die Suchleiste
        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.favoritesButton.visibility = View.GONE
                binding.favoritesButtonVertical.visibility = View.VISIBLE
            } else {
                binding.favoritesButton.visibility = View.VISIBLE
                binding.favoritesButtonVertical.visibility = View.GONE
            }
        }
    }

    private fun setupFavoritesButton() {
        val listener = View.OnClickListener {
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToFavoritesFragment()
            )
        }
        binding.favoritesButton.setOnClickListener(listener)
        binding.favoritesButtonVertical.setOnClickListener(listener)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredCharacters.collect { characters ->
                    Log.d("CharacterListFragment", "Gefilterte Liste: ${characters.size}")
                    adapter.submitList(characters)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}