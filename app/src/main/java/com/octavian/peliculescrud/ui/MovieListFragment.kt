package com.octavian.peliculescrud.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.octavian.peliculescrud.MainActivity
import com.octavian.peliculescrud.MovieAdapter
import com.octavian.peliculescrud.R
import com.octavian.peliculescrud.SwipeToDeleteCallback
import com.octavian.peliculescrud.databinding.FragmentMovieListBinding
import com.octavian.peliculescrud.viewmodel.MovieViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView i l'adapter amb la funció de clic
        adapter = MovieAdapter { movie ->
            val bundle = Bundle().apply { putInt("movieId", movie.id) }
            findNavController().navigate(R.id.EditMovieFragment, bundle)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observar les dades de la base de dades
        movieViewModel.allMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }


        // Configurar el FAB per a navegar a AddMovieFragment
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // Canviar el títol del toolbar
        (activity as? MainActivity)?.setToolbarTitle("Llistat de Pel·lícules")

        // Configurar ItemTouchHelper per desarregar
        val itemTouchHelperCallback = SwipeToDeleteCallback(adapter, movieViewModel)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
