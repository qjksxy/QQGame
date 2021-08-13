package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.GameUser;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.xml.sax.SAXException;
import res.Text;

import javax.xml.parsers.ParserConfigurationException;

public class Test {
    public static int errorCode = 0;

    public static void main(String[] args) {
        try {
            Hero.readHero();
            FileOperation.readMove();
        } catch (Exception e) {
            errorCode = 1;
            System.out.println(e.toString());
            e.printStackTrace();
        }
        acceptRequset();
        //MyTest();
    }

    public static void acceptRequset() {
        System.out.println("version:" + Text.version);
        int port = 7701;
        ServerSocket serverSocket = null;

        for (int i = 0; i < 1000; i++) {
            try {
                serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();
                Thread socketThread = new SocketThread(socket);
                socketThread.start();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void test() {
        Configuration cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        //开启事务
        //Transaction tx = session.beginTransaction();

        //处理事务
//		User user = new User();
//		user.setPassword("99999");
//		user.setUsername("腾化马");
//		session.save(user);

//		Good good = new Good();
//		good.setGoodname("逼傻腾化马2号");
//		good.setInv(1);
//		good.setPrice(9999);
//		session.save(good);
//		Good good2 = new Good();
//		good2.setGoodname("小米10 Pro");
//		good2.setInv(1000);
//		good2.setPrice(4999);
//		session.save(good2);


        //查询
//		Criteria criteria = session.createCriteria(Good.class);
        //criteria.add(Restrictions.eq("goodname", "小米透明电视"));
        //.between("uid", 2, 6)
        //.lt("uid", 3) --小于
        //.gt("uid", 3) --大于
//		List<Good> list = criteria.list();
//		System.out.println("******");
//		for(Good g : list) {
//			System.out.println(g.getGoodname());
//		}
//        Criteria criteria = session.createCriteria(User.class);
//        criteria.add(Restrictions.gt("userid", 0));
//        System.out.println("******");
//        List<User> list = criteria.list();
//        for(User u : list){
//            System.out.println(u.getUsername());
//        }


        //主键查询
//		Good g = session.get(Good.class, 1);
//		System.out.println(g.getGoodname());
//		User u = session.get(User.class, 1);
//		System.out.println(u.getUsername());

        //修改
//		Good d = (Good)session.get(Good.class, 6);
//		d.setPrice(4444);
//		session.saveOrUpdate(d);

        //提交处理
        //tx.commit();
        //关闭资源
        session.close();
        sessionFactory.close();
    }

}
