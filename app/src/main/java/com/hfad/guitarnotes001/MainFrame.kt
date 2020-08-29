package com.hfad.guitarnotes001

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
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
        toStrongPos()

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
        for(x in 0..119){
            tabsList.add("-  ")
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
        f0.setOnClickListener {listenerForButtons(0, "0  ")}
        f1.setOnClickListener {listenerForButtons(0, "1  ")}
        f2.setOnClickListener {listenerForButtons(0, "2  ")}
        f3.setOnClickListener {listenerForButtons(0, "3  ")}
        f4.setOnClickListener {listenerForButtons(0, "4  ")}
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
        val startIndex = tabPos + stringNum * (tabsList.size / 6)
        tabsList[startIndex] = symbol
        applyTabs()
        toStrongPos()
    }

    fun toStrongPos(){
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
        var ind = tabPos
        for (i in 0..5){
            WordtoSpan.setSpan(
                ForegroundColorSpan(Color.GREEN),
                ind,
                ind+2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ind+=skipRange
        }
        tabs.text=WordtoSpan
    }


}