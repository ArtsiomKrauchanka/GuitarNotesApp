package com.hfad.guitarnotes001

import java.io.Serializable

class Tonality(val name: String, tone: Int, pos: Int ) : Serializable{

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
    private val symbSeq=listOf("C","D","E","F","G","A","B")
    val symbPos=listOf(8,10,0,1,3,5,7)
    var chords: MutableList<Chord> = mutableListOf()
    var symbInd : Int

    lateinit var seq: List<Int>


    init{

        when(tone){
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

        when(pos){
            0 -> symbInd=2
            1,2 -> symbInd=3
            3,4 -> symbInd=4
            5,6 -> symbInd=5
            7 -> symbInd=6
            8,9 -> symbInd=0
            10,11 ->symbInd=1
            else -> symbInd=0
        }

        var position: Int
        var symbolIndex: Int
        when(tone){
           1 -> {
               position = pos
               symbolIndex = symbInd
               chords.add(Chord(symbSeq[symbInd]+addSymb(position,symbolIndex),1,false, pos))

               position = if (pos+seq[0]>11) pos+seq[0]-12 else pos+seq[0]
               symbolIndex = if (symbInd+1>6) symbInd-6 else symbInd+1
               chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex)+"m", 0,false, position))

               position = if (position+seq[1]>11) position+seq[1]-12 else position+seq[1]
               symbolIndex = if (symbInd+2>6) symbInd-5 else symbInd+2
               chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex) + "m", 0,false, position))

               position = if (position+seq[2]>11) position+seq[2]-12 else position+seq[2]
               symbolIndex = if (symbInd+3>6) symbInd-4 else symbInd+3
               chords.add(Chord(symbSeq[symbolIndex] +addSymb(position,symbolIndex), 1,false, position))

               position = if (position+seq[3]>11) position+seq[3]-12 else position+seq[3]
               symbolIndex = if (symbInd+4>6) symbInd-3 else symbInd+4
               chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex), 1,false,position))

               position = if (position+seq[4]>11) position+seq[4]-12 else position+seq[4]
               symbolIndex = if (symbInd+5>6) symbInd-2 else symbInd+5
               chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex)+"m", 0,false,position))

               position = if (position+seq[5]>11) position+seq[5]-12 else position+seq[5]
               symbolIndex = if (symbInd+6>6) symbInd-1 else symbInd+6
               chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex)+"dim", 0,true,position))
           }
            0->{
                position = pos
                symbolIndex = symbInd
                chords.add(Chord(symbSeq[symbInd]+addSymb(position,symbolIndex)+"m",0,false, pos))

                position = if (position+seq[0]>11) position+seq[0]-12 else position+seq[0]
                symbolIndex = if (symbInd+1>6) symbInd-6 else symbInd+1
                chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex)+"dim", 0,true, position))

                position = if (position+seq[1]>11) position+seq[1]-12 else position+seq[1]
                symbolIndex = if (symbInd+2>6) symbInd-5 else symbInd+2
                chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex), 1,false, position))

                position = if (position+seq[2]>11) position+seq[2]-12 else position+seq[2]
                symbolIndex = if (symbInd+3>6) symbInd-4 else symbInd+3
                chords.add(Chord(symbSeq[symbolIndex] +addSymb(position,symbolIndex)+"m", 0,false, position))

                position = if (position+seq[3]>11) position+seq[3]-12 else position+seq[3]
                symbolIndex = if (symbInd+4>6) symbInd-3 else symbInd+4
                chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex)+"m", 0,false,position))

                position = if (position+seq[4]>11) position+seq[4]-12 else position+seq[4]
                symbolIndex = if (symbInd+5>6) symbInd-2 else symbInd+5
                chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex), 1,false,position))

                position = if (position+seq[5]>11) position+seq[5]-12 else position+seq[5]
                symbolIndex = if (symbInd+6>6) symbInd-1 else symbInd+6
                chords.add(Chord(symbSeq[symbolIndex]+addSymb(position,symbolIndex), 1,false,position))
            }
        }
    }



        fun addSymb(position: Int, symbolIndex: Int):String{
            if (symbPos[symbolIndex]>position){
                return "b"
            }
            else if(symbPos[symbolIndex]<position){
                return "#"
            }else{
                return ""
            }
        }


    }

