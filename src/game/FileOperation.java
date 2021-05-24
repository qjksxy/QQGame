package game;

import java.io.*;

public class FileOperation {
    public static String[] readFile(String filePath, int rows) throws IOException {

        FileInputStream fis=new FileInputStream(filePath);
        InputStreamReader isr= null;
        String[] res = null;
        try {
            isr = new InputStreamReader(fis, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(isr);
        //简写如下
        //BufferedReader br = new BufferedReader(new InputStreamReader(
        //        new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
        String line="";
        String[] arrs = new String[rows];
        int lines = 0;
        while ((line=br.readLine())!=null) {
            arrs[lines] = line;
            lines++;
            if(lines==rows){
                break;
            }
        }
        res = new String[lines];
        for(int i=0; i<lines; i++){
            res[i] = arrs[i];
        }
        try{
            br.close();
            isr.close();
            fis.close();
        }catch (Exception e){
            System.out.println("文件处理错误：资源释放失败");
            e.printStackTrace();
        }
        return res;
    }
}
