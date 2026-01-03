fun main() {
    fun part1(input: List<String>): Int {
        val m = input.size
        val n = input[0].length
        var ans = 0

        val grid = input.map {
            buildList {
                it.forEach { char ->
                    add(char)
                }
            }
        }

        for (row in 0..<m) {
            for (col in 0..<n) {
                if (grid[row][col] == '@' && grid.canAccessAt(row, col)) ++ans
            }
        }

        return ans
    }

    fun part2(input: List<String>): Int {
        val m = input.size
        val n = input[0].length
        var ans = 0

        val grid = input.map {
            buildList {
                it.forEach { char ->
                    add(char)
                }
            }.toMutableList()
        }

        var removedRoll = true
        while (removedRoll) {
            removedRoll = false
            for (row in 0..<m) {
                for (col in 0..<n) {
                    if (grid[row][col] == '@' && grid.canAccessAt(row, col)) {
                        ++ans
                        grid[row][col] = '.'
                        removedRoll = true
                    }
                }
            }
        }

        return ans
    }

    // Or read a large test input from the `src/input/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
//    println("Test Part1 Day04: ${part1(testInput)}")
    println("Test Part2 Day04: ${part2(testInput)}")

    // Read the input from the `src/input/Day04.txt` file.
    val input = readInput("Day04")
//    println("Part1 Day04: ${part1(input)}")
    println("Part2 Day04: ${part2(input)}")
}

private fun List<List<Char>>.canAccessAt(row: Int, col: Int): Boolean {
    val m = size
    val n = get(0).size
    var rollsOfPaper = 0

    // Up
    if (row - 1 >= 0 && get(row - 1)[col] == '@') ++rollsOfPaper
    // Up-Right
    if (row - 1 >= 0 && col + 1 < n && get(row - 1)[col + 1] == '@') ++rollsOfPaper
    // Right
    if (col + 1 < n && get(row)[col + 1] == '@') ++rollsOfPaper
    // Down-Right
    if (row + 1 < m && col + 1 < n && get(row + 1)[col + 1] == '@') ++rollsOfPaper
    // Down
    if (row + 1 < m && get(row + 1)[col] == '@') ++rollsOfPaper
    // Down-Left
    if (row + 1 < m && col - 1 >= 0 && get(row + 1)[col - 1] == '@') ++rollsOfPaper
    // Left
    if (col - 1 >= 0 && get(row)[col - 1] == '@') ++rollsOfPaper
    // Up-Left
    if (row - 1 >= 0 && col - 1 >= 0 && get(row - 1)[col - 1] == '@') ++rollsOfPaper

    return rollsOfPaper < 4
}
