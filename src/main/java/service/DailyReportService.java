package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBException;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }

    public DailyReport getLastReport() {
        return new DailyReportDao(sessionFactory.openSession()).getLastDailyReport();
    }


//    public DailyReport getLastReport() throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            DailyReportDao dailyReportDao = new DailyReportDao(session);
//            DailyReport dailyReport = dailyReportDao.getLastDailyReport();
//            transaction.commit();
//            session.close();
//            return dailyReport;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }

    public void deleteAllReport() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            dailyReportDao.deletAllReport();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }

    }


}
