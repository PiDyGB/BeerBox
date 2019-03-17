package com.giuseppebuzzanca.beerbox.ui.beers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.giuseppebuzzanca.beerbox.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.beer_item.*

class BeerViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.beer_item, parent, false)),
    LayoutContainer {

    override val containerView: View?
        get() = itemView

    var beerItem: UIBeerItem? = null
        set(value) {
            field = value
            Glide.with(imageView).load(field?.imageUrl).into(imageView)
            titleTextView.text = field?.name
            tagLine.text = field?.tagLine
            description.text = field?.description
        }

}
