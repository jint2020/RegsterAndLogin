
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileHandler {

    /*
     * 函数名：FileExistTest
     * 功能描述：检查本地存储用户名和密码的文件是否已存在，若不存在，则创建新文件
     * 输入：String fileUrl--需要检查的文件路径（存储本地用户名和密码的文件）
     * 输出：若文件已存在或（不存在但新建文件成功），则返回true；否则返回false
     * */
    static boolean FileExistTest(String fileUrl) {
        boolean out = false;
        try {
            File f = new File(fileUrl);
            if(!f.exists()) {						//若文件不存在
                f.createNewFile();					//新建该文件

                String sets=" attrib +H  "+f.getAbsolutePath(); 	//设置文件属性为隐藏
                Runtime.getRuntime().exec(sets);			//执行文件设置
                out = true;
            }
            out = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    /*
     * 函数名：StringSearchLine
     * 功能描述：逐行读取路径为fileUrl的文件，查找字符串strDst是否存在
     * 输入：String strDst--需要查询的目标字符串
     * 		 String fileUrl--需要访问的文件路径
     * 输出：若在文件中查询到目标字符串strDst，则返回true；否则返回flase
     * */

    static boolean StringSearchLine(String strDst,String fileUrl) {
        boolean out = false;								//定义布尔类型的返回值
        try {
            FileInputStream fis = new FileInputStream(fileUrl);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader br = new BufferedReader(isr);				//至此，变量br与文件中的字符流已建立连接

            String line = "";							//暂存br中取出的每一行字符串
            while((line = br.readLine()) != null) {				//文件未读取完毕
                if(line.equals(strDst)) {					//若找到匹配的字符串
                    out = true;						//则返回值赋值为true，终止循环
                    break;
                }
            }

            br.close();								//结束对文件的访问
            isr.close();
            fis.close();
        } catch (Exception e) {							//处理异常（输出异常）
            e.printStackTrace();
        }
        return out;									//返回查询结果
    }

    /*
     * 函数名：StringWriteLine
     * 功能描述：向路径为fileUrl的文件中写入（追加方式）字符串strDIn
     * 输入：String strIn--需要向文件中写入（追加方式）的字符串
     * 		 String fileUrl--需要访问的文件路径
     * 输出：若向文件写入字符串strIn成功，则返回true；否则返回flase
     * */
    static boolean StringWriteLine(String strIn,String fileUrl) {
        boolean out = false;
        try {
            FileWriter fw = new FileWriter(fileUrl,true);				//建立文件写入访问连接

            fw.write("\n"+strIn);							//向文件中追加新的字符串StrIn
            fw.close();								//结束对文件的访问

            out = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    /*
     * 函数名：UserValidate
     * 功能描述：校验登录操作的用户名userName和密码password是否匹配
     * 输入：String userName--登录操作输入的用户名
     * 		 String password--登录操作输入的密码
     * 		 String fileUrl--需要访问的文件路径（存储本地用户名和密码的文件）
     * 输出：若输入的userName和password相匹配，则返回true；否则返回false
     * */
    static boolean UserValidate(String userName,String password,String fileUrl) {
        boolean out = false;
        try {
            FileInputStream fis = new FileInputStream(fileUrl);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader br = new BufferedReader(isr);				//至此，变量br与文件中的字符流建立连接

            String line = "";							//暂存br中取出的每一行字符串

            while((line=br.readLine()) != null) {					//若文件未读取完毕，则继续读取下一行
                if(line.equals("un:" + userName)) {				//若当前行与用户名相匹配
                    if(br.readLine().equals(String.valueOf(("pw:"+password).hashCode()))) {		//看密码是否匹配
                        out = true;									//若密码匹配，返回true
                    }
                    break;											//只要检索到用户名，不论密码是否匹配，都终止循环
                }
            }

            br.close();												//关闭对文件的访问
            isr.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    /*
     * 函数名：UserRegister
     * 功能描述：以注册操作输入的用户名userName和密码password注册新用户（向存储本地用户名和密码的文件中写入新的用户名的和密码）
     * 输入：String userName--注册操作输入的用户名
     * 		 String password--注册操作输入的密码
     * 		 String fileUrl--需要访问的文件路径（存储本地用户名和密码的文件）
     * 输出：若新用户注册成功，则返回true；否则返回false
     * */
    static boolean UserRegister(String userName,String password,String fileUrl) {
        boolean out = false;
        if(StringSearchLine("un:" + userName, fileUrl)) {				//检验用户名是否已存在
            return out;
        }else {										//若不存在，依次写入用户名和密码（分别加上前缀"un"和"pw"）
            StringWriteLine("un:" + userName, fileUrl);
            StringWriteLine(String.valueOf(("pw:" + password).hashCode()), fileUrl);
            out = true;
        }
        return out;
    }
}