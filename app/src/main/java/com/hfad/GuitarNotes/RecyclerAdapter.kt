package com.hfad.GuitarNotes

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.*

class RecyclerAdapter(private val fileList: Array<File>, private val cellClickListener: RecyclerViewItemClickListener)  :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recyclerview,
            parent,
            false
        )
        itemView.setBackgroundColor(getColor(parent.context, R.color.recycler_view_item_color))

        return RecyclerViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = fileList[position]
        val tonality : Tonality

        val tonalityFile = File(currentItem.path, "tonalityFile")
        val fos = FileInputStream(tonalityFile)
        val din = ObjectInputStream(fos)
        tonality = din.readObject() as Tonality
        din.close()

        holder.tonalityIconView.text = tonality.name
        holder.projectNameView.text= currentItem.name
        val date = Date(currentItem.lastModified())
        holder.lastChangeView.text = "last change: " + date.toString()

        holder.itemView.setOnClickListener {
            cellClickListener.itemOnClickListener(currentItem.name.toString())
        }
    }

    override fun getItemCount(): Int {
        return fileList.size
    }



}