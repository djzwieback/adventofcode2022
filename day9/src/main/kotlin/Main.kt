import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {


    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day9/input.txt"))
    val content = String(readAllBytes)

    val tail = Position(0, 0)
    val head = Position(0, 0)
    val snake: List<Position> = listOf(
        head,
        tail.copy(),
        tail.copy(),
        tail.copy(),
        tail.copy(),
        tail.copy(),
        tail.copy(),
        tail.copy(),
        tail.copy(),
        tail
    )
    val visitedPositions: MutableSet<Position> = mutableSetOf(tail.copy());
    val moveRegex = "([DURL]) (\\d+)".toRegex()
    var moves = 0

    for (line in content.lines()) {
        if (line.isBlank()) {
            continue
        }
        val groupValues = moveRegex.matchEntire(line)?.groupValues
        val move = Move(groupValues!!.get(1), groupValues.get(2).toInt())
        moves++
        for (i in 1..move.steps) {
            head.move(move)
            // Solution 1
//            if (!tail.isTouching(head)) {
//                tail.follow(head)
//                visitedPositions.add(tail.copy())
//            }
            // Solution 2
            for (snakePos in 1 until snake.size) {
                if (!snake.get(snakePos).isTouching(snake.get(snakePos - 1))) {
                    snake.get(snakePos).follow(snake.get(snakePos - 1))
                    if (snakePos == snake.size - 1) {
                        visitedPositions.add(snake.get(snakePos).copy())
                    }
                }
            }


        }
    }
    println("Visited positions: ${visitedPositions.size}")
}