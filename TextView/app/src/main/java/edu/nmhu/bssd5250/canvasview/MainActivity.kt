package edu.nmhu.bssd5250.canvasview

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {

    private var drawOnGraphic:Boolean = false
    private var highLightText:Boolean = false

    //private var ll: LinearLayoutCompat? = null
    private val highLitTxt:Button? = null
    private val drawOnImage:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //setContentView(CanvasView(this))
        val drawView = CanvasView(this)
        val txtView = CanvasView(this)

        val clrScreen = Button(this).apply {
            text = context.getString(R.string.clear)
            setOnClickListener {
                if(drawOnGraphic) {
                    drawView.clearAll()
                }else if(highLightText) {
                    txtView.clearAll()
                }
            }
        }

        val edTxt = EditText(this).apply {
            hint = "Enter Hex Color without #"
        }

        val setStrokeColor = Button(this).apply {
            text = context.getString(R.string.set_stroke_color)
            setOnClickListener {
                val strokeColor = "#77" + edTxt.text
                Log.d("strokeColor: ", strokeColor)
            }
        }

        val sc = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
            addView(edTxt)
            addView(setStrokeColor)
        }

        val drawOnImage = Button(this).apply {
            text = context.getString(R.string.draw_on_image)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            setOnClickListener {
                drawOnGraphic = true
                val bgImg = ImageView(this@MainActivity).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
                    setImageResource(R.drawable.will)
                }
                val ch = LinearLayoutCompat(this@MainActivity).apply {
                    orientation = LinearLayoutCompat.HORIZONTAL
                    addView(highLitTxt)
                    addView(drawOnImage)
                }
                val drawLayout = FrameLayout(this@MainActivity).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT)
                    addView(bgImg)
                    addView(drawView)
                }
                val ll = LinearLayoutCompat(this@MainActivity).apply {
                    orientation = LinearLayoutCompat.VERTICAL
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    )
                    addView(clrScreen)
                    addView(ch)
                    addView(sc)
                    addView(drawLayout)
                }
                setContentView(ll)
                hideSystemUI()
            }
        }

        val highLitTxt = Button(this).apply {
            text = context.getString(R.string.highlight_text)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
            setOnClickListener {
                highLightText = true
                val txView = TextView(this@MainActivity).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT)
                    text = readAsset(context,"text_view.txt")
                }
                val ch = LinearLayoutCompat(this@MainActivity).apply {
                    orientation = LinearLayoutCompat.HORIZONTAL
                    addView(highLitTxt)
                    addView(drawOnImage)
                }
                val textLayout = FrameLayout(this@MainActivity).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT)
                    addView(txView)
                    addView(txtView)
                }
                val ll = LinearLayoutCompat(this@MainActivity).apply {
                    orientation = LinearLayoutCompat.VERTICAL
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    )
                    addView(clrScreen)
                    addView(ch)
                    addView(sc)
                    addView(textLayout)
                }
                setContentView(ll)
                hideSystemUI()
            }
        }

        val ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            val ch = LinearLayoutCompat(this@MainActivity).apply {
                orientation = LinearLayoutCompat.HORIZONTAL
                addView(highLitTxt)
                addView(drawOnImage)
            }
            addView(clrScreen)
            addView(ch)
            addView(sc)
            }
        setContentView(ll)
        hideSystemUI()
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    // taken from: https://stackoverflow.com/questions/47201829/read-a-text-assettext-file-from-assets-folder-as-a-string-in-kotlin-android/62878278
    private fun readAsset(context: Context, fileName: String): String =
        context
            .assets
            .open(fileName)
            .bufferedReader()
            .use(BufferedReader::readText)
}