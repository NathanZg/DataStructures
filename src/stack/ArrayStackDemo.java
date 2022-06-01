package stack;

import java.util.Scanner;

/**
 * ClassName: ArrayStackDemo
 * Description:
 * date: 2022/5/31 22:08
 *数组模拟栈
 * @author Ekertree
 * @since JDK 1.8
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(4);
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
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数：");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int pop = stack.pop();
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

class ArrayStack{
    private int maxSize;//栈的大小
    private int[] stack;//数组模拟栈
    private int top = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    public boolean isFull(){
        return top == maxSize-1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    //入栈
    public void push(int value){
        //先判断是否栈满
        if(isFull()){
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    //出栈
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈空，无数据！");
        }
        int value = stack[top];
        top--;
        return value;
    }

    //遍历栈 需要从栈顶开始显示数据
    public void list(){
        if(isEmpty()){
            System.out.println("没有数据！");
            return;
        }
        for(int i = top;i >= 0;i--){
            System.out.println("stack["+i+"]"+"="+stack[i]);
        }
    }
}
