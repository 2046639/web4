package DAO;

import model.DailyReport;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        return dailyReports;
    }

    public DailyReport getLastDailyReport() {
        Query query = session.createQuery("FROM DailyReport ORDER BY id DESC");
        query.setMaxResults(1);
        return (DailyReport) query.uniqueResult();
    }

    public void addDailyReport(Long earnings, Long soldCars) {
        DailyReport report = new DailyReport(earnings, soldCars);
        session.save(report);
//        return (Long) session.save(report);
    }

    public void deletAllReport() {
        Query query = session.createQuery("delete DailyReport");
        query.executeUpdate();
    }

    /*
        public long addCar(String brand, String model, String licensePlate, Long price) {
        Car car = new Car(brand, model, licensePlate, price);
        Long result = (Long) session.save(car);
        return result;
    }
     */
}
