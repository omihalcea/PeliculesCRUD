package com.octavian.peliculescrud.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.octavian.peliculescrud.MainActivity
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
            if (validateFields()) {
                currentMovie?.let { movie ->
                    val updatedMovie = movie.copy(
                        title = binding.editTextTitle.text.toString().trim(),
                        director = binding.editTextDirector.text.toString().trim(),
                        year = binding.editTextYear.text.toString().toInt()
                    )
                    movieViewModel.updateMovie(updatedMovie)
                    findNavController().navigateUp()
                }
            }
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        // Canviar el títol del toolbar
        (activity as? MainActivity)?.setToolbarTitle("Editar Pel·lícula")
    }

    private fun validateFields(): Boolean {
        val title = binding.editTextTitle.text.toString().trim()
        val director = binding.editTextDirector.text.toString().trim()
        val yearText = binding.editTextYear.text.toString().trim()

        var isValid = true

        if (title.isEmpty()) {
            binding.editTextTitle.error = "El títol no pot estar buit"
            isValid = false
        }
        if (director.isEmpty()) {
            binding.editTextDirector.error = "El director no pot estar buit"
            isValid = false
        }
        if (yearText.isEmpty()) {
            binding.editTextYear.error = "L'any no pot estar buit"
            isValid = false
        } else {
            val year = yearText.toIntOrNull()
            if (year == null || year < 1800 || year > 2100) { // Rang d'any raonable
                binding.editTextYear.error = "Introdueix un any vàlid (1800 - 2100)"
                isValid = false
            }
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
