fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        val batteryBank = buildList {
            input.forEach { bank ->
                val bankInt = buildList {
                    bank.forEach { battery ->
                        add(battery.code - 48)
                    }
                }
                add(bankInt)
            }
        }

        batteryBank.forEach { bank ->
            ans += bank.findMaximumJoltage1()
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        var ans: Long = 0
        val batteryBank = buildList {
            input.forEach { bank ->
                val bankInt = buildList {
                    bank.forEach { battery ->
                        add(battery.code - 48)
                    }
                }
                add(bankInt)
            }
        }

        batteryBank.forEach { bank ->
            ans += bank.findMaximumJoltage2()
        }

        return ans
    }

    // Or read a large test input from the `src/input/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
//    println("Test Part1 Day03: ${part1(testInput)}")
    println("Test Part2 Day03: ${part2(testInput)}")

    // Read the input from the `src/input/Day03.txt` file.
    val input = readInput("Day03")
//    println("Part1 Day03: ${part1(input)}")
    println("Part2 Day03: ${part2(input)}")
}

private fun List<Int>.findMaximumJoltage1(): Int {
    var maxValue = get(0)
    var maxIndex = 0
    for (i in 1..<size - 1) {
        val value = get(i)
        if (value > maxValue) {
            maxValue = value
            maxIndex = i
        }
    }

    var secondMaxValue = get(maxIndex + 1)
    var secondMaxIndex = maxIndex + 1
    for (i in secondMaxIndex + 1..<size) {
        val value = get(i)
        if (value > secondMaxValue) {
            secondMaxValue = value
            secondMaxIndex = i
        }
    }

    return get(maxIndex) * 10 + get(secondMaxIndex)
}

private fun List<Int>.findMaximumJoltage2(): Long {
    val resIndices: MutableList<Int> = mutableListOf()
    var start = 0
    for (i in 0..<12) {
        val end = size - 11 + i
        var maxValue = -1
        var maxIndex = -1
        for (j in start..<end) {
            if (j in resIndices) continue
            val value = get(j)
            if (value > maxValue) {
                maxValue = value
                maxIndex = j
                start = j
            }
        }
        resIndices.add(maxIndex)
    }

    var multiplier: Long = 1
    return resIndices.foldRight(
        initial = 0,
        operation = { el, acc ->
            val newAcc = acc + get(el) * multiplier
            multiplier *= 10
            newAcc
        },
    )
}

