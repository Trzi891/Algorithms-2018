package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    // Трудоемкость T = O(E+V)
    // Ресурсоемкость R = O(E)
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Edge> result = new LinkedList<>();
        lookForEL(graph, (Graph.Vertex) graph.getVertices().toArray()[0], result);
        return result;
    }

    private static boolean lookForEL(Graph graph, Graph.Vertex vertex, List<Graph.Edge> result) {
        if (result.size() == graph.getEdges().size()) return true;
        Set<Graph.Vertex> neighbours = graph.getNeighbors(vertex);
        for (Graph.Vertex neighbour : neighbours) {
            if (result.contains(graph.getConnection(vertex, neighbour))) continue;
            result.add(graph.getConnection(vertex, neighbour));
            if (lookForEL(graph, neighbour, result)) return true;
        }
        if (!result.isEmpty()) result.remove(result.size() - 1);
        return false;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     * G    H
     * |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     * G -- H -- J
     * |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
        // Set<Graph.Vertex> result = new HashSet<>();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    //Трудоемкость T = O(V*(V+E))
    // Ресурсоемкость R = O(V)
    public static Path longestSimplePath(Graph graph) {
        List<Graph.Vertex> result = new ArrayList<>();
        for (Graph.Vertex vertex : graph.getVertices()) if (lookForLSP(graph, vertex, result)) break;
        return new Path(result);
    }

    private static boolean lookForLSP(Graph graph, Graph.Vertex vertex, List<Graph.Vertex> result) {
        if (result.size() == graph.getVertices().size()) return true;
        Set<Graph.Vertex> neighbours = graph.getNeighbors(vertex);
        for (Graph.Vertex neighbour : neighbours) {
            if (result.contains(neighbour)) continue;
            result.add(neighbour);
            if (lookForLSP(graph, neighbour, result)) return true;
        }
        if (!result.isEmpty()) result.remove(result.size() - 1);
        return false;
    }

}