import kotlin.math.absoluteValue

data class Position(var row: Int, var column: Int) {

    fun move(move: Move) {
        when (move.direction) {
            "L" -> column--
            "R" -> column++
            "U" -> row++
            "D" -> row--
        }
    }

    fun isTouching(position: Position): Boolean {
        return this == position ||
                isNextInRow(position) ||
                isNextInColumn(position) ||
                isNextDiagonal(position)
    }

    fun isNextInColumn(position: Position): Boolean {
        return this.column == position.column && (this.row - position.row).absoluteValue == 1
    }

    fun isNextInRow(position: Position): Boolean {
        return this.row == position.row && (this.column - position.column).absoluteValue == 1
    }

    fun isNextDiagonal(position: Position): Boolean {
        return (this.row - position.row).absoluteValue == 1 && (this.column - position.column).absoluteValue == 1
    }

    fun follow(head: Position) {
        if (this.column != head.column) {
            if (head.column > this.column) {
                this.column++
            } else {
                this.column--
            }
        }
        if (this.row != head.row) {
            if (head.row > this.row) {
                this.row++
            } else {
                this.row--
            }
        }
    }
}
