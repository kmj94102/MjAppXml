package com.example.mjappxml.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mjappxml.common.GridSpaceItemDecoration

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter.apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
}

@BindingAdapter("submitList")
fun bindSubmitList(view: RecyclerView, itemList: List<Any>?) {
    (view.adapter as? ListAdapter<Any, *>)?.submitList(itemList)
}

@BindingAdapter(value= ["gridSpan", "gridSpacing"])
fun bindGridSetting(view: RecyclerView, gridSpan: Int, gridSpacing: Int) {
    view.addItemDecoration(
        GridSpaceItemDecoration(gridSpan, gridSpacing)
    )
}