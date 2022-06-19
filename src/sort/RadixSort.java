package sort;

import java.util.Arrays;

/**
 * ClassName: RadixSort
 * Description:
 * date: 2022/6/19 17:18
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53,3,542,748,14,214};
        radixSort(arr);
    }

    public static void radixSort(int[] arr) {

        /*
        * 假如有80000000数据，10个桶，一个原来的数组
        * 80000000 * 11(个) * 4（字节） / 1024（kb） / 1024（m） / 1024（gb） = 3.278255462646484375G
        * */

        //得到数组中最大的数的位数
        int max = arr[0];//假设第一位最大
        for (int i = 0; i < arr.length; i++) {
            if(max < arr[i]) {
                max = arr[i];
            }
        }
        //得到最大数的是几位数
        int maxLength  = (max+"").length();

        /*
        * 定义一个二维数组，表示十个桶，每个桶是一个一维数组
        * 为了防止在放入数据的时候溢出，每一个桶的大小为arr的长度
        * 基数排序，以空间换时间
        * */
        int[][] buckets = new int[10][arr.length];

        //为了记录每个桶中，实际存放了多少个数据，需要定义一个一维数组来记录各个桶每次放入的数据个数
        int[] bucketElementCounts = new int[10];
        //n代表了对应位，1为个位，10为十位，100为百位
        for (int i = 0,n = 1; i < maxLength; i++,n *= 10) {
            //对每个数据的对应位进行排序，第一次个位，第二次十位，第三次百位
            for (int j = 0; j < arr.length; j++) {
                //取出对应位的值
                /*
                * 取出个位的值：number / 1 % 10
                * 取出十位的值：number / 10 % 10
                * 取出百位的值：number / 100 % 10
                * */
                int digitOfElement = arr[j] / n % 10;
                //放到对应的桶中
                buckets[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                //桶中数据的个数加一
                bucketElementCounts[digitOfElement]++;
            }

            int index = 0;//初始化原数组下标
            //遍历每一个桶，将桶中的数据放入原来的数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                if(bucketElementCounts[k] != 0){//该桶有数据
                    //遍历第k个桶，将其中数据放入原来的数组
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index++] = buckets[k][l];
                    }
                }
                bucketElementCounts[k] = 0;//将桶中的数据个数置零
            }
            System.out.println("第"+(i+1)+"轮处理结果为："+ Arrays.toString(arr));
        }
    }
}
