package com.hfad.guitarnotes001

import java.io.Serializable


class Chord(val name: String, tone: Int, dim: Boolean, pos: Int): Serializable {

    var sixString1: MutableList<Int> = mutableListOf()
    var fifthString1: MutableList<Int> = mutableListOf()
    var fourthString1: MutableList<Int> = mutableListOf()
    var sixString2: MutableList<Int> = mutableListOf()
    var fifthString2: MutableList<Int> = mutableListOf()
    var fourthString2: MutableList<Int> = mutableListOf()

    var isSixString1: Boolean = true
    var isFifthString1: Boolean = true
    var isFourthString1: Boolean = true
    var isSixString2: Boolean = true
    var isFifthString2: Boolean = true
    var isFourthString2: Boolean = true

    lateinit var sixSeq: List<Int>
    lateinit var fifthSeq: List<Int>
    lateinit var fourthSeq: List<Int>


    init{
        if(dim){
            sixSeq=listOf<Int>(6,8,9,8,7)
            fifthSeq=listOf<Int>(0,-1,0,2)
            fourthSeq=listOf<Int>(3,5,3,2)

            for(i in sixSeq){
                sixString1.add(if (pos+i > 24) (pos+i - 24) else (pos+i))
                sixString2.add(if (pos+i+12 > 24) (pos+i - 12) else (pos+i+12))
            }
            for(i in fifthSeq){
                fifthString1.add(if (pos+i > 24) (pos+i - 24) else (pos+i))
                fifthString2.add(if (pos+i+12 > 24) (pos+i - 12) else (pos+i+12))
            }
            for(i in fourthSeq){
                fourthString1.add(if (pos+i > 24) (pos+i - 24) else (pos+i))
                fourthString2.add(if (pos+i+12 > 24) (pos+i - 12) else (pos+i+12))
            }


            if (sixString1[1] == 0 || sixString1[2] == 0) isSixString1 = false
            if (sixString2[1] == 0 || sixString2[2] == 0) isSixString2 = false

            if (fifthString1[0] == 0 || fifthString1[3] == 1) isFifthString1 = false
            if (fifthString2[0] == 0 || fifthString2[3] == 1) isFifthString2 = false

            if (fourthString1[0] == 0 || fourthString1[1] == 1) isFourthString1 = false
            if (fourthString2[0] == 0 || fourthString2[1] == 1) isFourthString2 = false


        }else{
            when(tone){
                1 -> {
                    sixSeq=listOf<Int>(0,0,1,2,2,0)
                    fifthSeq=listOf<Int>(7,8,9,9,7)
                    fourthSeq=listOf<Int>(4,5,4,2)
                }
                0 -> {
                    sixSeq=listOf<Int>(0,0,0,2,2,0)
                    fifthSeq=listOf<Int>(7,9,9,9,7)
                    fourthSeq=listOf<Int>(3,5,4,2)
                }
            }

            for(i in sixSeq){
                sixString1.add(if (pos+i > 24) (pos+i - 24) else (pos+i))
                sixString2.add(if (pos+i+12 > 24) (pos+i - 12) else (pos+i+12))
            }
            for(i in fifthSeq){
                fifthString1.add(if (pos+i > 24) (pos+i - 24) else (pos+i))
                fifthString2.add(if (pos+i+12 > 24) (pos+i - 12) else (pos+i+12))
            }
            for(i in fourthSeq){
                fourthString1.add(if (pos+i > 24) (pos+i - 24) else (pos+i))
                fourthString2.add(if (pos+i+12 > 24) (pos+i - 12) else (pos+i+12))
            }

            if (sixString1[4] == 0 || sixString1[4] == 1) isSixString1 = false
            if (sixString2[4] == 0 || sixString2[4] == 1) isSixString2 = false

            if (fifthString1[3] == 0 || fifthString1[3] == 1) isFifthString1 = false
            if (fifthString2[3] == 0 || fifthString2[3] == 1) isFifthString2 = false

            if (fourthString1[1] == 0 || fourthString1[1] == 1 || fourthString1[1] == 2) isFourthString1 = false
            if (fourthString2[1] == 0 || fourthString2[1] == 1 || fourthString2[1] == 2) isFourthString2 = false
        }



    }



}