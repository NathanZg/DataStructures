package tree;

import java.util.Arrays;

/**
 * ClassName: HeapSort
 * Description:堆排序
 * date: 2022/7/7 15:08
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4,6,8,5,9};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void heapSort(int[] arr) {
        //构建一个大顶堆
        //从最后一个非叶子结点开始往上构建
        //这样子从下往上就都是有序的了
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        int temp = 0;

        //将堆顶元素与末尾元素进行交换，将最大元素沉到数组末端
        //有arr.length个数只需要交换arr.length-1次
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr,0,j);//去除最大元素后再将剩余元素编程大顶堆
        }
    }

    //将一个数组（二叉树）调整为大顶堆

    /**
     *将以i下标的结点作为根节点的子树调整为大顶堆
     * @param arr 二叉树数组
     * @param i 表示非叶子结点在数组中的下标
     * @param length 表示对多少个元素继续进行调整
     */
    public static void adjustHeap(int[] arr,int i,int length) {
        int temp = arr[i];//取出当前元素的值，保存在临时变量中
        //开始调整
        //k = i * 2 + 1 是i结点的左子结点
        for (int j = i * 2 + 1; j < length; j = j * 2 + 1) {
            //左子结点的值小于右子结点的值
            if(j+1 < length && arr[j] < arr[j + 1]) {
                j++;//指向右结点
            }
            if(arr[j] > temp) {//子结点大于父节点
                arr[i] = arr[j];//将较大的值赋给当前结点
                i = j;//i指向j,继续循环进行比较
            }else {//因为是从最后一个非叶子结点开始构建，所以下方的子树肯定已经构成大根堆了
                //所以可以直接break
                break;
            }
        }
        //循环结束 将以i为父结点的数的最大值放到了顶部
        arr[i] = temp;//将保存的值放到调整的位置
    }
}
