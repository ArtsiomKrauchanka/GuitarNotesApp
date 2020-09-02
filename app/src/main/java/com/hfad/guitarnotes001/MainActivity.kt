package com.hfad.guitarnotes001

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addProject.setOnClickListener { NewProjectOnClickListener() }

    }


    fun NewProjectOnClickListener(){
        // Для создания AlertDialog используется внутренний класс AlertDialog.Builder
        val builder = AlertDialog.Builder(this@MainActivity)

        // Заголовок AlertDialog задаётся при помощи метода setTitle
        builder.setTitle("Name your project")

        val et = EditText(this)

        et.setInputType(InputType.TYPE_CLASS_TEXT)
        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = LengthFilter(15)
        et.setFilters(FilterArray)
        et.requestFocus()
        builder.setView(et)


        // Установка текста кнопки соглашения в диалоге и обработчика по нажатию
        builder.setPositiveButton("Create"){dialog, which ->
            // Действия при нажатии кнопки соглашения в диалоге: всплывающее сообщение и смена цвета фона
            Toast.makeText(applicationContext,"creating",Toast.LENGTH_SHORT).show()
            val int = Intent(this, SetTune::class.java)
            startActivity(int)
        }

        // Установка текста кнопки отмены в диалоге и обработчика по нажатию
        builder.setNeutralButton("Cancel"){_,_ ->
            Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
        }

        // Создание настроенного экземпляра AlertDialog
        val dialog: AlertDialog = builder.create()

        et.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true)
                var text = p0.toString()
                if (!(text.matches("^[A-Za-z0-9]+[A-Za-z0-9 _]+[A-Za-z0-9 _]$".toRegex()) || text.matches("[A-Za-z0-9_]+".toRegex()))) { //if (!(text.matches("^[A-Za-z0-9]+[A-Za-z0-9 _]+[A-Za-z0-9_]$".toRegex()) || text.matches("[A-Za-z0-9_]+".toRegex()))) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })


        dialog.getWindow()?.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE)

        // Вывод на экран созданного AlertDialog
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false)
    }
}