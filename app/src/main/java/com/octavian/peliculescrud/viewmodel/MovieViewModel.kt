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

    fun updateMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.updateMovie(movie)
        }
    }

    // Suposem que afegim aquesta funció per obtenir una pel·lícula per id:
    fun getMovieById(id: Int): LiveData<Movie> = movieDao.getMovieById(id)
}
