package servlet;

import com.google.gson.Gson;
import service.CarService;
import service.DailyReportService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/report")
public class DailyReportServlet extends HttpServlet {
    private final DailyReportService dailyReportService = DailyReportService.getInstance();
    private final CarService carService = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            String json = "";
            if (req.getPathInfo().contains("all")) {
                json = gson.toJson(

                        dailyReportService.getAllDailyReports()
                );
            } else if (req.getPathInfo().contains("last")) {
                json = gson.toJson(

                        dailyReportService.getLastReport()
                );
            }
            resp.getWriter().write(json);
            resp.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        carService.drop();
    }
}
