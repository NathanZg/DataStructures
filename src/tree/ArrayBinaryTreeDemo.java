package tree;

/**
 * ClassName: ArrayBinaryTreeDemo
 * Description:顺序存储二叉树 只适用于满二叉树
 * date: 2022/7/2 20:43
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        ArrayBinaryTree tree = new ArrayBinaryTree(arr);
        tree.preOrder();
        System.out.println("~~~~~~~~~~");
        tree.infixOrder();
        System.out.println("~~~~~~~~~~");
        tree.postOrder();
    }
}

class ArrayBinaryTree{

    private int[] arr;//存储数据结点的数组

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder(){
        this.preOrder(0);
    }

    /**
     *完成顺序存储二叉树的前序遍历
     * @param index 数组的下标
     */
    public void preOrder(int index){
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空！");
        }
        //输出当前元素
        System.out.println(arr[index]);
        //向左递归遍历
        if (index * 2 + 1 < arr.length){//判断是否越界
            preOrder(index * 2 + 1);
        }

        if (index * 2 + 2 < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void infixOrder(){
        this.infixOrder(0);
    }

    //中序遍历
    public void infixOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空！");
        }
        if (index * 2 + 1 < arr.length) {
            infixOrder(index * 2 + 1);
        }

        System.out.println(arr[index]);

        if (index * 2 + 2 < arr.length) {
            infixOrder(index * 2 + 2);
        }
    }

    public void postOrder(){
        this.postOrder(0);
    }

    //后序遍历
    public void postOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空！");
        }
        if (index * 2 + 1 < arr.length) {
            postOrder(index * 2 + 1);
        }
        if (index * 2 + 2 < arr.length) {
            postOrder(index * 2 + 2);
        }
        System.out.println(arr[index]);
    }
}
