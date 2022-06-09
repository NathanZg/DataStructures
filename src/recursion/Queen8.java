package recursion;


/**
 * ClassName: Queen8
 * Description:递归解决八皇后问题
 * 1 第一个皇后先放第一行第一列
 * 2 第二个皇后放在第二行第一列、然后判断是否OK， 如果不OK，继续放在第二列、第三列、依次把所有列都放完，找到一个合适
 * 3 继续第三个皇后，还是第一列、第二列……直到第8个皇后也能放在一个不冲突的位置，算是找到了一个正确解
 * 4 当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到.
 * 5 然后回头继续第一个皇后放第二列，后面继续循环执行 1,2,3,4的步骤
 * date: 2022/6/9 20:36
 * @author Ekertree
 * @since JDK 1.8
 */
public class Queen8 {

    int max = 8;//定义有多少个皇后

    int[] result = new int[max];//保存皇后放置位置的结果

    int cnt = 0;

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.placeQueen(0);
    }

    //放置第n个皇后
    public void placeQueen(int n){
        if(max == n){//放完后
            print();
            return;
        }
        for(int i = 0;i < max;i++){
            //将当前皇后放到第一列
            result[n] = i;
            if(checkClash(n)){//不冲突
                placeQueen(n+1);
            }
            //如果冲突，则将该皇后放置到该行的下一列
        }
    }


    //当防止第n个皇后时，检测当前皇后是否和已经摆放好的皇后冲突
    public boolean checkClash(int n){
        for (int i = 0; i < n; i++) {
            if(result[i] == result[n] //是否在同一列
                    ||
                    Math.abs(n-i) == Math.abs(result[n] - result[i])){//是否在同一斜线
                return false;//有冲突
            }
        }
        return true;//无冲突
    }

    public void print(){
        cnt++;
        System.out.print(cnt+":");
        for (int j : result) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
