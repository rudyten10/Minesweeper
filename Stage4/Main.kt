package minesweeper
// Stage 4/5: Prepare for battle

import kotlin.random.Random

const val MINE = 'X'
const val SPACE = '.'

class Cell(){
    var value : Char = '.'
    var marked : Boolean = false
    var opened : Boolean = true
    var mine : Boolean = false
}

class MineSweeper(val width:Int, val height:Int) {
    var isOn = false
    var size = 0;
    var list2 = MutableList(width) { MutableList(width) { Cell() } }
    var totalMines = 0;


    var markCnt = 0
    var mineCnt = 0

    fun playGame() {


        while (true) {
            println("Set/delete mine marks (x and y coordinates):")
            var (xx, yy) = readLine()!!.split(" ")
            var y:Int = xx.toInt() - 1
            var x:Int = yy.toInt() - 1

            if (list2[x][y].mine) { //.value == minesweeper.MINE
                list2[x][y].opened = false
                list2[x][y].marked = true

                if (list2[x][y].value == 'X') {
                    list2[x][y].value = '*'
                    mineCnt++
                }
                else {
                    list2[x][y].value = 'X'
                    mineCnt--
                }
                break
            } else if (list2[x][y].value in ('1'..'8')){
                println("There is a number here!")
            } else if (list2[x][y].marked){
                list2[x][y].opened = true
                list2[x][y].marked = false
                list2[x][y].value = '.'
                markCnt--
                break
            } else if (list2[x][y].opened){
                list2[x][y].opened = false
                list2[x][y].marked = true
                list2[x][y].value = '*'
                markCnt++
                break
            }
        }

        drawGameBoard()

        if (markCnt == 0 && mineCnt == totalMines) {
            println("Congratulations! You found all the mines!")
            isOn = false
        }
    }

    fun setGameBoard() {
        size = width * height
    }

    fun setUpBoard(){
        size = width * height

        println("How many mines do you want on the field?")
        totalMines = readLine()!!.toInt()
    }


    fun markMine(x: Int, y: Int){
        list2[x][y].marked
    }


    fun initialDrawGameBoard() {
        var cntX = 0

        //val numberGen1 = Random(52) //43 52
        //val numberGen2 = Random(83) //81 83
        while (cntX < totalMines) {
            //var x = numberGen1.nextInt(1, 9)
            //var y = numberGen2.nextInt(1, 9)
            var x = Random.nextInt(1, 9)
            var y = Random.nextInt(1, 9)
            if (list2[x][y].value != MINE) {
                list2[x][y].value = MINE
                list2[x][y].opened = true
                list2[x][y].mine = true
                cntX++
            }
        }

        var cnt = 0
        for (x in list2.indices) {
            for (y in list2[x].indices) {

                var mineCnt = 0
                if (list2[x][y].value != MINE) {
                    if (y < 8 && list2[x][y + 1].value == MINE) mineCnt++      // RIGHT
                    if (y > 0 && list2[x][y - 1].value == MINE) mineCnt++      // LEFT
                    if (x > 0 && list2[x - 1][y].value == MINE) mineCnt++      // TOP
                    if (x < 8 && list2[x + 1][y].value == MINE) mineCnt++      // BOTTOMx
                    if (x > 0 && y < 8 && list2[x - 1][y + 1].value == MINE) mineCnt++      // TOP RIGTH
                    if (x > 0 && y > 0 && list2[x - 1][y - 1].value == MINE) mineCnt++      // TOP LEFT
                    if (x < 8 && y < 8 && list2[x + 1][y + 1].value == MINE) mineCnt++      // BOTTOM RIGTH
                    if (x < 8 && y > 0 && list2[x + 1][y - 1].value == MINE) mineCnt++      // BOTTOM LEFT
                    if (mineCnt > 0) list2[x][y].value = mineCnt.toString().first()
                }
            }
        }


        print("-|")
        for (i in 1..9){
            print(i)
        }
        println("|")

        print("-|")
        for (i in 1..9){
            print("-")
        }
        println("|")

        var cntz = 1
        for (i in list2) {
            print("${cntz++}|")

            for (j in i){
                if (j.value == MINE) print(SPACE) else print(j.value)
                //print(j.value)
            }
            println("|")
        }

        print("-|")
        for (i in 1..9){
            print("-")
        }
        println("|")

    }

    fun drawGameBoard() {

        print("-|")
        for (i in 1..9){
            print(i)
        }
        println("|")

        print("-|")
        for (i in 1..9){
            print("-")
        }
        println("|")

        var cntz = 1
        for (i in list2) {
            print("${cntz++}|")

            for (j in i){
                if (j.value == MINE) print(SPACE) else print(j.value)
                //print(j.value)
            }
            println("|")
        }
        print("-|")
        for (i in 1..9){
            print("-")
        }
        println("|")
    }
}

fun main() {
    val obj = MineSweeper(9, 9)

    obj.isOn = true

    obj.setUpBoard()

    obj.initialDrawGameBoard()

    while (obj.isOn){
        obj.playGame()
    }
}