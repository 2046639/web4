package servlet;

import com.google.gson.Gson;
import service.CarService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private final CarService carService = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            Gson gson = new Gson();
            String json = gson.toJson(carService.getAllCars());
            resp.getWriter().write(json);
            resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        carService.sellCar(brand, model, licensePlate);

        if (carService != null) {
            resp.getWriter().println(carService + "   OK");
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
        }
    }
}
