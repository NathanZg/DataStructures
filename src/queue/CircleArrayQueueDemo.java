package queue;

import java.util.Scanner;

/**
 * ClassName: CircleArrayQueue
 * Description:
 * date: 2022/4/18 17:13
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        //创建队列
        CirclArrayQueue queue = new CirclArrayQueue(4);
        char key = ' ';//接受用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s:显示队列");
            System.out.println("e:退出程序");
            System.out.println("a:添加数据到队列");
            System.out.println("g:从队列取出数据");
            System.out.println("h:查看队列头部的数据");
            key = scanner.next().charAt(0);//接受一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数：");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int result = queue.getQueue();
                        System.out.println("取出的数据是" + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.println("队列头的数据是：" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序已退出!");
    }
    }

    class CirclArrayQueue {

        private int maxSize;//数组最大容量

        private int front;//指向队列头

        private int rear;//指向队列尾

        private int[] arr;//队列数组

        public CirclArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            this.arr = new int[this.maxSize];
            this.front = 0;//队列头数据的位置
            this.rear = 0;//指向队列尾数据的后一个
        }

        //判断队列是否为满
        public boolean isFull() {
            return (rear + 1) % maxSize == front;
        }

        //判断队列是否为空
        public boolean isEmpty() {
            return front == rear;
        }

        //添加数据到队列
        public void addQueue(int n) {
            //判断队列是否为满
            if (isFull()) {
                System.out.println("队列已满，不能加入数据");
                return;
            } else {
                arr[rear] = n;
                rear = (rear + 1) % maxSize;
            }
        }

        //获取队列的数据
        public int getQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空！");
            }
            int value = arr[front];
            front = (front + 1) % maxSize;
            return value;
        }

        //显示队列的所有数据
        public void showQueue() {
            if (isEmpty()) {
                System.out.println("队列已空，没有数据");
                return;
            }
            for (int i = front; i < front + getSize(); i++) {
                System.out.printf("arr[%d]=%d\n", i %  maxSize, arr[i % maxSize]);
            }
        }

        //显示队列的头数据
        public int headQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列为空，没有数据！");
            }
            return arr[front];
        }

        //求出当前队列有效数据个数
        public int  getSize(){
            return (rear - front + maxSize) % maxSize;
        }
    }
