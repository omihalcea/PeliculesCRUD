package com.octavian.peliculescrud.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.octavian.peliculescrud.MainActivity
import com.octavian.peliculescrud.data.Movie
import com.octavian.peliculescrud.data.MovieDao
import com.octavian.peliculescrud.data.MovieDatabase
import com.octavian.peliculescrud.R
import com.octavian.peliculescrud.databinding.FragmentAddMovieBinding
import com.octavian.peliculescrud.viewmodel.MovieViewModel

class AddMovieFragment : Fragment() {

    private var _binding: FragmentAddMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieDao: MovieDao
    private val MovieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)

        // Inicialitzar la base de dades i el DAO
        val db = MovieDatabase.getDatabase(requireContext())
        movieDao = db.movieDao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            saveMovieToDatabase()
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        // Canviar el títol del toolbar
        (activity as? MainActivity)?.setToolbarTitle("Afegir Pel·lícula")
    }

    private fun saveMovieToDatabase() {
        val title = binding.editTextTitle.text.toString().trim()
        val director = binding.editTextDirector.text.toString().trim()
        val yearText = binding.editTextYear.text.toString().trim()

        if (validateFields(title, director, yearText)) {
            val year = yearText.toInt()

            val movie = Movie(title = title, director = director, year = year)

            MovieViewModel.addMovie(movie)

            findNavController().navigateUp()
        }
    }

    private fun validateFields(title: String, director: String, yearText: String): Boolean {
        var isValid = true

        if (title.isEmpty()) {
            binding.editTextTitle.error = "El títol és obligatori"
            isValid = false
        }
        if (director.isEmpty()) {
            binding.editTextDirector.error = "El director és obligatori"
            isValid = false
        }
        if (yearText.isEmpty()) {
            binding.editTextYear.error = "L'any és obligatori"
            isValid = false
        } else {
            val year = yearText.toIntOrNull()
            if (year == null || year < 1800 || year > 2100) { // Validació de l'any
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
