@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.Collator
import java.text.SimpleDateFormat
import java.util.*

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    val format = SimpleDateFormat("HH:mm:ss")
    val dateList = ArrayList<Date>()
    val outputStream = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        dateList.add(format.parse(line))
        dateList.sort()
    }
    for (time in dateList) {
        outputStream.write(format.format(time))
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    val collator = Collator.getInstance(Locale.US)
    val addressSet = TreeSet<String>(collator)
    val addressMap = IdentityHashMap<String, String>()
    val generalMap = HashMap<String, String>()
    val listMap = ArrayList<Map<String, String>>()
    val outputStream = File(outputName).bufferedWriter()
    for (line in File(inputName).readLines()) {
        generalMap[line.split(" - ")[1]] = line.split(" - ")[0]
        addressSet.add(line.split(" - ")[1])
        addressMap[line.split(" - ")[1]] = line.split(" - ")[0]
    }
    listMap.add(addressMap)
    for (string in addressSet) {
        outputStream.write(string + " - " +
                (mapCombine(listMap)[string] as Iterable<*>).joinToString(", "))
        outputStream.newLine()
    }
    outputStream.close()
}

fun mapCombine(list: List<Map<String, String>>): Map<*, *> {
    val map = HashMap<Any?, SortedSet<String>>()
    val collator = Collator.getInstance(Locale.US)
    for (m in list) {
        val it = m.keys.iterator()
        while (it.hasNext()) {
            val key = it.next()
            if (!map.containsKey(key)) {
                val nameSet = TreeSet<String>(collator)
                nameSet.add(m[key]!!)
                map[key] = nameSet
            } else {
                map[key]?.add(m[key])
            }
        }
    }
    return map
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val input = File(inputName).readLines()
    val buffer = input.asSequence().map { it.toDouble() }.sorted().toList()

    val output = buffer.asSequence().map { it.toString() }.toList()
    Files.write(Paths.get(outputName), output)
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    val listOfNumbers = File(inputName).readLines().asSequence().map { it.toInt() }.toMutableList()
    val numbersAndCounts = HashMap<Int, Int>()
    for (i in listOfNumbers.indices) {
        if (numbersAndCounts.containsKey(listOfNumbers[i])) {
            val temp = numbersAndCounts[listOfNumbers[i]]
            numbersAndCounts[listOfNumbers[i]] = temp!! + 1
        } else {
            numbersAndCounts[listOfNumbers[i]] = 1
        }
    }

    val count = numbersAndCounts.values
    val hasTheMostRepetitions = ArrayList<Int>()
    val maxCount = Collections.max(count)
    for ((key, value) in numbersAndCounts) {
        if (maxCount == value) {
            hasTheMostRepetitions.add(key)
        }
    }
    hasTheMostRepetitions.sort()

    val writer = File(outputName).bufferedWriter()
    for (num in listOfNumbers) {
        if (num != hasTheMostRepetitions[0]) {
            writer.write(num.toString())
            writer.newLine()
        }
    }
    for (i in 0 until maxCount) {
        writer.write(Integer.toString(hasTheMostRepetitions[0]))
        writer.newLine()
    }
    writer.close()
}


/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    val list = ArrayList<T>()
    System.arraycopy(first, 0, second, 0, first.size)
    for (i in second.indices) {
        if (second[i] != null) {
            list.add(second[i]!!)
        }
    }
    for (i in 0 until list.size - 1) {
        second[i] = list[i]
    }
    Arrays.sort(second)
}