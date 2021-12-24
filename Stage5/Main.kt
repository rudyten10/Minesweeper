package minesweeper
// Stage 5/5: Battle_

import kotlin.random.Random

const val MINE = 'X'
const val SPACE = '.'

class Cell(){
    var value : Char = '/'
    var marked : Boolean = false
    var opened : Boolean = false
    var mine : Boolean = false
}

class MineSweeper(var width:Int, var height:Int) {
    var isOn = false
    var list2 = MutableList(height) { MutableList(width) { Cell() } }
    var totalMines = 0;

    fun playGame() {
        var markCnt = 0
        var mineCnt = 0

        while (true) {
            println("Set/unset mine marks or claim a cell as free:")
            var (xx, yy, claim) = readLine()!!.split(" ")
            var y:Int = xx.toInt() - 1
            var x:Int = yy.toInt() - 1

            if (claim == "mine"){
                if (list2[x][y].marked) {
                    list2[x][y].marked = false
                    markCnt--
                } else {
                    list2[x][y].marked = true
                    markCnt++
                }
                break
            } else {
                if (list2[x][y].mine) { //.value == minesweeper.MINE
                    println("You stepped on a mine and failed!")
                    for (i in list2) {
                        for (j in i) {
                            j.opened = true
                        }
                   }
                    isOn = false
                    break
                } else if (list2[x][y].value in ('1'..'8')){
                    list2[x][y].opened = true
                    break
                } else if (!list2[x][y].opened){
                    list2[x][y].opened = true
                    list2[x][y].value = '/'
                    autoFreeAround(x,y)

                    analyzeFree()
                    break
                }
            }
        }

        drawGameBoard()

        if (markCnt == 0 && mineCnt == totalMines) {
            println("Congratulations! You found all the mines!")
            isOn = false
        }
    }

    fun setUpBoard(){
        this.isOn = true

        println("How many mines do you want on the field?")
        totalMines = readLine()!!.toInt()

        placeMines()
        placeHints()
        drawGameBoard()
    }

    fun placeMines(){
        var cnt = 0

        //val numberGen1 = Random(52) //43 52
        //val numberGen2 = Random(83) //81 83
        while (cnt < totalMines) {
            //var x = numberGen1.nextInt(1, 9)
            //var y = numberGen2.nextInt(1, 9)
            var x = Random.nextInt( width)
            var y = Random.nextInt(height)
            if (list2[x][y].value != MINE) {
                list2[x][y].value = MINE
                list2[x][y].mine = true
                cnt++
            }
        }
    }

    fun placeHints(){
        var cnt = 0
        for (x in list2.indices) {
            for (y in list2[x].indices) {

                var mineCnt = 0
                if (list2[x][y].value != MINE) {
                    if (y < height - 1 && list2[x][y + 1].value == MINE) mineCnt++      // RIGHT
                    if (y > 0 && list2[x][y - 1].value == MINE) mineCnt++      // LEFT
                    if (x > 0 && list2[x - 1][y].value == MINE) mineCnt++      // TOP
                    if (x < width - 1 && list2[x + 1][y].value == MINE) mineCnt++      // BOTTOMx
                    if (x > 0 && y < height - 1 && list2[x - 1][y + 1].value == MINE) mineCnt++      // TOP RIGTH
                    if (x > 0 && y > 0 && list2[x - 1][y - 1].value == MINE) mineCnt++      // TOP LEFT
                    if (x < width - 1 && y < height - 1 && list2[x + 1][y + 1].value == MINE) mineCnt++      // BOTTOM RIGTH
                    if (x < width - 1 && y > 0 && list2[x + 1][y - 1].value == MINE) mineCnt++      // BOTTOM LEFT
                    if (mineCnt > 0) list2[x][y].value = mineCnt.toString().first()
                }
            }
        }
    }

    fun drawGameBoard() {
        print("-|")
        for (i in 1..width){
            if (i > 9) {
                var a = i.toString().substring(1,2)
                print(a)
            } else {
                print(i)
            }
        }
        println("|")

        print("-|")
        for (i in 1..height){
            print("-")
        }
        println("|")

        var cnt = 1
        for (i in list2) {
            if (cnt > 9) {
                var a = cnt.toString().substring(1,2)
                print("${a}|")
            } else {
                print("${cnt}|")
            }
            cnt++

            for (j in i){
                if (j.opened) {
                    print(j.value)
                } else if (j.marked) {
                    print("*")
                } else {
                    print(SPACE)
                }
            }
            println("|")
        }
        print("-|")
        repeat(width) {
            print("-")
        }
        println("|")
    }

    fun analyzeFree(){
        for (x in list2.indices) {
            for (y in list2[x].indices) {
                autoFreeAround(x,y)
            }
        }
    }

    fun autoFreeAround(x: Int, y: Int){
        val poop = listOf<Char>('1','2','3','4','5', '6', '7', '8', '/' )

        if (y < height - 1 && list2[x][y + 1].value in poop) {      // RIGHT
            list2[x][y + 1].opened = true
        }
        if (y > 0 && list2[x][y - 1].value in poop) {      // LEFT
            list2[x][y - 1].opened = true
        }
        if (x > 0 && list2[x - 1][y].value in poop) {     // TOP
            list2[x - 1][y].opened = true
        }
        if (x < width - 1 && list2[x + 1][y].value in poop) {      // BOTTOMx
            list2[x + 1][y].opened = true
        }
        if (x > 0 && y < height - 1 && list2[x - 1][y + 1].value in poop) {      // TOP RIGTH
            list2[x - 1][y + 1].opened = true
        }
        if (x > 0 && y > 0 && list2[x - 1][y - 1].value in poop) {      // TOP LEFT
            list2[x - 1][y - 1].opened = true
        }
        if (x < width - 1 && y < height - 1 && list2[x + 1][y + 1].value in poop) {      // BOTTOM RIGTH
            list2[x + 1][y + 1].opened = true
        }
        if (x < width - 1 && y > 0 && list2[x + 1][y - 1].value in poop) {      // BOTTOM LEFT
            list2[x + 1][y - 1].opened = true
        }
    }
}

fun main() {
    val obj = MineSweeper(9, 9)
    obj.setUpBoard()

    while (obj.isOn){
        obj.playGame()
    }
}
//214 207 212