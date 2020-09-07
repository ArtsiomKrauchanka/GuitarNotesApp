package com.hfad.GuitarNotes

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_frame.*
import java.io.*


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
        setContentView(R.layout.activity_main_frame)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE






        //tonality = (intent.extras?.getSerializable("EXTRA_TONALITY") ?: Tonality("C", 1, 8))  as Tonality
        restoreTonality()
        applyScale()

        StartFillTabs()
        restoreMarks()
        applyTabs()

        setListenersForScaleButtons()

        left_button.setOnClickListener { leftButtonListener() }
        right_button.setOnClickListener { rightButtonListener() }

        left_button.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                if (maxTabPos > 6) {
                    var skipRange = tabsList.size / 6
                    var ind = tabIndex
                    for (i in 0..5) {
                        tabsList.removeAt(ind - i)
                        ind += skipRange
                    }
                    var isDelete = false
                    var posToDel = 0
                    for (pos in 0..(chord1ColoredTabs.size - 1)) {
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
                    for (pos in 0..(chord2ColoredTabs.size - 1)) {
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


                    maxTabPos -= 3
                    applyTabs()
                }
                return true
            }

        })

        right_button.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
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

        })

        chord_button_1.text=tonality.chords[0].name
        chord_button_2.text=tonality.chords[1].name
        chord_button_3.text=tonality.chords[2].name
        chord_button_4.text=tonality.chords[3].name
        chord_button_5.text=tonality.chords[4].name
        chord_button_6.text=tonality.chords[5].name
        chord_button_7.text=tonality.chords[6].name

        chord_button_1.setOnClickListener {
            chord_button_1OnClick()
        }
        chord_button_2.setOnClickListener {
            chord_button_2OnClick()
        }
        chord_button_3.setOnClickListener {
            chord_button_3OnClick()
        }
        chord_button_4.setOnClickListener {
            chord_button_4OnClick()
        }
        chord_button_5.setOnClickListener {
            chord_button_5OnClick()
        }
        chord_button_6.setOnClickListener {
            chord_button_6OnClick()
        }
        chord_button_7.setOnClickListener {
            chord_button_7OnClick()
        }

        chord_button_1.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[0])
                return true
            }
        })
        chord_button_2.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[1])
                return true
            }

        })
        chord_button_3.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[2])
                return true
            }

        })
        chord_button_4.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[3])
                return true
            }

        })
        chord_button_5.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[4])
                return true
            }

        })
        chord_button_6.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[5])
                return true
            }

        })
        chord_button_7.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                applyChordApplication(tonality.chords[6])
                return true
            }

        })

        add_tabmark.setOnClickListener { addTabMarkOnClick() }
        delete_mark.setOnClickListener { delTabMarkOnClick() }

        add_tabmark.setEnabled(false)

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



    fun applyScale() {
        for (pos in 0..24) {
            if (tonality.first.contains(pos)) {
                val buttonID = "f" + pos.toString()
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                if (tonality.firstTonalityNotes.contains(pos)){
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.tonality_scale_button,
                            null
                        )
                    )
                }else if (tonality.firstSumdominantNotes.contains(pos)){
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
                }else if (tonality.secondSumdominantNotes.contains(pos)){
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
                }else if (tonality.thirdSumdominantNotes.contains(pos)){
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
                else if (tonality.fourthSumdominantNotes.contains(pos)){
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
                }else if (tonality.fifthSumdominantNotes.contains(pos)){
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
                else if (tonality.sixthSumdominantNotes.contains(pos)){
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

    fun restoreTonality(){
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

    fun restoreMarks(){
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

    fun StartFillTabs(){//potom wstawit sohranenija
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

    fun saveMarks(){
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

    fun saveTabs(){
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

    override fun onStop() {
        super.onStop()

        saveTabs()
        saveMarks()
    }


    override fun onBackPressed() {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
    }

    fun setListenersForScaleButtons(){
        f0.setOnClickListener {listenerForButtons(0, "0  ")}
        f1.setOnClickListener {listenerForButtons(0, "1  ")}
        f2.setOnClickListener {listenerForButtons(0, "2  ")}
        f3.setOnClickListener {listenerForButtons(0, "3  ")}
        f4.setOnClickListener {listenerForButtons(0, "4  ")}
        f5.setOnClickListener {listenerForButtons(0, "5  ")}
        f6.setOnClickListener {listenerForButtons(0, "6  ")}
        f7.setOnClickListener {listenerForButtons(0, "7  ")}
        f8.setOnClickListener {listenerForButtons(0, "8  ")}
        f9.setOnClickListener {listenerForButtons(0, "9  ")}
        f10.setOnClickListener {listenerForButtons(0, "10 ")}
        f11.setOnClickListener {listenerForButtons(0, "11 ")}
        f12.setOnClickListener {listenerForButtons(0, "12 ")}
        f13.setOnClickListener {listenerForButtons(0, "13 ")}
        f14.setOnClickListener {listenerForButtons(0, "14 ")}
        f15.setOnClickListener {listenerForButtons(0, "15 ")}
        f16.setOnClickListener {listenerForButtons(0, "16 ")}
        f17.setOnClickListener {listenerForButtons(0, "17 ")}
        f18.setOnClickListener {listenerForButtons(0, "18 ")}
        f19.setOnClickListener {listenerForButtons(0, "19 ")}
        f20.setOnClickListener {listenerForButtons(0, "20 ")}
        f21.setOnClickListener {listenerForButtons(0, "21 ")}
        f22.setOnClickListener {listenerForButtons(0, "22 ")}
        f23.setOnClickListener {listenerForButtons(0, "23 ")}
        f24.setOnClickListener {listenerForButtons(0, "24 ")}


        s0.setOnClickListener {listenerForButtons(1, "0  ")}
        s1.setOnClickListener {listenerForButtons(1, "1  ")}
        s2.setOnClickListener {listenerForButtons(1, "2  ")}
        s3.setOnClickListener {listenerForButtons(1, "3  ")}
        s4.setOnClickListener {listenerForButtons(1, "4  ")}
        s5.setOnClickListener {listenerForButtons(1, "5  ")}
        s6.setOnClickListener {listenerForButtons(1, "6  ")}
        s7.setOnClickListener {listenerForButtons(1, "7  ")}
        s8.setOnClickListener {listenerForButtons(1, "8  ")}
        s9.setOnClickListener {listenerForButtons(1, "9  ")}
        s10.setOnClickListener {listenerForButtons(1, "10 ")}
        s11.setOnClickListener {listenerForButtons(1, "11 ")}
        s12.setOnClickListener {listenerForButtons(1, "12 ")}
        s13.setOnClickListener {listenerForButtons(1, "13 ")}
        s14.setOnClickListener {listenerForButtons(1, "14 ")}
        s15.setOnClickListener {listenerForButtons(1, "15 ")}
        s16.setOnClickListener {listenerForButtons(1, "16 ")}
        s17.setOnClickListener {listenerForButtons(1, "17 ")}
        s18.setOnClickListener {listenerForButtons(1, "18 ")}
        s19.setOnClickListener {listenerForButtons(1, "19 ")}
        s20.setOnClickListener {listenerForButtons(1, "20 ")}
        s21.setOnClickListener {listenerForButtons(1, "21 ")}
        s22.setOnClickListener {listenerForButtons(1, "22 ")}
        s23.setOnClickListener {listenerForButtons(1, "23 ")}
        s24.setOnClickListener {listenerForButtons(1, "24 ")}

        t0.setOnClickListener {listenerForButtons(2, "0  ")}
        t1.setOnClickListener {listenerForButtons(2, "1  ")}
        t2.setOnClickListener {listenerForButtons(2, "2  ")}
        t3.setOnClickListener {listenerForButtons(2, "3  ")}
        t4.setOnClickListener {listenerForButtons(2, "4  ")}
        t5.setOnClickListener {listenerForButtons(2, "5  ")}
        t6.setOnClickListener {listenerForButtons(2, "6  ")}
        t7.setOnClickListener {listenerForButtons(2, "7  ")}
        t8.setOnClickListener {listenerForButtons(2, "8  ")}
        t9.setOnClickListener {listenerForButtons(2, "9  ")}
        t10.setOnClickListener {listenerForButtons(2, "10 ")}
        t11.setOnClickListener {listenerForButtons(2, "11 ")}
        t12.setOnClickListener {listenerForButtons(2, "12 ")}
        t13.setOnClickListener {listenerForButtons(2, "13 ")}
        t14.setOnClickListener {listenerForButtons(2, "14 ")}
        t15.setOnClickListener {listenerForButtons(2, "15 ")}
        t16.setOnClickListener {listenerForButtons(2, "16 ")}
        t17.setOnClickListener {listenerForButtons(2, "17 ")}
        t18.setOnClickListener {listenerForButtons(2, "18 ")}
        t19.setOnClickListener {listenerForButtons(2, "19 ")}
        t20.setOnClickListener {listenerForButtons(2, "20 ")}
        t21.setOnClickListener {listenerForButtons(2, "21 ")}
        t22.setOnClickListener {listenerForButtons(2, "22 ")}
        t23.setOnClickListener {listenerForButtons(2, "23 ")}
        t24.setOnClickListener {listenerForButtons(2, "24 ")}

        fo0.setOnClickListener {listenerForButtons(3, "0  ")}
        fo1.setOnClickListener {listenerForButtons(3, "1  ")}
        fo2.setOnClickListener {listenerForButtons(3, "2  ")}
        fo3.setOnClickListener {listenerForButtons(3, "3  ")}
        fo4.setOnClickListener {listenerForButtons(3, "4  ")}
        fo5.setOnClickListener {listenerForButtons(3, "5  ")}
        fo6.setOnClickListener {listenerForButtons(3, "6  ")}
        fo7.setOnClickListener {listenerForButtons(3, "7  ")}
        fo8.setOnClickListener {listenerForButtons(3, "8  ")}
        fo9.setOnClickListener {listenerForButtons(3, "9  ")}
        fo10.setOnClickListener {listenerForButtons(3, "10 ")}
        fo11.setOnClickListener {listenerForButtons(3, "11 ")}
        fo12.setOnClickListener {listenerForButtons(3, "12 ")}
        fo13.setOnClickListener {listenerForButtons(3, "13 ")}
        fo14.setOnClickListener {listenerForButtons(3, "14 ")}
        fo15.setOnClickListener {listenerForButtons(3, "15 ")}
        fo16.setOnClickListener {listenerForButtons(3, "16 ")}
        fo17.setOnClickListener {listenerForButtons(3, "17 ")}
        fo18.setOnClickListener {listenerForButtons(3, "18 ")}
        fo19.setOnClickListener {listenerForButtons(3, "19 ")}
        fo20.setOnClickListener {listenerForButtons(3, "20 ")}
        fo21.setOnClickListener {listenerForButtons(3, "21 ")}
        fo22.setOnClickListener {listenerForButtons(3, "22 ")}
        fo23.setOnClickListener {listenerForButtons(3, "23 ")}
        fo24.setOnClickListener {listenerForButtons(3, "24 ")}

        fi0.setOnClickListener {listenerForButtons(4, "0  ")}
        fi1.setOnClickListener {listenerForButtons(4, "1  ")}
        fi2.setOnClickListener {listenerForButtons(4, "2  ")}
        fi3.setOnClickListener {listenerForButtons(4, "3  ")}
        fi4.setOnClickListener {listenerForButtons(4, "4  ")}
        fi5.setOnClickListener {listenerForButtons(4, "5  ")}
        fi6.setOnClickListener {listenerForButtons(4, "6  ")}
        fi7.setOnClickListener {listenerForButtons(4, "7  ")}
        fi8.setOnClickListener {listenerForButtons(4, "8  ")}
        fi9.setOnClickListener {listenerForButtons(4, "9  ")}
        fi10.setOnClickListener {listenerForButtons(4, "10 ")}
        fi11.setOnClickListener {listenerForButtons(4, "11 ")}
        fi12.setOnClickListener {listenerForButtons(4, "12 ")}
        fi13.setOnClickListener {listenerForButtons(4, "13 ")}
        fi14.setOnClickListener {listenerForButtons(4, "14 ")}
        fi15.setOnClickListener {listenerForButtons(4, "15 ")}
        fi16.setOnClickListener {listenerForButtons(4, "16 ")}
        fi17.setOnClickListener {listenerForButtons(4, "17 ")}
        fi18.setOnClickListener {listenerForButtons(4, "18 ")}
        fi19.setOnClickListener {listenerForButtons(4, "19 ")}
        fi20.setOnClickListener {listenerForButtons(4, "20 ")}
        fi21.setOnClickListener {listenerForButtons(4, "21 ")}
        fi22.setOnClickListener {listenerForButtons(4, "22 ")}
        fi23.setOnClickListener {listenerForButtons(4, "23 ")}
        fi24.setOnClickListener {listenerForButtons(4, "24 ")}

        si0.setOnClickListener {listenerForButtons(5, "0  ")}
        si1.setOnClickListener {listenerForButtons(5, "1  ")}
        si2.setOnClickListener {listenerForButtons(5, "2  ")}
        si3.setOnClickListener {listenerForButtons(5, "3  ")}
        si4.setOnClickListener {listenerForButtons(5, "4  ")}
        si5.setOnClickListener {listenerForButtons(5, "5  ")}
        si6.setOnClickListener {listenerForButtons(5, "6  ")}
        si7.setOnClickListener {listenerForButtons(5, "7  ")}
        si8.setOnClickListener {listenerForButtons(5, "8  ")}
        si9.setOnClickListener {listenerForButtons(5, "9  ")}
        si10.setOnClickListener {listenerForButtons(5, "10 ")}
        si11.setOnClickListener {listenerForButtons(5, "11 ")}
        si12.setOnClickListener {listenerForButtons(5, "12 ")}
        si13.setOnClickListener {listenerForButtons(5, "13 ")}
        si14.setOnClickListener {listenerForButtons(5, "14 ")}
        si15.setOnClickListener {listenerForButtons(5, "15 ")}
        si16.setOnClickListener {listenerForButtons(5, "16 ")}
        si17.setOnClickListener {listenerForButtons(5, "17 ")}
        si18.setOnClickListener {listenerForButtons(5, "18 ")}
        si19.setOnClickListener {listenerForButtons(5, "19 ")}
        si20.setOnClickListener {listenerForButtons(5, "20 ")}
        si21.setOnClickListener {listenerForButtons(5, "21 ")}
        si22.setOnClickListener {listenerForButtons(5, "22 ")}
        si23.setOnClickListener {listenerForButtons(5, "23 ")}
        si24.setOnClickListener {listenerForButtons(5, "24 ")}


    }

    fun listenerForButtons(stringNum: Int, symbol: String){
        val startIndex = tabIndex + stringNum * (tabsList.size / 6)
        if (tabsList[startIndex] == symbol) tabsList[startIndex] = "-  " else tabsList[startIndex] = symbol
        applyTabs()
    }

    fun applyTabs(){
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
    }

    fun leftButtonListener(){
        if (tabPos != 0){
            tabPos-=3
            tabIndex -= 1
            applyTabs()
        }
    }

    fun rightButtonListener(){
        if (tabPos != maxTabPos){
            tabPos+=3
            tabIndex += 1
            applyTabs()
        }
    }

    fun chord_button_1OnClick(){
        if(chord_button_1.isSelected){
            chord_button_1.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_1.setSelected(true)
            chord_button_2.setSelected(false)
            chord_button_3.setSelected(false)
            chord_button_4.setSelected(false)
            chord_button_5.setSelected(false)
            chord_button_6.setSelected(false)
            chord_button_7.setSelected(false)
            applyChordScale(tonality.chords[0])
            add_tabmark.setEnabled(true)
        }
    }

    fun chord_button_2OnClick(){
        if(chord_button_2.isSelected){
            chord_button_2.isSelected = false
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_2.setSelected(true)
            chord_button_1.setSelected(false)
            chord_button_3.setSelected(false)
            chord_button_4.setSelected(false)
            chord_button_5.setSelected(false)
            chord_button_6.setSelected(false)
            chord_button_7.setSelected(false)
            applyChordScale(tonality.chords[1])
            add_tabmark.setEnabled(true)
        }
    }
    fun chord_button_3OnClick(){
        if(chord_button_3.isSelected){
            chord_button_3.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_3.setSelected(true)
            chord_button_2.setSelected(false)
            chord_button_1.setSelected(false)
            chord_button_4.setSelected(false)
            chord_button_5.setSelected(false)
            chord_button_6.setSelected(false)
            chord_button_7.setSelected(false)
            applyChordScale(tonality.chords[2])
            add_tabmark.setEnabled(true)
        }
    }
    fun chord_button_4OnClick(){
        if(chord_button_4.isSelected){
            chord_button_4.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_4.setSelected(true)
            chord_button_2.setSelected(false)
            chord_button_3.setSelected(false)
            chord_button_1.setSelected(false)
            chord_button_5.setSelected(false)
            chord_button_6.setSelected(false)
            chord_button_7.setSelected(false)
            applyChordScale(tonality.chords[3])
            add_tabmark.setEnabled(true)
        }
    }
    fun chord_button_5OnClick(){
        if(chord_button_5.isSelected){
            chord_button_5.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_5.setSelected(true)
            chord_button_2.setSelected(false)
            chord_button_3.setSelected(false)
            chord_button_4.setSelected(false)
            chord_button_1.setSelected(false)
            chord_button_6.setSelected(false)
            chord_button_7.setSelected(false)
            applyChordScale(tonality.chords[4])
            add_tabmark.setEnabled(true)
        }
    }
    fun chord_button_6OnClick(){
        if(chord_button_6.isSelected){
            chord_button_6.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_6.setSelected(true)
            chord_button_2.setSelected(false)
            chord_button_3.setSelected(false)
            chord_button_4.setSelected(false)
            chord_button_5.setSelected(false)
            chord_button_1.setSelected(false)
            chord_button_7.setSelected(false)
            applyChordScale(tonality.chords[5])
            add_tabmark.setEnabled(true)
        }
    }
    fun chord_button_7OnClick(){
        if(chord_button_7.isSelected){
            chord_button_7.setSelected(false)
            applyScale()
            add_tabmark.setEnabled(false)
        }else{
            chord_button_7.setSelected(true)
            chord_button_2.setSelected(false)
            chord_button_3.setSelected(false)
            chord_button_4.setSelected(false)
            chord_button_5.setSelected(false)
            chord_button_1.setSelected(false)
            chord_button_6.setSelected(false)
            applyChordScale(tonality.chords[6])
            add_tabmark.setEnabled(true)
        }
    }

    fun applyChordScale(chord: Chord) {
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


    fun applyChordApplication(chord: Chord) {
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

    fun addTabMarkOnClick(){
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

    fun delTabMarkOnClick(){
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

    fun autoSizeTextView(){
        val textPaint = TextPaint()
        textPaint.textSize = tabs.textSize

        val width = textPaint.measureText(tabs.getText().toString())

        val layoutParams: ViewGroup.LayoutParams = tabs.getLayoutParams()
        layoutParams.width = width.toInt()
        tabs.setLayoutParams(layoutParams)
    }


}