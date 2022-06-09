package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * ClassName: PolandNotation
 * Description:
 * date: 2022/6/1 15:19
 *实现逆波兰计算器
 *
 * 中缀表达式转换为后缀表达式
 * 一、初始化两个栈：运算符栈s1和储存中间结果的栈s2；
 * 二、从左至右扫描中缀表达式；
 * 三、遇到操作数时，将其压s2；
 * 四、遇到运算符时，比较其与s1栈顶运算符的优先级：
 *  ①如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
 *  ②否则，若优先级比栈顶运算符的高，也将运算符压入s1；
 *  ③否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4-1)与s1中新的栈顶运算符相比较；
 * 五、遇到括号时：
 *  (1) 如果是左括号“(”，则直接压入s1
 *  (2) 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
 * 六、重复步骤2至5，直到表达式的最右边
 * 七、将s1中剩余的运算符依次弹出并压入s2
 * 八、依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
 * @author Ekertree
 * @since JDK 1.8
 */
public class PolandNotation {
    public static void main(String[] args) {
        //中缀表达式转换成后缀表达式
        //1+((2+3)x4)-5 => 1 2 3 + 4 x + 5 -
        String expression = "1+((2+3)*4)-5";
        System.out.print("请输入计算表达式：");
        Scanner scanner = new Scanner(System.in);
        expression = scanner.nextLine();
        List<String> s = toInfixExpressionList(expression);
        List<String> suffixExpressionList = parseSuffixExpressionList(s);
        //逆波兰表达式 (3+4)*5-6
        String suffixExpression = "";
        int i = 1;
        for (String item : suffixExpressionList) {
            if(i == 1){
                suffixExpression = suffixExpression + item;
                i++;
            }else{
                suffixExpression = suffixExpression + " " + item;
            }

        }
        List<String> list = getListString(suffixExpression);
        int result = calculate(list);
        System.out.println(expression+"="+result);
        scanner.close();
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

    //将中缀表达式转换成对应的List
    public static List<String> toInfixExpressionList(String s){
        List<String> infixStr = new ArrayList<>();
        int i = 0;//指针，用于遍历中缀字符串表达式
        String numStr;//对多位数的拼接
        char ch;//遍历的每一个字符，存放在这
        do{
            //如果c是一个非数字，加入infixStr
            if(((ch = s.charAt(i)) < 48) || ((ch = s.charAt(i)) > 57) ){
                infixStr.add(""+ch);
                i++;//指针后移
            }else{//如果是数字，考虑多位数
                numStr = "";//置空
                while (i < s.length() && ((ch = s.charAt(i)) >= 48) && ((ch = s.charAt(i)) <= 57)){
                    numStr += ch;//拼接数字
                    i++;
                }
                infixStr.add(numStr);
            }
        }while (i < s.length());
        return infixStr;
    }

    //将中缀表达式的list转换为后缀表达式的list
    public static List<String> parseSuffixExpressionList(List<String> infixStr){
        //定义两个栈
        Stack<String> operStack = new Stack<>();
        List<String> midResult = new ArrayList<>();//存储中间结果
        //遍历infixStr
        infixStr.forEach(item->{
            //如果是一个数就加入midResult
            if(item.matches("\\d+")){
                midResult.add(item);
            }else if(item.equals("(")){//如果是左括号入符号栈
                operStack.push(item);
            }else if(item.equals(")")){
                //如果是右括号，则依次弹出operStack栈顶的运算符，并压入midResult，直到遇到左括号为止，此时丢弃这对括号
                while (!operStack.peek().equals("(")){//不等于左括号
                    midResult.add(operStack.pop());
                }
                operStack.pop();//去除左括号
            }else{
                //item的优先级小于等于栈顶运算符的优先级，将operStack栈顶的运算符弹出并加入midResult中
                //然后再次比较优先级
                while (operStack.size() != 0 && (Operation.getValue(operStack.peek()) >= Operation.getValue(item))){
                    midResult.add(operStack.pop());
                }
                //还需要将item压入符号栈
                operStack.push(item);
            }
        });
        //将符号栈中剩余的运算符依次弹出加入midResult中
        while (operStack.size() != 0){
            midResult.add(operStack.pop());
        }
        return midResult;//顺序输出就是后缀表达式
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

//编写一个类Operation，可以返回一个运算符对应的优先级
class Operation{

    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String operation){
        int result = 0;
        switch (operation){
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            case "(":
            case ")":
                break;
            default:
                System.out.println("运算符出错！");
                break;
        }
        return result;
    }
}
