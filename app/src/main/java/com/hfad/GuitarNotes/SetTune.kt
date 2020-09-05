package com.hfad.GuitarNotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_set_tune.*
import java.io.*


class SetTune : AppCompatActivity() {

    lateinit var adapter: Adapter
    lateinit var tonalities: MutableList<Tonality>
    lateinit var tonality: Tonality

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_tune)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)


        initializing()
        applyScale()

        selectTonality.setOnClickListener { select() }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun initializing(){

        tonalities = mutableListOf(
            Tonality("C", 1, 8),
            Tonality("Cm", 0, 8),
            Tonality("C#", 1, 9),
            Tonality("C#m", 0, 9),
            Tonality("D", 1, 10),
            Tonality("Dm", 0, 10),
            Tonality("Eb", 1, 11),
            Tonality("D#m", 0, 11),
            Tonality("E", 1, 0),
            Tonality("Em", 0, 0),
            Tonality("F", 1, 1),
            Tonality("Fm", 0, 1),
            Tonality("F#", 1, 2),
            Tonality("F#m", 0, 2),
            Tonality("G", 1, 3),
            Tonality("Gm", 0, 3),
            Tonality("Ab", 1, 4),
            Tonality("G#m", 0, 4),
            Tonality("A", 1, 5),
            Tonality("Am", 0, 5),
            Tonality("Bb", 1, 6),
            Tonality("A#m", 0, 6),
            Tonality("B", 1, 7),
            Tonality("Bm", 0, 7)
        )

        adapter = Adapter(tonalities, this)
        viewPager.adapter = adapter
        viewPager.setPadding(40, 0, 40, 0)

        tonality = tonalities.get(0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                tonality = tonalities.get(position)
                applyScale()
            }

        })
    }

    fun  select(){
        val int = Intent(this, MainFrame::class.java)
        val projectName = intent.extras?.getString("EXTRA_FILENAME") ?: "Unknown"
        int.putExtra("EXTRA_ProjectName", projectName);
        if(isExternalStorageWritable()){
            try{
                var path = applicationContext.getExternalFilesDir(null)?.path
                val newDir = File(path, projectName)
                if (!newDir.exists())  newDir.mkdirs()
                path = newDir?.path
                val tonalityFile = File(path, "tonalityFile")
                val fos = FileOutputStream(tonalityFile)
                val oos = ObjectOutputStream(fos)
                oos.writeObject(tonality)
                oos.flush()
                fos.fd.sync();
                fos.close();

                val tabsList: List<String> = List<String>(479, {i -> "-  "})
                val tabsFile = File(path, "tabsFile")
                val fileWriter = BufferedWriter(FileWriter(tabsFile))
                for (chunk in tabsList) {
                    fileWriter.write(chunk)
                    fileWriter.newLine()
                }
                fileWriter.close()

                startActivity(int)
            }catch (e: Exception){
                Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_SHORT).show()
        }
    }

    fun applyScale(){
        for(string in listOf("f", "s", "t", "fo", "fi", "si")){
            for(pos in 0..24){
                if(tonality.first.contains(pos)){ 
                    val buttonID = "f" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }else{
                    val buttonID = "f" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.round_button,
                            null
                        )
                    )
                }
                if(tonality.second.contains(pos)){
                    val buttonID = "s" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
                else{
                    val buttonID = "s" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.round_button,
                            null
                        )
                    )
                }
                if(tonality.third.contains(pos)){
                    val buttonID = "t" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
                else{
                    val buttonID = "t" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.round_button,
                            null
                        )
                    )
                }
                if(tonality.fourth.contains(pos)){
                    val buttonID = "fo" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    val button = findViewById<Button>(resID)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
                else{
                    val buttonID = "fo" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.round_button,
                            null
                        )
                    )
                }
                if(tonality.fifth.contains(pos)){
                    val buttonID = "fi" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
                else{
                    val buttonID = "fi" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.round_button,
                            null
                        )
                    )
                }
                if(tonality.sixth.contains(pos)){
                    val buttonID = "si" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
                else{
                    val buttonID = "si" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.round_button,
                            null
                        )
                    )
                }

            }
        }
    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state)
    }

    /* Checks if external storage is available to at least read */
    fun isExternalStorageReadable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
    }

}