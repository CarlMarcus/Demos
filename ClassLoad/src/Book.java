public class Book {
    public static void main(String[] args) {
        staticFunction();
    }

    static Book book = new Book();

    static {
        System.out.println("书的静态代码块");
    }

    {
        System.out.println("书的普通代码块");
    }

    Book() {
        System.out.println("书的构造方法");
        System.out.println("price=" + price +",amount=" + amount);
    }

    public static void staticFunction() {
        System.out.println("书的静态方法");
    }

    int price = 110;
    static int amount = 112;
}
/*
当 JVM 在准备阶段的时候，便会为类变量分配内存和进行初始化。
此时，我们的 book 实例变量被初始化为 null，amount 变量被初始化为 0。
当进入初始化阶段后，因为 Book 方法是程序的入口，根据我们上面说到的类初始化
的五种情况的第四种（当虚拟机启动时，用户需要指定一个要执行的主类（包含main()
方法的那个类），虚拟机会先初始化这个主类）。所以JVM 会初始化 Book 类，即执行
类构造器 。
JVM 对 Book 类进行初始化首先是执行类构造器（按顺序收集类中所有静态代码块和
类变量赋值语句就组成了类构造器 ），后执行对象的构造器（按顺序收集成员变量赋
值和普通代码块，最后收集对象构造器，最终组成对象构造器 ）。
对于 Book 类，其类构造方法（）可以简单表示如下：
static Book book = new Book();
static
{
    System.out.println("书的静态代码块");
}
static int amount = 112;
于是首先执行static Book book = new Book();这一条语句，这条语句又触发了类
的实例化。于是 JVM 执行对象构造器 ，收集后的对象构造器 代码：
{
    System.out.println("书的普通代码块");
}
int price = 110;
Book()
{
    System.out.println("书的构造方法");
    System.out.println("price=" + price +", amount=" + amount);
}
于是此时 price 赋予 110 的值，输出：「书的普通代码块」、「书的构造方法」。
而此时 price 为 110 的值，而 amount 的赋值语句并未执行，所以只有在准备阶
段赋予的零值，所以之后输出「price=110,amount=0」。
当类实例化完成之后，JVM 继续进行类构造器的初始化：
static Book book = new Book();  //完成类实例化
static
{
    System.out.println("书的静态代码块");
}
static int amount = 112;
即输出：「书的静态代码块」，之后对 amount 赋予 112 的值。
到这里，类的初始化已经完成，JVM 执行 main 方法的内容。
public static void main(String[] args)
{
    staticFunction();
}
即输出：「书的静态方法」。
 */