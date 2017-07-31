 package com.portal;

import java.util.ArrayList;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class PlayerDao {

	public Integer addOfficer(Officer officer) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer loginid = 0;
		try {
			
			String email=officer.getEmail().toLowerCase();
			officer.setEmail(email);
			@SuppressWarnings("unchecked")
			List<Officer> list = session.createCriteria(Officer.class).add(Restrictions.eq("email", officer.getEmail())).list();
			if(list.size()==0)
			{
				tx = session.beginTransaction();
				loginid=(Integer)session.save(officer);			
				tx.commit();
			}
			else{
				System.out.println("Email Already Exists");
			}
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return loginid;
	}

	public Integer addRegister(PlayerAuth playerAuth) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		Integer loginid = 0;
		
		try {
			String email=playerAuth.getEmail().toLowerCase();
			playerAuth.setEmail(email);
			@SuppressWarnings("unchecked")
			List<Officer> list = session.createCriteria(PlayerAuth.class).add(Restrictions.eq("email", playerAuth.getEmail())).list();
			if(list.size()==0)
			{
				tx = session.beginTransaction();
				loginid=(Integer)session.save(playerAuth);	
				tx.commit();
			}
			else{
				System.out.println("Email Already Exists");
			}
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return loginid;
	}

	public void updateInfo(PlayerInfo playerInfo) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(playerInfo);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public int find(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			List<PlayerAuth> list = session.createCriteria(PlayerAuth.class).add(Restrictions.eq("email", email))
					.add(Restrictions.eq("password", password)).list();

			if (list.size() > 0) {
				session.close();
				return list.get(0).getPlayerInfo().getLoginId();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		session.close();

		return 0;
	}

	public int findOfficer(String email, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			List<Officer> list = session.createCriteria(Officer.class).add(Restrictions.eq("email", email))
					.add(Restrictions.eq("password", password)).list();

			if (list.size() > 0) {
				session.close();
				return list.get(0).getLoginid();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		session.close();
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<PlayerInfo> getParticularPlayerInfo(int playerId) {
		List<PlayerInfo> playerinfo = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			playerinfo = session.createCriteria(PlayerInfo.class).add(Restrictions.eq("loginId", playerId)).list();

		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			session.close();

		}
		return playerinfo;
	}

	@SuppressWarnings("unchecked")
	public List<PlayerInfo> getPlayerInfos() {
		List<PlayerInfo> playerinfos = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			playerinfos = session.createCriteria(PlayerInfo.class).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();

		}
		return playerinfos;
	}

}
