package com.octavian.peliculescrud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.octavian.peliculescrud.data.Movie
import com.octavian.peliculescrud.data.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao = MovieDatabase.getDatabase(application).movieDao()
    val allMovies: LiveData<List<Movie>> = movieDao.getAllMovies()

    // Funció per afegir una nova pel·lícula a la base de dades
    fun addMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.insertMovie(movie)
        }
    }

    // Funció per actualitzar una pel·lícula a la base de dades
    fun updateMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.updateMovie(movie)
        }
    }

    // Funció per esborrar una pel·lícula de la base de dades
    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.deleteMovie(movie)
        }
    }

    fun getMovieById(id: Int): LiveData<Movie> = movieDao.getMovieById(id)
}
