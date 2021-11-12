package edu.nmhu.bssd5250.sb_hwk121

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import java.io.BufferedReader
import java.io.InputStream

private const val AUDIO_RES = "audio_file" //key for bundle

class CanvasFragment : Fragment() {

    private val DRAW_TAG:String = "edu.nmhu.bssd5250.mediaplayer.step_tag"  // tag to look up frag
    private val TEXT_TAG:String = "edu.nmhu.bssd5250.mediaplayer.eagle_tag"  // tag to look up frag

    private var drawOnGraphic:Boolean = false
    private var highLightText:Boolean = false
    private val drawView = CanvasView(this)
    private val txtView = CanvasView(this)

    private lateinit var view:CanvasView

    private var mediaPlayer: MediaPlayer? = null //will hold mediaplayer
    private var audioRes:Int? = null //resource to play

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(this).get(CanvasViewModel::class.java)
        arguments?.let {
            audioRes = it.getInt(AUDIO_RES) //load argument from companion
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val v:View = inflater.inflate(R.layout.fragment_canvas, container, false)

        val clrScreen = Button(this).apply {
            text = context?.getString(R.string.clear)
            setOnClickListener {
                if(drawOnGraphic) {
                    drawView.clearAll()
                }else if(highLightText) {
                    txtView.clearAll()
                }
            }
        }

        val highLitTxt: Button = Button(this).apply {
            text = context.getString(R.string.highlight_text)
            setOnClickListener {
                highLightText = true
            }
        }

        val drawOnImage = Button(this).apply {
            text = context.getString(R.string.draw_on_image)
            setOnClickListener {
                drawOnGraphic = true
            }
        }

        val ch = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.HORIZONTAL
            addView(highLitTxt)
            addView(drawOnImage)
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

        val bgImg = ImageView(this).apply {
            setImageResource(R.drawable.will)
        }

        val txView = TextView(this).apply {
            text = readAsset(context, "text_view.txt")
        }


        return v
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(view.getCurrentTime() > 0){
            playMedia(view.getCurrentTime())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        view.setCurrentTime((this.mediaPlayer?.currentPosition!!))
        stopMedia()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun playMedia(time:Int = 0) {
        mediaPlayer?.seekTo(time)
        mediaPlayer?.start()
    }

    private fun stopMedia(time:Int = 0) {
        mediaPlayer?.stop()
        mediaPlayer?.prepare()
    }

    // taken from: https://stackoverflow.com/questions/47201829/read-a-text-assettext-file-from-assets-folder-as-a-string-in-kotlin-android/62878278
    private fun readAsset(context: Context, fileName: String): String =
        context
            .assets
            .open(fileName)
            .bufferedReader()
            .use(BufferedReader::readText)

    companion object {

        @JvmStatic
        fun newInstance(audio: InputStream) =
            CanvasFragment().apply {
                arguments = Bundle().apply {
                    putInt(AUDIO_RES, audio)
                }
            }
    }
}