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
import java.util.Random;

public class MyTest {

    private static File file = new File("/home/temp/QQGame/demo.xml");

    public static void main(String[] args) {
//        try {
//            testDocument();
//            for(Hero hero : heros){
//                System.out.println(hero.name);
//            }
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
        Random r = new Random();
        for(int l=4; l<70; l++){
            int total = 0;
            for(int k=0; k<100; k++){
                int succ = 0;
                for(int i=0; i<10000; i++){
                    int sum = 0;
                    for(int j=0; j<l; j++){
                        int rand = r.nextInt(100);
                        if(rand < 8){
                            sum++;
                        }else if(rand < 11){
                            sum += 5;
                        }
                    }
                    if(sum >= 20){
                        succ++;
                    }
                }
                total+=succ;
            }
            System.out.println(l+":"+1.0*total/100/10000);
        }

    }
}
