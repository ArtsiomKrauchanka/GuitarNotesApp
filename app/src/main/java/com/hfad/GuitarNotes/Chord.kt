package com.hfad.GuitarNotes

import java.io.Serializable


class Chord(val name: String, tone: Int,var dim: Boolean, pos: Int, var drawRes: Int): Serializable {

    var sixString1: MutableList<Int> = mutableListOf() //from six string application 1
    var fifthString1: MutableList<Int> = mutableListOf()
    var fourthString1: MutableList<Int> = mutableListOf()
    var sixString2: MutableList<Int> = mutableListOf()
    var fifthString2: MutableList<Int> = mutableListOf()
    var fourthString2: MutableList<Int> = mutableListOf()

    var firstStep1Notes : MutableList<Int> // 1 step notes on first string
    var firstStep2Notes : MutableList<Int>
    var firstStep3Notes : MutableList<Int>
    var secondStep1Notes : MutableList<Int> // 1 step notes on second string
    var secondStep2Notes : MutableList<Int>
    var secondStep3Notes : MutableList<Int>
    var thirdStep1Notes : MutableList<Int>// 1 step notes on third string
    var thirdStep2Notes : MutableList<Int>
    var thirdStep3Notes : MutableList<Int>
    var fourthStep1Notes : MutableList<Int> // 1 step notes on fourth string
    var fourthStep2Notes : MutableList<Int>
    var fourthStep3Notes : MutableList<Int>
    var fifthStep1Notes : MutableList<Int>// 1 step notes on fifth string
    var fifthStep2Notes : MutableList<Int>
    var fifthStep3Notes : MutableList<Int>
    var sixthStep1Notes : MutableList<Int> // 1 step notes on sixth string
    var sixthStep2Notes : MutableList<Int>
    var sixthStep3Notes : MutableList<Int>


    var isSixString1: Boolean = true
    var isFifthString1: Boolean = true
    var isFourthString1: Boolean = true
    var isSixString2: Boolean = true
    var isFifthString2: Boolean = true
    var isFourthString2: Boolean = true

    lateinit var sixSeq: List<Int>
    lateinit var fifthSeq: List<Int>
    lateinit var fourthSeq: List<Int>
    var position:Int
    var step1 :Int
    var step2 :Int


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

            if (sixString1[0] == 24){
                sixString1[0] = 0
            }
            if (sixString2[0] == 24){
                sixString2[0] = 0
            }

            if (fifthString1[0] == 24){
                fifthString1[0] = 0
                fifthString1[2] = 0
            }
            if (fourthString2[0] == 24){
                fifthString2[0] = 0
                fifthString2[2] = 0
            }

            if (fourthString1[3] == 24){
                fourthString1[3] = 0
            }
            if (fourthString2[3] == 24){
                fourthString2[3] = 0
            }

            step1 = 3
            step2 = 3

        }else{
            when(tone){
                1 -> {
                    sixSeq=listOf<Int>(0,0,1,2,2,0)
                    fifthSeq=listOf<Int>(7,9,9,9,7)
                    fourthSeq=listOf<Int>(4,5,4,2)

                    step1 = 4
                    step2 = 3
                }
                0 -> {
                    sixSeq=listOf<Int>(0,0,0,2,2,0)
                    fifthSeq=listOf<Int>(7,8,9,9,7)
                    fourthSeq=listOf<Int>(3,5,4,2)

                    step1 = 3
                    step2 = 4
                }
                else -> {
                    step1 = 3
                    step2 = 4
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

            if (sixString1[0] == 24 || sixString1[5] == 24){
                sixString1[0] = 0
                sixString1[5] = 0
            }
            if (sixString2[0] == 24 || sixString2[5] == 24){
                sixString2[0] = 0
                sixString2[5] =0
            }
            if (fifthString1[0] == 24 || fifthString1[4] == 24){
                fifthString1[0] = 0
                fifthString1[4] = 0
            }
            if (fifthString2[0] == 24 || fifthString2[4] == 24){
                fifthString2[0] = 0
                fifthString2[4] = 0
            }
            if (fourthString1[3] == 24){
                fourthString1[3] = 0
            }
            if (fourthString2[3] == 24){
                fourthString2[3] = 0
            }

        }


        firstStep1Notes = mutableListOf(pos, if (pos+12 <= 24) (pos+12) else (pos), if (pos-12 >= 0) (pos - 12) else pos)
        position = if (pos + step1 > 24) (pos + step1 - 24) else (pos + step1)
        firstStep2Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step2  > 24) (position + step2  - 24) else (position + step2)
        firstStep3Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)

        position =  if (pos+5 > 24) (pos+5 - 24) else (pos+5)
        secondStep1Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step1 > 24) (position + step1 - 24) else (position + step1)
        secondStep2Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step2 > 24) (position + step2 - 24) else (position + step2)
        secondStep3Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)

        position = if (pos+9 > 24) (pos+9 - 24) else (pos+9)
        thirdStep1Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step1 > 24) (position + step1 - 24) else (position + step1)
        thirdStep2Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step2 > 24) (position + step2 - 24) else (position + step2)
        thirdStep3Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)

        position = if (pos+2 > 24) (pos+2 - 24) else (pos+2)
        fourthStep1Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step1 > 24) (position + step1 - 24) else (position + step1)
        fourthStep2Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step2 > 24) (position + step2 - 24) else (position + step2)
        fourthStep3Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)

        position = if (pos+7 > 24) (pos+7 - 24) else (pos+7)
        fifthStep1Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step1 > 24) (position + step1 - 24) else (position + step1)
        fifthStep2Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step2 > 24) (position + step2 - 24) else (position + step2)
        fifthStep3Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)

        position = pos
        sixthStep1Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step1 > 24) (position + step1 - 24) else (position + step1)
        sixthStep2Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)
        position = if (position + step2 > 24) (position + step2 - 24) else (position + step2)
        sixthStep3Notes = mutableListOf(position, if (position+12 <= 24) (position+12) else (position), if (position-12 >= 0) (position - 12) else position)

        val listOfStepNotesLists = listOf(firstStep1Notes, firstStep2Notes, firstStep3Notes, secondStep1Notes, secondStep2Notes, secondStep3Notes, thirdStep1Notes, thirdStep2Notes, thirdStep3Notes,
            fourthStep1Notes,fourthStep2Notes,fourthStep3Notes,fifthStep1Notes,fifthStep2Notes,fifthStep3Notes,sixthStep1Notes,sixthStep2Notes,sixthStep3Notes)
        for (list in listOfStepNotesLists){
            if (list.contains(24)) list.add(0)
            if (list.contains(0)) list.add(24)
        }

    }



}