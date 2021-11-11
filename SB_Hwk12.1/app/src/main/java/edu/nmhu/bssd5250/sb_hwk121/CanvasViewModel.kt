package edu.nmhu.bssd5250.sb_hwk121

import androidx.lifecycle.ViewModel

class CanvasViewModel : ViewModel() {
    //could store the audio file name in here too
    // if it were chosen by the user, but it is
    // sent via a bundle. which persists on rotate
    private var currTime:Int =0

    fun setCurrentTime(time: Int) {
        currTime = time
    }

    fun getCurrentTime():Int{
        return currTime
    }
}