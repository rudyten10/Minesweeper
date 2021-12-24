package minesweeper
// Stage 3/5: Look around you

import kotlin.random.Random

const val MINE = 'X'
const val SPACE = '.'

class Cell(){
    var value : Char = '.'
    var marked : Boolean = false
    var opened : Boolean = true
    var hint : Int = 0
}

class MineSweeper(val width:Int, val height:Int) {
    var size = 0;
    var list2 = MutableList(width) { MutableList(width) { Cell() } }
    var input = 0;

    fun startGame() {
        println("How many mines do you want on the field?")
        input = readLine()!!.toInt()

        setGameBoard()

        drawGameBoard()
    }

    fun setGameBoard() {
        size = width * height
    }

    fun setUpBoard(){
    }

    fun drawGameBoard() {
        var cntX = 0

        val numberGen1 = Random(12) //43
        val numberGen2 = Random(111) //81
        while (cntX < input) {
            var x = numberGen1.nextInt(1, 9)
            var y = numberGen2.nextInt(1, 9)
            if (list2[x][y].value != MINE) {
                list2[x][y].value = MINE
                list2[x][y].opened = true
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

        for (i in list2) {
            for (j in i){
                print(j.value)
            }
            println()
        }
    }
}

fun main() {
    val obj = MineSweeper(9, 9)

    obj.setUpBoard()

    obj.startGame()
}