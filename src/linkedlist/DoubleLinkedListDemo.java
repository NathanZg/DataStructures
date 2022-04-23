package linkedlist;

import java.net.URLEncoder;
import java.sql.SQLOutput;

/**
 * ClassName: DoubleLinkedListDemo
 * Description:
 * date: 2022/4/23 20:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {

        Node2 n1 = new Node2(1, "红猫", "redcat");
        Node2 n2 = new Node2(2, "蓝猫", "bluecat");
        Node2 n3 = new Node2(3, "黄猫", "yellowcat");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

        System.out.println("添加：");
        doubleLinkedList.add(n1);
        doubleLinkedList.add(n2);
        doubleLinkedList.add(n3);
        doubleLinkedList.list();

        System.out.println("修改：");
        System.out.println("修改前:");
        doubleLinkedList.list();
        Node2 n4 = new Node2(3, "粉红猫", "pinkcat");
        doubleLinkedList.update(n4);
        System.out.println("修改后：");
        doubleLinkedList.list();

        System.out.println("删除：");
        System.out.println("删除前：");
        doubleLinkedList.list();
        System.out.println("删除后：");
        doubleLinkedList.delete(2);
        doubleLinkedList.list();

        System.out.println("有序插入：");
        System.out.println("插入前：");
        doubleLinkedList.list();
        System.out.println("插入后：");
        Node2 n5 = new Node2(7, "红猫", "redcat");
        Node2 n6 = new Node2(5, "蓝猫", "bluecat");
        Node2 n7 = new Node2(4, "黄猫", "yellowcat");
        doubleLinkedList.addByOrder(n5);
        doubleLinkedList.addByOrder(n6);
        doubleLinkedList.addByOrder(n7);
        doubleLinkedList.list();

    }
}

class DoubleLinkedList {

    //初始化头结点
    private Node2 head = new Node2(0, "", "");

    public Node2 getHead() {
        return head;
    }

    public void setHead(Node2 head) {
        this.head = head;
    }

    //遍历显示链表
    public void list(){
        if(head.next==null){
            System.out.println("链表为空！");
            return;
        }
        Node2 temp = head.next;
        while(true){
            if(temp == null){
                return;
            }
            //输出节点信息
            System.out.println(temp);
            temp = temp.next;//后移
        }
    }

    //添加节点到双向链表
    //不考虑编号顺序
    public void add(Node2 node){
        //头节点不能动，需要一个辅助的遍历节点
        Node2 temp = head;
        while (true) {
            if(temp.next == null) {
                break;
            }
            temp = temp.next;//后移
        }
        //退出循环的时候，temp指向了链表的最后
        temp.next = node;
        node.pre = temp;
    }

    //根据no大小插入到相应位置
    public void addByOrder(Node2 node){
        //头结点不能动，使用辅助指针
        Node2 temp = head;
        boolean flag = false;//判断添加的标号是否存在
        while (true) {
            if(temp.next == null){
                break;
            }
            if(temp.next.no > node.no){//位置找到了
                break;
            }else if(temp.next.no == node.no){//已经存在
                flag = true;
                break;
            }
            temp = temp.next;//后移
        }
        if(flag){//不能添加
            System.out.printf("编号为%d的英雄已经存在了，不可加入！\n",node.no);
        }else{
            //将新节点插入到对应位置
            node.pre = temp;//新加入节点的前驱
            node.next = temp.next;//新加入节点的后继
            temp.next = node;//比新加入节点no大的节点的前一个节点的后继
            //当插入位置后还有节点才执行
            if(temp.next != null)
            temp.next.pre = node;//比新加入节点no大的节点的前驱
        }
    }

    //修改一个节点的内容
    public void update(Node2 node){
        if (head.next == null){
            System.out.println("链表为空！");
            return;
        }
        //找到需要修改的节点
        //定义辅助变量
        boolean flag = false;//判断是否找到
        Node2 temp = head.next;
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
    //双向链表可以直接找到要删除的节点自我删除
    public void delete(int no){
        //判断当前链表是否为空
        if(head.next == null){
            System.out.println("链表为空，无法删除");
            return;
        }
        Node2 temp = head.next;
        boolean flag = false;//是否找到待删除节点
        while(true){
            if(temp == null){//遍历完成
                break;
            }
            if(temp.no == no){//找到待删除节点
                flag = true;
                break;
            }
            temp = temp.next;//后移
        }
        if(flag){//找到
            temp.pre.next = temp.next;
            //如果是最后一个节点，不需要执行这一句话
            if(temp.next != null)
                temp.next.pre = temp.pre;
        }else{
            System.out.printf("编号为%d的节点不存在\n",no);
        }
    }
}

class Node2{

    public int no;

    public String name;

    public String nickName;

    public Node2 next;//指向下一个节点

    public Node2 pre;//指向前一个节点

    public Node2(int no, String name, String nickName) {
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