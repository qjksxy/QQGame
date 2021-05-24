package game;
import entity.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HiFun {
    private Configuration cfg;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;
    public HiFun(){
        cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
    }
    public List<GameUser> findUserEq(String pName, Object value){
        Criteria criteria = session.createCriteria(GameUser.class);
        criteria.add(Restrictions.eq(pName, value));
        List<GameUser> list = criteria.list();
        return list;
    }

    public void addUser(GameUser gu){
        session.save(gu);
    }

    public void updateUser(GameUser gu){
        session.saveOrUpdate(gu);
    }

    public void close(){
        tx.commit();
        session.close();
        sessionFactory.close();
    }
}
