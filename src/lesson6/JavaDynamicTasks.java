package lesson6;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import kotlin.NotImplementedError;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоемкость T = O(n^2)
    // Ресурсоемкость R = O(n)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() == 0 || list.size() == 1) return list;
        int[] lens = new int[list.size()], selectNum = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            lens[i] = 1;
            selectNum[i] = -1;
        }
        int maxIndex = 0;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && lens[j] + 1 > lens[i]) {
                    lens[i] = lens[j] + 1;
                    selectNum[i] = j;
                    if (lens[maxIndex] < lens[i]) {
                        maxIndex = i;
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        int current = maxIndex;
        while (current >= 0) {
            result.add(list.get(current));
            current = selectNum[current];
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    // Трудоемкость T = O(n*m),где n - кол-во строк таблицы, а m - кол-во столбцов
    // Ресурсоемкость R = O(n*m + n*m) = O(n*m)
    public static int shortestPathOnField(String inputName) throws IOException {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                list.add(line.split(" "));
            }
        }
        int height = list.size(), width = list.get(0).length;
        int[][] array = new int[height][width];
        array[0][0] = Integer.parseInt(list.get(0)[0]);
        for (int i = 1; i < width; i++) array[0][i] = Integer.parseInt(list.get(0)[i]) + array[0][i - 1];
        for (int i = 1; i < height; i++) array[i][0] = Integer.parseInt(list.get(i)[0]) + array[i - 1][0];

        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                int minSum = Math.min(Math.min(array[j - 1][i], array[j][i - 1]), array[j - 1][i - 1]);
                array[j][i] = Integer.parseInt(list.get(j)[i]) + minSum;
            }
        }
        return array[height - 1][width - 1];
    }


    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
