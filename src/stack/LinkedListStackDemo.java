package stack;

import java.util.Scanner;

/**
 * ClassName: LinkedListStackDemo
 * Description:
 * date: 2022/5/31 22:42
 *使用链表模拟栈
 * @author Ekertree
 * @since JDK 1.8
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        LinkedListStack s = new LinkedListStack();
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while(loop){
            System.out.println("show:显示栈");
            System.out.println("push:入栈");
            System.out.println("pop:出栈");
            System.out.println("exit:退出程序");
            System.out.println("输入的你选择：");
            key = scanner.next();
            switch (key){
                case "show":
                    s.list();
                    break;
                case "push":
                    System.out.println("请输入一个数：");
                    int value = scanner.nextInt();
                    s.push(value);
                    break;
                case "pop":
                    try {
                        int pop = s.pop();
                        System.out.println("出栈数据为："+ pop);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
}

class Node{
    private int value;
    private Node next;

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

class LinkedListStack{

    private Node head;

    public LinkedListStack() {
        this.head = new Node(-1, null);
    }

    public boolean isEmpty(){
        return head.getNext() == null;
    }

    public Node getHead() {
        return head;
    }

    //入栈
    public void push(int value){
        Node node = new Node(value, head.getNext());
        head.setNext(node);
    }

    //出栈
    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("栈空，没有数据！");
        }
        int v = head.getNext().getValue();
        Node p = head.getNext();
        head.setNext(p.getNext());
        return v;
    }

    public void list(){
        if(isEmpty()){
            System.out.println("没有数据！");
            return;
        }
        Node help = head.getNext();
        int i = 1;
        while (help != null) {
            System.out.println(i + ":" + help.getValue());
            i++;
            help = help.getNext();
        }
    }
}
