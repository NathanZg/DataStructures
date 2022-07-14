package binarysorttree;

/**
 * ClassName: BinarySortTreeDemo
 * Description:二叉排序树
 * date: 2022/7/14 9:26
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7,3,10,12,5,1,9,0};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        System.out.println("删除之前");
        binarySortTree.infixOrder();
        binarySortTree.delNode2(7);
        binarySortTree.delNode2(3);
        binarySortTree.delNode2(10);
        binarySortTree.delNode2(12);
        binarySortTree.delNode2(5);
        binarySortTree.delNode2(1);
        binarySortTree.delNode2(9);
        binarySortTree.delNode2(0);
        System.out.println("删除之后");
        binarySortTree.infixOrder();
    }
}

class BinarySortTree{
    private Node root;
    //添加结点
    public void add(Node node) {
        //root为空则让root指向node
        if (root == null) {
            root = node;
        }else{
            root.add(node);
        }
    }
    //中序遍历
    public void infixOrder() {
        if (root == null) {
            System.out.println("二叉排序树为空，无法遍历！");
        }else {
            root.infixOrder();
        }
    }
    //查找要删除的结点
    public Node search(int value) {
        if (root == null) {
            return null;
        }else{
            return root.search(value);
        }
    }

    //查找要删除结点的父结点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        }else{
            return root.searchParent(value);
        }
    }

    /**
     *
     * @param node 传入的结点
     * @return 返回以node为根节点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }
        //删除最小的结点
        delNode2(target.value);
        return target.value;
    }

    /**
     *
     * @param node 传入的结点
     * @return 返回以node为根节点的二叉排序树的最大结点的值
     */
    public int delLeftTreeMax(Node node) {
        Node target = node;
        while (target.right != null) {
            target = target.right;
        }
        //删除最小的结点
        delNode1(target.value);
        return target.value;
    }

    //删除结点
    /*
        第一种情况: 删除叶子节点 (比如：2, 5, 9, 12)
            思路
            (1) 需求先去找到要删除的结点 targetNode
            (2) 找到 targetNode 的 父结点 parent
            (3) 确定 targetNode 是 parent 的左子结点 还是右子结点
            (4) 根据前面的情况来对应删除 左子结点 parent.left = null 右子结点 parent.right = null;
        第二种情况: 删除只有一颗子树的节点 比如 1 思路
            (1) 需求先去找到要删除的结点 targetNode
            (2) 找到 targetNode 的 父结点 parent
            (3) 确定 targetNode 的子结点是左子结点还是右子结点
            (4) targetNode 是 parent 的左子结点还是右子结点
            (5) 如果 targetNode 有左子结点
                5. 1 如果 targetNode 是 parent 的左子结点
                    parent.left = targetNode.left;
                5.2 如果 targetNode 是 parent 的右子结点 parent.right = targetNode.left;
            (6) 如果 targetNode 有右子结点
                6.1 如果 targetNode 是 parent 的左子结点 parent.left = targetNode.right;
                6.2 如果 targetNode 是 parent 的右子结点 parent.right = targetNode.right;
         情况三 ： 删除有两颗子树的节点. (比如：7, 3，10 )
             思路
             (1) 需求先去找到要删除的结点 targetNode
             (2) 找到 targetNode 的 父结点 parent
             (3) 从 targetNode 的右子树找到最小的结点
             (4) 用一个临时变量，将 最小结点的值保存 temp = 11
             (5) 删除该最小结点
             (6) targetNode.value = temp
     */
    //删除的结点有左右子树的时候，删除该结点的右子树的最小值，并将该值赋值给该结点
    public void delNode1(int value) {
        if (root == null) {
            return;
        }else{
            //找到要删除的结点
            Node targetNode = search(value);
            //没有找到
            if (targetNode == null) {
                return;
            }
            //如果二叉树只有一个结点,而且前面又找到了要删除的结点
            //则说明根节点就是我们要删除的结点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            //找到targetNode 父结点
            Node parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if (targetNode.left ==  null && targetNode.right == null) {
                //判断targetNode是父结点的左子结点还是右子结点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                }else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
                //如果targetNode有左右子树
            } else if (targetNode.left != null && targetNode.right != null) {
                //右子树最小的结点的值 （当然也可以找左子树的最大值）
                int minVal = delRightTreeMin(targetNode.right);
                //将最小值赋值给target
                targetNode.value = minVal;
            }else { //只有一个子结点
                //如果targetNode有左子结点
                if (targetNode.left != null) {//需要考虑删除的结点是不是根结点
                    if(parent != null) {//当要删除的结点不是根结点
                        //如果targetNode是parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        }else{//targetNode是parent的右子结点
                            parent.right = targetNode.left;
                        }
                    }else{//要删除的结点是根结点
                        root = targetNode.left;
                    }
                }else { //targetNode有右子结点
                    if (parent != null) {//要删除的结点不是根结点
                        //targetNode 是 parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        }else {//targetNode是parent的右子结点
                            parent.right = targetNode.right;
                        }
                    }else {//要删除的结点是根结点
                        root = targetNode.right;
                    }
                }
            }
        }
    }

    //删除的结点有左右子树的时候，删除该结点的右子树的最小值，并将该值赋值给该结点
    public void delNode2(int value) {
        if (root == null) {
            return;
        }else{
            //找到要删除的结点
            Node targetNode = search(value);
            //没有找到
            if (targetNode == null) {
                return;
            }
            //如果二叉树只有一个结点,而且前面又找到了要删除的结点
            //则说明根节点就是我们要删除的结点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            //找到targetNode 父结点
            Node parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if (targetNode.left ==  null && targetNode.right == null) {
                //判断targetNode是父结点的左子结点还是右子结点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                }else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
                //如果targetNode有左右子树
            } else if (targetNode.left != null && targetNode.right != null) {
                //左子树最大的结点的值 （当然也可以找右子树的最小值）
                int maxVal = delLeftTreeMax(targetNode.left);
                //将最小值赋值给target
                targetNode.value = maxVal;
            }else { //只有一个子结点
                //如果targetNode有左子结点
                if (targetNode.left != null) {//需要考虑删除的结点是不是根结点
                    if(parent != null) {//当要删除的结点不是根结点
                        //如果targetNode是parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        }else{//targetNode是parent的右子结点
                            parent.right = targetNode.left;
                        }
                    }else{//要删除的结点是根结点
                        root = targetNode.left;
                    }
                }else { //targetNode有右子结点
                    if (parent != null) {//要删除的结点不是根结点
                        //targetNode 是 parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        }else {//targetNode是parent的右子结点
                            parent.right = targetNode.right;
                        }
                    }else {//要删除的结点是根结点
                        root = targetNode.right;
                    }
                }
            }
        }
    }
}


class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //添加结点
    public void add(Node node) {
        if (node == null) {
            return;
        }
        //判断传入的结点的值与当前子树的根结点的关系
        //要添加的结点的值小于当前结点的值
        if (node.value < this.value) {
            //当前结点的左子结点为空
            if (this.left == null) {
                this.left = node;
            }else{
                //递归向左子树添加
                this.left.add(node);
            }
        }else if (node.value > this.value) {//要添加的结点的值大于当前结点的值
            //当前结点的右子结点为空
            if (this.right == null) {
                this.right = node;
            }else{
                //递归向右子树添加
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    //查找要删除的结点
    public Node search(int value) {
        if (value == this.value){
            return this;
        } else if (value < this.value) {//往左子树找
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {//往右子树找
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    //查找要删除结点的父结点
    public Node searchParent(int value) {
        //如果当前结点是父结点，直接返回
        if((this.left != null && this.left.value == value)
        ||  (this.right != null && this.right.value == value)) {
            return this;
        }else {
            //如果查找的这个值小于当前结点的值
            //且当前结点的左子结点不为空
            if (value < this.value && this.left != null) {
                //向左子树递归查找
                return this.left.searchParent(value);
            } else if (value > this.value && this.right != null){
                //向右子树
                return this.right.searchParent(value);
            }
        }
        //没有找到
        return null;
    }
}