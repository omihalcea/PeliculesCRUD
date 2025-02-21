package com.octavian.peliculescrud // Ajusta el paquet segons on tinguis els teus arxius

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.octavian.peliculescrud.viewmodel.MovieViewModel

class SwipeToDeleteCallback(
    private val adapter: MovieAdapter,
    private val movieViewModel: MovieViewModel
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // Permet el desarregament cap a l'esquerra només
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Agafem l'element seleccionat
        val movie = adapter.currentList[viewHolder.adapterPosition]
        // Eliminem la pel·lícula de la base de dades
        movieViewModel.deleteMovie(movie)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}
