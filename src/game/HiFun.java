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

    public Card findCard(String qqAcc, int heroId){
        Criteria criteria = session.createCriteria(Card.class);
        criteria.add(Restrictions.eq("userAcc", qqAcc));
        criteria.add(Restrictions.eq("heroId", heroId));
        List<Card> list = criteria.list();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    public void saveCard(Card card){
        session.save(card);
    }

    public void updateCard(Card card){
        session.saveOrUpdate(card);
    }

    public void close(){
        tx.commit();
        session.close();
        sessionFactory.close();
    }
}
