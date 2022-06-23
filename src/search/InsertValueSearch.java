package search;

/**
 * ClassName: InsertValueSearch
 * Description:插值查找算法
 * date: 2022/6/23 13:09
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i+1;
        }
        int valueSearch = InsertValueSearch(arr, 0, arr.length - 1, 0);
        System.out.println(valueSearch);
    }

    /**
     *在数据量较大 关键字分布较均匀的查找表来说 插值查找比二分查找快
     * 分布不均匀的情况下，该方法不一定比折半查找好
     * @param arr 数组
     * @param left 左下标
     * @param right 右下标
     * @param findVal 查找的值
     * @return
     */
    public static int InsertValueSearch(int[] arr,int left,int right,int findVal) {
        //左下标大于右下标 找不到
        //小于arr[0] 则不存在，提前终止
        //大于arr[arr.length - 1] 不存在，提前终止
        if (left > right || findVal < arr[0] || findVal > arr[arr.length-1]) {
            return -1;
        }
        // （left + right）/ 2 = left + 1/2(right - left)
        //而插值查找法把1/2这个系数给换掉了、
        //换成了（findVal - arr[left]）/ (arr[right] - arr[left])
        int mid = left + ((findVal -arr[left]) / (arr[right] - arr[left])) * (right - left);
        int midVal = arr[mid];

        //右递归
        if(findVal > midVal) {
            return InsertValueSearch(arr,mid + 1,right,findVal);
        }else if (findVal < midVal) {
            //左递归
            return InsertValueSearch(arr,left, mid - 1,findVal);
        }else {
            return mid;
        }
    }
}
