package sparsearray;

import java.io.*;

/**
 * ClassName: SparseArray
 * Description:
 * date: 2022/4/18 11:28
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class SparseArray {

    public static void main(String[] args) {
        //创建一个原始的二维数组 11*11
        //0：无棋子 1：黑子 2：白子
        int chessArray[][] = new int[11][11];
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        chessArray[4][5] = 2;
        //输出原始二维数组
        for (int[] column : chessArray){
            for (int data : column) {
                System.out.print(data);
            }
            System.out.println();
        }
        //二维数组转化为稀疏数组
        //遍历二维数组得到非零值的个数
        int sum = 0;
        for (int i = 0; i < chessArray.length; i++) {
            for (int j = 0; j < chessArray.length; j++) {
                if (chessArray[i][j]!=0)
                    sum++;
            }
        }
        //创建一个稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArray[0][0] = chessArray.length;
        sparseArray[0][1] = chessArray.length;
        sparseArray[0][2] = sum;
        //遍历二维数组，将非零的值存入稀疏数组
        int count = 0;//用于记录是第几个非0的值
        for (int i = 0; i < chessArray.length; i++) {
            for (int j = 0; j < chessArray.length; j++) {
                if (chessArray[i][j]!=0){
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArray[i][j];
                }
            }
        }
        //输出稀疏数组
        System.out.println();
        System.out.println("得到的稀疏数组为");
        for (int i = 0; i < sparseArray.length; i++) {
            System.out.printf("%d\t%d\t%d\n",sparseArray[i][0],sparseArray[i][1],sparseArray[i][2]);
        }
        //将稀疏数组恢复成原始的二维数组
        //读取稀疏数组第一行，创建二维数组
        int[][] reviewChessArray = new int[sparseArray[0][0]][sparseArray[0][1]];
        //把稀疏数组的值赋值给二维数组
        for (int i = 1; i < sparseArray.length; i++) {
            reviewChessArray[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        System.out.println();

        //输出恢复后的二维数组
        System.out.println("恢复的二维数组：");
        for (int i = 0; i < reviewChessArray.length; i++) {
            for (int j = 0; j < reviewChessArray.length; j++) {
                System.out.printf("%d\t ",reviewChessArray[i][j]);
            }
            System.out.println();
        }
        //把稀疏数组存入磁盘
        PrintStream ps = null;
        FileOutputStream fos = null;
        File file = new File("map.data");
        PrintStream defaultout = System.out;
        try {
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos, true);
            if(ps != null){
                System.setOut(ps);
            }
            for (int i = 0; i < sparseArray.length; i++) {
                System.out.printf("%d\t%d\t%d\n",sparseArray[i][0],sparseArray[i][1],sparseArray[i][2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (ps != null) {
                ps.close();
            }
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //读入稀疏数组
        System.setOut(defaultout);
        FileReader fr = null;
        String result = "";
        try {
            fr = new FileReader(file);
            char[] cbuf = new char[5];
            int len;
            while ((len = fr.read(cbuf)) != -1){
                String str = new String(cbuf, 0, len);
                String s1 = str.replaceAll("\t", " ");
                String s2 = s1.replaceAll("\n", " ");
                result = result + s2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fr != null){
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("读入的：");
        String[] s = result.split(" ");
        int[][] readSparseArray = new int[s.length/3][3];
        int k = 0;
        for (int i = 0; i < readSparseArray.length; i++) {
            for (int j = 0; j < 3; j++) {
                readSparseArray[i][j] = Integer.parseInt(s[k++]);
            }
        }
        for (int i = 0; i < readSparseArray.length; i++) {
                System.out.printf("%d\t%d\t%d\n",readSparseArray[i][0],readSparseArray[i][1],readSparseArray[i][2]);
        }
    }
}
