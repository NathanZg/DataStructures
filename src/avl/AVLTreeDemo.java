package avl;

/**
 * ClassName: AVLTreeDemo
 * Description:
 * date: 2022/7/15 9:47
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4,3,6,5,7,8};
        int[] arr1 = {20,10,25,8,15,30,9};
        AVLTree avltree = new AVLTree();
        for (int i = 0; i < arr1.length; i++) {
                avltree.add(new Node(arr1[i]));
        }
        avltree.infixOrder();
        System.out.println("......");
        avltree.delNode(20);
        avltree.infixOrder();
        System.out.println("根结点"+avltree.getRoot());
        System.out.println(avltree.getRoot().height());
        System.out.println(avltree.getRoot().leftHeight());
        System.out.println(avltree.getRoot().rightHeight());
    }
}

class AVLTree {

    private Node root;

    public Node getRoot() {
        return root;
    }

    //添加结点
    public void add(Node node) {
        //root为空则让root指向node
        if (this.root == null) {
            this.root = node;
        } else {
            this.root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.root == null) {
            System.out.println("二叉排序树为空，无法遍历！");
        } else {
            this.root.infixOrder();
        }
    }

    //查找要删除的结点
    public Node search(int value) {
        if (this.root == null) {
            return null;
        } else {
            return this.root.search(value);
        }
    }

    public Node search(int value,Node virtualRoot) {
        if (virtualRoot == null) {
            return null;
        } else {
            return virtualRoot.search(value);
        }
    }

    //查找要删除结点的父结点
    public Node searchParent(int value) {
        if (this.root == null) {
            return null;
        } else {
            return this.root.searchParent(value);
        }
    }

    public Node searchParent(int value,Node virtualRoot) {
        if (virtualRoot == null) {
            return null;
        } else {
            return virtualRoot.searchParent(value);
        }
    }

    public void adjust(Node parent) {
        while (parent != null) {
            //如果右子树的高度-左子树的高度 > 1 则左旋转
            if (parent.rightHeight() - parent.leftHeight() > 1) {
                //如果当前结点的右子树的左子树的高度大于当前结点的右子树的右子树的高度
                //则需要先对当前结点的右子树进行右旋转
                //因为rightHeight() - leftHeight() > 1 所以this.right肯定不为null
                if (parent.right.leftHeight() > parent.right.rightHeight()) {
                    parent.right.rightRotate();
                }
                //左旋转
                parent.leftRotate();
            }
            //左子树的高度-右子树的高度 > 1 则右旋转
            else if (parent.leftHeight() - parent.rightHeight() > 1) {
                //如果当前结点的左子树的右子树的高度大于当前结点的左子树的左子树的高度
                //则需要先对当前结点的左子树进行左旋转
                //因为leftHeight() - rightHeight() > 1 所以this.left肯定不为null
                if (parent.left.rightHeight() > parent.left.leftHeight()) {
                    parent.left.leftRotate();
                }
                //右旋转
                parent.rightRotate();
            }
            parent = searchParent(parent.value);
        }
    }

    /**
     * @param node 传入的结点
     * @return 返回以node为根节点的二叉排序树的最小结点的值
     */
    public Node findRightTreeMin(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }
        return target;
    }

    /**
     * @param node 传入的结点
     * @return 返回以node为根节点的二叉排序树的最大结点的值
     */
    public Node findLeftTreeMax(Node node) {
        Node target = node;
        while (target.right != null) {
            target = target.right;
        }
        return target;
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

    public void delNode(int value) {
        if (this.root == null) {
            return;
        } else {
            //找到要删除的结点
            Node targetNode = search(value);
            //没有找到
            if (targetNode == null) {
                return;
            }
            //如果二叉树只有一个结点,而且前面又找到了要删除的结点
            //则说明根节点就是我们要删除的结点
            if (this.root.left == null && this.root.right == null) {
                this.root = null;
                return;
            }
            //找到targetNode 父结点
            Node parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                //判断targetNode是父结点的左子结点还是右子结点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
                //删除后对每个父节点进行检查是否失衡并进行调整
                adjust(parent);
                //如果targetNode有左右子树
            } else if (targetNode.left != null && targetNode.right != null) {
                //如果targetNode左子树的高度大于等于右子树的高度 则从左子树中找最大值和要删除的结点的值进行替换
                //替换完后，左子树的最大值的结点就变成了我们要删除的对象
                //而最大值在左子树的位置要么是叶子结点，要么是没有右子树的一个结点
                //则只需再按照删除叶子结点或者只有一棵子树的结点的方法删除即可
                if (targetNode.leftHeight() - targetNode.rightHeight() >= 0) {
                    Node LeftMaxNode = findLeftTreeMax(targetNode.left);
                    int temp = LeftMaxNode.value;
                    LeftMaxNode.value = targetNode.value;
                    targetNode.value = temp;
                    delNode(LeftMaxNode.value,targetNode.left);
                }else {
                    //如果targetNode右子树的高度大于左子树的高度 则从右子树中找最小值和要删除的结点的值进行替换
                    //替换完后，右子树的最小值的结点就变成了我们要删除的对象
                    //而最小值在左子树的位置要么是叶子结点，要么是没有左子树的一个结点
                    //则只需再按照删除叶子结点或者只有一棵子树的结点的方法删除即可
                    Node rightMaxNode = findRightTreeMin(targetNode);
                    int temp = rightMaxNode.value;
                    rightMaxNode.value = targetNode.value;
                    targetNode.value = temp;
                    delNode(rightMaxNode.value,targetNode.right);
                }
            } else { //只有一个子结点
                //如果targetNode有左子结点
                if (targetNode.left != null) {//需要考虑删除的结点是不是根结点
                    if (parent != null) {//当要删除的结点不是根结点
                        //如果targetNode是parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else {//targetNode是parent的右子结点
                            parent.right = targetNode.left;
                        }
                    } else {//要删除的结点是根结点 删除后不需要进行调整
                        this.root = targetNode.left;
                    }
                } else { //targetNode有右子结点
                    if (parent != null) {//要删除的结点不是根结点
                        //targetNode 是 parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else {//targetNode是parent的右子结点
                            parent.right = targetNode.right;
                        }
                    } else {//要删除的结点是根结点
                        this.root = targetNode.right;
                    }
                }
                //删除后对每个父节点进行检查是否失衡并进行调整
                adjust(parent);
            }
        }
    }

    //重载删除方法，用于将删除有左子树和右子树的结点转化成删除叶子节点或单子树结点
    public void delNode(int value,Node virtualRoot) {
        if (virtualRoot == null) {
            return;
        } else {
            //找到要删除的结点
            Node targetNode = search(value,virtualRoot);
            //没有找到
            if (targetNode == null) {
                return;
            }
            //如果二叉树只有一个结点,而且前面又找到了要删除的结点
            //则说明根节点就是我们要删除的结点
            if (virtualRoot.left == null && virtualRoot.right == null) {
                virtualRoot = null;
                return;
            }
            //找到targetNode 父结点
            Node parent = searchParent(value,virtualRoot);
            //如果要删除的结点是叶子结点
            if (targetNode.left == null && targetNode.right == null) {
                //判断targetNode是父结点的左子结点还是右子结点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
                //删除后对每个父节点进行检查是否失衡并进行调整
                adjust(parent);
                //如果targetNode有左右子树
            } else if (targetNode.left != null && targetNode.right != null) {
                //如果targetNode左子树的高度大于等于右子树的高度 则从左子树中找最大值和要删除的结点的值进行替换
                //替换完后，左子树的最大值的结点就变成了我们要删除的对象
                //而最大值在左子树的位置要么是叶子结点，要么是没有右子树的一个结点
                //则只需再按照删除叶子结点或者只有一棵子树的结点的方法删除即可
                if (targetNode.leftHeight() - targetNode.rightHeight() >= 0) {
                    Node LeftMaxNode = findLeftTreeMax(targetNode.left);
                    int temp = LeftMaxNode.value;
                    LeftMaxNode.value = targetNode.value;
                    targetNode.value = temp;
                    delNode(LeftMaxNode.value,virtualRoot);
                }else {
                    //如果targetNode右子树的高度大于左子树的高度 则从右子树中找最小值和要删除的结点的值进行替换
                    //替换完后，右子树的最小值的结点就变成了我们要删除的对象
                    //而最小值在左子树的位置要么是叶子结点，要么是没有左子树的一个结点
                    //则只需再按照删除叶子结点或者只有一棵子树的结点的方法删除即可
                    Node rightMaxNode = findRightTreeMin(targetNode);
                    int temp = rightMaxNode.value;
                    rightMaxNode.value = targetNode.value;
                    targetNode.value = temp;
                    delNode(rightMaxNode.value,virtualRoot);
                }
            } else { //只有一个子结点
                //如果targetNode有左子结点
                if (targetNode.left != null) {//需要考虑删除的结点是不是根结点
                    if (parent != null) {//当要删除的结点不是根结点
                        //如果targetNode是parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.left;
                        } else {//targetNode是parent的右子结点
                            parent.right = targetNode.left;
                        }
                    } else {//要删除的结点是根结点 删除后不需要进行调整
                        virtualRoot = targetNode.left;
                    }
                } else { //targetNode有右子结点
                    if (parent != null) {//要删除的结点不是根结点
                        //targetNode 是 parent的左子结点
                        if (parent.left.value == value) {
                            parent.left = targetNode.right;
                        } else {//targetNode是parent的右子结点
                            parent.right = targetNode.right;
                        }
                    } else {//要删除的结点是根结点
                        virtualRoot = targetNode.right;
                    }
                }
                //删除后对每个父节点进行检查是否失衡并进行调整
                adjust(parent);
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

    //左旋转
    public void leftRotate() {
        //创建一个新结点 将当前结点的值赋值给它
        Node newNode = new Node(this.value);
        //将新结点的左子树设置成当前结点的左子树
        newNode.left = this.left;
        //将新节点的右子树设置为当前结点的右子结点的左子树
        newNode.right = this.right.left;
        //把当前结点的值换成右子结点的值
        this.value = this.right.value;
        //把当前结点的右子树设置成当前结点的右子结点的右子树
        this.right = this.right.right;
        //把当前结点的左子结点设置成新结点
        this.left = newNode;
    }

    //右旋转
    public void rightRotate() {
        //创建一个新结点，把当前结点的值赋值给新结点
        Node newNode = new Node(this.value);
        //将新结点的右子树设置成当前结点的右子树
        newNode.right = this.right;
        //将新结点的左子树设置成当前结点的左子结点的右子树
        newNode.left = this.left.right;
        //将当前结点的值设置成当前结点左子结点的值
        this.value = this.left.value;
        //将当前结点的右子结点设置成新结点
        this.right = newNode;
        //将当前结点的左子结点设置成左子结点的左子树
        this.left = this.left.left;
    }

    //返回以当前结点为根结点的树的高度
    public int height() {
        //返回左子树和右子树中较大的高度再加上当前结点的高度
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    //返回左子树的高度
    public int leftHeight() {
        if (this.left == null) {
            return 0;
        }
        return this.left.height();
    }

    //返回右子树的高度
    public int rightHeight() {
        if (this.right == null) {
            return 0;
        }
        return this.right.height();
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
            } else {
                //递归向左子树添加
                this.left.add(node);
            }
        } else if (node.value > this.value) {//要添加的结点的值大于当前结点的值
            //当前结点的右子结点为空
            if (this.right == null) {
                this.right = node;
            } else {
                //递归向右子树添加
                this.right.add(node);
            }
        }

        //当添加完一个结点后，如果右子树的高度-左子树的高度 > 1 则左旋转
        if (rightHeight() - leftHeight() > 1) {
            //如果当前结点的右子树的左子树的高度大于当前结点的右子树的右子树的高度
            //则需要先对当前结点的右子树进行右旋转
            //因为rightHeight() - leftHeight() > 1 所以this.right肯定不为null
            if (this.right.leftHeight() > this.right.rightHeight()) {
                this.right.rightRotate();
            }
            //左旋转
            leftRotate();
            //一定要return！因为旋转完后已经平衡，
            //如果还让下面的进行判断，很有可能出问题
            return;
        }
        //左子树的高度-右子树的高度 > 1 则右旋转
        if (leftHeight() - rightHeight() > 1) {
            //如果当前结点的左子树的右子树的高度大于当前结点的左子树的左子树的高度
            //则需要先对当前结点的左子树进行左旋转
            //因为leftHeight() - rightHeight() > 1 所以this.left肯定不为null
            if (this.left.rightHeight() > this.left.leftHeight()) {
                this.left.leftRotate();
            }
            //右旋转
            rightRotate();
            return;
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
        if (value == this.value) {
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
        if ((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            //如果查找的这个值小于当前结点的值
            //且当前结点的左子结点不为空
            if (value < this.value && this.left != null) {
                //向左子树递归查找
                return this.left.searchParent(value);
            } else if (value > this.value && this.right != null) {
                //向右子树
                return this.right.searchParent(value);
            }
        }
        //没有找到
        return null;
    }
}