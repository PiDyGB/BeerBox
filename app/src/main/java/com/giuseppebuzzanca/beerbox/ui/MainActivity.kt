package com.giuseppebuzzanca.beerbox.ui

import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import com.giuseppebuzzanca.beerbox.R

import com.giuseppebuzzanca.beerbox.ui.beers.BeersFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //This is presentation logic, should be in a view model
        toolbarTitle.text = SpannableStringBuilder().append(getString(R.string.beer_name))
            .append(" ")
            .append(getString(R.string.box_name), StyleSpan(BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BeersFragment.newInstance())
                .commit()
    }
}
