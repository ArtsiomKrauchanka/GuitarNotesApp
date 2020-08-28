package com.hfad.guitarnotes001

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_frame.*



class MainFrame : AppCompatActivity() {

    lateinit var tonality: Tonality
    var tabPos= 0
    var tabsList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_frame)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)


        tonality = (intent.extras?.getSerializable("EXTRA_TONALITY") ?: Tonality("C", 1, 8))  as Tonality

        applyScale()

        StartFillTabs()

        setListenersForScaleButtons()
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


    fun select() {
        val int = Intent(this, MainFrame::class.java)
        startActivity(int)
    }

    fun applyScale() {
        for (string in listOf("f", "s", "t", "fo", "fi", "si")) {
            for (pos in 0..24) {
                if (tonality.first.contains(pos)) {
                    val buttonID = "f" + pos.toString()
                    val resID = resources.getIdentifier(buttonID, "id", packageName)
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
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
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
                } else {
                    val buttonID = "s" + pos.toString()
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
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
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
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
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
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
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
                    findViewById<Button>(resID).setBackground(
                        resources.getDrawable(
                            R.drawable.scale_button,
                            null
                        )
                    )
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
    }

    fun StartFillTabs(){//potom wstawit sohranenija
        for(x in 0..17){
            tabsList.add("—  ")
        }
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
        tabs.text=tabsString
    }

    override fun onBackPressed() {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
    }

    fun setListenersForScaleButtons(){
        f0.setOnClickListener {listenerForButtons(0,"0  ")}
        f1.setOnClickListener {listenerForButtons(0,"1  ")}
        f2.setOnClickListener {listenerForButtons(0,"2  ")}
        f3.setOnClickListener {listenerForButtons(0,"3  ")}
        f4.setOnClickListener {listenerForButtons(0,"4  ")}
        f6.setOnClickListener {listenerForButtons(0,"6  ")}
        f7.setOnClickListener {listenerForButtons(0,"7  ")}
        f8.setOnClickListener {listenerForButtons(0,"8  ")}
        f9.setOnClickListener {listenerForButtons(0,"9  ")}
        f10.setOnClickListener {listenerForButtons(0,"10")}
        f11.setOnClickListener {listenerForButtons(0,"11")}
        f12.setOnClickListener {listenerForButtons(0,"12")}
        f13.setOnClickListener {listenerForButtons(0,"13")}
        f14.setOnClickListener {listenerForButtons(0,"14")}
        f15.setOnClickListener {listenerForButtons(0,"15")}
        f16.setOnClickListener {listenerForButtons(0,"16")}
        f17.setOnClickListener {listenerForButtons(0,"17")}
        f18.setOnClickListener {listenerForButtons(0,"18")}
        f19.setOnClickListener {listenerForButtons(0,"19")}
        f20.setOnClickListener {listenerForButtons(0,"20")}
        f21.setOnClickListener {listenerForButtons(0,"21")}
        f22.setOnClickListener {listenerForButtons(0,"22")}
        f23.setOnClickListener {listenerForButtons(0,"23")}
        f24.setOnClickListener {listenerForButtons(0,"24")}


        s0.setOnClickListener {listenerForButtons(1,"0  ")}
        s1.setOnClickListener {listenerForButtons(1,"1  ")}
        s2.setOnClickListener {listenerForButtons(1,"2  ")}
        s3.setOnClickListener {listenerForButtons(1,"3  ")}
        s4.setOnClickListener {listenerForButtons(1,"4  ")}
        s6.setOnClickListener {listenerForButtons(1,"6  ")}
        s7.setOnClickListener {listenerForButtons(1,"7  ")}
        s8.setOnClickListener {listenerForButtons(1,"8  ")}
        s9.setOnClickListener {listenerForButtons(1,"9  ")}
        s10.setOnClickListener {listenerForButtons(1,"10")}
        s11.setOnClickListener {listenerForButtons(1,"11")}
        s12.setOnClickListener {listenerForButtons(1,"12")}
        s13.setOnClickListener {listenerForButtons(1,"13")}
        s14.setOnClickListener {listenerForButtons(1,"14")}
        s15.setOnClickListener {listenerForButtons(1,"15")}
        s16.setOnClickListener {listenerForButtons(1,"16")}
        s17.setOnClickListener {listenerForButtons(1,"17")}
        s18.setOnClickListener {listenerForButtons(1,"18")}
        s19.setOnClickListener {listenerForButtons(1,"19")}
        s20.setOnClickListener {listenerForButtons(1,"20")}
        s21.setOnClickListener {listenerForButtons(1,"21")}
        s22.setOnClickListener {listenerForButtons(1,"22")}
        s23.setOnClickListener {listenerForButtons(1,"23")}
        s24.setOnClickListener {listenerForButtons(1,"24")}

    }

    fun listenerForButtons(stringNum : Int, symbol : String){
        val startIndex = tabPos + stringNum * (tabsList.size / 6)
        tabsList[startIndex] = symbol
        applyTabs()
    }


}