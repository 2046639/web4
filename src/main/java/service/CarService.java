package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.spi.SessionFactoryServiceInitiator;
import util.DBException;
import util.DBHelper;

import java.util.List;

public class CarService {

    public static long todayEarning;

    public static long todaySoldCars;

    private static CarService carService;

    private SessionFactory sessionFactory;

    private final CarDao dao = CarDao.getInstance();

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

//    CarDao dao = CarDao.getInstance();

    public void addCar(String brand, String model, String licensePlate, Long price) throws DBException {

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CarDao dao = new CarDao(session);
            long id = dao.addCar(brand, model, licensePlate, price);
            if (dao.brandCarCount(brand) > 10) {
                throw new DBException();
            }
            transaction.commit();
    }

    public void sellCar(String brand, String model, String licensePlate)  {
            Session session = sessionFactory.openSession();
        CarDao dao = new CarDao(session);
        Car car = dao.getCar(brand, model, licensePlate);
        long id = car.getId();
            Transaction transaction = session.beginTransaction();
            dao.deleteCarById(id);
            todayEarning += car.getPrice();
            todaySoldCars ++;
            transaction.commit();
//            session.close();
    }

    public void addDailyReport() throws DBException {

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            DailyReportDao dao = new DailyReportDao(session);
            dao.addDailyReport(todayEarning, todaySoldCars);
            todayEarning = 0;
            todaySoldCars = 0;
            transaction.commit();
//            session.close();
    }

    public void deleteAllCar() throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            CarDao dao = new CarDao(session);
            dao.deletAllCar();
            transaction.commit();
//            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<Car> getAllCars() {

            Session session = sessionFactory.openSession();
            CarDao dao = new CarDao(session);
//            session.close();
            return dao.getAllCars();
    }


    public void drop() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CarDao dao = new CarDao(session);
        dao.deletAllCar();
        DailyReportDao dailyReportDao = new DailyReportDao(session);
        dailyReportDao.deletAllReport();
        transaction.commit();
//        session.close();
    }
}
