package minesweeper

import kotlin.random.Random

// Stage 1/5: Lay the groundwork
const val MINE = "X"
const val SPACE = "."

class Cell(){
    var value : Char = '.'
    var marked : Boolean = false
    var opened : Boolean = true
}

class MineSweeper(val width:Int, val height:Int) {
    var size = 0;
    var input = 10;

    fun startGame() {
        setUpGame()
        drawGameBoard()
    }

    fun setUpGame(){
        size = width * height
        var list = MutableList(size) { Cell() }
    }

    fun drawGameBoard() {
        var list = MutableList(size) { SPACE }

        var cntX = 0
        while (cntX < input) {
            var x = Random.nextInt(1, 81)
            if (list[x] == SPACE) {
                list[x] = MINE
                cntX ++
            }
        }

        var cnt: Int = 0
        for (i in list) {
            if (cnt++ == 8 ) {
                println(i)
                cnt = 0
            } else {
                print(i)
            }
        }
    }
}

fun main() {
    val obj = MineSweeper(9, 9)
    obj.startGame()
}