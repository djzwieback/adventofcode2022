import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun extractMove(line: String): List<Int> {
    return "move (\\d+) from (\\d+) to (\\d+)".toRegex().findAll(
        line
    ).toList().get(0).groupValues.subList(1, 4).map { it.toInt() }
}

fun main(args: Array<String>) {
    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day5/input.txt"))
    val content = String(readAllBytes)
    val lines = content.lines()

    val loadingArea = hashMapOf<Int, Stack<Char>>(
        1 to Stack(),
        2 to Stack(),
        3 to Stack(),
        4 to Stack(),
        5 to Stack(),
        6 to Stack(),
        7 to Stack(),
        8 to Stack(),
        9 to Stack()
    );
    var processedLoadingArea = false;

    for (line in lines) {
        if (!processedLoadingArea) {
            // Hardcoded for 9 rows because of laziness
            for (i in 1 until 36 step 4) {
                val craneIndex = (i + 1) / 4 + 1
                if (line.get(i).isLetter()) {
                    loadingArea.get(craneIndex)?.add(0, line.get(i))
                }
            }
        }
        // Baseline of loading area
        if (line.trim().startsWith("1")) {
            processedLoadingArea = true
            continue
        }
        //Separator between crane and moves
        if (line.isBlank()) {
            continue;
        }

        if (processedLoadingArea) {
            println("Command ${line}")
            val move = extractMove(line)
            val list = mutableListOf<Char>()
            for(i in 1 .. move.get(0)) {
                list.add(loadingArea.get(move.get(1))!!.pop())
            }
            loadingArea.get(move.get(2))?.addAll(list.reversed())
            //riddle 1
//            for (i in 1..moves.get(0)) {
//                crane.get(moves.get(2))?.add(crane.get(moves.get(1))?.pop())
//            }
        }


    }
    var resultString = "";
    for (i in 1..9) {
        resultString += loadingArea.get(i)?.peek()
    }
    println("Result is: ${resultString}")

}