package hashtable;

import java.util.Scanner;

/**
 * ClassName: HashTableDemo
 * Description:
 * date: 2022/6/26 15:22
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class HashTableDemo {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(7);
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("find:查找雇员");
            System.out.println("del:删除雇员");
            System.out.println("exit:退出");
            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id:");
                    int id = scanner.nextInt();
                    System.out.println("输入姓名:");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTable.add(emp);
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "find":
                    System.out.println("请输入雇员id：");
                    id = scanner.nextInt();
                    hashTable.findEmpById(id);
                    break;
                case "del":
                    System.out.println("请输入雇员id：");
                    id = scanner.nextInt();
                    hashTable.deleteEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}

class HashTable{
    private EmpLinkedList[] empLinkedListArray;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.empLinkedListArray = new EmpLinkedList[size];
        //初始化每一个链表
        for (int i = 0; i < empLinkedListArray.length; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    //hash
    public int hash(int id) {
        return id % size;
    }

    //添加雇员
    public void add(Emp emp) {
        //得到hash值
        int hash = hash(emp.id);
        //将emp添加到对应链表
        empLinkedListArray[hash].add(emp);
    }

    //遍历hash表
    public void list() {
        for (int i = 0; i < empLinkedListArray.length; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    //根据id查找雇员
    public void findEmpById(int id) {
        int hash = hash(id);
        Emp emp = empLinkedListArray[hash].findEmpById(id);
        if (emp != null) {
            System.out.print("在第"+hash+"个链表中找到雇员");
            System.out.println(emp);
        }else{
            System.out.println("没有找到对应雇员！");
        }
    }

    //根据雇员id删除雇员
    public void deleteEmpById(int id) {
        int hash = hash(id);
        empLinkedListArray[hash].deleteEmpById(id);
    }
}

//雇员
class Emp {
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

//链表
class EmpLinkedList {
    //头指针直接指向第一个雇员
    private Emp head;

    //添加雇员
    public void add(Emp emp) {
        //添加的是第一个雇员
        if(head == null) {
            head = emp;
            return;
        }
        //不是第一个雇员.使用辅助指针定位
        Emp curEmp = emp;
        while (true) {
            //到达链表末尾
            if (curEmp.next == null) {
                break;
            }
            curEmp.next = curEmp.next;
        }
        curEmp.next = emp;
    }

    //遍历雇员信息
    public void list(int i) {
        if (head == null) {
            System.out.println("第"+i+"个链表为空");
            return;
        }
        System.out.println("第"+i+"个链表的信息为：");
        Emp curEmp = head;
        while (true) {
            System.out.print("-->");
            System.out.print(curEmp);
            System.out.print("-->");
            //到达链表末尾
            if (curEmp.next == null) {
                System.out.println();
                break;
            }
            curEmp = curEmp.next;
        }
    }

    //根据id查找雇员
    public Emp findEmpById(int id) {
        if (head == null) {
            System.out.println("链表为空！");
            return null;
        }
        Emp curEmp = head;
        while (true) {
            //找到
            if (curEmp.id == id) {
                break;
            }
            //没有找到
            if (curEmp.next == null) {
                    curEmp = null;
                    break;
            }
        }
        return curEmp;
    }

    //根据id删除雇员
    public void deleteEmpById(int id) {
        if (head == null) {
            System.out.println("当前链表为空，没有该雇员！");
            return;
        }
        Emp curEmp = head;
        if (curEmp.id == id) {
            head = head.next;
            return;
        }
        while (true) {
            if (curEmp.next != null) {
                if (curEmp.next.id == id) {
                    curEmp.next = curEmp.next.next;
                    break;
                }else{
                    curEmp = curEmp.next;
                }
            }else{
                System.out.println("没有该雇员!");
                break;
            }
        }
    }
}
