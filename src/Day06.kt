fun main() {
    fun part1(input: List<String>): Long {
        val operations = buildList {
            val line = input[input.size - 1]
            var i = 0
            while (i < line.length) {
                if (line[i] == '+') add(Operation.Add)
                if (line[i] == '*') add(Operation.Mul)
                ++i
            }
        }

        val problemNumbers: List<MutableList<Int>> = List(operations.size) { mutableListOf() }
        for (i in 0..<input.size - 1) {
            val line = input[i]
            var problemIndex = 0
            var j = 0
            var number = 0
            while (j < line.length && line[j] == ' ') ++j
            while (j < line.length) {
                if (line[j] == ' ') {
                    problemNumbers[problemIndex].add(number)
                    number = 0
                    ++problemIndex
                    while (j < line.length && line[j] == ' ') ++j
                } else {
                    number = number * 10 + (line[j].code - 48)
                    ++j
                }
            }
            problemNumbers[problemIndex].add(number)
        }

        val problems = problemNumbers.zip(operations) { numbers, operation ->
            Problem(numbers = numbers, operation = operation)
        }

        return problems.fold(0L) { acc, problem ->
            acc + when (problem.operation) {
                Operation.Add -> problem.numbers.fold(0L, Long::plus)
                Operation.Mul -> problem.numbers.fold(1L, Long::times)
            }
        }
    }

    fun part2(input: List<String>): Long {
        val operations = buildList {
            val line = input[input.size - 1]
            var i = 0
            while (i < line.length) {
                if (line[i] == '+') add(Operation.Add)
                if (line[i] == '*') add(Operation.Mul)
                ++i
            }
        }

        val problemNumbers: List<MutableList<Int>> = List(operations.size) { mutableListOf() }
        var i = 0
        var j = 0
        while (i in 0..<operations.size) {
            var number = 0
            for (k in 0..<input.size - 1) {
                if (j < input[k].length) {
                    val char = input[k][j]
                    if (char != ' ') {
                        number = number * 10 + char.code - 48
                    }
                }
            }
            problemNumbers[i].add(number)
            ++j

            if (input.nextProblem(j)) {
                ++i
                ++j
            }
        }

        val problems = problemNumbers.zip(operations) { numbers, operation ->
            Problem(numbers = numbers, operation = operation)
        }

        return problems.fold(0L) { acc, problem ->
            acc + when (problem.operation) {
                Operation.Add -> problem.numbers.fold(0L, Long::plus)
                Operation.Mul -> problem.numbers.fold(1L, Long::times)
            }
        }
    }

    // Or read a large test input from the `src/input/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
//    println("Test Part1 Day06: ${part1(testInput)}")
    println("Test Part2 Day06: ${part2(testInput)}")

    // Read the input from the `src/input/Day06.txt` file.
    val input = readInput("Day06")
//    println("Part1 Day06: ${part1(input)}")
    println("Part2 Day06: ${part2(input)}")
}

private enum class Operation {
    Add,
    Mul,
}

private data class Problem(val numbers: List<Int>, val operation: Operation)

private fun List<String>.nextProblem(j: Int): Boolean {
    return buildList {
        for (i in 0..<this@nextProblem.size - 1) {
            if (j < this@nextProblem[i].length) {
                add(this@nextProblem[i][j])
            }
        }
    }.all { it == ' ' }
}
