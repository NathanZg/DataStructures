package huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: HuffmanTree
 * Description:哈夫曼树
 * date: 2022/7/12 10:19
 *
 * @author Ekertree
 * @since JDK 1.8
 * 1)从小到大进行排序, 将每一个数据，每个数据都是一个节点 ， 每个节点可以看成是一颗最简单的二叉树
 * 2) 取出根节点权值最小的两颗二叉树
 * 3) 组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
 * 4) 再将这颗新的二叉树，以根节点的权值大小 再次排序， 不断重复 1-2-3-4 的步骤，直到数列中，所有的数 据都被处理，就得到一颗赫夫曼树
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(arr);
        preOrder(huffmanTree);
    }


    /**
     *
     * @param arr 需要创建哈夫曼树的数组
     * @return 返回哈夫曼树的根结点
     */
    public static Node createHuffmanTree(int[] arr){
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }
        while (nodes.size() > 1) {//处理完后，只会剩下一个结点，剩下的就是哈夫曼树的根节点
            //排序
            Collections.sort(nodes);
            //取出根节点权值最小的二叉树
            //第一小的
            Node leftNode = nodes.get(0);
            //第二小的
            Node rightNode = nodes.get(1);
            //构建一棵新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //从list中删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将parent 加入list
            nodes.add(parent);
        }
        //返回哈夫曼树的根节点
        return nodes.get(0);
    }

    //前序遍历哈夫曼树
    public static void preOrder(Node root){
        if (root != null){
            System.out.println(root);
        }else {
            System.out.println("空树无法遍历！");
        }
        if (root.left != null) {
            root.left.preOrder();
        }
        if (root.right != null){
            root.right.preOrder();
        }
    }
}

//结点
class Node implements Comparable<Node>{
    int value;//结点权值
    Node left;//指向左子结点
    Node right;//指向左子结点

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }


    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}