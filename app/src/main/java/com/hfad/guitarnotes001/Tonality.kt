package com.hfad.guitarnotes001

import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class Tonality(val name: String, val scale: Int, val pos: Int ) : Serializable{

    var first: MutableList<Int> = mutableListOf()
    var second: MutableList<Int> = mutableListOf()
    var third: MutableList<Int> = mutableListOf()
    var fourth: MutableList<Int> = mutableListOf()
    var fifth: MutableList<Int> = mutableListOf()
    var sixth: MutableList<Int> = mutableListOf()
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

        first.add(24)
        sixth.add(24)
        for(n in seq){
            first.add(if (firstPos >= 24) (firstPos - 24) else firstPos)
            second.add(if (secondPos >= 24) (secondPos - 24) else secondPos)
            if (secondPos == 24) second.add(24)
            third.add(if (thirdPos >= 24) (thirdPos - 24) else thirdPos)
            if (secondPos == 24) second.add(24)
            fourth.add(if (fourthPos >= 24) (fourthPos - 24) else fourthPos)
            if (secondPos == 24) second.add(24)
            fifth.add(if (fifthPos >= 24) (fifthPos - 24) else fifthPos)
            if (secondPos == 24) second.add(24)
            sixth.add(if (sixthPos >= 24) (sixthPos - 24) else sixthPos)

            firstPos += n
            secondPos += n
            thirdPos += n
            fourthPos += n
            fifthPos += n
            sixthPos += n

        }

    }



}