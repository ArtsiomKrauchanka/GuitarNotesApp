package com.hfad.GuitarNotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OpenProjectActivity : AppCompatActivity(), RecyclerViewItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_project)

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(applicationContext.getExternalFilesDir(null)?.listFiles() ?: emptyArray(), this, this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    override fun itemOnClickListener(name : String){
        val int = Intent(this, MainFrame::class.java)
        int.putExtra("EXTRA_ProjectName", name)
        startActivity(int)
    }

    override fun onBackPressed() {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
    }
}