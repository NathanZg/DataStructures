package stack;

/**
 * ClassName: Calculator
 * Description:
 * date: 2022/6/1 11:32
 *实现中缀表达式的计数器
 * @author Ekertree
 * @since JDK 1.8
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "3*2*20+2-18+200";
        CalculatorStack numStack = new CalculatorStack();//数字栈
        CalculatorStack operStack = new CalculatorStack();//操作符栈
        int index = 0;//扫描
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';//每次扫描的字符
        String keepNum = "";//用于拼接多位数
        //扫描表达式
        while (true) {
            ch = expression.substring(index, index + 1).charAt(0);
            //判断ch
            if (operStack.isOper(ch)) {//是运算符
                if (operStack.isEmpty()) {
                    operStack.push(ch);
                    index++;
                } else {
                    //符号栈有操作符，进行比较，当前符号优先级小于栈中符号优先级，数栈pop两个数
                    //符号栈pop一个符号，进行运算，将结果入数栈，将当前操作符入符号栈
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        //把结果入数栈
                        numStack.push(res);
                        //将当前操作符入符号栈
                        operStack.push(ch);
                        index++;
                    } else {//反之直接入符号栈
                        operStack.push(ch);
                        index++;
                    }
                }
            } else {
                //在处理多位数时，不能发现是一个数就马上入栈，可能是多位数
                //在处理数的时候，需要向index后多看一位，是数则进行扫描，是符号则入栈
                keepNum += ch;
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));//字符1 int值为49
                    keepNum = "";//清空
                } else {
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        numStack.push(Integer.parseInt(keepNum));//字符1 int值为49
                        keepNum = "";//清空
                    }
                }
                index++;
                if (index >= expression.length()) {//扫描完毕
                    break;
                }
            }
        }
            //扫描完毕，顺序从数栈和符号栈pop相应的数和符号进行运算
            while (true) {
                if (operStack.isEmpty()) {//符号栈为空，则计算结束，数栈只剩一个数字，为结果
                    break;
                }
                num1 = numStack.pop();
                num2 = numStack.pop();
                oper = operStack.pop();
                res = numStack.cal(num1, num2, oper);
                numStack.push(res);
            }
            int res2 = numStack.pop();
            System.out.println("表达式" + expression + "=" + res2);
        }
    }


    class CalculatorStack extends LinkedListStack {

        //返回运算符的优先级，数字越大优先级越高
        public int priority(int oper) {
            if (oper == '*' || oper == '/') {
                return 1;
            } else if (oper == '+' || oper == '-') {
                return 0;
            } else {
                return -1;
            }
        }

        //判断是不是一个运算符
        boolean isOper(char val) {
            return val == '+' || val == '-' || val == '*' || val == '/';
        }

        //计算方法
        public int cal(int num1, int num2, int oper) {
            int result = 0;
            switch (oper) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num2 - num1;
                    break;
                case '*':
                    result = num2 * num1;
                    break;
                case '/':
                    result = num2 / num1;
                    break;
                default:
                    break;
            }
            return result;
        }

        //返回当前栈顶元素，不出栈
        public int peek() {
            return super.getHead().getNext().getValue();
        }
    }

