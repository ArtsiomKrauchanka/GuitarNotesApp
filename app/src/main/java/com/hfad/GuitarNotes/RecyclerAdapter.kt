package com.hfad.GuitarNotes

import android.R.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class RecyclerAdapter(
    private var fileList: Array<File>,
    private val cellClickListener: RecyclerViewItemClickListener, val ctx: Context
)  :
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
        var currentItem = fileList[position]
        val tonality : Tonality

        val tonalityFile = File(currentItem.path, "tonalityFile")
        val fos = FileInputStream(tonalityFile)
        val din = ObjectInputStream(fos)
        tonality = din.readObject() as Tonality
        din.close()

        holder.tonalityIconView.text = tonality.name
        holder.projectNameView.text= currentItem.name
        val date = Date(currentItem.lastModified())
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val formattedDateString: String = formatter.format(date)
        holder.lastChangeView.text = "last change: $formattedDateString"

        holder.itemView.setOnClickListener {
            cellClickListener.itemOnClickListener(currentItem.name.toString())
        }

        holder.itemView.viewOptions.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                //creating a popup menu
                val popup = PopupMenu(ctx, holder.itemView.viewOptions)
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                //adding click listener
                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem): Boolean {
                        when (item.itemId) {
                            R.id.Rename -> {
                                val builder = AlertDialog.Builder(ctx)

                                builder.setTitle("Provide new name: ")

                                val et = EditText(ctx)

                                et.setInputType(InputType.TYPE_CLASS_TEXT)
                                val FilterArray = arrayOfNulls<InputFilter>(1)
                                FilterArray[0] = InputFilter.LengthFilter(20)
                                et.setFilters(FilterArray)
                                et.requestFocus()
                                builder.setView(et)

                                builder.setPositiveButton("Rename"){dialog, which ->
                                    val to = File(currentItem.parent,et.text.toString().trim())
                                    currentItem.renameTo(to)
                                    val int = Intent(ctx, OpenProjectActivity::class.java)
                                    ctx.startActivity(int)
                                }

                                // Установка текста кнопки отмены в диалоге и обработчика по нажатию
                                builder.setNeutralButton("Cancel"){_,_ ->

                                }

                                val dialog: AlertDialog = builder.create()

                                et.addTextChangedListener(object: TextWatcher {
                                    override fun afterTextChanged(p0: Editable?) {
                                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                                        var text = p0.toString()
                                        if (!(text.matches("^[A-Za-z0-9 ]+[A-Za-z0-9 _]+[A-Za-z0-9 _]$".toRegex()) || text.matches("[A-Za-z0-9 _]+".toRegex()))) { //if (!(text.matches("^[A-Za-z0-9]+[A-Za-z0-9 _]+[A-Za-z0-9_]$".toRegex()) || text.matches("[A-Za-z0-9_]+".toRegex()))) {
                                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                                        }
                                    }

                                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                                    }

                                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                                    }

                                })


                                dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

                                // Вывод на экран созданного AlertDialog
                                dialog.show()
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false)

                            }
                            R.id.Delete -> {
                                val builder = AlertDialog.Builder(ctx)

                                builder.setTitle("Delete project ${currentItem.name}?")

                                builder.setPositiveButton("Delete"){dialog, which ->
                                    try{
                                        var fileList = currentItem.listFiles()
                                        for (i in fileList.size-1 downTo 0){
                                            fileList[i].delete()
                                        }
                                        currentItem.delete()
                                        val int = Intent(ctx, OpenProjectActivity::class.java)
                                        ctx.startActivity(int)
                                    }catch(e : Exception){
                                        Toast.makeText(ctx,"Error",Toast.LENGTH_SHORT).show()
                                    }

                                }

                                // Установка текста кнопки отмены в диалоге и обработчика по нажатию
                                builder.setNeutralButton("Cancel"){_,_ ->

                                }

                                val dialog: AlertDialog = builder.create()
                                dialog.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

                                // Вывод на экран созданного AlertDialog
                                dialog.show()
                            }
                        }
                        return false
                    }
                })
                //displaying the popup
                popup.show()
            }
        })
    }

    override fun getItemCount(): Int {
        return fileList.size
    }



}