import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day1/input.txt"))
    val content = String(readAllBytes)
    val splittedContent = content.split("\n\n");
    val map = splittedContent
        .map {
            it.split("\n")
                .filter { number -> number.isNotBlank() }
                .map { number -> number.toInt() }.sum()
        }
        .sortedDescending()
    var sum = 0;
    for ((index, item) in map.withIndex()) {
        if (index > 2) {
            break;
        }
        sum += item
    }
    println("max value is: ${sum}")
}