package com.giuseppebuzzanca.beerbox.ui.beers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.giuseppebuzzanca.beerbox.R
import com.giuseppebuzzanca.beerbox.ui.beer.BeerBottomSheetDialogFragment
import com.giuseppebuzzanca.beerbox.util.BeerType
import com.giuseppebuzzanca.beerbox.util.BeerTypeId
import com.giuseppebuzzanca.beerbox.util.ViewModelsFactory
import com.giuseppebuzzanca.beerbox.util.bindToLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_beers.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BeersFragment : DaggerFragment(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private var beerViewModel: BeersViewModel? = null
    private val layoutManager = LinearLayoutManager(context)
    private var beersAdapter: BeersAdapter = BeersAdapter()
    private val queryPublisher = PublishSubject.create<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_beers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = layoutManager
        beersAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                recyclerView.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart <= layoutManager.findFirstVisibleItemPosition())
                    recyclerView.scrollToPosition(0)
            }
        })
        recyclerView.adapter = beersAdapter
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        context?.getDrawable(R.drawable.list_divider_dark)?.let { dividerItemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(dividerItemDecoration)

        searchView.setOnQueryTextListener(this)
        val searchCloseButtonId = searchView.context.resources
            .getIdentifier("android:id/search_close_btn", null, null)
        searchView.findViewById<View>(searchCloseButtonId).setOnClickListener { onClose() }

        beerViewModel = ViewModelProviders.of(this, viewModelsFactory).get(BeersViewModel::class.java)
        beerViewModel?.beers?.observe(viewLifecycleOwner, Observer { updateBeers(it) })

        recyclerView.addOnScrollListener(ScrollListener(beerViewModel))
        beersAdapter.onMoreInfo.subscribe { openBeerBottomSheet(it) }.bindToLifecycle(this)

        queryPublisher.debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { beerViewModel?.onQuery(it) }
            .bindToLifecycle(this)

        addFilters(beerViewModel?.beerTypes)
    }

    override fun onClose(): Boolean {
        searchView.setQuery("", true)
        searchView.clearFocus()
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            queryPublisher.onNext(query)
            searchView.clearFocus()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            queryPublisher.onNext(newText)
        }
        return true
    }

    private fun onChipChanged(view: CompoundButton, isChecked: Boolean) {
        if (isChecked)
            beerViewModel?.addFilter(view.tag as BeerType)
        else
            beerViewModel?.removeFilter(view.tag as BeerType)
    }

    private fun openBeerBottomSheet(beerItem: UIBeerItem) {
        BeerBottomSheetDialogFragment.newInstance(beerItem).apply {
            show(this@BeersFragment.fragmentManager, BottomSheetDialogFragment::class.java.canonicalName)
        }
    }

    private fun addFilters(beerTypes: List<BeerType>?) {
        val layoutInflater = LayoutInflater.from(context)
        beerTypes?.forEach {
            val chip: Chip = layoutInflater.inflate(R.layout.filter_chip, filterChipGroup, false) as Chip
            chip.id = BeerTypeId.values().getOrNull(it.ordinal)?.id ?: View.generateViewId()
            chip.text = it.displayName
            chip.tag = it
            chip.setOnCheckedChangeListener { buttonView, isChecked -> onChipChanged(buttonView, isChecked) }
            TransitionManager.beginDelayedTransition(filterChipGroup)
            filterChipGroup.addView(chip)
        }
    }

    private fun updateBeers(beerItems: List<UIBeerItem>) {
        beersAdapter.submitList(beerItems)
    }

    //This should be changed with the new Paging Library
    class ScrollListener(private val beersViewModel: BeersViewModel?) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = recyclerView.layoutManager?.childCount
            val totalItemCount = recyclerView.layoutManager?.itemCount
            val firstVisibleItemPosition =
                (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()

            beersViewModel?.getBeers(visibleItemCount, firstVisibleItemPosition, totalItemCount)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = BeersFragment()
    }
}
