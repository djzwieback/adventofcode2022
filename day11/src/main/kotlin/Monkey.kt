data class Monkey(
    var number: Long = -1,
    var items: MutableList<Long> = mutableListOf(),
    var operation: (Long) -> Long = { it -> it },
    var divisor: Long = -1,
    var trueMonkey: Long = -1,
    var falseMonkey: Long = -1,
    var inspections: Long = 0
) {
}