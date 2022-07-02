package tree;

/**
 * ClassName: BinaryTree
 * Description:
 * date: 2022/6/29 16:10
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        Node node = new Node(1, "11");
        Node node2 = new Node(2, "22");
        Node node3 = new Node(3, "33");
        Node node4 = new Node(4, "44");
        Node node5 = new Node(5, "55");
        node.setLeft(node2);
        node.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        BinaryTree binaryTree = new BinaryTree(node);

    }
}

class BinaryTree{
    private Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    //删除结点
    public void delNode(int no) {
        //判断根结点是否为空
        if (root != null) {
            //判断根结点是不是要找的结点
            if (root.getNo() == no) {
                root = null;
            }else {//不是则进行递归
                root.delNode(no);
            }
        }else {//根结点为空
            System.out.println("空树，无法删除！");
        }
    }

    //前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空!");
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空!");
        }
    }

    //后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空!");
        }
    }

    //前序遍历查找
    public Node preOrderSearch(int no) {
        if (this.root != null) {
           return this.root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    //中序遍历查找
    public Node infixOrderSearch(int no) {
        if (this.root != null) {
            return this.root.infixOrderSearch(no);
        }else {
            return null;
        }
    }

    //后序遍历查找
    public Node postOrderSearch(int no) {
        if (this.root != null) {
            return this.root.postOrderSearch(no);
        }else{
            return null;
        }
    }
}

//结点
class Node {
    private int no;
    private String name;
    private Node left;
    private Node right;

    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //递归删除结点
    /*
        1. 因为我们的二叉树是单向的，所以我们是判断当前结点的子结点是否需要删除结点，而不能去判断 当前这个结点是不是需要删除结点
        2. 如果当前结点的左子结点不为空，并且左子结点 就是要删除结点，就将 this.left = null; 并且就返回 (结束递归删除)
        3. 如果当前结点的右子结点不为空，并且右子结点 就是要删除结点，就将 this.right= null ;并且就返回 (结束递归删除)
        4. 如果第 2 和第 3 步没有删除结点，那么我们就需要向左子树进行递归删除
        5. 如果第 4 步也没有删除结点，则应当向右子树进行递归删除
     */
    public void delNode(int no) {

        //左子结点不为空且为要删除结点 置空并结束递归
        if (this.left != null && this.left.getNo() == no)  {
            this.left = null;
            return;
        }
        //右子结点不为空且为要删除结点 置空并结束递归
        if (this.right != null && this.right.getNo() == no) {
            this.right = null;
            return;
        }
        //向左子树递归删除
        if (this.left != null){
            this.left.delNode(no);
        }
        //向右子树递归删除
        if (this.right != null) {
            this.right.delNode(no);
        }
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);//输出父结点
        //递归向左子树遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        //递归向右子树遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }


    //中序遍历
    public void infixOrder() {
        //递归向左子树遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        //输出父结点
        System.out.println(this);
        //递归向右子树遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    //前序遍历查找

    /**
     *
     * @param no 要查找的编号
     * @return 找到返回该node，没有则返回null
     *
     */
    public Node preOrderSearch(int no) {
        //比较当前结点是不是要找的结点
        if (this.no == no) {
            return this;
        }
        //查找的结果存放在这个结点
        Node resultNode = null;
        //判断当前结点的左子结点是否为空，如果不为空，则向左递归前序查找
        if (this.left != null) {
            resultNode = this.left.preOrderSearch(no);
        }
        if (resultNode != null) {//找到了
            return resultNode;
        }
        //左递归没找到
        //判断当前结点的右子结点是否为空，不为空则继续向右递归前序查找
        if (this.right != null) {
            resultNode = this.right.preOrderSearch(no);
        }
        //不管找没找到，直接返回
        return resultNode;
    }


    //中序遍历查找
    public Node infixOrderSearch(int no) {
        Node resultNode = null;
        //判断当前结点的左子结点是否为空，不为空则向左递归中序遍历查找
        if (this.left != null) {
            resultNode = this.left.infixOrderSearch(no);
        }
        if (resultNode != null) {//找到了
            return resultNode;
        }

        //当前结点no相等
        if (this.no == no) {
            return this;
        }

        //没找到
        //判断当前结点的右子结点是否为空，不为空则向右递归中序遍历查找
        if (this.right != null) {
            resultNode = this.right.infixOrderSearch(no);
        }

        //不管找没找到，直接返回
        return resultNode;
    }

    //后序遍历查找
    public Node postOrderSearch(int no) {
        Node resultNode = null;
        //向左递归遍历查找
        if (this.left != null) {
            resultNode = this.left.postOrderSearch(no);
        }
        //找到了
        if (resultNode != null) {
            return resultNode;
        }
        //没找到，向右递归遍历查找
        if (this.right != null) {
            resultNode = this.right.postOrderSearch(no);
        }
        //找到了
        if (resultNode != null) {
            return resultNode;
        }
        //左右子树都没找到，比较当前结点是不是
        if (this.no == no) {
            return this;
        }else {
            return resultNode;
        }
    }
}
