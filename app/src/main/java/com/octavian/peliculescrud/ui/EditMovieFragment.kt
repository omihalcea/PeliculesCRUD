package com.octavian.peliculescrud.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.octavian.peliculescrud.data.Movie
import com.octavian.peliculescrud.databinding.FragmentEditMovieBinding
import com.octavian.peliculescrud.viewmodel.MovieViewModel

class EditMovieFragment : Fragment() {

    private var _binding: FragmentEditMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private var currentMovie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        // Recuperem l'argument (l'id de la pel·lícula)
        val movieId = arguments?.getInt("movieId")
        movieId?.let { id ->
            // Suposem que al MovieViewModel tens una funció getMovieById
            movieViewModel.getMovieById(id).observe(viewLifecycleOwner) { movie ->
                movie?.let {
                    currentMovie = it
                    binding.editTextTitle.setText(it.title)
                    binding.editTextDirector.setText(it.director)
                    binding.editTextYear.setText(it.year.toString())
                }
            }
        }

        binding.buttonUpdate.setOnClickListener {
            currentMovie?.let { movie ->
                val updatedMovie = movie.copy(
                    title = binding.editTextTitle.text.toString(),
                    director = binding.editTextDirector.text.toString(),
                    year = binding.editTextYear.text.toString().toIntOrNull() ?: movie.year
                )
                movieViewModel.updateMovie(updatedMovie)
                findNavController().navigateUp()
            }
        }

        // Afegim el listener per al botó de cancel·lar
        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
