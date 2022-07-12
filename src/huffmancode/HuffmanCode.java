package huffmancode;
import java.io.*;
import java.util.*;

/**
 * ClassName: HuffmanCode
 * Description:
 * date: 2022/7/12 15:25
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i love java!";
        byte[] contentsBytes = content.getBytes();
        byte[] bytes = huffmanZip(contentsBytes);
        String srcFile = "D:\\1. 尚硅谷_Shiro_简介.avi";
        String dstFile = "D:\\dst.zip";
        //zipFile(srcFile, dstFile);
        unzipFile(dstFile, "D:\\copy2.avi");
        System.out.println("ok!");
    }

    /**
     *
     * @param bytes 接收的字节数组
     * @return
     */
    public static List<Node> getNodes(byte[] bytes) {
        //创建一个array list
        List<Node> nodes = new ArrayList<>();
        //遍历bytes 统计每个bytes出现的次数
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte aByte : bytes) {
            Integer count = counts.get(aByte);
            if (count == null){//map中没有这个数据
                counts.put(aByte, 1);
            }else{
                counts.put(aByte,  count + 1);
            }
        }
        //把每个键值对转换成一个Node对象，并加入到nodes集合
        for(Map.Entry<Byte,Integer> entry : counts.entrySet()){
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    public static Node createHuffmanTree(List<Node> nodes){
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node parent = new Node(null,leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    //前序遍历
    public static void preOrder(Node root) {
        if (root != null) {
            System.out.println(root);
        }else{
            System.out.println("树为空！");
        }
        if (root.left != null) {
            root.left.preOrder();
        }
        if (root.right != null) {
            root.right.preOrder();
        }
    }

    //生成赫夫曼树对应的赫夫曼编码
    //将赫夫曼编码存放在Map<Byte,String>中
    static Map<Byte,String> huffmanCodes =  new HashMap<>();
    //用stringBuilder存储叶子结点的路径
    static StringBuilder stringBuilder = new StringBuilder();
    //用于记录最后一个字节有多少位
    static int lastBit;
    /**
     * 将传入的node结点的所有叶子结点的赫夫曼编码得到 ，并存放到huffmanCodes集合中
     * @param node 传入的结点
     * @param code 路径，左子结点是0，右子结点是1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(Node node,String code,StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(code);
        if (node != null) {
            //判断当前结点是不是叶子结点
            if (node.data == null) {
                //向左递归
                getCodes(node.left, "0", stringBuilder2);
                getCodes(node.right, "1", stringBuilder2);
            }else { //叶子结点
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }

    public static Map<Byte,String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        //左子树
        getCodes(root.left, "0", stringBuilder);
        //右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }


    /**
     *
     * @param bytes 字符串对应的byte数组
     * @param huffmanCodes 哈夫曼编码集合
     * @return 返回哈夫曼编码处理后的哈夫曼编码
     */
    public static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes) {
        //需要一个stringBuilder记录压缩的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes数组
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(huffmanCodes.get(bytes[i]));
        }
        //统计长度
        //如果length能被8整除，加上的7则会被舍掉，如果不能被8整除
        //原来的余数加上7肯定能凑够一个8，则len还会加1
        int len = (stringBuilder.length() + 7) / 8;
        //创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录是第几个byte
        for (int i = 0;i < stringBuilder.length();i += 8){
            String strByte;
            if (i + 8 > stringBuilder.length()) {//不够八位，肯定是最后一个字节
                strByte = stringBuilder.substring(i);
                //记录最后一个字节的位数
                lastBit = stringBuilder.length() - i;
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            //将strByte转成byte放入huffmanCodeBytes
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte,2);
            index++;
        }

        return huffmanCodeBytes;
    }

    /**
     * 封装便于调用
     * @param bytes 原始的数组
     * @return
     */
    public static byte[] huffmanZip(byte[] bytes) {
        //得到结点
        List<Node> nodes = getNodes(bytes);
        //根据拿到的结点创建哈夫曼树
        Node huffmanTree = createHuffmanTree(nodes);
        //生成对应的哈夫曼编码
        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        //根据哈夫曼编码对原字节数组进行压缩
        return zip(bytes, huffmanCodes);
    }

    /**
     * 将一个byte 转换成一个二进制字符串
     * @param b 要转换的字节
     * @param flag true则需要补高位，反之不用
     * @param lastBit 最后一个字节的位数 用于应对最后一个字节的特殊情况
     * @return b对应的二进制字符串 是以补码返回
     */
    public static String byteToBinString(byte b,boolean flag,int lastBit) {
        int temp = b;
        //所有字节都与256按位与
        //会得到八位
        temp |=256;
        //String str = Integer.toBinaryString(-88); 返回11111111111111111111111110101000
        String str = Integer.toBinaryString(temp);
        /*
            最后一个字节的特殊情况：
            1.正数，但是不足八位，Integer.toBinaryString()会将该正数前面的0去掉
            2.负数，会返回三十二位的值，前面多了24位1，因为Integer对应的int是32位
         */
        //最后一个字节 或者小于0 或者 整除 则取八位
        if (flag || temp < 0 || lastBit == 0){
            return str.substring(str.length()-8);
        }else {//是最后一个但不是负数，则按记录的位数取
            return str.substring(str.length()-lastBit);
        }
    }


    /**
     * 解码压缩的数据
     * @param huffmanCodes 哈夫曼编码表
     * @param huffmanBytes 哈夫曼编码后的字节数组
     * @return 解码后的字节数组
     */
    public static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes) {
        //builder用于拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            /*
            压缩的时候，在zip方法中，根据i+8是否大于
            数组长度来判度是取八位
            还是剩下有多少取多少
             */
            //判断是不是最后一个字节
            //如果是最后一个字节，分情况讨论
            //情况注解写在byteToBinString方法中
            boolean flag = (i != huffmanBytes.length-1);
            stringBuilder.append(byteToBinString(huffmanBytes[i],flag,lastBit));
        }
        //将编码表翻转生成哈夫曼解码表
        Map<String,Byte> map = new HashMap<>();
        for (Map.Entry<Byte,String> entry : huffmanCodes.entrySet()){
            map.put(entry.getValue(), entry.getKey());
        }
        //按照指定的哈夫曼解码表进行解码
        //创建一个集合 存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length();) {//这里不能写i++，i的位置已经由count来决定！
            int count = 1;//记录匹配到的字符的最后一个二进制位的下标
            boolean flag = true;
            Byte b = null;
            while (flag) {
                //取出一个'1' 或 '0'
                //i 不动  count移动 直到匹配到一个字符
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {//没有匹配到
                    count++;
                }else{//匹配到了
                    flag = false;
                }
            }
            list.add(b);//将匹配到的字符加入集合
            i += count;//移动count位
        }
        //将list装入byte数组中
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }

    /**
     *压缩文件
     * @param srcFile 源文件全局路径
     * @param dstFile 目标文件的全局路径
     */
    public static void zipFile(String srcFile,String dstFile){
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream oos = null;//使用对象流能直接将字节数组写出
        try {
            //创建输入流
            fis = new FileInputStream(srcFile);
            bis = new BufferedInputStream(fis);
            //创建输出流
            fos = new FileOutputStream(dstFile);
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            byte[] buff = new byte[fis.available()];
            bis.read(buff);
            //压缩
            byte[] huffmanBytes = huffmanZip(buff);
            //输出
            //将哈夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //以对象流的方式写入哈夫曼的编码，为了在恢复文件时使用
            oos.writeObject(huffmanCodes);
            //以对象流的方式写入最后一个字节的位数
            oos.writeObject(lastBit);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bis != null)
                    bis.close();
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void unzipFile(String zipFile,String dstFile) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ObjectInputStream ois = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fis = new FileInputStream(zipFile);
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            fos = new FileOutputStream(dstFile);
            bos = new BufferedOutputStream(fos);
            //读取byte数组 huffmanBytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //读取哈夫曼编码表
            huffmanCodes = (Map<Byte, String>) ois.readObject();
            //读取最后一个字节的位数
            lastBit = (int) ois.readObject();
            //解码
            byte[] decode = decode(huffmanCodes, huffmanBytes);
            //将decode的byte数组写入文件
            bos.write(decode);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }finally {
                try {
                    if (bos != null)
                        bos.close();
                    if (ois != null)
                        ois.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
    }
}

class Node implements Comparable<Node>{
    //注意！是Byte 不是byte
    Byte data;//存放数据
    int weight;//权值，表示字符出现的次数
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}
