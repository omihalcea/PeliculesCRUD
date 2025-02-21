package com.octavian.peliculescrud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.octavian.peliculescrud.data.Movie
import com.octavian.peliculescrud.data.MovieDao
import com.octavian.peliculescrud.data.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao: MovieDao

    // Obté totes les pel·lícules en temps real
    val allMovies: LiveData<List<Movie>>

    init {
        val db = MovieDatabase.getDatabase(application)
        movieDao = db.movieDao()
        allMovies = movieDao.getAllMovies()
    }

    // Funció per afegir una nova pel·lícula a la base de dades
    fun addMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.insertMovie(movie)
        }
    }
}
