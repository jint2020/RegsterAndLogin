import java.util.Scanner;



public class App {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String flag = String.valueOf(in.nextLine());		//标志变量，区分注册或登录
        System.out.println("Please enter your username:");
        String userName = in.nextLine();			//存储输入的用户名
        System.out.println("Please enter your password: ");
        String password = in.nextLine();			//存储输入的密码

        in.close();

        String fileUrl = "./files/user.csv";			//存储文件路径
        FileHandler.FileExistTest(fileUrl);			//检测文件是否存在，若不存在，则在指定路径新建

        if(flag.equalsIgnoreCase("reg") ) {									//flag为0，注册操作
            if(FileHandler.UserRegister(userName, password, fileUrl)) {		//调用用户注册函数
                System.out.println("注册成功");
            }else {
                System.out.println("用户名已存在");
            }
        }else if(flag.equalsIgnoreCase("log") ) {										//flag为1，登录操作
            if(FileHandler.UserValidate(userName, password, fileUrl)) {		//调用用户登录验证函数
                System.out.println("登录成功");
            }else {
                System.out.println("用户名或密码错误");
            }
        }
    }
}
