package com.rves.controller;

import com.rves.Dto.ReportDTO;
import com.rves.pojo.Booking;
import com.rves.services.BookingService;
import com.rves.services.ReportBuilder;
import com.rves.services.UserService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportBuilder reportBuilder;

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @RequestMapping("/reports")
    public String repotPage(Model model){
        model.addAttribute("users", userService.list());
        model.addAttribute("report", new ReportDTO());
        return "/reports/reports";
    }

    //Receiving parameters for generating the report
    @RequestMapping(value = "/generating", method = RequestMethod.POST)
    public void generatingReport (HttpServletResponse response , HttpServletRequest request) throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateFrom = request.getParameter("dateFromRep").replace("T"," ");
        String dateTo = request.getParameter("dateToRep").replace("T"," ");
        String user = request.getParameter("user");
        String canceledBooking = request.getParameter("canceledBooking");

        Integer userId = Integer.valueOf(user);
        Timestamp from = new Timestamp(dateFormat.parse(dateFrom).getTime());
        Timestamp to = new Timestamp(dateFormat.parse(dateTo).getTime());
        Boolean canceled = Boolean.valueOf(canceledBooking);
        List<Booking> reportList = bookingService.generationForReport(from,to,userId,canceled);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");

        XSSFWorkbook workbook = reportBuilder.generateReport(reportList);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
    }
}
