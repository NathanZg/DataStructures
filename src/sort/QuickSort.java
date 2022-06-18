package sort;

import java.util.Arrays;

/**
 * ClassName: QuickSort
 * Description:
 * date: 2022/6/17 15:45
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class QuickSort {
    public static void main(String[] args) {
        int arr[] = {-9,78,0,23,-576,70};
        QuickSort2(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    //以中间值为pivot
    public static void QuickSort(int[] arr,int left,int right){
        int l = left;//左下标
        int r = right;//右下标
        int pivot = arr[(left + right) / 2];//中轴值
        int temp = 0;//临时变量
        //循环让比pivot小的值放到左边，比pivot大的值放到右边
        while (l < r) {
            //在pivot左边找到一个大于等于pivot的值
            while (arr[l] < pivot){
                l++;
            }

            //在pivot的右边找到一个小于等于pivot的值
            while (arr[r] > pivot) {
                r--;
            }
            //l >= r 则说明pivot左边全部小于等于它，右边全部大于等于它
            if (l >= r) {
                break;
            }

            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //如果左侧交换后的值等于pivot的，右下标前移，交换的那个值没必要再比较一次
            if (arr[l] == pivot) {
                r--;
            }

            //如果右侧交换后的值等于pivot的，左下标后移，交换的那个值没必要再比较一次
            if (arr[r] == pivot) {
                l++;
            }
        }
        //当l==r时，左右下标都指向pivot，pivot在递归中不需要排序，只需要排它的左侧子数组和右侧子数组
        //否则栈溢出
        if (l == r) {
            l++;//右子数组的最左侧
            r--;//左子数组的最右侧
        }

        //向左递归
        if(left < r) {
            QuickSort(arr, left, r);
        }

        //向右递归
        if (right > l) {
            QuickSort(arr, l, right);
        }
    }

    //以最左边为pivot
    public static void QuickSort2(int[] arr,int left,int right){
        int l = left;
        int r = right;
        int pivot = arr[left];
        int temp = 0;
        while (l < r) {
            while (arr[l] < pivot) {
                l++;
            }
            while (arr[r] > pivot) {
                r--;
            }
            if (l == r) {
                break;
            }
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            if (arr[l] == pivot) {
                r--;
            }
            if (arr[r] == pivot) {
                l++;
            }

            if (l == r) {
                l++;
                r--;
            }

            if (left < r) {
                QuickSort2(arr,left,r);
            }

            if(l < right) {
                QuickSort2(arr, l, right);
            }
        }
    }
}
