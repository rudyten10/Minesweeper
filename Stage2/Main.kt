package minesweeper
// Stage 2/5: Flexible mines

import kotlin.random.Random

const val MINE = 'X'
const val SPACE = '.'

class Cell(){
    var value : Char = '.'
    var marked : Boolean = false
    var opened : Boolean = true
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
        while (cntX < input) {
            var x = Random.nextInt(1, 9)
            var y = Random.nextInt(1, 9)
            if (list2[x][y].value != MINE) {
                list2[x][y].value = MINE
                list2[x][y].opened = true
                cntX++
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