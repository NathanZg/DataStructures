package search;

import java.util.Arrays;

/**
 * ClassName: FIbonacciSearch
 * Description:斐波那契黄金分割查找算法
 * date: 2022/6/24 13:48
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class FibonacciSearch {

    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1,8,10,89,1000,1234};
        int i = fibonacciSearch(arr, 1234);
        System.out.println(i);
    }

    //得到斐波那契数组
    public static int[] fibonacci() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /*
        斐波那契数列，该数列公式为F(K) = F(k-1) + F(k-2)
        如果一个数组长度为斐波那契数，则可以分成两段也是斐波那契数长度
        由斐波那契数列F(k) = F(k-1) + F(k - 2)的性质，可以得到
        (F[k] - 1) = (F[k - 1] - 1) + (F[k - 2] - 1) + 1
        所以，如果数组长度是斐波那契数-1，则可以分为(F[k - 1] - 1)，(F[k - 2] - 1)，1共三段
        则这个1那一段，就是我们要找的mid的位置
        则mid=low+F(k-1)-1
     */


    /**
     *
     * @param arr 数组
     * @param findVal 查找的值
     * @return 找到返回下标，没找到返回-1
     */
    public static int fibonacciSearch(int[] arr,int findVal) {
        int low = 0;
        int high = arr.length -1;
        int k = 0;//斐波那契数列下标
        int mid = 0;//中间值
        int[] f = fibonacci();//得到斐波那契数组
        while (high > f[k] - 1) {//最大的下标大于斐波那契数-1
            k++;
        }
        //f[k]可能大于arr的长度，则需要构造一个新数组，不足的部分会用0填充
        int[] copy = Arrays.copyOf(arr, f[k]);
        //把用0填充的部分使用原来最高位的数填充
        for (int i = high + 1; i < copy.length; i++) {
            copy[i] = arr[high];
        }
        //用循环找到我们的findVal
        while (low <= high) {
            mid = low + f[k - 1] - 1;
            if(findVal < copy[mid]) {
                high = mid - 1;
                /*
                    原来的f[k] 就变成了f[k-1]
                    也就是到原来分成的两段的前半段继续去找，把前半段又分成两段
                 */
                k--;
            } else if (findVal > copy[mid]){
                /*
                    前半段是f[k-1]
                    后半段是f[k-2]
                    则是到后半段去找，继续把后半段分成两段
                 */
                low = mid + 1;
                k -= 2;
            } else {
                //找到了
                if (mid <= high) {//mid的位置不在填充的数据中,即是不在用high下标位置的值填充的位置
                    return mid;
                }else {//在被用high下标所在位置数据填充的位置
                    return high;
                }
            }
        }
        return -1;
    }
}
