package com.hfad.guitarnotes001

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_frame.*
import kotlinx.android.synthetic.main.activity_set_tune.*


class MainFrame : AppCompatActivity() {

    lateinit var tonality: Tonality

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_frame)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Tabs.setBackgroundColor(Color.parseColor("#ffffff"))
        TabsScroll.setBackgroundColor(Color.parseColor("#ffffff"))

        tonality = (intent.extras?.getSerializable("EXTRA_TONALITY") ?: Tonality("C", 1, 8))  as Tonality

        applyScale()

        //selectTonality.setOnClickListener { select() }
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

    override fun onBackPressed() {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
    }

}