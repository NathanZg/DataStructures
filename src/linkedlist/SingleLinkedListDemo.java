package linkedlist;

import java.util.Stack;

/**
 * ClassName: SingleLinkedList
 * Description:
 * date: 2022/4/19 10:54
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        Node node1 = new Node(1, "小明", "dd");
        Node node2 = new Node(2, "大名", "ss");
        Node node3 = new Node(3, "大sfg", "cc");
        Node node4 = new Node(4, "sdfsd", "dd");

        Node node5 = new Node(5, "sdfgsdfd", "ss");
        Node node6 = new Node(6, "sdfsdfsdfsdsd", "cc");
        Node node7 = new Node(7, "sdfsddfgdffsdfsdsd", "cdfgdfgc");
        Node node8 = new Node(8, "sdfsddfgdffsdsdfsdfsdsd", "cdsdfsfgdfgc");

        System.out.println("添加......");
        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.addByOrder(node1);
        linkedList.addByOrder(node3);
        linkedList.addByOrder(node2);
        linkedList.list();


        System.out.println("修改.........");
        linkedList.update(new Node(3, "dsb", "xsb"));
        linkedList.list();
        System.out.println("删除.........");
        linkedList.delete(1);
        linkedList.list();

        System.out.println("长度:");
        System.out.println(SingleLinkedList.getLength(linkedList.getHead()));

        System.out.println("找倒数第2个:");
        System.out.println(SingleLinkedList.findLastIndexNode(linkedList.getHead(),2));

        System.out.println("反转：");
        linkedList.add(new Node(4, "asdas","asdf"));
        SingleLinkedList.reverse(linkedList.getHead());
        linkedList.list();

        System.out.println("逆序输出");
        SingleLinkedList.reversePrint(linkedList.getHead());

        System.out.println("合并：");
        SingleLinkedList A = new SingleLinkedList();
        A.addByOrder(node1);
        A.addByOrder(node2);
        A.addByOrder(node3);
        A.addByOrder(node4);
        System.out.println("A:");
        A.list();

        SingleLinkedList B = new SingleLinkedList();
        B.addByOrder(node5);
        B.addByOrder(node6);
        B.addByOrder(node7);
        B.addByOrder(node8);
        System.out.println("B:");
        B.list();

       SingleLinkedList C = new SingleLinkedList();
       SingleLinkedList.merge(A.getHead(), B.getHead(), C.getHead());
        System.out.println("C:");
       C.list();

    }
}

//定义SingleLinkedList
class SingleLinkedList{
     //初始化头结点，不可以动
    private Node head = new Node(0, "", "");

    public void setHead(Node head) {
        this.head = head;
    }

    //添加节点到单向链表
    //不考虑编号顺序
    public void add(Node node){
        //头节点不能动，需要一个辅助的遍历节点
        Node temp = head;
        while (true) {
            if(temp.next == null) {
                break;
            }
            temp = temp.next;//后移
        }
        //退出循环的时候，temp指向了链表的最后
        temp.next = node;
    }

    //根据no大小插入到相应位置
    public void addByOrder(Node node){
        //头结点不能动，使用辅助指针
        Node temp = head;
        boolean flag = false;//判断添加的标号是否存在
        while (true) {
            if(temp.next == null){
                break;
            }
            if(temp.next.no > node.no){//位置找到了
                break;
            }else if(temp.next.no == node.no){//已经存在
                flag = true;
            }
            temp = temp.next;//后移
        }
        if(flag){//不能添加
            System.out.printf("编号为%d的英雄已经存在了，不可加入！\n",node.no);
        }else{
            //将新节点插入到对应位置
            node.next = temp.next;
            temp.next = node;
        }
    }

    //遍历显示链表
    public void list(){
        if(head.next==null){
            System.out.println("链表为空！");
            return;
        }
        Node temp = head.next;
        while(true){
            if(temp == null){
                return;
            }
            //输出节点信息
            System.out.println(temp);
            temp = temp.next;//后移
        }
    }

    public Node getHead(){
        return head;
    }

    //根据node的no进行修改节点
    public void update(Node node){
        if (head.next == null){
            System.out.println("链表为空！");
            return;
        }
        //找到需要修改的节点
        //定义辅助变量
        boolean flag = false;//判断是否找到
        Node temp = head.next;
        while (true){
            if(temp == null){//遍历完成
                break;
            }
            if(temp.no == node.no){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if(flag){
            temp.name = node.name;
            temp.nickName = node.nickName;
        }else{
            System.out.printf("找不到需要更新的节点！\n");
        }
    }

    //删除
    public void delete(int no){
        Node temp = head;
        boolean flag = false;//是否找到待删除节点的前一个节点
        while(true){
            if(temp.next == null){//遍历完成
                break;
            }
            if(temp.next.no == no){//找到待删除节点的前一个节点
                flag = true;
                break;
            }
        }
        temp = temp.next;//后移
        if(flag){//找到
            temp.next = temp.next.next;
        }else{
            System.out.printf("编号为%d的节点不存在\n",no);
        }
    }


    /**
     * @param head 链表的头节点
     * @return 有效节点的个数
     */
    public static int getLength(Node head){
        if(head.next == null){//空链表
            return 0;
        }
        int length = 0;
        Node temp = head.next;
        while (true){
            if(temp == null){//遍历完成
                break;
            }
            length++;
            temp = temp.next;
        }
        return length;
    }

    //查找倒数第index个节点 （新浪）
    public static Node findLastIndexNode(Node head,int index){
        if(head == null){//空链表
            return null;
        }
        int size = getLength(head);//得到长度
        //遍历到size-index
        if(index <= 0 || index > size){
            return null;
        }
        int cnt = 0;
        Node temp = head.next;
        while(true){
            if(cnt == (size-index)){
                return temp;
            }
            cnt++;
            temp = temp.next;
        }
    }

    //反转 （腾讯）
    public static void reverse(Node head) {
        //空链表和只有一个节点则直接返回
        if(head == null || head.next.next == null){
            return;
        }
        //辅助指针
        Node cur = head.next;
        Node next = null;//指向当前节点的下一个节点
        Node reverseHead = new Node(0, "", "");
        //从头遍历链表
        //每遍历一个节点，利用头插法插入reverseHead
        while (cur != null){
            next = cur.next;//保存当前节点的下一个节点
            cur.next = reverseHead.next;//让cur下一个节点指向新链表reverseHead的最前端
            reverseHead.next = cur;//让新链表的头节点指向cur
            cur = next;//cur后移
        }
        head.next = reverseHead.next;//让原来的头节点指向新链表的头节点的下一个节点
    }

    //倒叙输出 （百度）
    public static void reversePrint(Node head){
        if(head.next == null){//空链表
            return;
        }
        //创建一个栈
        Stack<Node> stack = new Stack<>();
        Node cur = head.next;
        //将个节点压入栈
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }

    //合并两个有序单链表，合并后仍然有序
    public static void merge(Node headA,Node headB,Node headC) {
        if(headA.next == null && headB.next == null){
            return;
        }
        if(headA.next == null){
            headC.next = headB.next;
        }else if(headB.next == null){
            headC.next = headA.next;
        }else{
            Node curA = headA.next;
            Node curB = headB.next;
            Node nextA = null;
            Node nextB = null;
            Node tail = headC;
            while(curA != null && curB != null){
                if(curA.no <= curB.no){
                    nextA = curA.next;
                    curA.next = tail.next;
                    tail.next = curA;
                    tail = curA;
                    curA = nextA;
                }else{
                    nextB = curB.next;
                    curB.next = tail.next;
                    tail.next = curB;
                    tail = curB;
                    curB = nextB;
                }
            }
            if(curA != null){
                tail.next = curA;
            }
            if(curB != null){
                tail.next = curB;
            }
        }
    }

}

//定义Node 每一个Node对象就是一个节点
class Node{

    public int no;

    public String name;

    public String nickName;

    public Node next;

    public Node(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\''  + '}';
    }
}