package com.hfad.GuitarNotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_frame.*
import java.io.*
import kotlin.random.Random


class MainFrame : AppCompatActivity() {

    lateinit var tonality: Tonality
    var tabPos = 0
    var tabIndex = 0
    var maxTabPos = 0
    var tabsList: MutableList<String> = mutableListOf()
    var chord1ColoredTabs : MutableList<Int> = mutableListOf()
    var chord2ColoredTabs : MutableList<Int> = mutableListOf()
    var chord3ColoredTabs : MutableList<Int> = mutableListOf()
    var chord4ColoredTabs : MutableList<Int> = mutableListOf()
    var chord5ColoredTabs : MutableList<Int> = mutableListOf()
    var chord6ColoredTabs : MutableList<Int> = mutableListOf()
    var chord7ColoredTabs : MutableList<Int> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main_frame)





        //tonality = (intent.extras?.getSerializable("EXTRA_TONALITY") ?: Tonality("C", 1, 8))  as Tonality
        restoreTonality()
        applyScale()

        StartFillTabs()
        restoreMarks()
        applyTabs()

        setListenersForScaleButtons()

        left_button.setOnClickListener { leftButtonListener() }
        right_button.setOnClickListener { rightButtonListener() }

        left_button.setOnLongClickListener{leftLongClick() }

        right_button.setOnLongClickListener{rightLongClick()}


        chord_button_1.text=tonality.chords[0].name
        chord_button_2.text=tonality.chords[1].name
        chord_button_3.text=tonality.chords[2].name
        chord_button_4.text=tonality.chords[3].name
        chord_button_5.text=tonality.chords[4].name
        chord_button_6.text=tonality.chords[5].name
        chord_button_7.text=tonality.chords[6].name

        chord_button_1.setOnClickListener {
            chord_button_1OnClick()
            diactivateAllChords()
        }
        chord_button_2.setOnClickListener {
            chord_button_2OnClick()
            diactivateAllChords()
        }
        chord_button_3.setOnClickListener {
            chord_button_3OnClick()
            diactivateAllChords()
        }
        chord_button_4.setOnClickListener {
            chord_button_4OnClick()
            diactivateAllChords()
        }
        chord_button_5.setOnClickListener {
            chord_button_5OnClick()
            diactivateAllChords()
        }
        chord_button_6.setOnClickListener {
            chord_button_6OnClick()
            diactivateAllChords()
        }
        chord_button_7.setOnClickListener {
            chord_button_7OnClick()
            diactivateAllChords()
        }

        chord_button_1.setOnLongClickListener {
            selectChord(1)
            activateChord(1)
            applyChordApplication(tonality.chords[0])
            true
        }
        chord_button_2.setOnLongClickListener {
            selectChord(2)
            activateChord(2)
            applyChordApplication(tonality.chords[1])
            true
        }
        chord_button_3.setOnLongClickListener {
            selectChord(3)
            activateChord(3)
            applyChordApplication(tonality.chords[2])
            true
        }
        chord_button_4.setOnLongClickListener {
            selectChord(4)
            activateChord(4)
            applyChordApplication(tonality.chords[3])
            true
        }
        chord_button_5.setOnLongClickListener {
            selectChord(5)
            activateChord(5)
            applyChordApplication(tonality.chords[4])
            true
        }
        chord_button_6.setOnLongClickListener {
            selectChord(6)
            activateChord(6)
            applyChordApplication(tonality.chords[5])
            true
        }
        chord_button_7.setOnLongClickListener {
            selectChord(7)
            activateChord(7)
            applyChordApplication(tonality.chords[6])
            true
        }

        add_tabmark.setOnClickListener { addTabMarkOnClick() }
        add_tabmark.setOnLongClickListener{ generateTabs() }
        delete_mark.setOnClickListener { delTabMarkOnClick() }

        add_tabmark.isEnabled = false

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
                //content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                 //Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
    }



    private fun applyScale() {
        for (pos in 0..24) {
            if (tonality.first.contains(pos)) {
                val buttonID = "f$pos"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (tonality.firstTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).background = resources.getDrawable(
                        R.drawable.tonality_scale_button,
                        null
                    )
                }else if (tonality.firstSubdominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.subdominant_scale_button,
                            null
                        )
                    )
                } else if (tonality.firstDominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.dominant_scale_button,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "f" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.second.contains(pos)) {
                val buttonID = "s" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (tonality.secondTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.tonality_scale_button,
                            null
                        )
                    )
                }else if (tonality.secondSubdominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.subdominant_scale_button,
                            null
                        )
                    )
                } else if (tonality.secondDominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.dominant_scale_button,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "s$pos"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.third.contains(pos)) {
                val buttonID = "t" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (tonality.thirdTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.tonality_scale_button,
                            null
                        )
                    )
                }else if (tonality.thirdSubdominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.subdominant_scale_button,
                            null
                        )
                    )
                }else if (tonality.thirdDominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.dominant_scale_button,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "t" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.fourth.contains(pos)) {
                val buttonID = "fo" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                val button = findViewById<Button>(resID)
                if (tonality.fourthTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.tonality_scale_button,
                            null
                        )
                    )
                }
                else if (tonality.fourthSubdominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.subdominant_scale_button,
                            null
                        )
                    )
                } else if (tonality.fourthDominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.dominant_scale_button,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "fo" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.fifth.contains(pos)) {
                val buttonID = "fi" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (tonality.fifthTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.tonality_scale_button,
                            null
                        )
                    )
                }else if (tonality.fifthSubdominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.subdominant_scale_button,
                            null
                        )
                    )
                } else if (tonality.fifthDominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.dominant_scale_button,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "fi" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.sixth.contains(pos)) {
                val buttonID = "si" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (tonality.sixthTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.tonality_scale_button,
                            null
                        )
                    )
                }
                else if (tonality.sixthSubdominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.subdominant_scale_button,
                            null
                        )
                    )
                } else if (tonality.sixthDominantNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.dominant_scale_button,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
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

    private fun restoreTonality(){
        val projectName = (intent.extras?.getString("EXTRA_ProjectName") ?: "")
        var path = applicationContext.getExternalFilesDir(null)?.path

        val newDir = File(path, projectName)
        path = newDir?.path
        val tonalityFile = File(path, "tonalityFile")
        val fos = FileInputStream(tonalityFile)
        val din = ObjectInputStream(fos)
        tonality = din.readObject() as Tonality

        din.close()
    }

    private fun restoreMarks(){
        val projectName = (intent.extras?.getString("EXTRA_ProjectName") ?: "")
        var path = applicationContext.getExternalFilesDir(null)?.path

        val newDir = File(path, projectName)
        path = newDir?.path
        val mark1File = File(path, "mark1File")
        if (mark1File.length() > 0){
            val reader = BufferedReader(FileReader(mark1File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord1ColoredTabs.add(currentLine.toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }

        val mark2File = File(path, "mark2File")
        if (mark2File.length() > 0){
            val reader = BufferedReader(FileReader(mark2File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord2ColoredTabs.add(currentLine.toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }

        val mark3File = File(path, "mark3File")
        if (mark3File.length() > 0){
            val reader = BufferedReader(FileReader(mark3File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord3ColoredTabs.add(currentLine.toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }
        val mark4File = File(path, "mark4File")
        if (mark4File.length() > 0){
            val reader = BufferedReader(FileReader(mark4File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord4ColoredTabs.add(currentLine.toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }
        val mark5File = File(path, "mark5File")
        if (mark5File.length() > 0){
            val reader = BufferedReader(FileReader(mark5File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord5ColoredTabs.add(currentLine.toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }
        val mark6File = File(path, "mark6File")
        if (mark6File.length() > 0){
            val reader = BufferedReader(FileReader(mark6File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord6ColoredTabs.add(currentLine.toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }
        val mark7File = File(path, "mark7File")
        if (mark7File.length() > 0){
            val reader = BufferedReader(FileReader(mark7File))

            // Reading all the lines of input file one by one and adding them into ArrayList

            var currentLine: String? = reader.readLine()

            while (currentLine != null) {
                chord7ColoredTabs.add(currentLine.toString().toInt())
                currentLine = reader.readLine()
            }
            reader.close()
        }
        applyTabs()
    }

    private fun StartFillTabs(){//potom wstawit sohranenija
        val projectName = intent.extras?.getString("EXTRA_ProjectName") ?: "Unknown"
        var path = applicationContext.getExternalFilesDir(null)?.path
        val newDir = File(path, projectName)
        path = newDir?.path
        val tabsFile = File(path, "tabsFile")
        val reader = BufferedReader(FileReader(tabsFile))

        // Reading all the lines of input file one by one and adding them into ArrayList

        var currentLine: String? = reader.readLine()

        while (currentLine != null) {
            tabsList.add(currentLine)
            currentLine = reader.readLine()
        }
        reader.close()
        maxTabPos = tabsList.size / 6 * 3 - 3
        applyTabs()
    }

    private fun saveMarks(){
        val projectName = intent.extras?.getString("EXTRA_ProjectName") ?: "Unknown"
        if(isExternalStorageWritable()){
            try{
                var path = applicationContext.getExternalFilesDir(null)?.path
                val newDir = File(path, projectName)
                path = newDir?.path

                val mark1File = File(path, "mark1File")
                val fileWriter1 = BufferedWriter(FileWriter(mark1File))
                for (chunk in chord1ColoredTabs) {
                    fileWriter1.write(chunk.toString())
                    fileWriter1.newLine()
                }
                fileWriter1.close()

                val mark2File = File(path, "mark2File")
                val fileWriter2 = BufferedWriter(FileWriter(mark2File))
                for (chunk in chord2ColoredTabs) {
                    fileWriter2.write(chunk.toString())
                    fileWriter2.newLine()
                }
                fileWriter2.close()

                val mark3File = File(path, "mark3File")
                val fileWriter3 = BufferedWriter(FileWriter(mark3File))
                for (chunk in chord3ColoredTabs) {
                    fileWriter3.write(chunk.toString())
                    fileWriter3.newLine()
                }
                fileWriter3.close()

                val mark4File = File(path, "mark4File")
                val fileWriter4 = BufferedWriter(FileWriter(mark4File))
                for (chunk in chord4ColoredTabs) {
                    fileWriter4.write(chunk.toString())
                    fileWriter4.newLine()
                }
                fileWriter4.close()

                val mark5File = File(path, "mark5File")
                val fileWriter5 = BufferedWriter(FileWriter(mark5File))
                for (chunk in chord5ColoredTabs) {
                    fileWriter5.write(chunk.toString())
                    fileWriter5.newLine()
                }
                fileWriter5.close()

                val mark6File = File(path, "mark6File")
                val fileWriter6 = BufferedWriter(FileWriter(mark6File))
                for (chunk in chord6ColoredTabs) {
                    fileWriter6.write(chunk.toString())
                    fileWriter6.newLine()
                }
                fileWriter6.close()

                val mark7File = File(path, "mark7File")
                val fileWriter7 = BufferedWriter(FileWriter(mark7File))
                for (chunk in chord7ColoredTabs) {
                    fileWriter7.write(chunk.toString())
                    fileWriter7.newLine()
                }
                fileWriter7.close()

            }catch (e: Exception){
                Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveTabs(){
        val projectName = intent.extras?.getString("EXTRA_ProjectName") ?: "Unknown"
        if(isExternalStorageWritable()){
            try{
                var path = applicationContext.getExternalFilesDir(null)?.path
                val newDir = File(path, projectName)
                if (!newDir.exists())  newDir.mkdirs()
                path = newDir?.path

                val tabsFile = File(path, "tabsFile")
                val fileWriter = BufferedWriter(FileWriter(tabsFile))
                for (chunk in tabsList) {
                    fileWriter.write(chunk)
                    fileWriter.newLine()
                }
                fileWriter.close()

            }catch (e: Exception){
                Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()

        saveTabs()
        saveMarks()
    }



    override fun onBackPressed() {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
    }

    private fun setListenersForScaleButtons(){
        f0.setOnClickListener {listenerForButtons(0, "0  ")}
        f0.setOnLongClickListener { longClickForButtons(f0, 0, "0 ") }
        f1.setOnClickListener {listenerForButtons(0, "1  ")}
        f1.setOnLongClickListener { longClickForButtons(f1, 0, "1 ") }
        f2.setOnClickListener {listenerForButtons(0, "2  ")}
        f2.setOnLongClickListener { longClickForButtons(f2, 0, "2 ") }
        f3.setOnClickListener {listenerForButtons(0, "3  ")}
        f3.setOnLongClickListener { longClickForButtons(f3, 0, "3 ") }
        f4.setOnClickListener {listenerForButtons(0, "4  ")}
        f4.setOnLongClickListener { longClickForButtons(f4, 0, "4 ") }
        f5.setOnClickListener {listenerForButtons(0, "5  ")}
        f5.setOnLongClickListener { longClickForButtons(f5, 0, "5 ") }
        f6.setOnClickListener {listenerForButtons(0, "6  ")}
        f6.setOnLongClickListener { longClickForButtons(f6, 0, "6 ") }
        f7.setOnClickListener {listenerForButtons(0, "7  ")}
        f7.setOnLongClickListener { longClickForButtons(f7, 0, "7 ") }
        f8.setOnClickListener {listenerForButtons(0, "8  ")}
        f8.setOnLongClickListener { longClickForButtons(f8, 0, "8 ") }
        f9.setOnClickListener {listenerForButtons(0, "9  ")}
        f9.setOnLongClickListener { longClickForButtons(f9, 0, "9 ") }
        f10.setOnClickListener {listenerForButtons(0, "10")}
        f10.setOnLongClickListener { longClickForButtons(f10, 0, "10") }
        f11.setOnClickListener {listenerForButtons(0, "11 ")}
        f11.setOnLongClickListener { longClickForButtons(f11, 0, "11") }
        f12.setOnClickListener {listenerForButtons(0, "12 ")}
        f12.setOnLongClickListener { longClickForButtons(f12, 0, "12") }
        f13.setOnClickListener {listenerForButtons(0, "13 ")}
        f13.setOnLongClickListener { longClickForButtons(f13, 0, "13") }
        f14.setOnClickListener {listenerForButtons(0, "14 ")}
        f14.setOnLongClickListener { longClickForButtons(f14, 0, "14") }
        f15.setOnClickListener {listenerForButtons(0, "15 ")}
        f15.setOnLongClickListener { longClickForButtons(f15, 0, "15") }
        f16.setOnClickListener {listenerForButtons(0, "16 ")}
        f16.setOnLongClickListener { longClickForButtons(f16, 0, "16") }
        f17.setOnClickListener {listenerForButtons(0, "17 ")}
        f17.setOnLongClickListener { longClickForButtons(f17, 0, "17") }
        f18.setOnClickListener {listenerForButtons(0, "18 ")}
        f18.setOnLongClickListener { longClickForButtons(f18, 0, "18") }
        f19.setOnClickListener {listenerForButtons(0, "19 ")}
        f19.setOnLongClickListener { longClickForButtons(f19, 0, "19") }
        f20.setOnClickListener {listenerForButtons(0, "20 ")}
        f20.setOnLongClickListener { longClickForButtons(f20, 0, "20") }
        f21.setOnClickListener {listenerForButtons(0, "21 ")}
        f21.setOnLongClickListener { longClickForButtons(f21, 0, "21") }
        f22.setOnClickListener {listenerForButtons(0, "22 ")}
        f22.setOnLongClickListener { longClickForButtons(f22, 0, "22") }
        f23.setOnClickListener {listenerForButtons(0, "23 ")}
        f23.setOnLongClickListener { longClickForButtons(f23, 0, "23") }
        f24.setOnClickListener {listenerForButtons(0, "24 ")}
        f24.setOnLongClickListener { longClickForButtons(f24, 0, "24") }


        s0.setOnClickListener {listenerForButtons(1, "0  ")}
        s0.setOnLongClickListener { longClickForButtons(s0, 1, "0 ") }
        s1.setOnClickListener {listenerForButtons(1, "1  ")}
        s1.setOnLongClickListener { longClickForButtons(s1, 1, "1 ") }
        s2.setOnClickListener {listenerForButtons(1, "2  ")}
        s2.setOnLongClickListener { longClickForButtons(s2, 1, "2 ") }
        s3.setOnClickListener {listenerForButtons(1, "3  ")}
        s3.setOnLongClickListener { longClickForButtons(s3, 1, "3 ") }
        s4.setOnClickListener {listenerForButtons(1, "4  ")}
        s4.setOnLongClickListener { longClickForButtons(s4, 1, "4 ") }
        s5.setOnClickListener {listenerForButtons(1, "5  ")}
        s5.setOnLongClickListener { longClickForButtons(s5, 1, "5 ") }
        s6.setOnClickListener {listenerForButtons(1, "6  ")}
        s6.setOnLongClickListener { longClickForButtons(s6, 1, "6 ") }
        s7.setOnClickListener {listenerForButtons(1, "7  ")}
        s7.setOnLongClickListener { longClickForButtons(s7, 1, "7 ") }
        s8.setOnClickListener {listenerForButtons(1, "8  ")}
        s8.setOnLongClickListener { longClickForButtons(s8, 1, "8 ") }
        s9.setOnClickListener {listenerForButtons(1, "9  ")}
        s9.setOnLongClickListener { longClickForButtons(s9, 1, "9 ") }
        s10.setOnClickListener {listenerForButtons(1, "10 ")}
        s10.setOnLongClickListener { longClickForButtons(s10, 1, "10") }
        s11.setOnClickListener {listenerForButtons(1, "11 ")}
        s11.setOnLongClickListener { longClickForButtons(s11, 1, "11") }
        s12.setOnClickListener {listenerForButtons(1, "12 ")}
        s12.setOnLongClickListener { longClickForButtons(s12, 1, "12") }
        s13.setOnClickListener {listenerForButtons(1, "13 ")}
        s13.setOnLongClickListener { longClickForButtons(s13, 1, "13") }
        s14.setOnClickListener {listenerForButtons(1, "14 ")}
        s14.setOnLongClickListener { longClickForButtons(s14, 1, "14") }
        s15.setOnClickListener {listenerForButtons(1, "15 ")}
        s15.setOnLongClickListener { longClickForButtons(s15, 1, "15") }
        s16.setOnClickListener {listenerForButtons(1, "16 ")}
        s16.setOnLongClickListener { longClickForButtons(s16, 1, "16") }
        s17.setOnClickListener {listenerForButtons(1, "17 ")}
        s17.setOnLongClickListener { longClickForButtons(s17, 1, "17") }
        s18.setOnClickListener {listenerForButtons(1, "18 ")}
        s18.setOnLongClickListener { longClickForButtons(s18, 1, "18") }
        s19.setOnClickListener {listenerForButtons(1, "19 ")}
        s19.setOnLongClickListener { longClickForButtons(s19, 1, "19") }
        s20.setOnClickListener {listenerForButtons(1, "20 ")}
        s20.setOnLongClickListener { longClickForButtons(s20, 1, "20") }
        s21.setOnClickListener {listenerForButtons(1, "21 ")}
        s21.setOnLongClickListener { longClickForButtons(s21, 1, "21") }
        s22.setOnClickListener {listenerForButtons(1, "22 ")}
        s22.setOnLongClickListener { longClickForButtons(s22, 1, "22") }
        s23.setOnClickListener {listenerForButtons(1, "23 ")}
        s23.setOnLongClickListener { longClickForButtons(s23, 1, "23") }
        s24.setOnClickListener {listenerForButtons(1, "24 ")}
        s24.setOnLongClickListener { longClickForButtons(s24, 1, "24") }

        t0.setOnClickListener {listenerForButtons(2, "0  ")}
        t0.setOnLongClickListener { longClickForButtons(t0, 2, "0 ") }
        t1.setOnClickListener {listenerForButtons(2, "1  ")}
        t1.setOnLongClickListener { longClickForButtons(t1, 2, "1 ") }
        t2.setOnClickListener {listenerForButtons(2, "2  ")}
        t2.setOnLongClickListener { longClickForButtons(t2, 2, "2 ") }
        t3.setOnClickListener {listenerForButtons(2, "3  ")}
        t3.setOnLongClickListener { longClickForButtons(t3, 2, "3 ") }
        t4.setOnClickListener {listenerForButtons(2, "4  ")}
        t4.setOnLongClickListener { longClickForButtons(t4, 2, "4 ") }
        t5.setOnClickListener {listenerForButtons(2, "5  ")}
        t5.setOnLongClickListener { longClickForButtons(t5, 2, "5 ") }
        t6.setOnClickListener {listenerForButtons(2, "6  ")}
        t6.setOnLongClickListener { longClickForButtons(t6, 2, "6 ") }
        t7.setOnClickListener {listenerForButtons(2, "7  ")}
        t7.setOnLongClickListener { longClickForButtons(t7, 2, "7 ") }
        t8.setOnClickListener {listenerForButtons(2, "8  ")}
        t8.setOnLongClickListener { longClickForButtons(t8, 2, "8 ") }
        t9.setOnClickListener {listenerForButtons(2, "9  ")}
        t9.setOnLongClickListener { longClickForButtons(t9, 2, "9 ") }
        t10.setOnClickListener {listenerForButtons(2, "10 ")}
        t10.setOnLongClickListener { longClickForButtons(t10, 2, "10") }
        t11.setOnClickListener {listenerForButtons(2, "11 ")}
        t11.setOnLongClickListener { longClickForButtons(t11, 2, "11") }
        t12.setOnClickListener {listenerForButtons(2, "12 ")}
        t12.setOnLongClickListener { longClickForButtons(t12, 2, "12") }
        t13.setOnClickListener {listenerForButtons(2, "13 ")}
        t13.setOnLongClickListener { longClickForButtons(t13, 2, "13") }
        t14.setOnClickListener {listenerForButtons(2, "14 ")}
        t14.setOnLongClickListener { longClickForButtons(t14, 2, "14") }
        t15.setOnClickListener {listenerForButtons(2, "15 ")}
        t15.setOnLongClickListener { longClickForButtons(t15, 2, "15") }
        t16.setOnClickListener {listenerForButtons(2, "16 ")}
        t16.setOnLongClickListener { longClickForButtons(t16, 2, "16") }
        t17.setOnClickListener {listenerForButtons(2, "17 ")}
        t17.setOnLongClickListener { longClickForButtons(t17, 2, "17") }
        t18.setOnClickListener {listenerForButtons(2, "18 ")}
        t18.setOnLongClickListener { longClickForButtons(t18, 2, "18") }
        t19.setOnClickListener {listenerForButtons(2, "19 ")}
        t19.setOnLongClickListener { longClickForButtons(t19, 2, "19") }
        t20.setOnClickListener {listenerForButtons(2, "20 ")}
        t20.setOnLongClickListener { longClickForButtons(t20, 2, "20") }
        t21.setOnClickListener {listenerForButtons(2, "21 ")}
        t21.setOnLongClickListener { longClickForButtons(t21, 2, "21") }
        t22.setOnClickListener {listenerForButtons(2, "22 ")}
        t22.setOnLongClickListener { longClickForButtons(t22, 2, "22") }
        t23.setOnClickListener {listenerForButtons(2, "23 ")}
        t23.setOnLongClickListener { longClickForButtons(t23, 2, "23") }
        t24.setOnClickListener {listenerForButtons(2, "24 ")}
        t24.setOnLongClickListener { longClickForButtons(t24, 2, "24") }

        fo0.setOnClickListener {listenerForButtons(3, "0  ")}
        fo0.setOnLongClickListener { longClickForButtons(fo0, 3, "0 ") }
        fo1.setOnClickListener {listenerForButtons(3, "1  ")}
        fo1.setOnLongClickListener { longClickForButtons(fo1, 3, "1 ") }
        fo2.setOnClickListener {listenerForButtons(3, "2  ")}
        fo2.setOnLongClickListener { longClickForButtons(fo2, 3, "2 ") }
        fo3.setOnClickListener {listenerForButtons(3, "3  ")}
        fo3.setOnLongClickListener { longClickForButtons(fo3, 3, "3 ") }
        fo4.setOnClickListener {listenerForButtons(3, "4  ")}
        fo4.setOnLongClickListener { longClickForButtons(fo4, 3, "4 ") }
        fo5.setOnClickListener {listenerForButtons(3, "5  ")}
        fo5.setOnLongClickListener { longClickForButtons(fo5, 3, "5 ") }
        fo6.setOnClickListener {listenerForButtons(3, "6  ")}
        fo6.setOnLongClickListener { longClickForButtons(fo6, 3, "6 ") }
        fo7.setOnClickListener {listenerForButtons(3, "7  ")}
        fo7.setOnLongClickListener { longClickForButtons(fo7, 3, "7 ") }
        fo8.setOnClickListener {listenerForButtons(3, "8  ")}
        fo8.setOnLongClickListener { longClickForButtons(fo8, 3, "8 ") }
        fo9.setOnClickListener {listenerForButtons(3, "9  ")}
        fo9.setOnLongClickListener { longClickForButtons(fo9, 3, "9 ") }
        fo10.setOnClickListener {listenerForButtons(3, "10 ")}
        fo10.setOnLongClickListener { longClickForButtons(fo10, 3, "10") }
        fo11.setOnClickListener {listenerForButtons(3, "11 ")}
        fo11.setOnLongClickListener { longClickForButtons(fo11, 3, "11") }
        fo12.setOnClickListener {listenerForButtons(3, "12 ")}
        fo12.setOnLongClickListener { longClickForButtons(fo12, 3, "12") }
        fo13.setOnClickListener {listenerForButtons(3, "13 ")}
        fo13.setOnLongClickListener { longClickForButtons(fo13, 3, "13") }
        fo14.setOnClickListener {listenerForButtons(3, "14 ")}
        fo14.setOnLongClickListener { longClickForButtons(fo14, 3, "14") }
        fo15.setOnClickListener {listenerForButtons(3, "15 ")}
        fo15.setOnLongClickListener { longClickForButtons(fo15, 3, "15") }
        fo16.setOnClickListener {listenerForButtons(3, "16 ")}
        fo16.setOnLongClickListener { longClickForButtons(fo16, 3, "16") }
        fo17.setOnClickListener {listenerForButtons(3, "17 ")}
        fo17.setOnLongClickListener { longClickForButtons(fo17, 3, "17") }
        fo18.setOnClickListener {listenerForButtons(3, "18 ")}
        fo18.setOnLongClickListener { longClickForButtons(fo18, 3, "18") }
        fo19.setOnClickListener {listenerForButtons(3, "19 ")}
        fo19.setOnLongClickListener { longClickForButtons(fo19, 3, "19") }
        fo20.setOnClickListener {listenerForButtons(3, "20 ")}
        fo20.setOnLongClickListener { longClickForButtons(fo20, 3, "20") }
        fo21.setOnClickListener {listenerForButtons(3, "21 ")}
        fo21.setOnLongClickListener { longClickForButtons(fo21, 3, "21") }
        fo22.setOnClickListener {listenerForButtons(3, "22 ")}
        fo22.setOnLongClickListener { longClickForButtons(fo22, 3, "22") }
        fo23.setOnClickListener {listenerForButtons(3, "23 ")}
        fo23.setOnLongClickListener { longClickForButtons(fo23, 3, "23") }
        fo24.setOnClickListener {listenerForButtons(3, "24 ")}
        fo24.setOnLongClickListener { longClickForButtons(fo24, 3, "24") }

        fi0.setOnClickListener {listenerForButtons(4, "0  ")}
        fi0.setOnLongClickListener { longClickForButtons(fi0, 4, "0 ") }
        fi1.setOnClickListener {listenerForButtons(4, "1  ")}
        fi1.setOnLongClickListener { longClickForButtons(fi1, 4, "1 ") }
        fi2.setOnClickListener {listenerForButtons(4, "2  ")}
        fi2.setOnLongClickListener { longClickForButtons(fi2, 4, "2 ") }
        fi3.setOnLongClickListener { longClickForButtons(fi3, 4, "3 ") }
        fi3.setOnClickListener {listenerForButtons(4, "3  ")}
        fi4.setOnClickListener {listenerForButtons(4, "4  ")}
        fi4.setOnLongClickListener { longClickForButtons(fi4, 4, "4 ") }
        fi5.setOnClickListener {listenerForButtons(4, "5  ")}
        fi5.setOnLongClickListener { longClickForButtons(fi5, 4, "5 ") }
        fi6.setOnClickListener {listenerForButtons(4, "6  ")}
        fi6.setOnLongClickListener { longClickForButtons(fi6, 4, "6 ") }
        fi7.setOnClickListener {listenerForButtons(4, "7  ")}
        fi7.setOnLongClickListener { longClickForButtons(fi7, 4, "7 ") }
        fi8.setOnClickListener {listenerForButtons(4, "8  ")}
        fi8.setOnLongClickListener { longClickForButtons(fi8, 4, "8 ") }
        fi9.setOnClickListener {listenerForButtons(4, "9  ")}
        fi9.setOnLongClickListener { longClickForButtons(fi9, 4, "9 ") }
        fi10.setOnClickListener {listenerForButtons(4, "10 ")}
        fi10.setOnLongClickListener { longClickForButtons(fi10, 4, "10") }
        fi11.setOnClickListener {listenerForButtons(4, "11 ")}
        fi11.setOnLongClickListener { longClickForButtons(fi11, 4, "11") }
        fi12.setOnClickListener {listenerForButtons(4, "12 ")}
        fi12.setOnLongClickListener { longClickForButtons(fi12, 4, "12") }
        fi13.setOnClickListener {listenerForButtons(4, "13 ")}
        fi13.setOnLongClickListener { longClickForButtons(fi13, 4, "13") }
        fi14.setOnClickListener {listenerForButtons(4, "14 ")}
        fi14.setOnLongClickListener { longClickForButtons(fi14, 4, "14") }
        fi15.setOnClickListener {listenerForButtons(4, "15 ")}
        fi15.setOnLongClickListener { longClickForButtons(fi15, 4, "15") }
        fi16.setOnClickListener {listenerForButtons(4, "16 ")}
        fi16.setOnLongClickListener { longClickForButtons(fi16, 4, "16") }
        fi17.setOnClickListener {listenerForButtons(4, "17 ")}
        fi17.setOnLongClickListener { longClickForButtons(fi17, 4, "17") }
        fi18.setOnClickListener {listenerForButtons(4, "18 ")}
        fi18.setOnLongClickListener { longClickForButtons(fi18, 4, "18") }
        fi19.setOnClickListener {listenerForButtons(4, "19 ")}
        fi19.setOnLongClickListener { longClickForButtons(fi19, 4, "19") }
        fi20.setOnClickListener {listenerForButtons(4, "20 ")}
        fi20.setOnLongClickListener { longClickForButtons(fi20, 4, "20") }
        fi21.setOnClickListener {listenerForButtons(4, "21 ")}
        fi21.setOnLongClickListener { longClickForButtons(fi21, 4, "21") }
        fi22.setOnClickListener {listenerForButtons(4, "22 ")}
        fi22.setOnLongClickListener { longClickForButtons(fi22, 4, "22") }
        fi23.setOnClickListener {listenerForButtons(4, "23 ")}
        fi23.setOnLongClickListener { longClickForButtons(fi23, 4, "23") }
        fi24.setOnClickListener {listenerForButtons(4, "24 ")}
        fi24.setOnLongClickListener { longClickForButtons(fi24, 4, "24") }

        si0.setOnClickListener {listenerForButtons(5, "0  ")}
        si0.setOnLongClickListener { longClickForButtons(si0, 5, "0 ") }
        si1.setOnClickListener {listenerForButtons(5, "1  ")}
        si1.setOnLongClickListener { longClickForButtons(si1, 5, "1 ") }
        si2.setOnClickListener {listenerForButtons(5, "2  ")}
        si2.setOnLongClickListener { longClickForButtons(si2, 5, "2 ") }
        si3.setOnClickListener {listenerForButtons(5, "3  ")}
        si3.setOnLongClickListener { longClickForButtons(si3, 5, "3 ") }
        si4.setOnClickListener {listenerForButtons(5, "4  ")}
        si4.setOnLongClickListener { longClickForButtons(si4, 5, "4 ") }
        si5.setOnClickListener {listenerForButtons(5, "5  ")}
        si5.setOnLongClickListener { longClickForButtons(si5, 5, "5 ") }
        si6.setOnClickListener {listenerForButtons(5, "6  ")}
        si6.setOnLongClickListener { longClickForButtons(si6, 5, "6 ") }
        si7.setOnClickListener {listenerForButtons(5, "7  ")}
        si7.setOnLongClickListener { longClickForButtons(si7, 5, "7 ") }
        si8.setOnClickListener {listenerForButtons(5, "8  ")}
        si8.setOnLongClickListener { longClickForButtons(si8, 5, "8 ") }
        si9.setOnClickListener {listenerForButtons(5, "9  ")}
        si9.setOnLongClickListener { longClickForButtons(si9, 5, "9 ") }
        si10.setOnClickListener {listenerForButtons(5, "10 ")}
        si10.setOnLongClickListener { longClickForButtons(si10, 5, "10") }
        si11.setOnClickListener {listenerForButtons(5, "11 ")}
        si11.setOnLongClickListener { longClickForButtons(si11, 5, "11") }
        si12.setOnClickListener {listenerForButtons(5, "12 ")}
        si12.setOnLongClickListener { longClickForButtons(si12, 5, "12") }
        si13.setOnClickListener {listenerForButtons(5, "13 ")}
        si13.setOnLongClickListener { longClickForButtons(si13, 5, "13") }
        si14.setOnClickListener {listenerForButtons(5, "14 ")}
        si14.setOnLongClickListener { longClickForButtons(si14, 5, "14") }
        si15.setOnClickListener {listenerForButtons(5, "15 ")}
        si15.setOnLongClickListener { longClickForButtons(si15, 5, "15") }
        si16.setOnClickListener {listenerForButtons(5, "16 ")}
        si16.setOnLongClickListener { longClickForButtons(si16, 5, "16") }
        si17.setOnClickListener {listenerForButtons(5, "17 ")}
        si17.setOnLongClickListener { longClickForButtons(si17, 5, "17") }
        si18.setOnClickListener {listenerForButtons(5, "18 ")}
        si18.setOnLongClickListener { longClickForButtons(si18, 5, "18") }
        si19.setOnClickListener {listenerForButtons(5, "19 ")}
        si19.setOnLongClickListener { longClickForButtons(si19, 5, "19") }
        si20.setOnClickListener {listenerForButtons(5, "20 ")}
        si20.setOnLongClickListener { longClickForButtons(si20, 5, "20") }
        si21.setOnClickListener {listenerForButtons(5, "21 ")}
        si21.setOnLongClickListener { longClickForButtons(si21, 5, "21") }
        si22.setOnClickListener {listenerForButtons(5, "22 ")}
        si22.setOnLongClickListener { longClickForButtons(si22, 5, "22") }
        si23.setOnClickListener {listenerForButtons(5, "23 ")}
        si23.setOnLongClickListener { longClickForButtons(si23, 5, "23") }
        si24.setOnClickListener {listenerForButtons(5, "24 ")}
        si24.setOnLongClickListener { longClickForButtons(si24, 5, "24") }


    }

    private fun listenerForButtons(stringNum: Int, symbol: String){
        val startIndex = tabIndex + stringNum * (tabsList.size / 6)
        val strOld0 = tabsList[startIndex][0].toString() + tabsList[startIndex][1].toString()
        val strOld1 = tabsList[startIndex][1].toString() + tabsList[startIndex][2].toString()
        val strNew = symbol[0].toString() + symbol[1].toString()
        if (strOld0 == strNew || strOld1 == strNew) tabsList[startIndex] = "-  " else tabsList[startIndex] = symbol
        addTabMarkOnClick()
        if (tabPos == maxTabPos){
            rightLongClick()
        }else{
            rightButtonListener()
        }
        applyTabs()
    }

    private fun longClickForButtons(view : View, stringNum: Int, symbol: String): Boolean {
        val popup = PopupMenu(applicationContext, view)
        //inflating menu from xml resource
        popup.inflate(R.menu.tabs_menu)
        //adding click listener
        popup.setOnMenuItemClickListener { item ->
            val startIndex = tabIndex + stringNum * (tabsList.size / 6)
            fun addElem(char: Char) {
                tabsList[startIndex] = char + symbol
                addTabMarkOnClick()
                if (tabPos == maxTabPos){
                    rightLongClick()
                }else{
                    rightButtonListener()
                }
                applyTabs()
            }
            when (item.itemId) {
                R.id.SlideUp -> {
                    addElem('/')
                }
                R.id.SlideDown -> {
                    addElem('\\')
                }

                R.id.HammerOn -> {
                    addElem('h')
                }

                R.id.PullOff -> {
                    addElem('p')
                }

                R.id.Band -> {
                    addElem('b')
                }

                R.id.Vibrato -> {
                    var str = symbol.trim()
                    str = "$str~"
                    if(str.length != 3) str = "$str "
                    tabsList[startIndex] = str
                    applyTabs()
                }
            }
            false
        }
        //displaying the popup
        popup.show()
        return true
    }

    private fun applyTabs(){
        var tabsString = ""
        var n = tabsList.size / 6
        var firstEl = 0
        var lastEl = n
        for(l in 0..5){
            for (i in firstEl..(lastEl-1)){
                tabsString += tabsList[i]
            }
            firstEl+=n
            lastEl+=n
            tabsString += "\n"
        }

        val WordtoSpan: Spannable = SpannableString(tabsString)

        var skipRange = tabsString.length / 6
        for(pos in chord1ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_1)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }
        for(pos in chord2ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_2)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }
        for(pos in chord3ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_3)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }
        for(pos in chord4ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_4)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }
        for(pos in chord5ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_5)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }
        for(pos in chord6ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_6)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }
        for(pos in chord7ColoredTabs){
            var index = pos
            for (i in 0..5){
                WordtoSpan.setSpan(
                    BackgroundColorSpan(getColor(R.color.chord_7)), //BackgroundColorSpan()
                    index,
                    index + 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                index+=skipRange
            }
        }

        var ind = tabPos
        for (i in 0..5){
            WordtoSpan.setSpan(
                ForegroundColorSpan(Color.WHITE), //BackgroundColorSpan()
                ind,
                ind + 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            WordtoSpan.setSpan(
                BackgroundColorSpan(getColor(R.color.mainTheme)), //BackgroundColorSpan()
                ind,
                ind + 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ind+=skipRange
        }
        tabs.text=WordtoSpan
        delMarkButtonEnable()
    }

    private fun leftButtonListener(){
        if (tabPos != 0){
            tabPos-=3
            tabIndex -= 1
            applyTabs()
        }
    }

    private fun rightButtonListener(){
        if (tabPos != maxTabPos){
            tabPos+=3
            tabIndex += 1
            applyTabs()
        }
    }

    private fun chord_button_1OnClick(){
        if(chord_button_1.isSelected){
            chord_button_1.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(1)
            applyChordScale(tonality.chords[0])
            add_tabmark.setEnabled(true)
        }
    }

    private fun chord_button_2OnClick(){
        if(chord_button_2.isSelected){
            chord_button_2.isSelected = false
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(2)
            applyChordScale(tonality.chords[1])
            add_tabmark.setEnabled(true)
        }
    }
    private fun chord_button_3OnClick(){
        if(chord_button_3.isSelected){
            chord_button_3.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(3)
            applyChordScale(tonality.chords[2])
            add_tabmark.setEnabled(true)
        }
    }
    private fun chord_button_4OnClick(){
        if(chord_button_4.isSelected){
            chord_button_4.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(4)
            applyChordScale(tonality.chords[3])
            add_tabmark.setEnabled(true)
        }
    }
    private fun chord_button_5OnClick(){
        if(chord_button_5.isSelected){
            chord_button_5.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(5)
            applyChordScale(tonality.chords[4])
            add_tabmark.setEnabled(true)
        }
    }
    private fun chord_button_6OnClick(){
        if(chord_button_6.isSelected){
            chord_button_6.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(6)
            applyChordScale(tonality.chords[5])
            add_tabmark.setEnabled(true)
        }
    }
    private fun chord_button_7OnClick(){
        if(chord_button_7.isSelected){
            chord_button_7.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            selectChord(7)
            applyChordScale(tonality.chords[6])
            add_tabmark.setEnabled(true)
        }
    }

    private fun applyChordScale(chord: Chord) {
        for (pos in 0..24) {
            if (tonality.first.contains(pos)) {
                val buttonID = "f" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (chord.firstStep1Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else if (chord.firstStep2Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                } else if (chord.firstStep3Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "f" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.second.contains(pos)) {
                val buttonID = "s" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (chord.secondStep1Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else if (chord.secondStep2Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                } else if (chord.secondStep3Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "s$pos"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.third.contains(pos)) {
                val buttonID = "t" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (chord.thirdStep1Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else if (chord.thirdStep2Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else if (chord.thirdStep3Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "t" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.fourth.contains(pos)) {
                val buttonID = "fo" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                val button = findViewById<Button>(resID)
                if (chord.fourthStep1Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }
                else if (chord.fourthStep2Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                } else if (chord.fourthStep3Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "fo" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.fifth.contains(pos)) {
                val buttonID = "fi" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (chord.fifthStep1Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else if (chord.fifthStep2Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                } else if (chord.fifthStep3Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
                val buttonID = "fi" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).setBackground(
                    resources.getDrawable(
                        R.drawable.round_button,
                        null
                    )
                )
            }
            if (tonality.sixth.contains(pos)) {
                val buttonID = "si" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (chord.sixthStep1Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }
                else if (chord.sixthStep2Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                } else if (chord.sixthStep3Notes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            chord.drawRes,
                            null
                        )
                    )
                }else{
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                }
            } else {
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


    private fun applyChordApplication(chord: Chord) {
        for (pos in 0..24) {
            for( str in listOf("f", "s", "t", "fo", "fi", "si")){
                val buttonID = str + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<Button>(resID).background = resources.getDrawable(
                    R.drawable.round_button,
                    null
                )
            }
        }
        for (pos in 0..24) {
            if (chord.isSixString1){
                var listOfButtonInd = listOf("f", "s", "t", "fo", "fi", "si")
                var ind = 0
                for(sixString1Pos in chord.sixString1){
                    val buttonID = listOfButtonInd[ind] + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    if (sixString1Pos==pos){
                        findViewById<Button>(resID).setBackground(
                            resources.getDrawable(
                                R.drawable.tonality_scale_button,
                                null
                            )
                        )
                    }
                    ind++
                }
            }
            if (chord.isSixString2){
                var listOfButtonInd = listOf("f", "s", "t", "fo", "fi", "si")
                var ind = 0
                for(sixString1Pos in chord.sixString2){
                    val buttonID = listOfButtonInd[ind] + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    if (sixString1Pos==pos){
                        findViewById<Button>(resID).setBackground(
                            resources.getDrawable(
                                R.drawable.tonality_scale_button,
                                null
                            )
                        )
                    }
                    ind++
                }
            }
            if (chord.isFifthString1){
                var listOfButtonInd = listOf("f", "s", "t", "fo", "fi", "si")
                var ind = 0
                for(sixString1Pos in chord.fifthString1){
                    val buttonID = listOfButtonInd[ind] + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    if (sixString1Pos==pos){
                        findViewById<Button>(resID).setBackground(
                            resources.getDrawable(
                                R.drawable.subdominant_scale_button,
                                null
                            )
                        )
                    }
                    ind++
                }
            }
            if (chord.isFifthString2){
                var listOfButtonInd = listOf("f", "s", "t", "fo", "fi", "si")
                var ind = 0
                for(sixString1Pos in chord.fifthString2){
                    val buttonID = listOfButtonInd[ind] + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    if (sixString1Pos==pos){
                        findViewById<Button>(resID).setBackground(
                            resources.getDrawable(
                                R.drawable.subdominant_scale_button,
                                null
                            )
                        )
                    }
                    ind++
                }
            }
            if (chord.isFourthString1){
                var listOfButtonInd = listOf("f", "s", "t", "fo", "fi", "si")
                var ind = 0
                for(sixString1Pos in chord.fourthString1){
                    val buttonID = listOfButtonInd[ind] + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    if (sixString1Pos==pos){
                        findViewById<Button>(resID).setBackground(
                            resources.getDrawable(
                                R.drawable.fourth_string_chord_button,
                                null
                            )
                        )
                    }
                    if(ind==3){
                        if(chord.isSixString1){
                            if (sixString1Pos==pos){
                                findViewById<Button>(resID).setBackground(
                                    resources.getDrawable(
                                        R.drawable.half_colored_scale_button,
                                        null
                                    )
                                )
                            }
                            if (chord.dim){
                                if (sixString1Pos==pos){
                                    findViewById<Button>(resID).setBackground(
                                        resources.getDrawable(
                                            R.drawable.half_colored_dim_scale_button,
                                            null
                                        )
                                    )
                                }
                            }
                        }
                    }
                    ind++
                }
            }
            if (chord.isFourthString2){
                var listOfButtonInd = listOf("f", "s", "t", "fo", "fi", "si")
                var ind = 0
                for(sixString1Pos in chord.fourthString2){
                    val buttonID = listOfButtonInd[ind] + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    if (sixString1Pos==pos){
                        findViewById<Button>(resID).setBackground(
                            resources.getDrawable(
                                R.drawable.fourth_string_chord_button,
                                null
                            )
                        )
                    }
                    if(ind==3){
                        if(chord.isSixString1){
                            if (sixString1Pos==pos){
                                findViewById<Button>(resID).setBackground(
                                    resources.getDrawable(
                                        R.drawable.half_colored_scale_button,
                                        null
                                    )
                                )
                            }
                            if (chord.dim){
                                if (sixString1Pos==pos){
                                    findViewById<Button>(resID).setBackground(
                                        resources.getDrawable(
                                            R.drawable.half_colored_dim_scale_button,
                                            null
                                        )
                                    )
                                }
                            }
                        }
                    }
                    ind++
                }
            }

        }
    }

    private fun addTabMarkOnClick(){
        if(chord_button_1.isSelected){
            chord1ColoredTabs.add(tabPos)
            chord2ColoredTabs.remove(tabPos)
            chord3ColoredTabs.remove(tabPos)
            chord4ColoredTabs.remove(tabPos)
            chord5ColoredTabs.remove(tabPos)
            chord6ColoredTabs.remove(tabPos)
            chord7ColoredTabs.remove(tabPos)
        }
        if(chord_button_2.isSelected){
            chord2ColoredTabs.add(tabPos)
            chord1ColoredTabs.remove(tabPos)
            chord3ColoredTabs.remove(tabPos)
            chord4ColoredTabs.remove(tabPos)
            chord5ColoredTabs.remove(tabPos)
            chord6ColoredTabs.remove(tabPos)
            chord7ColoredTabs.remove(tabPos)
        }
        if(chord_button_3.isSelected){
            chord3ColoredTabs.add(tabPos)
            chord2ColoredTabs.remove(tabPos)
            chord1ColoredTabs.remove(tabPos)
            chord4ColoredTabs.remove(tabPos)
            chord5ColoredTabs.remove(tabPos)
            chord6ColoredTabs.remove(tabPos)
            chord7ColoredTabs.remove(tabPos)
        }
        if(chord_button_4.isSelected){
            chord4ColoredTabs.add(tabPos)
            chord2ColoredTabs.remove(tabPos)
            chord3ColoredTabs.remove(tabPos)
            chord1ColoredTabs.remove(tabPos)
            chord5ColoredTabs.remove(tabPos)
            chord6ColoredTabs.remove(tabPos)
            chord7ColoredTabs.remove(tabPos)
        }
        if(chord_button_5.isSelected){
            chord5ColoredTabs.add(tabPos)
            chord2ColoredTabs.remove(tabPos)
            chord3ColoredTabs.remove(tabPos)
            chord4ColoredTabs.remove(tabPos)
            chord1ColoredTabs.remove(tabPos)
            chord6ColoredTabs.remove(tabPos)
            chord7ColoredTabs.remove(tabPos)
        }
        if(chord_button_6.isSelected){
            chord6ColoredTabs.add(tabPos)
            chord2ColoredTabs.remove(tabPos)
            chord3ColoredTabs.remove(tabPos)
            chord4ColoredTabs.remove(tabPos)
            chord5ColoredTabs.remove(tabPos)
            chord1ColoredTabs.remove(tabPos)
            chord7ColoredTabs.remove(tabPos)
        }
        if(chord_button_7.isSelected){
            chord7ColoredTabs.add(tabPos)
            chord2ColoredTabs.remove(tabPos)
            chord3ColoredTabs.remove(tabPos)
            chord4ColoredTabs.remove(tabPos)
            chord5ColoredTabs.remove(tabPos)
            chord6ColoredTabs.remove(tabPos)
            chord1ColoredTabs.remove(tabPos)
        }
        applyTabs()
    }

    private fun delTabMarkOnClick(){
        chord1ColoredTabs.remove(tabPos)
        chord2ColoredTabs.remove(tabPos)
        chord3ColoredTabs.remove(tabPos)
        chord4ColoredTabs.remove(tabPos)
        chord5ColoredTabs.remove(tabPos)
        chord6ColoredTabs.remove(tabPos)
        chord7ColoredTabs.remove(tabPos)
        applyTabs()
    }

    /* Checks if external storage is available for read and write */
    private fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state)
    }

    /* Checks if external storage is available to at least read */
    private fun isExternalStorageReadable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
    }

    private fun leftLongClick():Boolean{
        if (maxTabPos > 6) {
            var skipRange = tabsList.size / 6
            var ind = tabIndex
            for (i in 0..5) {
                tabsList.removeAt(ind - i)
                ind += skipRange
            }
            var isDelete = false
            var posToDel = 0
            for (pos in 0 until chord1ColoredTabs.size) {
                if (chord1ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord1ColoredTabs[pos] > tabPos) {
                        chord1ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord1ColoredTabs.removeAt(posToDel)
            }
            isDelete = false
            for (pos in 0 until chord2ColoredTabs.size) {
                if (chord2ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord2ColoredTabs[pos] > tabPos) {
                        chord2ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord2ColoredTabs.removeAt(posToDel)
            }
            isDelete = false
            for (pos in 0..(chord3ColoredTabs.size - 1)) {
                if (chord3ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord3ColoredTabs[pos] > tabPos) {
                        chord3ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord3ColoredTabs.removeAt(posToDel)
            }
            isDelete = false
            for (pos in 0..(chord4ColoredTabs.size - 1)) {
                if (chord4ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord4ColoredTabs[pos] > tabPos) {
                        chord4ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord4ColoredTabs.removeAt(posToDel)
            }
            isDelete = false
            for (pos in 0..(chord5ColoredTabs.size - 1)) {
                if (chord5ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord5ColoredTabs[pos] > tabPos) {
                        chord5ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord5ColoredTabs.removeAt(posToDel)
            }
            isDelete = false
            for (pos in 0..(chord6ColoredTabs.size - 1)) {
                if (chord6ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord6ColoredTabs[pos] > tabPos) {
                        chord6ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord6ColoredTabs.removeAt(posToDel)
            }
            isDelete = false
            for (pos in 0..(chord7ColoredTabs.size - 1)) {
                if (chord7ColoredTabs[pos] == tabPos) {
                    posToDel = pos
                    isDelete = true
                } else
                    if (chord7ColoredTabs[pos] > tabPos) {
                        chord7ColoredTabs[pos] -= 3
                    }
            }
            if (isDelete) {
                chord7ColoredTabs.removeAt(posToDel)
            }
            skipRange = tabsList.size / 6
            var endIndex = skipRange
            for (i in 0..5) {
                tabsList.add(endIndex + i, "-  ")
                endIndex += skipRange
            }

            
            applyTabs()
        }
        return true
    }

    private fun rightLongClick(): Boolean {
        if (tabIndex < 2500) {
            var skipRange = tabsList.size / 6
            var ind = tabIndex
            for (i in 0..5) {
                tabsList.add(ind + 1 + i, "-  ")
                ind += skipRange
            }

            for (pos in 0..(chord1ColoredTabs.size - 1)) {
                if (chord1ColoredTabs[pos] > tabPos) {
                    chord1ColoredTabs[pos] += 3
                }
            }
            for (pos in 0..(chord2ColoredTabs.size - 1)) {
                if (chord2ColoredTabs[pos] > tabPos) {
                    chord2ColoredTabs[pos] += 3
                }
            }
            for (pos in 0..(chord3ColoredTabs.size - 1)) {
                if (chord3ColoredTabs[pos] > tabPos) {
                    chord3ColoredTabs[pos] += 3
                }
            }
            for (pos in 0..(chord4ColoredTabs.size - 1)) {
                if (chord4ColoredTabs[pos] > tabPos) {
                    chord4ColoredTabs[pos] += 3
                }
            }
            for (pos in 0..(chord5ColoredTabs.size - 1)) {
                if (chord5ColoredTabs[pos] > tabPos) {
                    chord5ColoredTabs[pos] += 3
                }
            }
            for (pos in 0..(chord6ColoredTabs.size - 1)) {
                if (chord6ColoredTabs[pos] > tabPos) {
                    chord6ColoredTabs[pos] += 3
                }
            }
            for (pos in 0..(chord7ColoredTabs.size - 1)) {
                if (chord7ColoredTabs[pos] > tabPos) {
                    chord7ColoredTabs[pos] += 3
                }
            }

            tabPos += 3
            maxTabPos += 3
            tabIndex += 1
            applyTabs()
        }
        return true
    }

    private fun generateTabs(): Boolean {
        var chord = tonality.chords[0]
        if(chord_button_1.isSelected) chord =tonality.chords[0]
        if(chord_button_2.isSelected) chord =tonality.chords[1]
        if(chord_button_3.isSelected) chord =tonality.chords[2]
        if(chord_button_4.isSelected) chord =tonality.chords[3]
        if(chord_button_5.isSelected) chord =tonality.chords[4]
        if(chord_button_6.isSelected) chord =tonality.chords[5]
        if(chord_button_7.isSelected) chord =tonality.chords[6]
        val listOfChordNotesLists = listOf(chord.firstStep1Notes, chord.firstStep2Notes, chord.firstStep3Notes,
            chord.secondStep1Notes, chord.secondStep2Notes, chord.secondStep3Notes, chord.thirdStep1Notes, chord.thirdStep2Notes,
            chord.thirdStep3Notes, chord.fourthStep1Notes,chord.fourthStep2Notes,
            chord.fourthStep3Notes,chord.fifthStep1Notes,chord.fifthStep2Notes,chord.fifthStep3Notes,
            chord.sixthStep1Notes,chord.sixthStep2Notes,chord.sixthStep3Notes)
        val listOfTonalityNotes = listOf(tonality.firstTonalityNotes, tonality.firstDominantNotes,
        tonality.firstSubdominantNotes,tonality.secondTonalityNotes, tonality.secondDominantNotes,
            tonality.secondSubdominantNotes, tonality.thirdTonalityNotes, tonality.thirdDominantNotes,
            tonality.thirdSubdominantNotes, tonality.fourthTonalityNotes, tonality.fourthDominantNotes,
            tonality.fourthSubdominantNotes, tonality.fifthTonalityNotes, tonality.fifthDominantNotes,
            tonality.fifthSubdominantNotes, tonality.sixthTonalityNotes, tonality.sixthDominantNotes,
            tonality.sixthSubdominantNotes)
        val length = Random.nextInt(3,10)
        var strings : MutableList<Int> = mutableListOf()
        var notes : MutableList<Int> = mutableListOf()
        var previousNote = Random.nextInt(0,22)
        var string = Random.nextInt(0,6)
        var stepUp = 6
        var stepDown = 4
        while(!(listOfChordNotesLists[string*3].contains(previousNote) || listOfChordNotesLists[string*3 + 1].contains(previousNote) || listOfChordNotesLists[string*3 + 2].contains(previousNote))){
            previousNote = Random.nextInt(0,22)
            string = Random.nextInt(0,6)
        }
        notes.add(previousNote)
        strings.add(string)
        while(notes.size != length-1){
            var from = previousNote-stepDown
            var until = previousNote+stepUp
            while(until>22) until -= 1
            while(from<0) from += 1
            var actual = Random.nextInt(from,until)
            string = Random.nextInt(0,6)
            while(!(listOfTonalityNotes[string*3].contains(actual) || listOfTonalityNotes[string*3 + 1].contains(actual)
                        || listOfTonalityNotes[string*3 + 2].contains(actual))){
                actual = Random.nextInt(from,until)
            }
            notes.add(actual)
            strings.add(string)
            previousNote=actual
        }
        var from = previousNote-stepDown
        var until = previousNote+stepUp
        while(until>22) until-=1
        while(from<0) from+=1
        var actual = Random.nextInt(from,until)
        string = Random.nextInt(0,6)
        while(!(listOfChordNotesLists[string*3].contains(actual) || listOfChordNotesLists[string*3 + 1].contains(actual)
                    || listOfChordNotesLists[string*3 + 2].contains(actual))){
            actual = Random.nextInt(from,until)
        }
        notes.add(actual)
        strings.add(string)

        for (i in 0 until length){
            var str="${notes[i]} "
            if (str.length != 3) str = "$str "
            listenerForButtons(strings[i], str)
            addTabMarkOnClick()
            rightLongClick()
        }
        return true
    }

    private fun selectChord(num: Int){
        when(num){
            1 -> {
                chord_button_1.isSelected=true
                chord_button_2.isSelected=false
                chord_button_3.isSelected=false
                chord_button_4.isSelected=false
                chord_button_5.isSelected=false
                chord_button_6.isSelected=false
                chord_button_7.isSelected=false
            }
            2 -> {
                chord_button_1.isSelected=false
                chord_button_2.isSelected=true
                chord_button_3.isSelected=false
                chord_button_4.isSelected=false
                chord_button_5.isSelected=false
                chord_button_6.isSelected=false
                chord_button_7.isSelected=false
            }
            3 -> {
                chord_button_1.isSelected=false
                chord_button_2.isSelected=false
                chord_button_3.isSelected=true
                chord_button_4.isSelected=false
                chord_button_5.isSelected=false
                chord_button_6.isSelected=false
                chord_button_7.isSelected=false
            }
            4 -> {
                chord_button_1.isSelected=false
                chord_button_2.isSelected=false
                chord_button_3.isSelected=false
                chord_button_4.isSelected=true
                chord_button_5.isSelected=false
                chord_button_6.isSelected=false
                chord_button_7.isSelected=false
            }
            5 -> {
                chord_button_1.isSelected=false
                chord_button_2.isSelected=false
                chord_button_3.isSelected=false
                chord_button_4.isSelected=false
                chord_button_5.isSelected=true
                chord_button_6.isSelected=false
                chord_button_7.isSelected=false
            }
            6 -> {
                chord_button_1.isSelected=false
                chord_button_2.isSelected=false
                chord_button_3.isSelected=false
                chord_button_4.isSelected=false
                chord_button_5.isSelected=false
                chord_button_6.isSelected=true
                chord_button_7.isSelected=false
            }
            7 -> {
                chord_button_1.isSelected=false
                chord_button_2.isSelected=false
                chord_button_3.isSelected=false
                chord_button_4.isSelected=false
                chord_button_5.isSelected=false
                chord_button_6.isSelected=false
                chord_button_7.isSelected=true
            }

        }
        add_tabmark.setEnabled(true)
    }

    private fun delMarkButtonEnable(){
        delete_mark.isEnabled = false
        if(chord1ColoredTabs.contains(tabPos) ||
            chord2ColoredTabs.contains(tabPos) ||
            chord3ColoredTabs.contains(tabPos) ||
            chord4ColoredTabs.contains(tabPos) ||
            chord5ColoredTabs.contains(tabPos) ||
            chord6ColoredTabs.contains(tabPos) ||
            chord7ColoredTabs.contains(tabPos)  ){
            delete_mark.isEnabled = true
        }
    }

    private fun activateChord(num: Int){
        when(num){
            1 -> {
                chord_button_1.isActivated=true
                chord_button_2.isActivated=false
                chord_button_3.isActivated=false
                chord_button_4.isActivated=false
                chord_button_5.isActivated=false
                chord_button_6.isActivated=false
                chord_button_7.isActivated=false
            }
            2 -> {
                chord_button_1.isActivated=false
                chord_button_2.isActivated=true
                chord_button_3.isActivated=false
                chord_button_4.isActivated=false
                chord_button_5.isActivated=false
                chord_button_6.isActivated=false
                chord_button_7.isActivated=false
            }
            3 -> {
                chord_button_1.isActivated=false
                chord_button_2.isActivated=false
                chord_button_3.isActivated=true
                chord_button_4.isActivated=false
                chord_button_5.isActivated=false
                chord_button_6.isActivated=false
                chord_button_7.isActivated=false
            }
            4 -> {
                chord_button_1.isActivated=false
                chord_button_2.isActivated=false
                chord_button_3.isActivated=false
                chord_button_4.isActivated=true
                chord_button_5.isActivated=false
                chord_button_6.isActivated=false
                chord_button_7.isActivated=false
            }
            5 -> {
                chord_button_1.isActivated=false
                chord_button_2.isActivated=false
                chord_button_3.isActivated=false
                chord_button_4.isActivated=false
                chord_button_5.isActivated=true
                chord_button_6.isActivated=false
                chord_button_7.isActivated=false
            }
            6 -> {
                chord_button_1.isActivated=false
                chord_button_2.isActivated=false
                chord_button_3.isActivated=false
                chord_button_4.isActivated=false
                chord_button_5.isActivated=false
                chord_button_6.isActivated=true
                chord_button_7.isActivated=false
            }
            7 -> {
                chord_button_1.isActivated=false
                chord_button_2.isActivated=false
                chord_button_3.isActivated=false
                chord_button_4.isActivated=false
                chord_button_5.isActivated=false
                chord_button_6.isActivated=false
                chord_button_7.isActivated=true
            }

        }
    }

    private fun diactivateAllChords(){
        chord_button_1.isActivated=false
        chord_button_2.isActivated=false
        chord_button_3.isActivated=false
        chord_button_4.isActivated=false
        chord_button_5.isActivated=false
        chord_button_6.isActivated=false
        chord_button_7.isActivated=false
    }


}