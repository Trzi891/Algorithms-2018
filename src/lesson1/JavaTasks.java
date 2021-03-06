package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Float.max;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        ArrayList<Date> dateList = new ArrayList<>();
        if (inputName != null && outputName != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                dateList.add(format.parse(line));
            }
            Collections.sort(dateList);
            for (Date time : dateList) {
                writer.write(format.format(time));
                writer.newLine();
            }
            writer.close();
        }
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        final Collator collator = Collator.getInstance(Locale.US);//The Collator class performs locale-sensitive String comparison.
        // we can use this class to build searching and sorting routines for natural language text
        final SortedSet<String> addressSet = new TreeSet<>(collator);
        Map addressMap = new IdentityHashMap<String, String>();
        Map generalMap = new HashMap();
        List<Map> listMap = new ArrayList();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                generalMap.put(line.split(" - ")[1], line.split(" - ")[0]);
                addressSet.add(line.split(" - ")[1]);
                addressMap.put(line.split(" - ")[1], line.split(" - ")[0]);
            }
        }
        listMap.add(addressMap);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (String string : addressSet) {
            writer.write(string + " - " + String.join(", ",
                    (Iterable<? extends CharSequence>) mapCombine(listMap).get(string)));
            writer.newLine();
        }
        writer.close();
    }

    public static Map mapCombine(List<Map> list) {
        Map<Object, SortedSet> map = new HashMap<>();
        Collator collator = Collator.getInstance(Locale.US);
        for (Map m : list) {
            Iterator<Object> it = m.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (!map.containsKey(key)) {
                    SortedSet nameSet = new TreeSet<String>(collator);
                    nameSet.add(m.get(key));
                    map.put(key, nameSet);
                } else {
                    map.get(key).add(m.get(key));
                }
            }
        }
        return map;
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        List<Double> input = Files.readAllLines(Paths.get(inputName)).stream()
                .map(Double::parseDouble).sorted().collect(Collectors.toList());

        List<String> output = input.stream().map(String::valueOf).collect(Collectors.toList());
        Files.write(Paths.get(outputName), output);
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        List<Integer> listOfNumbers = Files.readAllLines(Paths.get(inputName)).stream()
                .map(Integer::parseInt).collect(Collectors.toList());
        HashMap<Integer, Integer> numbersAndCounts = new HashMap<>();
        for (int i = 0; i < listOfNumbers.size(); i++) {
            if (numbersAndCounts.containsKey(listOfNumbers.get(i))) {
                int temp = numbersAndCounts.get(listOfNumbers.get(i));
                numbersAndCounts.put(listOfNumbers.get(i), temp + 1);
            } else {
                numbersAndCounts.put(listOfNumbers.get(i), 1);
            }
        }

        Collection<Integer> count = numbersAndCounts.values();
        List<Integer> hasTheMostRepetitions = new ArrayList<>();
        int maxCount = Collections.max(count);
        for (Map.Entry<Integer, Integer> entry : numbersAndCounts.entrySet()) {
            if (maxCount == entry.getValue()) {
                hasTheMostRepetitions.add(entry.getKey());
            }
        }
        Collections.sort(hasTheMostRepetitions);

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (Integer num : listOfNumbers) {
            if (!num.equals(hasTheMostRepetitions.get(0))) {
                writer.write(num.toString());
                writer.newLine();
            }
        }
        for (int i = 0; i < maxCount; i++) {
            writer.write(Integer.toString(hasTheMostRepetitions.get(0)));
            writer.newLine();
        }
        writer.close();
    }


    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        List<T> list = new ArrayList();
        System.arraycopy(first, 0, second, 0, first.length);
        for (int i = 0; i < second.length; i++) {
            if (second[i] != null) {
                list.add(second[i]);
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            second[i] = list.get(i);
        }
        Arrays.sort(second);
    }
}
