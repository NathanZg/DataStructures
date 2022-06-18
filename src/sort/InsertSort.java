package sort;

import java.util.Arrays;

/**
 * ClassName: insertSort
 * Description:
 * date: 2022/6/16 12:07
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class InsertSort {
    public static void main(String[] args) {
        int arr[] = {17,9,-1,10,-2};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int insertValue = arr[i];//保存需要插入的值
            int insertIndex = i - 1;//有序队列最后一个元素位置
            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex+1] = arr[insertIndex];
                insertIndex--;
            }
            //循环结束后 index最后指向的是value大于等于的值的位置 或者是-1 则待插入的位置为index + 1
            arr[insertIndex+1] = insertValue;
        }
    }
}
