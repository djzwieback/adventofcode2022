import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {

    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day7/input.txt"))
    val content = String(readAllBytes)

    var currentDirectory: Directory? = null;
    for (line in content.lines()) {
        //Is command
        if (line.startsWith("$")) {
            val regex = "cd ([.\\w/]+)".toRegex()
            val matches = regex.findAll(line).map { it.groupValues }.flatMap { it }.toList()
            if (matches.isNotEmpty()) {
                val matchResult = matches.get(1);
                if (matchResult == "..") {
                    currentDirectory = currentDirectory?.parent!!
                } else if (matchResult == "/") {
                    currentDirectory = Directory("/", parent = null);
                } else {
                    val directory = Directory(name = matchResult, parent = currentDirectory)
                    currentDirectory?.addDirectory(directory)
                    currentDirectory = directory;
                }
            }
            //Listing
        } else {
            "(\\d+) ([\\w.]+)".toRegex().findAll(line)
                .map { File(size = it.groupValues.get(1).toInt(), name = it.groupValues.get(2)) }
                .forEach { currentDirectory?.addFile(it) }
        }
    }
    while (currentDirectory?.parent != null) {
        currentDirectory = currentDirectory.parent
    }
    val root: Directory = currentDirectory!!;
    val allDirectories = root.getAllDirectories()
    val sum = allDirectories.map { it.getSize() }.filter { it < 100000 }.sum()
    println("Finished processing. Sum is ${sum}");

    val fileSystemSize: Int = 70000000;
    val requiredSize: Int = 30000000;
    val usedSpace = root.getSize();
    val requiredCleanup = requiredSize - (fileSystemSize - usedSpace);
    val sizeOfDirectoryForCleanup =
        allDirectories.map { it.getSize() }.filter { it >= requiredCleanup }.sorted()
    println("Size of directory to delete is ${sizeOfDirectoryForCleanup.get(0)}")
}