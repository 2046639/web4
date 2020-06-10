import model.DailyReport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.CarService;
import util.DBHelper;
import service.DailyReportService;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import servlet.NewDayServlet;
import servlet.ProducerServlet;

public class Main {

    public static void main(String[] args) throws Exception {
//        DBHelper dbHelper = new DBHelper();
//        dbHelper.printConnectInfo();

//        CarService service = CarService.getInstance();
//        service.addCar("BMW", "X5", "e347", 10000L);
//        service.addCar("BMW", "X6", "e349", 20000L);
//        service.addCar("BMW", "X7", "e348", 30000L);
//        service.addDailyReport();
//        service.sellCar("BMW", "X5", "e347");
//        service.addDailyReport();
//        service.sellCar("BMW", "X6", "e349");
//        service.addDailyReport();
//        service.sellCar("BMW", "X7", "e348");
//        service.addDailyReport();
//
//        DailyReportService reportService = DailyReportService.getInstance();
//        reportService.getAllDailyReports();

        CustomerServlet customerServlet = new CustomerServlet();
        DailyReportServlet dailyReportService = new DailyReportServlet();
        NewDayServlet newDayServlet = new NewDayServlet();
        ProducerServlet producerServlet = new ProducerServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(customerServlet),"/customer");
        context.addServlet(new ServletHolder(dailyReportService),"/report/*");
        context.addServlet(new ServletHolder(newDayServlet),"/newday");
        context.addServlet(new ServletHolder(producerServlet),"/producer");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();

//        DBHelper dbHelper = new DBHelper();
//        dbHelper.printConnectInfo();

//        CarService service = CarService.getInstance();
//        service.addCar("BMW", "X6", "e346", 10000L);


    }
}
