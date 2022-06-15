package sort;

import java.util.Arrays;

/**
 * ClassName: BubbleSort
 * Description:冒泡排序
 * date: 2022/6/15 12:16
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class BubbleSort {
    public static void main(String[] args) {
        int arr[] = {17,9,-1,10,-2};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr){
        int temp = 0;//临时变量
        boolean flag = false;//是否进行过交换

        for (int i = 0; i < arr.length-1; i++) {//n个数，只需要排序n-1次
            for (int j = 0; j < arr.length-1-i; j++) {//每次得出一个最大值，下一次就少排一个
                if(arr[j] > arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            if(flag == false){//该趟排序没有进行交换
                break;
            }else{
                flag = true;//重置flag
            }
        }
    }
}
