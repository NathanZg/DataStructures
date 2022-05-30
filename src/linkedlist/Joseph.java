package linkedlist;

/**
 * ClassName: Joseph
 * Description:
 * date: 2022/5/30 12:31
 *使用单向循环链表解决约瑟夫问题
 * @author Ekertree
 * @since JDK 1.8
 */
public class Joseph {
    public static void main(String[] args) {
        CircleSingleLinkedList list = new CircleSingleLinkedList();
        list.addBoy(125);
        list.showBoy();

        list.countBoy(10,20,25);
    }
}


class Boy{
    private int no;//编号
    private Boy next;//指向下一个节点，默认为空

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}

//环形单向链表
class CircleSingleLinkedList {
    //创建一个first结点
    private Boy first = null;

    //添加小孩结点，构建成一个环形链表
    public void addBoy(int num) {
        //数据校验
        if (num < 1) {
            System.out.println("个数小于1！");
            return;
        }
        Boy curBoy = null;//复制指针，帮助构建环形链表
        //for循环创建环形链表
        for (int i = 1; i <= num; i++) {
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first);//构成环
                curBoy = first;//指向第一个小孩
            } else {
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    //遍历当前环形链表
    public void showBoy(){
        //判断链表是否为空
        if(first == null){
            System.out.println("链表为空！");
            return;
        }
        Boy curBoy = first;//辅助遍历指针
        while (true){
            System.out.println("小孩的编号是"+curBoy.getNo());
            if(curBoy.getNext() == first){//遍历完毕
                break;
            }else{
                curBoy = curBoy.getNext();//后移
            }
        }
    }

    /**
     *
     * @param startNo 从第几个小孩开始数
     * @param cnt 表示数几下
     * @param nums 表示多少小孩在圈中
     */
    public void countBoy(int startNo,int cnt,int nums){
        //数据校验
        if(first == null || startNo < 1 || startNo > nums){
            System.out.println("参数输入有误！");
            return;
        }
        Boy helps = first;//辅助指针 应指向最后一个结点
        while (true){
            if(helps.getNext() == first){
                break;
            }else{
                helps = helps.getNext();
            }
        }
        //移动到起始报数的位置
        for (int i = 0; i < startNo-1; i++) {
            first = first.getNext();
            helps = helps.getNext();
        }
        //开始报数 first helps 移动 cnt-1 次 出圈
        //循环直到圈中只有一个人
        while(true){
            if(helps == first){//圈中只有一个结点
                break;
            }
            //让first helps 同时移动 cnt-1次
            for (int i = 0; i < cnt-1; i++) {
                first = first.getNext();
                helps = helps.getNext();
            }
            //这时first指向的结点就是需要出圈的小孩
            System.out.println("小孩"+first.getNo()+"出圈");
            //出圈
            first = first.getNext();
            helps.setNext(first);
        }
        System.out.println("最后留在圈中的小孩编号为"+first.getNo());
    }
}