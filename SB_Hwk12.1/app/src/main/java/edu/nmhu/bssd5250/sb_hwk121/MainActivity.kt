package edu.nmhu.bssd5250.sb_hwk121

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.commit
import android.content.res.AssetManager




class MainActivity : AppCompatActivity() {

    private val DRAW_TAG:String = "edu.nmhu.bssd5250.mediaplayer.draw_tag"  // tag to look up frag
    private val TEXT_TAG:String = "edu.nmhu.bssd5250.mediaplayer.eagle_tag"  // tag to look up frag
    private val LLID:Int = 123 // constant id for linear layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
            id = LLID
        }
        setContentView(ll)

        if (savedInstanceState == null) {
            // create fragment for collection edit buttons
            supportFragmentManager.commit {
                replace(ll.id, CanvasFragment.newInstance(R.drawable.will), DRAW_TAG)
            }
        }else{
            val drawFragment = supportFragmentManager.findFragmentByTag(DRAW_TAG) as CanvasFragment
            supportFragmentManager.commit {
                replace(ll.id, drawFragment, DRAW_TAG)
            }
        }

        if (savedInstanceState == null) {
            // create fragment for collection edit buttons
            supportFragmentManager.commit {
                //val am: AssetManager = applicationContext.getAssets()
                replace(ll.id, CanvasFragment.newInstance(assets.open("text_view.txt")), TEXT_TAG)
            }
        }else{
            val textFragment = supportFragmentManager.findFragmentByTag(TEXT_TAG) as CanvasFragment
            supportFragmentManager.commit {
                replace(ll.id, textFragment, TEXT_TAG)
            }
        }

        supportFragmentManager.commit{
            add(ll.id, CanvasFragment.newInstance(R.drawable.will))
            add(ll.id, CanvasFragment.newInstance(R.raw.eagle))
            add(ll.id, CanvasFragment.newInstance(R.raw.launch))
        }*/
    }
}