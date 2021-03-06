package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //Трудоёмкость O(n)
    //Ресурсоёмкость O(n)
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {
        List<Integer> listOfNumbers = Files.readAllLines(Paths.get(inputName)).stream().map(Integer::parseInt)
                .collect(Collectors.toList());
        int[] env = {listOfNumbers.get(0), listOfNumbers.get(0)};
        int start = 0, end = 0;
        int maxD = 0;

        for (int i = 1; i < listOfNumbers.size(); i++) {
            if (listOfNumbers.get(i) > env[1]) {
                env[1] = listOfNumbers.get(i);
                if (maxD < env[1] - env[0]) {
                    maxD = env[1] - env[0];
                    start = env[0];
                    end = env[1];
                }
            } else if (listOfNumbers.get(i) < env[0]) {
                env[0] = listOfNumbers.get(i);
                env[1] = listOfNumbers.get(i);
            }
        }
        return new Pair<>(listOfNumbers.indexOf(start) + 1, listOfNumbers.lastIndexOf(end) + 1);
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    //Трудоёмкость O(n)
    //Ресурсоёмкость O(n)
    static public int josephTask(int menNumber, int choiceInterval) {
        int result = 0;
        for (int i = 1; i <= menNumber; i++) {
            result = (result + choiceInterval) % i;
        }
        return result + 1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    //Трудоёмкость O(m*n)
    //Ресурсоёмкость O(n)
    static public String longestCommonSubstring(String first, String second) {
        int longestLength = 0;
        String result = "";
        int[][] matrix = new int[first.length() + 1][second.length() + 1]; // ///不赋值时的初始值为0
        for (int i = 1; i <= first.length(); i++) {
            char c1 = first.charAt(i - 1);
            for (int j = 1; j <= second.length(); j++) {
                char c2 = second.charAt(j - 1);
                if (c1 == c2) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = 0;
                }
                if (matrix[i][j] > longestLength) {
                    longestLength = matrix[i][j];
                    result = first.substring(i - longestLength , i );
                }
            }
        }
        return result;
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //Трудоёмкость O(n*m)
    //Ресурсоёмкость O(n)
    static public int calcPrimesNumber(int limit) {
        int count = 0;
        if (limit <= 1) return count;
        for (int i = 1; i <= limit; i++) {
            if (isPrime(i))
                count++;
        }
        return count;
    }

    static boolean isPrime(int num) {
        if (num < 2)
            return false;
        int i = 2;
        for (; i * i <= num; i++) {
            if (num % i == 0)
                break;
        }
        return i * i > num;
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
