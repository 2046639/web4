package DAO;

import model.Car;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import service.CarService;
import util.DBHelper;

import java.util.List;

public class CarDao {

    public SessionFactory sessionFactory;

    private Session session;

    //Добавил 04 июн 2020
    private static CarDao INSTANCE;

    private CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarDao(DBHelper.getSessionFactory());
        }
        return INSTANCE;
    }

    public CarDao(Session session) {
        this.session = session;
    }

    public Car get(long id) {
        return (Car) session.get(Car.class, id);
    }

    public long brandCarCount(String brand) {
//        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Car where brand = :paramBrand");
        query.setParameter("paramBrand", brand);
//        transaction.commit();
//        session.close();
        return query.list().size();
    }

    public long addCar(String brand, String model, String licensePlate, Long price) {
        Car car = new Car(brand, model, licensePlate, price);
        Long result = (Long) session.save(car);
        return result;
    }

    //Заменить этот метод на getCar + deleteCarById
    public void deleteCar(String brand, String model, String licensePlate) {
        Query query = session.createQuery("delete Car where brand = :paramBrand AND model = :paramModel AND licensePlate = :paramLicensePlate");
        query.setParameter("paramBrand", brand);
        query.setParameter("paramModel", model);
        query.setParameter("paramLicensePlate", licensePlate);
//        int row = query.executeUpdate();
        query.executeUpdate();

    }

    public void deleteCarById(long id) {
        Query query = session.createQuery("delete Car where id = :paramId");
        query.setParameter("paramId", id);
        query.executeUpdate();
    }


    public void deletAllCar() {
        Query query = session.createQuery("delete Car");
        query.executeUpdate();
    }

    public Car getCar(String brand, String model, String licensePlate) {
        Query query = session.createQuery("FROM Car where brand = :paramBrand AND model = :paramModel AND licensePlate = :paramLicensePlate");
        query.setParameter("paramBrand", brand);
        query.setParameter("paramModel", model);
        query.setParameter("paramLicensePlate", licensePlate);
        query.setMaxResults(1);
        Car car = (Car) query.uniqueResult();
        System.out.println("car id= " + car.getId());
        return (Car) query.uniqueResult();

//        List<Car> listCar = session.createCriteria(Car.class).
//                add(Restrictions.eq("brand", brand)).
//                add(Restrictions.eq("model", model)).
//                add(Restrictions.eq("licensePlate", licensePlate)).
//                list();
//        int size = listCar.size();
//        Car car = listCar.get(0);
//        return car;
    }



    public List<Car> getAllCars() {
//        Session session = sessionFactory.openSession();

//        Transaction transaction = session.beginTransaction();
//        List<Car> listCar = session.createCriteria(Car.class).list();
//        transaction.commit();
//        session.close();
//        return listCar;


        List<Car> carList = session.createQuery("FROM Car").list();
        return carList;
    }






}
