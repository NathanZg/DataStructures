package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: SequenceSort
 * Description:顺序查找
 * date: 2022/6/20 16:28
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class SequenceSort {
    public static void main(String[] args) {
        int[] arr = {1,9,11,-1,34,89,11};

        //找一个
        int i = sequenceSort(arr, 34);
        if (i == -1) {
            System.out.println("没找到！");
        } else {
            System.out.println(i);
        }

        //找全部
        Integer[] integers = sequenceSort2(arr, 11);
        for (Integer integer : integers) {
            System.out.print(integer+" ");
        }
    }

    /*
    * 找到一个满足条件的就返回
    * */
    public static int sequenceSort(int[] arr,int value) {
        //遍历逐一比对，发现相同值返回下标
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }


    /*
    *找到数组中所有相等的
    * */
    public static Integer[] sequenceSort2(int[] arr,int value) {
        //遍历逐一比对，发现相同值加入list
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                list.add(i);
            }
        }
        Integer[] array = list.toArray(new Integer[list.size()]);
        return array;
    }
}
