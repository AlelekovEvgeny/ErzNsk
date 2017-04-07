package model.other;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import loadparse.ZpRecord;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class RecordH {
	
	/*Object record;
	
	public RecordH(Object record) {
		this.record = record;
	}
     */
     public void addrecord(ZpRecord record) throws SQLException 
     {
             Session session = null;
             try {
                 session = HibernateUtil.getSessionFactory().openSession();
                 Transaction tr =  session.beginTransaction();
                 session.save(record);
                 tr.commit();
                
             } catch (Exception e) {
                 System.out.println(e.getMessage());
             } finally {
                 if (session != null && session.isOpen()) {
                     session.close();
                 }
             }
       }

       public void updaterecord(ZpRecord record) throws SQLException {
             Session session = null;
             try {
                 session = HibernateUtil.getSessionFactory().openSession();
                 session.beginTransaction();
                 session.update(record);
                 session.getTransaction().commit();
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
             } finally {
                 if (session != null && session.isOpen()) {
                     session.close();
                 }
             }
       }

       public Object getrecordById(int id) throws SQLException {
             Session session = null;
             Object record = null;
             try {
                 session = HibernateUtil.getSessionFactory().openSession();
                 record = (Object) session.load(Object.class, id);
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
             } finally {
                 if (session != null && session.isOpen()) {
                     session.close();
                 }
             }
             return record;
       }

       public List<Object> getAllrecord() throws SQLException {
             Session session = null;
             List<Object> record = new ArrayList<Object>();
             try {
                 session = HibernateUtil.getSessionFactory().openSession();
                 record = session.createCriteria(Object.class).list();
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
             } finally {
                 if (session != null && session.isOpen()) {
                     session.close();
                 }
             }
             return record;
       }

       public void deleterecord(ZpRecord record) throws SQLException {
             Session session = null;
             try {
                 session = HibernateUtil.getSessionFactory().openSession();
                 session.beginTransaction();
                 session.delete(record);
                 session.getTransaction().commit();
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка I/O", JOptionPane.OK_OPTION);
             } finally {
                 if (session != null && session.isOpen()) {
                     session.close();
                 }
             }
       }

}