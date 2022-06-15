package sort;

import java.util.Arrays;

/**
 * ClassName: selectSort
 * Description:选择排序
 * date: 2022/6/15 12:46
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class selectSort {
    public static void main(String[] args) {
        int arr[] = {17,9,-1,10,-2};
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void selectSort(int[] arr){

        for (int i = 0; i < arr.length - 1; i++) {//总共进行n-1轮排序，每次排序得到一个最小值
            //假定最小值
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {//第i轮排序从第i+1的位置开始与第i个进行比较
                if(min > arr[j]){//假定的最小值不是真的最小值，修改最小值
                    min = arr[j];
                    minIndex = j;
                }
            }
            if(minIndex != i){//假定的最小值不是真的最小值，才进行交换
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }
}
