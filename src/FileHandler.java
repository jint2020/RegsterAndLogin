import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileHandler {

    // 检查文件是否存在
    // 传入filePath
    public boolean checkFileExist(String filePath) {
        boolean flag = false;
        try {
            File f = new File(filePath);
            //若文件不存在
            if(!f.exists()) {
                //新建文件
                f.createNewFile();
                flag = true;
            }
            flag = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    // 向目标文件（filePath）写入字符串inStr
    // 传入inStr，filePath
    public boolean writeStr(String inStr,String filePath) {
        boolean flag = false;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath,true));
            //向文件中追加新的字符串passStr
            out.write("\n"+inStr);
            out.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 校验用户名和密码
    // 传入userName，password 与user.csv存的un,pw做比较
    public boolean valUserDetail(String userName,String password,String filePath) {
        boolean flag = false;
        try {
            // 读取csv文件，BufferedReader提供readLine功能
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
            //暂存br中取出的每一行字符串
            String lines = "";

            //若文件未读取完毕，则继续读取下一行
            while((lines=br.readLine()) != null) {
                //若当前行与用户名相匹配
                if(lines.equals("id:" + userName)) {
                    //看密码是否匹配
                    if(br.readLine().equals("pw:"+password)) {
                        flag = true;
                    }
                    //username不匹配即停止loop
                    break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 查找输入的字符串
    public boolean searchPassStr(String passStr,String filePath) {
        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
            String line = "";

            //文件未读取完毕
            while((line = br.readLine()) != null) {
                //若找到匹配的字符串
                if(line.equals(passStr)) {
                    //则返回值赋值为true，终止循环
                    flag = true;
                    break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回查询结果
        return flag;
    }

    // 用户选择注册功能时输入的username和password，向user.csv写入username和password
    public boolean regFunc(String userName,String password,String filePath) {
        boolean flag = false;
        //检验用户名是否已存在
        if(searchPassStr("id:" + userName, filePath)) {
            return flag;
        }else {
            //若不存在，依次写入用户名和密码（分别加上前缀"un:"和"pw:"）
            writeStr("id:" + userName, filePath);
            writeStr("pw:" + password, filePath);
            flag = true;
        }
        return flag;
    }
}