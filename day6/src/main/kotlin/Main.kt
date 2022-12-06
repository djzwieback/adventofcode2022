import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {

    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day6/input.txt"))
    val content = String(readAllBytes)

    println("Start of marker: ${getMarkerPosition(content)}")
    println("Start of marker: ${getMessageStart(content)}")

}

private fun getMarkerPosition(content: String): Int {
    for (i in 0..content.length) {
        if (content.substring(i, i + 4).groupBy { it }.keys.size == 4) return i + 4
    }
    return -1
}

private fun getMessageStart(content: String): Int {
    for (i in 0..content.length) {
        if (content.substring(i, i + 14).groupBy { it }.keys.size == 14) return i + 14
    }
    return -1
}