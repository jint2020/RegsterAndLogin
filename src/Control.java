import java.util.Scanner;



public class Control {
    private final FileHandler fh;

    public Control(){
        fh = new FileHandler();
    }

    //菜单
    public void menu(){
        System.out.println("===============MAIN MENU==============");
        System.out.println("(1) Register");
        System.out.println("(2) Login");
        System.out.println("(3) Exit the system");
        System.out.println("Enter an option:\n");
    }

    // 用户输入选择功能
    public void program() {
        menu();
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();

        while (!option.equals("3")) {
            if (option.equals("1"))
                registerUser();
            else if (option.equals("2"))
                loginUser();
            else {
                System.out.println("[Wrong Choice] ");
                System.out.println("[You must select 1 - 3]");
            }
            menu();
            option = input.nextLine();
        }
        System.out.println("System exit");
    }

    // 用户注册功能
    public void registerUser(){
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your username:");
        //存储输入的用户名
        String userName = input.nextLine();
        System.out.println("Please enter your password: ");
        //存储输入的密码
        String password = input.nextLine();


        //存储文件路径
        String filePath = "./files/user.csv";
        //调用checkFileExist方法检测文件是否存在
        fh.checkFileExist(filePath);

        //调用用户注册方法检验用户信息是否存在
        if(fh.regFunc(userName, password, filePath)) {
            System.out.println("Completed registration\n");
        }else {
            System.out.println("Username has been taken\n");
        }
    }

    // 用户登录功能
    public void loginUser(){
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your username:");
        //存储输入的用户名
        String userName = input.nextLine();
        System.out.println("Please enter your password: ");
        //存储输入的密码
        String password = input.nextLine();
        //存储文件路径
        String filePath = "./files/user.csv";
        fh.checkFileExist(filePath);

        //调用用户登录验证方法检验用户信息是否匹配
        if(fh.valUserDetail(userName, password, filePath)) {
            System.out.println("Login successfully\n");
        } else if (!fh.searchPassStr("pw:" + password,filePath)){
            System.out.println("Wrong password\n");
        }else {
            System.out.println("User does not exist\n");
        }
    }

    public static void main(String[] args) {
        Control run = new Control();
        run.program();
    }
}
