package com.hfad.GuitarNotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview.view.*

class RecyclerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    val tonalityIconView : Button = itemView.tonality_icon
    val projectNameView : TextView = itemView.project_name
    val lastChangeView : TextView = itemView.last_change


}