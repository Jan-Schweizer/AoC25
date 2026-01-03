fun main() {
    fun part1(input: List<String>): Int {
        var current = 50
        var ans = 0
        input.forEach { el ->
            val value = el.drop(1).toInt()
            if (el[0] == 'L') {
                current -= value
                while (current < 0) {
                    current += 100
                }
            } else if (el[0] == 'R') {
                val value = el.drop(1).toInt()
                current += value
                while (current >= 100) {
                    current -= 100
                }
            }

            if (current == 0) ++ans
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        var current = 50
        var ans: Long = 0
        input.forEach { el ->
            val value = el.drop(1).toInt()
            val wasZero = current == 0
            ans += value / 100
            if (el[0] == 'L') {
                current -= value % 100
                if (current < 0) {
                    current += 100
                    if (!wasZero && current != 0) ++ans
                }
            } else {
                current += value % 100
                if (current >= 100) {
                    current -= 100
                    if (!wasZero && current != 0) ++ans
                }
            }
            if (current == 0) ++ans
        }

        return ans
    }

    // Or read a large test input from the `src/input/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
//    println("Test Part1 Day01: ${part1(testInput)}")
    println("Test Part2 Day01: ${part2(testInput)}")

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
//    println("Part1 Day01: ${part1(input)}")
    println("Part2 Day01: ${part2(input)}")
}
