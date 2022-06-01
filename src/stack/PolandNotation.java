package stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * ClassName: PolandNotation
 * Description:
 * date: 2022/6/1 15:19
 *实现逆波兰计算器
 * @author Ekertree
 * @since JDK 1.8
 */
public class PolandNotation {
    public static void main(String[] args) {
        //逆波兰表达式 (3+4)*5-6
        String suffixExpression = "30 4 + 5 * 6 -";
        List<String> list = getListString(suffixExpression);
        int result = calculate(list);
        System.out.println("计算结果为:"+result);
    }

    //将逆波兰表达式，依次将数据和运算符 放到arraylist中
    public static List<String> getListString(String suffixExpression){
        String[] split = suffixExpression.split(" ");//分割
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        return list;
    }

    //完成对逆波兰表达式的运算
    public static int calculate(List<String> ls){
        //创建栈
        Stack<String> stack = new Stack<>();
        for (String l : ls) {
            if(l.matches("\\d+")){//匹配多位数
                //入栈
                stack.push(l);
            }else{//不是数
                //pop两个数运算后入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int result = 0;
                if(l.equals("+")){
                    result = num1+num2;
                }else if(l.equals("-")){
                    result = num1 - num2;
                }else if(l.equals("*")){
                    result = num1 * num2;
                }else if(l.equals("/")){
                    result = num1 / num2;
                }else{
                    throw new RuntimeException("运算符有误！");
                }
                stack.push(result+"");
            }
        }
        return Integer.parseInt(stack.pop());
    }
}
