import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

fun main(args: Array<String>) {

    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day8/input.txt"))
    val content = String(readAllBytes)

    val grid: MutableList<List<Int>> = mutableListOf()
    for (line in content.lines()) {
        if (line.isNotBlank()) {
            grid.add(line.asIterable().map { it.digitToInt() }.toList())
        }
    }

    var numberOfVisibleTrees = 0
    var scenicHighscore = 0
    for ((rowIndex, row) in grid.withIndex()) {
        for ((columnIndex, cell) in row.withIndex()) {
            var scenicScore = 1
            var visible = true
            var rowCounter = rowIndex - 1
            if (rowIndex == 0 || columnIndex == 0 || rowIndex == grid.size - 1 || columnIndex == row.size - 1) {
                numberOfVisibleTrees++
                continue
            }
            var numberOfTrees = 0
            while (rowCounter >= 0) {
                numberOfTrees++
                if (grid.get(rowCounter).get(columnIndex) >= cell) {
                    visible = false
                    break
                }
                rowCounter--
            }
            scenicScore *= numberOfTrees
            numberOfTrees = 0
//            if (visible) {
//                numberOfVisibleTrees++
//                continue;
//            }
            visible = true
            rowCounter = rowIndex + 1
            while (rowCounter < grid.size) {
                numberOfTrees++
                if (grid.get(rowCounter).get(columnIndex) >= cell) {
                    visible = false
                    break
                }
                rowCounter++
            }
            scenicScore *= numberOfTrees
            numberOfTrees = 0
//            if (visible) {
//                numberOfVisibleTrees++
//                continue;
//            }
            visible = true
            var columnCounter = columnIndex - 1
            while (columnCounter >= 0) {
                numberOfTrees++
                if (grid.get(rowIndex).get(columnCounter) >= cell) {
                    visible = false
                    break
                }
                columnCounter--
            }
            scenicScore *= numberOfTrees
            numberOfTrees = 0
//            if (visible) {
//                numberOfVisibleTrees++
//                continue;
//            }
            visible = true
            columnCounter = columnIndex + 1
            while (columnCounter < row.size) {
                numberOfTrees++
                if (grid.get(rowIndex).get(columnCounter) >= cell) {
                    visible = false
                    break;
                }
                columnCounter++
            }
            scenicScore *= numberOfTrees
            if (scenicScore > scenicHighscore) {
                scenicHighscore = scenicScore
            }
//            if (visible) {
//                numberOfVisibleTrees++
//                continue;
//            }
        }
    }

//    println("Number of visible trees is ${numberOfVisibleTrees}")
    println("Scenic highscore is ${scenicHighscore}")
}