package com.octavian.peliculescrud.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.octavian.peliculescrud.data.Movie
import com.octavian.peliculescrud.data.MovieDao
import com.octavian.peliculescrud.data.MovieDatabase
import com.octavian.peliculescrud.R
import com.octavian.peliculescrud.databinding.FragmentAddMovieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddMovieFragment : Fragment() {

    private var _binding: FragmentAddMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieDao: MovieDao

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
    }

    private fun saveMovieToDatabase() {
        val title = binding.editTextTitle.text.toString().trim()
        val director = binding.editTextDirector.text.toString().trim()
        val yearText = binding.editTextYear.text.toString().trim()

        if (title.isNotEmpty() && director.isNotEmpty() && yearText.isNotEmpty()) {
            val year = yearText.toInt()

            val movie = Movie(title = title, director = director, year = year)

            lifecycleScope.launch(Dispatchers.IO) {
                movieDao.insertMovie(movie)
            }

            findNavController().navigateUp()
        } else {
            binding.editTextTitle.error = if (title.isEmpty()) "El títol és obligatori" else null
            binding.editTextDirector.error = if (director.isEmpty()) "El director és obligatori" else null
            binding.editTextYear.error = if (yearText.isEmpty()) "L'any és obligatori" else null
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}