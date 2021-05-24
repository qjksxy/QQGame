package game;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import fight.Buff;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MyTest {
    private static LinkedList<Hero> heros = new LinkedList<>();
    private static File file = new File("/home/temp/QQGame/demo.xml");
    public static void testDocument() throws ParserConfigurationException, IOException, SAXException {
        //1、创建读取xml文件的类
        SAXReader saxReader = new SAXReader();
        //2、给定需要读取的文件xml
        if (!file.exists()){
            //  System.exit(0);结束程序
            System.out.println("文件不存在");
            return;//结束方法
        }
        try {
            //3、read方法的返回值表示的意思是将问价中所有的内容存到一个Document对象中
            Document doc = saxReader.read(file);
            //4、获得所有的元素
            Element rootElement = doc.getRootElement();
            // System.out.println(rootElement.asXML());
            //5、获得跟节点下的所有子节点
            //获得根节点下所有的emp子节点
            // List elements = rootElement.elements("emp")
            List<Element> elements = rootElement.elements();
            for (Element e: elements) {
                //获得节点上的属性值
                //String empno = e.attributeValue("empno");
                //System.out.println("属性值："+empno);
                Hero hero = new Hero();
                //6、根据节点获得对应的节点
                hero.id = Integer.parseInt(e.element("id").getText());

                hero.name = e.element("name").getText();
                hero.title = e.element("title").getText();
                hero.grade = e.element("grade").getText();
                hero.type = e.element("type").getText();
                hero.stuhp = Integer.parseInt(e.element("stuhp").getText());
                hero.stump = Integer.parseInt(e.element("stump").getText());
                hero.stuphyatt = Integer.parseInt(e.element("stuphyatt").getText());
                hero.stumagatt = Integer.parseInt(e.element("stumagatt").getText());
                hero.stuphydef = Integer.parseInt(e.element("stuphydef").getText());
                hero.stumagdef = Integer.parseInt(e.element("stumagdef").getText());
                hero.stuacc = Integer.parseInt(e.element("stuacc").getText());
                hero.stumiss = Integer.parseInt(e.element("stumiss").getText());
                hero.stucrit = Integer.parseInt(e.element("stucrit").getText());
                hero.stuspeed = Integer.parseInt(e.element("stuspeed").getText());
                hero.bashp = Integer.parseInt(e.element("bashp").getText());
                hero.basmp = Integer.parseInt(e.element("basmp").getText());
                hero.basphyatt = Integer.parseInt(e.element("basphyatt").getText());
                hero.basmagatt = Integer.parseInt(e.element("basmagatt").getText());
                hero.basphydef = Integer.parseInt(e.element("basphydef").getText());
                hero.basmagdef = Integer.parseInt(e.element("basmagdef").getText());
                hero.basacc = Integer.parseInt(e.element("basacc").getText());
                hero.basmiss = Integer.parseInt(e.element("basmiss").getText());
                hero.bascrit = Integer.parseInt(e.element("bascrit").getText());
                hero.basspeed = Integer.parseInt(e.element("basspeed").getText());
                heros.add(hero);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            testDocument();
            for(Hero hero : heros){
                System.out.println(hero.name);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
