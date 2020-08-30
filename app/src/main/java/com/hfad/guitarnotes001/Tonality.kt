package com.hfad.guitarnotes001

import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class Tonality(val name: String, val scale: Int, val pos: Int ) : Serializable{

    var first: MutableList<Int> = mutableListOf()
    var firstTonalityNotes : List<Int>
    var second: MutableList<Int> = mutableListOf()
    var secondTonalityNotes : List<Int>
    var third: MutableList<Int> = mutableListOf()
    var thirdTonalityNotes : List<Int>
    var fourth: MutableList<Int> = mutableListOf()
    var fourthTonalityNotes : List<Int>
    var fifth: MutableList<Int> = mutableListOf()
    var fifthTonalityNotes : List<Int>
    var sixth: MutableList<Int> = mutableListOf()
    var sixthTonalityNotes : List<Int>

    lateinit var seq: List<Int>


    init{

        when(scale){
            0 -> seq=listOf<Int>(2,1,2,2,1,2,2,2,1,2,2,1,2,2)
            1 -> seq=listOf<Int>(2,2,1,2,2,2,1,2,2,1,2,2,2,1)
            else -> listOf<Int>()
        }
        var firstPos = pos
        var secondPos = pos + 5
        var thirdPos = pos + 9
        var fourthPos = pos + 2
        var fifthPos = pos + 7
        var sixthPos = pos

        firstTonalityNotes = listOf(firstPos, if (firstPos+12 <= 24) (firstPos+12) else (firstPos), if (firstPos-12 >= 0) (firstPos - 12) else firstPos)
        secondTonalityNotes = listOf(secondPos, if (secondPos+12 <= 24) (secondPos+12) else (secondPos), if (secondPos-12 >= 0) (secondPos - 12) else secondPos)
        thirdTonalityNotes = listOf(thirdPos, if (thirdPos+12 <= 24) (thirdPos+12) else (thirdPos), if (thirdPos-12 >= 0) (thirdPos - 12) else thirdPos)
        fourthTonalityNotes = listOf(fourthPos, if (fourthPos+12 <= 24) (fourthPos+12) else (fourthPos), if (fourthPos-12 >= 0) (fourthPos - 12) else fourthPos)
        fifthTonalityNotes = listOf(fifthPos, if (fifthPos+12 <= 24) (fifthPos+12) else (fifthPos), if (fifthPos-12 >= 0) (fifthPos - 12) else fifthPos)
        sixthTonalityNotes = listOf(sixthPos, if (sixthPos+12 <= 24) (sixthPos+12) else (sixthPos), if (sixthPos-12 >= 0) (sixthPos - 12) else sixthPos)

        for(n in seq){
            first.add(if (firstPos >= 24) (firstPos - 24) else firstPos)
            if (firstPos == 24) first.add(24)
            second.add(if (secondPos >= 24) (secondPos - 24) else secondPos)
            if (secondPos == 24) second.add(24)
            third.add(if (thirdPos >= 24) (thirdPos - 24) else thirdPos)
            if (thirdPos == 24) third.add(24)
            fourth.add(if (fourthPos >= 24) (fourthPos - 24) else fourthPos)
            if (fourthPos == 24) fourth.add(24)
            fifth.add(if (fifthPos >= 24) (fifthPos - 24) else fifthPos)
            if (fifthPos == 24) fifth.add(24)
            sixth.add(if (sixthPos >= 24) (sixthPos - 24) else sixthPos)
            if (sixthPos == 24) sixth.add(24)

            firstPos += n
            secondPos += n
            thirdPos += n
            fourthPos += n
            fifthPos += n
            sixthPos += n

        }

        first.add(if (firstPos >= 24) (firstPos - 24) else firstPos)
        if (firstPos == 24) first.add(24)
        second.add(if (secondPos >= 24) (secondPos - 24) else secondPos)
        if (secondPos == 24) second.add(24)
        third.add(if (thirdPos >= 24) (thirdPos - 24) else thirdPos)
        if (thirdPos == 24) third.add(24)
        fourth.add(if (fourthPos >= 24) (fourthPos - 24) else fourthPos)
        if (fourthPos == 24) fourth.add(24)
        fifth.add(if (fifthPos >= 24) (fifthPos - 24) else fifthPos)
        if (fifthPos == 24) fifth.add(24)
        sixth.add(if (sixthPos >= 24) (sixthPos - 24) else sixthPos)
        if (sixthPos == 24) sixth.add(24)

    }



}