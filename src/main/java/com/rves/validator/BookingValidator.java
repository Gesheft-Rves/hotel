package com.rves.validator;


import com.rves.Dto.BookingDto;
import com.rves.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Component
public class BookingValidator implements Validator {

    @Autowired
    private BookingService bookingService;

    @Override
    public boolean supports(Class<?> aClass) {
        return BookingDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookingDto booking = (BookingDto) o ;

        if (booking.getRoom() == null) {
            errors.rejectValue("room", "error.message.no_room_available");
        }

        if (!isValidArrivalDate(booking.getArrivalDate())) {
            errors.rejectValue("arrivalDate", "error.message.invalid_arrival_date");
        }

        if(booking.getDateOfDeparture().before(booking.getArrivalDate())){
            errors.rejectValue("dateOfDeparture", "error.message.invalid_date_of_departure");
        }
    }

    private boolean isValidArrivalDate(Timestamp timestampArrivalDate){
        GregorianCalendar currentDate = new GregorianCalendar();
        Timestamp currentTimestampDate = new Timestamp (new Date().getTime());
        currentDate.setTimeInMillis(currentTimestampDate.getTime());


        GregorianCalendar arrivalDate = new GregorianCalendar();
        arrivalDate.setTimeInMillis(timestampArrivalDate.getTime());


        int arrYear = arrivalDate.get(Calendar.YEAR);
        int currYear = currentDate.get(Calendar.YEAR);

        int arrMonth = arrivalDate.get(Calendar.MONTH);
        int currMonth = currentDate.get(Calendar.MONTH);

        int arrDay = arrivalDate.get(Calendar.DATE);
        int currDay = currentDate.get(Calendar.DATE);

        int arrHour = arrivalDate.get(Calendar.HOUR);
        int currHour = currentDate.get(Calendar.HOUR);

        int arrMinute = arrivalDate.get(Calendar.MINUTE);
        int currMinute = currentDate.get(Calendar.MINUTE);

        if (arrYear < currYear){
            return false;
        } else if (arrMonth < currMonth && arrYear <= currYear){
            return false;
        } else if (arrDay < currDay && arrMonth <= currMonth && arrYear <= currYear){
            return false;
        } else if (arrHour < currHour && arrDay <= currDay && arrMonth <= currMonth && arrYear <= currYear){
            return false;
        } else if (arrMinute < currMinute && arrHour <= currHour && arrDay <= currDay && arrMonth <= currMonth && arrYear <= currYear){
            return false;
        }

        return true;
    }
}
