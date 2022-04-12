package ca.thenightcrew.supervinebros.animation

abstract class SimpleKeyFrame(keyTimes: KeyTime, tweening: Boolean) :
    KeyFrame(keyTimes, tweening) {
    constructor(keyTimes: KeyTime, tweening: Boolean, loopTimes: Int): this(keyTimes, tweening){
        this.loopTimes = loopTimes
    }
    override fun onDefaultFrame(frameNumber: Int, totalFramesCalled: Int) {}
}
