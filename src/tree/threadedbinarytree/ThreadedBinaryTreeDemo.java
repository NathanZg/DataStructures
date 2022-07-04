package tree.threadedbinarytree;

/**
 * ClassName: ThreadedBinaryTreeDemo
 * Description:
 * date: 2022/7/3 21:50
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        Node root = new Node(1, "11");
        Node node2 = new Node(3, "33");
        Node node3 = new Node(6, "66");
        Node node4 = new Node(8, "88");
        Node node5 = new Node(10, "1010");
        Node node6 = new Node(14, "1414");
        root.setLeft(node2);
        node2.setParent(root);
        root.setRight(node3);
        node3.setParent(root);
        node2.setLeft(node4);
        node4.setParent(node2);
        node2.setRight(node5);
        node5.setParent(node2);
        node3.setLeft(node6);
        node6.setParent(node3);
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(root);
        threadedBinaryTree.postThreadedNodes(root);
        Node left = node5.getLeft();
        System.out.println(left);
        System.out.println(node5.getRight());
        threadedBinaryTree.postThreadedList();
    }
}


class ThreadedBinaryTree{
    private Node root;

    //为实现线索化 创建指向当前结点的的前驱结点
    private Node pre = null;

    public ThreadedBinaryTree(Node root) {
        this.root = root;
    }

    //遍历中序线索化二叉树
    public void midThreadedList() {
        Node node = root;
        while (node != null) {
            //循环找到leftType=1的结点
            //也就是找到第一个有前驱结点的结点，第一个输出的结点的前驱结点就是null
            //所以找到后就可以直接输出了
            while (node.getLeftType() == 0) {
                    node = node.getLeft();
            }
            //打印这个结点
            System.out.println(node);
            //如果当前结点的右指针指向的是后继结点，一直输出
            while (node.getRightType() == 1) {
                //获取当前结点的后继结点
                node = node.getRight();
                System.out.println(node);
            }
            //替换这个遍历的结点
            //为什么是替换成右边这个？
            //意味是中序遍历，左中右，左边的肯定已经早就输出完了
            node = node.getRight();
        }
    }

    //中序线索化
    public void midThreadedNodes(Node node){
        //如果node为空 不能线索化
        if (node == null) {
            return;
        }

        //先线索化左子树
        midThreadedNodes(node.getLeft());

        //处理后继结点 后继结点是在跳到上一层才处理的
        if (pre != null && pre.getRight() == null) {
            //让前驱结点的右指针指向当前结点
            pre.setRight(node);
            //修改前驱结点的右指针类型
            pre.setRightType(1);
        }

        //线索化当前结点
        if (node.getLeft() == null) {
            //当前结点左指针指向前驱结点
            node.setLeft(pre);
            //修改当前结点的左指针的类型
            node.setLeftType(1);
        }

        //每处理一个结点后，让当前结点是下一个结点的前驱结点
        pre = node;

        //线索化右子树
        midThreadedNodes(node.getRight());
    }

    //遍历前序线索化二叉树
    public void preThreadedList() {
        Node node = root;
        while (node != null) {
            //前序是中左右，所以没到最左叶子结点之前一直输出左子结点
            while (node.getLeftType() == 0) {
                System.out.println(node);
                node = node.getLeft();
            }
            //输出左叶子结点
            System.out.println(node);
            //根据线索一直输出后序结点
            while (node.getRightType() == 1) {
                node = node.getRight();
                System.out.println(node);
            }
            //没有线索了就将结点置为右子结点
            //中左右 左边的肯定都访问过了
            node = node.getRight();
        }
    }

    //前序线索化二叉树
    public void preThreadedNodes(Node node) {
        if (node == null) {
            return;
        }
        //用临时变量存储该结点的左右指针
        //如果左右指针为空，则在递归遍历之前，它就会被替换成前驱结点 和 后继结点
        //就会造成死循环，造成栈溢出
        Node tLeft = node.getLeft();
        Node tRight = node.getRight();
        //处理后继结点 后继结点是在跳到上一层才处理的
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        pre = node;
        //线索化左子树
        preThreadedNodes(tLeft);
        //线索化右子树
        preThreadedNodes(tRight);
    }

    //遍历后序线索化二叉树
    //遍历后序线索化二叉树需要在结点增添一个存储父结点的结点
    public void postThreadedList(){
        Node node = root;
        //找到最左叶子结点
        while (node.getLeftType() == 0) {
            node = node.getLeft();
        }
        while (node != null) {
            //如果该节点有右线索
            if (node.getRightType() == 1) {
                System.out.println(node);
                pre = node;
                node = node.getRight();
            }else{//没有线索
                //上一个处理的结点是当前结点的右结点
                if (pre == node.getRight()) {
                    System.out.println(node);
                    if (node == root) {//到达根节点则退出
                        return;
                    }
                    pre = node;
                    node = node.getParent();
                }else{//上一次处理的结点是当前结点的左结点
                    //进入到右子树
                    node = node.getRight();
                    //找到右子树的最左叶子结点
                    while (node.getLeftType() == 0) {
                        node = node.getLeft();
                    }
                }
            }
        }
    }

    //后序线索化二叉树
    public void postThreadedNodes(Node node) {
        if (node == null){
            return;
        }
        //线索化左子树
        postThreadedNodes(node.getLeft());
        //线索化右子树
        postThreadedNodes(node.getRight());
        //处理后继结点 后继结点是在跳到上一层才处理的
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        pre = node;
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
    //0为指向左子树，1为指向前驱结点
    private int leftType;
    //0为指向右子树，1为指向后继结点
    private int rightType;
    //为了实现后序线索化遍历
    private Node parent;//父节点

    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
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


