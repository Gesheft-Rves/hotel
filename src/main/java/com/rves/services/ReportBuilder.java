package com.rves.services;

import com.rves.pojo.Booking;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class ReportBuilder {

    @Autowired
    private BookingService bookingService;

    public XSSFWorkbook generateReport(List<Booking> bookings) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat timestampDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

        int rowNum = 0;

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        Row header = sheet.createRow(rowNum++);
        int cellNum = 0;
        Cell bookingDateCellHeader = header.createCell(cellNum++);
        Cell roomCellHeader = header.createCell(cellNum++);
        Cell arrivalDateCellHeader = header.createCell(cellNum++);
        Cell dateOfDepartureCellHeader = header.createCell(cellNum++);
        Cell userCellHeader = header.createCell(cellNum++);

        bookingDateCellHeader.setCellValue("Дата бронирования");
        roomCellHeader.setCellValue("Номер");
        arrivalDateCellHeader.setCellValue("Дата заезда");
        dateOfDepartureCellHeader.setCellValue("Дата выезда");
        userCellHeader.setCellValue("Администратор");

        for (Booking booking : bookings) {
            Row row = sheet.createRow(rowNum++);

            cellNum = 0;
            Cell bookingDateCell = row.createCell(cellNum++);
            Cell roomCell = row.createCell(cellNum++);
            Cell arrivalDateCell = row.createCell(cellNum++);
            Cell dateOfDepartureCell = row.createCell(cellNum++);
            Cell userCell = row.createCell(cellNum++);

            String strBookingDate = booking.getDateBuking().toString().replace("T"," ");

            bookingDateCell.setCellValue(strBookingDate);
            roomCell.setCellValue(booking.getRoom().getNo());
            arrivalDateCell.setCellValue(dateFormat.format(booking.getArrivalDate()));
            dateOfDepartureCell.setCellValue(dateFormat.format(booking.getDateOfDeparture()));
            userCell.setCellValue(booking.getUser().getUsername());
        }

        return workbook;
    }

}
