package game;

import fight.Move;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;

public class FileOperation {
    public static File moveFile = new File("/home/temp/QQGame/move.xml");

    public static String[] readFile(String filePath, int rows) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = null;
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
        String line = "";
        String[] arrs = new String[rows];
        int lines = 0;
        while ((line = br.readLine()) != null) {
            arrs[lines] = line;
            lines++;
            if (lines == rows) {
                break;
            }
        }
        res = new String[lines];
        for (int i = 0; i < lines; i++) {
            res[i] = arrs[i];
        }
        try {
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("文件处理错误：资源释放失败");
            e.printStackTrace();
        }
        return res;
    }

    public static void readMove() throws ParserConfigurationException, IOException, SAXException {
        //1、创建读取xml文件的类
        SAXReader saxReader = new SAXReader();
        //2、给定需要读取的文件xml
        if (!moveFile.exists()) {
            //  System.exit(0);结束程序
            System.out.println("文件不存在");
            return;//结束方法
        }
        try {
            //3、read方法的返回值表示的意思是将问价中所有的内容存到一个Document对象中
            Document doc = saxReader.read(moveFile);
            //4、获得所有的元素
            Element rootElement = doc.getRootElement();
            // System.out.println(rootElement.asXML());
            //5、获得跟节点下的所有子节点
            //获得根节点下所有的emp子节点
            // List elements = rootElement.elements("emp")
            List<Element> elements = rootElement.elements();
            for (Element e : elements) {
                //获得节点上的属性值
                //String empno = e.attributeValue("empno");
                //System.out.println("属性值："+empno);
                Move move = Move.getMoveByNameNo(e.element("name").getText());
                //6、根据节点获得对应的节点
                move.setMoveId(Integer.parseInt(e.element("id").getText()));
                move.setName(e.element("name").getText());
                move.setType(Integer.parseInt(e.element("type").getText()));
                move.setPhyPower(Integer.parseInt(e.element("phypower").getText()));
                move.setMagPower(Integer.parseInt(e.element("magpower").getText()));
                move.setConsume(Integer.parseInt(e.element("consume").getText()));
                move.setDesc(e.element("desc").getText());
                move.setLevlimit(Integer.parseInt(e.element("levlimit").getText()));
                move.setPremise(Integer.parseInt(e.element("premise").getText()));
                move.setHeroid(Integer.parseInt(e.element("heroid").getText()));
                move.setIsnear(Integer.parseInt(e.element("isnear").getText()));
                Move.addMoveInList(move);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
