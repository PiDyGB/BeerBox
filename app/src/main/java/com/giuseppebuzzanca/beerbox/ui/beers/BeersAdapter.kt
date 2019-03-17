package com.giuseppebuzzanca.beerbox.ui.beers

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.beer_item.*

class BeersAdapter : ListAdapter<UIBeerItem, BeerViewHolder>(DiffCallback()) {


    val onMoreInfo: Subject<UIBeerItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val uiBeerItem = getItem(position)
        holder.beerItem = uiBeerItem
        holder.moreInfo.setOnClickListener { onMoreInfo.onNext(uiBeerItem) }
    }

    private class DiffCallback : DiffUtil.ItemCallback<UIBeerItem>() {
        override fun areItemsTheSame(oldItem: UIBeerItem, newItem: UIBeerItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UIBeerItem, newItem: UIBeerItem): Boolean {
            return oldItem == newItem
        }

    }

}
