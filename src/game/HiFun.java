package game;
import entity.*;
import fight.Move;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Random;

public class HiFun {
    public static final int ALL_HERO = 0;

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

    public UserHero findUserHeroById(int userHeroId){
        Criteria criteria = session.createCriteria(UserHero.class);
        criteria.add(Restrictions.eq("id", userHeroId));
        List<UserHero> list = criteria.list();
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
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

    public List<Card> findAllCard(String qqAcc){
        Criteria criteria = session.createCriteria(Card.class);
        criteria.add(Restrictions.eq("userAcc", qqAcc));
        List<Card> list = criteria.list();
        return list;
    }

    public void saveCard(Card card){
        session.save(card);
    }

    public void updateCard(Card card){
        session.saveOrUpdate(card);
    }

    public void saveUserHero(UserHero userHero){
        session.save(userHero);
    }

    public void saveHeroMove(HeroMove heroMove){
        session.save(heroMove);
    }

    public void updateUserHero(UserHero userHero){
        session.saveOrUpdate(userHero);
    }

    public List<UserHero> findUserHero(String qqAcc, int heroId){
        Criteria criteria = session.createCriteria(UserHero.class);
        criteria.add(Restrictions.eq("userAcc", qqAcc));
        if(heroId != ALL_HERO){
            criteria.add(Restrictions.eq("heroId", heroId));
        }
        List<UserHero> list = criteria.list();
        return list;
    }

    public UserHero findRandomUserHero(){
        Criteria criteria = session.createCriteria(UserHero.class);
        List<UserHero> list = criteria.list();
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public List<HeroMove> findHeroMove(int userHeroId, boolean isSelectedOnly){
        Criteria criteria = session.createCriteria(HeroMove.class);
        criteria.add(Restrictions.eq("userHeroId", userHeroId));
        if(isSelectedOnly){
            criteria.add(Restrictions.eq("isSelected", 1));
        }
        List<HeroMove> list = criteria.list();
        return list;
    }

    public void close(){
        tx.commit();
        session.close();
        sessionFactory.close();
    }
}
