import java.nio.file.Files
import java.nio.file.Path

val MONKEY_REGEX = "Monkey (\\d+)".toRegex()
val STARTING_REGEX = "Starting items: (\\d+)".toRegex()
val OPERATION_REGEX = "Operation: new = old ([+*]) (old|\\d+)".toRegex()
val TEST_REGEX = "Test: divisible by (\\d+)".toRegex()
val TRUE_REGEX = "If true: throw to monkey (\\d+)".toRegex()
val FALSE_REGEX = "If false: throw to monkey (\\d+)".toRegex()

fun main(args: Array<String>) {

    val readAllBytes =
        Files.readAllBytes(Path.of("/Users/risc/dev/adventofcode/2022/day11/input.txt"))
    val content = String(readAllBytes)

    var monkey = Monkey();
    val monkeyList = mutableListOf<Monkey>()
    content.lines().forEach { line ->
        if (MONKEY_REGEX.containsMatchIn(line)) {
            monkey.number = MONKEY_REGEX.find(line)!!.groupValues.get(1).toLong()
        } else if (STARTING_REGEX.containsMatchIn(line)) {
            monkey.items = line.split(":").get(1).split(",").map { it.trim() }.map { it.toLong() }
                .toMutableList();
        } else if (OPERATION_REGEX.containsMatchIn(line)) {
            val groupValues = OPERATION_REGEX.find(line)!!.groupValues
            monkey.operation = getOperation(groupValues.get(1), groupValues.get(2))
        } else if (TEST_REGEX.containsMatchIn(line)) {
            monkey.divisor = TEST_REGEX.find(line)!!.groupValues.get(1).toLong()
        } else if (TRUE_REGEX.containsMatchIn(line)) {
            monkey.trueMonkey = TRUE_REGEX.find(line)!!.groupValues.get(1).toLong()
        } else if (FALSE_REGEX.containsMatchIn(line)) {
            monkey.falseMonkey = FALSE_REGEX.find(line)!!.groupValues.get(1).toLong()
        } else if (line.isBlank()) {
            monkeyList.add(monkey.copy())
            monkey = Monkey()
        }
    }

    val commonDivisor = monkeyList.map { it.divisor }.reduce { acc, l -> acc * l }

    for (i in 1..10000) {
        for (activeMonkey in monkeyList) {
            while (activeMonkey.items.isNotEmpty()) {
                activeMonkey.inspections++
                var removeLast = activeMonkey.items.removeLast()
                removeLast = activeMonkey.operation(removeLast)
                //Only in first riddle
//                removeLast /= 3;
                removeLast %= commonDivisor
                if (removeLast % activeMonkey.divisor == 0L) {
                    monkeyList.get(activeMonkey.trueMonkey.toInt()).items.add(removeLast)
                } else {
                    monkeyList.get(activeMonkey.falseMonkey.toInt()).items.add(removeLast)
                }
            }
        }
    }

    val monkeyBusiness = monkeyList.map { it.inspections }.sortedDescending().subList(0, 2)
        .reduce { acc, i -> acc * i }
    println("Monkey business is ${monkeyBusiness}")
}

fun getOperation(operator: String, value: String): (Long) -> Long {
    if (operator == "+") {
        return { a: Long ->
            a + if (value == "old") {
                a
            } else {
                value.toLong()
            }
        }
    } else {
        return { a: Long ->
            a * if (value == "old") {
                a
            } else {
                value.toLong()
            }
        }
    }
}

