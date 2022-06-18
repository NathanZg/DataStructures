package sort;

import java.util.Arrays;

/**
 * ClassName: MergeSort
 * Description:归并排序
 * date: 2022/6/18 9:36
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {8,4,5,7,1,3,6,2};
        int[] temp = new int[arr.length];
        mergeSort(arr,0,arr.length - 1, temp);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr,int left,int right,int[] temp) {
        if (left < right) {//当分解到每个都只剩一个元素时停止分解，从栈顶开始合并，栈顶的情况就是单个元素的情况
            int mid = (left + right) / 2;
            //向左递归分解
            mergeSort(arr,left,mid,temp);
            //向右递归分解
            mergeSort(arr, mid + 1, right, temp);
            //合并
            merge(arr,left,mid,right,temp);
        }
    }

    /**
     *
     * @param arr 排序原始数组
     * @param left 左边有序序列的初始索引
     * @param mid 中间索引
     * @param right 右边索引
     * @param temp 中转数组
     */
    public static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int i = left;//初始化左边有序序列的索引
        int j = mid + 1;//让中间这个元素作为左边的序列，则有右边的索引应该为mid+1
        int t = 0;//指向temp数组的当前索引

        //把左右两边的数据按照规则填充到temp数组，知道有一遍处理完毕
        while (i <= mid && j <= right) {
            //左边的小于等于右边的，将左边的填充到temp数组
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            }else {//反之 把右边的填充到temp数组
                temp[t++] = arr[j++];
            }
        }

        //把剩余数据的一边的所有数据依次全部填充到temp
        while (i <= mid) { //如果左边有剩余
            temp[t++] = arr[i++];
        }

        //把剩余数据的一边的所有数据依次全部填充到temp
        while (j <= right) { //如果右边有剩余
            temp[t++] = arr[j++];
        }

        //把temp数组拷贝到arr
        int tempLeft = left;
        t = 0;//置零
        //第一次合并 tempLeft = 0 right = 1
        //第二 tempLeft = 2 right = 3
        //第三 tempLeft = 0 right = 3
        //最后一次 tempLeft = 0 right = 7
        while (tempLeft <= right) {
            arr[tempLeft++] = temp[t++];
        }

    }
}
