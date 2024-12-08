package ir.co.sadad.promissory.services.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.co.sadad.hambaam.persiandatetime.PersianDate;
import ir.co.sadad.hambaam.persiandatetime.PersianDateTime;
import ir.co.sadad.hambaam.persiandatetime.PersianUTC;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.ThirdPartyServicesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DataConverter {

    public static String personTypeConverter(String nationalCode) {
        if (nationalCode == null || nationalCode.isEmpty())
            return null;
        return switch (nationalCode.length()) {
            case 10 -> "I";
            case 11 -> "C";
            default -> throw new PromissoryException("INVALID_NATIONAL_CODE",
                    HttpStatusCode.valueOf(400), HttpStatus.BAD_REQUEST);
        };
    }

    public static int cartableTypeConverter(String nationalCode) {
        return switch (nationalCode.length()) {
            case 10 -> 1;
            case 11 -> 3;
            default -> throw new PromissoryException("INVALID_NATIONAL_CODE",
                    HttpStatusCode.valueOf(400), HttpStatus.BAD_REQUEST);
        };
    }

    public static String convertResponseToJson(ThirdPartyServicesResponse data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (data != null)
                return objectMapper.writeValueAsString(data);
            else return null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String currentUTC() {
        return PersianUTC.currentUTC().toString();
    }

    public static String toPersianDate(String utcDate) {
        return PersianUTC.fromUTC(utcDate).getDate(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String toPersianUtcDate(String persianDate) {
        if (persianDate != null && !persianDate.isEmpty())
            return PersianUTC.toUTCDateOfBeginningOfDay(persianDate, DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss")).toString();
        else return null;
    }

    public static String toPersianUtcDateTime(String persianDate, String persianTime) {
        String persianDteTimeMerged = PersianDateTime.of(PersianDate.parse(persianDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                LocalTime.parse(persianTime, DateTimeFormatter.ofPattern("HH:mm:ss"))).toString();
        return PersianUTC.toUTCDateTime(persianDteTimeMerged, DateTimeFormatter.ofPattern("yyyyMMddHH:mm:ss")).toString();
    }

    public Map<String, Integer> getSubListInRange(int listSize, int page_number, int page_size) {
        int startIndex = (page_number - 1) * page_size;
        int endIndex = Math.min(startIndex + page_size, listSize);

        startIndex = Math.max(startIndex, 0);  // ensure startIndex is not negative
        endIndex = Math.min(endIndex, listSize);

        Map<String, Integer> res = new HashMap<>();
        res.put("startIndex", startIndex);
        res.put("endIndex", endIndex);
        return res;
    }
}
