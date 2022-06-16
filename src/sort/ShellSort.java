package sort;

import java.util.Arrays;

/**
 * ClassName: ShellSort
 * Description:希尔排序的两种形式
 * date: 2022/6/16 14:56
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {8,9,1,7,2,3,5,4,6,0};
        ShellSort2(arr);
        System.out.println(Arrays.toString(arr));
    }

    //交换式
    public static void ShellSort(int[] arr){
        int temp = 0;
        //初始以数组长度的一半为步长，每次的步长是上一次步长的一般，步长为1时是最后一次
        //步长 = 分的组数
        for (int gap = arr.length/2; gap > 0; gap /= 2) {
            //i = gap 是设置i为第一组的第二个数 希尔排序是简单插入排序的改进版，也叫缩小增量排序
            //也就是对每组进行简单插入排序
            //这里不是每次单独对一组进行简单插入排序，而是交替得对每一组进行插入排序，即是每次给一组插入一个数
            //i++一次就到下一组去了
            for (int i = gap; i < arr.length ; i++) {
                //j = i - gap 即找到该组上一个元素
                for (int j = i - gap; j >= 0 ; j -= gap) {
                    //该组上一个元素大于当前元素则交换
                    if(arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    //移位法
    public static void ShellSort2(int[] arr){
        int temp = 0;
        int j = 0;
        //初始以数组长度的一半为步长，每次的步长是上一次步长的一般，步长为1时是最后一次
        //步长 = 分的组数
        for (int gap = arr.length/2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length ; i++) {
                j = i;//当前元素位置
                temp = arr[j];//保存需要插入的元素
                if(temp < arr[j - gap]) {//当前位置元素小于前一个位置的元素
                    //保证j不会越界后且前一个元素仍小于需要插入的元素，再往前移动到该组前一个元素的位置
                    while (j - gap >= 0 && temp < arr[j - gap]){
                        arr[j] = arr[j - gap];//讲前一个元素后移到当前位置
                        j -= gap;
                    }
                    //循环结束后，找到合适的位置j
                    arr[j] = temp;
                }

            }
        }
    }
}
