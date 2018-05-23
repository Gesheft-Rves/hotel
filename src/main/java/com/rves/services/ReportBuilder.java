package com.rves.services;

import com.rves.pojo.Booking;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportBuilder {

    @Autowired
    private BookingService bookingService;

    public XSSFWorkbook generateExample() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
//        private Integer id;
//        private Timestamp date_buking;
//        private Room room;
//        private Date arrival_date;
//        private Date date_of_departure;
//        private User user;
//        private boolean canceled;
        int rowNum = 0;
        List<Booking> bookings = bookingService.list();
        for (Booking booking : bookings) {
            Row row = sheet.createRow(rowNum++);

            int cellNum = 0;
            Cell idCell = row.createCell(cellNum++);
//            Cell bookingDateCell = row.createCell(cellNum++);
            Cell roomCell = row.createCell(cellNum++);

            idCell.setCellValue(booking.getId());
            roomCell.setCellValue(booking.getRoom().getNo());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            bookingDateCell.setCellValue(dateFormat.format( new Date(booking.getDate_buking().getTime()));
        }

        return workbook;
    }

}
