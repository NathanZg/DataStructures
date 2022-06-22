package search;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ClassName: BinarySearch
 * Description:
 * date: 2022/6/22 16:34
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class BinarySearch {

    public static void main(String[] args) {
        //数组必须有序
        int[] arr = {1,1,1,1,1,1,18,1000,1000,1000,1000,1000,1234};
        int i = binarySearch(arr, 0, arr.length - 1, 1000);
        List<Integer> list = binarySearch2(arr, 0, arr.length - 1, 1000);
        System.out.println(i);
        list.forEach(t-> System.out.println(t));
    }

    /**
     *
     * @param arr 数组
     * @param left 左索引
     * @param right 右索引
     * @param findVal 要查找的值
     * @return 找到返回下标，找不到返回-1
     */
    public static int binarySearch(int[] arr,int left,int right,int findVal){
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if(findVal > midVal) {
             return binarySearch(arr,mid + 1,right, findVal);
        } else if (findVal < midVal) {
            return binarySearch(arr,left,mid -1,findVal);
        }else {
            return mid;
        }
    }

    /*
    * 有多个相同值时，如何把所有位置查出来？
    * 1、找到mid后不要马上返回
    * 2、向mid左扫描，将所有相同值的下标加入arraylist
    * 3、向mid右扫描，将所有相同值的下标加入arraylist
    * */
    public static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal){
        if (left > right) {
            return new ArrayList<Integer>();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if(findVal > midVal) {
            return binarySearch2(arr,mid + 1,right, findVal);
        } else if (findVal < midVal) {
            return binarySearch2(arr,left,mid -1,findVal);
        }else {
            List<Integer> list = new ArrayList<>();
            int temp = mid - 1;
            while (true) {
                if(temp < 0 || arr[temp] != findVal) {//查找完毕，因为数组有序，相同的值肯定相邻
                    break;
                }
                list.add(temp--);
            }
            list.add(mid);
            temp = mid + 1;
            while (true) {
                if(temp < arr.length || arr[temp] != findVal) {//查找完毕，因为数组有序，相同的值肯定相邻
                    break;
                }
                list.add(temp++);
            }
            list.sort(Comparator.naturalOrder());
            return list;
        }
    }

}
