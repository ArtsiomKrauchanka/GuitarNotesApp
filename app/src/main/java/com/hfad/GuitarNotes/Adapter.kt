package com.hfad.GuitarNotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter

class Adapter(val tonalities: List<Tonality>, val context: Context) : PagerAdapter(){

    lateinit var inflater: LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun getCount(): Int {
        return tonalities.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_set_tune, container, false)
        var button: Button = view.findViewById(R.id.tonality_button)
        button.setText(tonalities.get(position).name)

        container.addView(view, 0)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}