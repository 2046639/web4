package servlet;

import service.CarService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/producer")
public class ProducerServlet extends HttpServlet {
    private final CarService carService = CarService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String brand = req.getParameter("brand").trim();
            String model = req.getParameter("model").trim();
            String licensePlate = req.getParameter("licensePlate").trim();
            Long price = Long.parseLong(req.getParameter("price").trim());
            carService.addCar(brand, model, licensePlate, price);

            resp.setStatus(200);
        } catch (DBException e) {
            resp.setStatus(403);
        }
    }
}
