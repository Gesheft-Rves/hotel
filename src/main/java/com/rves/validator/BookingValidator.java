package com.rves.validator;


import com.rves.Dto.BookingDto;
import com.rves.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


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

        if (!isValidArrivalDate(booking.getArrival_date())) {
            errors.rejectValue("arrival_date", "error.message.invalid_arrival_date");
        }

        if(booking.getDate_of_departure().before(booking.getArrival_date())){
            errors.rejectValue("date_of_departure", "error.message.invalid_date_of_departure");
        }
    }

    private boolean isValidArrivalDate(Date d){
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        currentDate.set(Calendar.MILLISECOND,0);
        currentDate.set(Calendar.SECOND,0);
        currentDate.set(Calendar.MINUTE,0);
        currentDate.set(Calendar.HOUR,0);

        GregorianCalendar arrivalDate = new GregorianCalendar();
        arrivalDate.setTime(d);
        arrivalDate.set(Calendar.MILLISECOND,0);
        arrivalDate.set(Calendar.SECOND,0);
        arrivalDate.set(Calendar.MINUTE,0);
        arrivalDate.set(Calendar.HOUR,0);

        if (arrivalDate.get(Calendar.YEAR) < currentDate.get(Calendar.YEAR)){
            return false;
        }
        if (arrivalDate.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH)){
            return false;
        }
        if (arrivalDate.get(Calendar.DATE) < currentDate.get(Calendar.DATE)){
            return false;
        }
//        if (arrivalDate.get(Calendar.HOUR) < currentDate.get(Calendar.HOUR)){
//            return false;
//        }

        return true;
    }

}
