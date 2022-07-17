package graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ClassName: Graph
 * Description:
 * date: 2022/7/17 14:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class Graph {
    //存储顶点的集合
    private ArrayList<String> vertexList;
    //图的邻接矩阵
    private int[][] edges;
    //边的数目
    private int numOfEdges;
    //记录顶点是否被访问过
    private boolean isVisited[];

    public Graph(int n) {
        //初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        //初始化边的条数
        numOfEdges = 0;
        //初始化访问数组
        isVisited = new boolean[n];
    }

    public static void main(String[] args) {
        int n = 5;
        String vertexs[] = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(n);
        for (String s : vertexs) {
            graph.insertVertex(s);
        }
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        graph.ShowGraph();
        System.out.println("dfs深度遍历：");
        graph.dfs();
    }

    //插入结点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    //添加边

    /**
     * @param v1     点的下标
     * @param v2     点的下标
     * @param weight 权值
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    //得到结点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //返回结点i（下标）对应的数据
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1 和 v2 的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    //显示矩阵
    public void ShowGraph() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }

    //得到下标为index的第一个邻接结点的下标
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] > 0) {
                return i;//存在返回对应下标
            }
        }
        //不存在返回-1
        return -1;
    }

    //根据前一个邻接结点的下标来获取下一个邻接结点

    /**
     * n1的邻接点为n2
     * 也就是说，当n2被访问后，找不到与其邻接的点后
     * 就会返回到n1,找n1的其他邻接点
     *
     * @param v1 n1的下标
     * @param v2 n2的下标
     * @return
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    //深度优先遍历
    private void dfs(boolean[] isVisited, int i) {
        //访问当前结点
        System.out.print(getValueByIndex(i) + "->");
        //将当前结点置为已访问
        isVisited[i] = true;
        //查找结点i的第一个邻接结点
        int neighbor = getFirstNeighbor(i);
        while (neighbor != -1) {//只要存在就继续找
            if (!isVisited[neighbor]) {//该结点没有被访问过
                dfs(isVisited, neighbor);
            }
            //如果被访问过了
            //查找下一个邻接结点
            neighbor = getNextNeighbor(i, neighbor);
        }
    }

    //这个重载的方法是对于连通图没有必要
    //但是如果是非连通图，即是存在部分联通以及孤立的点
    public void dfs() {
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                /*
                    如果是连通图,则只要进入一次方法就已经遍历完毕
                    如果是非连通图，则每一次进入方法遍历的只是部分连通的结点或者孤立的点
                 */
                dfs(isVisited, i);
            }
        }
    }
}
