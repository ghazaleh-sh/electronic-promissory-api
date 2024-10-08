package ir.co.sadad.promissory.services.utils;

import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataConverterTest {

    @Test
    void personTypeConverter() {
        String nationalCode1 = "0079993141";
        String nationalCode2 = "09090909";
        String nationalCode3 = "01234567891";

        assertEquals("I", DataConverter.personTypeConverter(nationalCode1));
        assertThrows(PromissoryException.class, () -> DataConverter.personTypeConverter(nationalCode2));
        assertEquals("C", DataConverter.personTypeConverter(nationalCode3));
    }

    @Test
    void toPersianDate() {
        String utcInput = "2024-09-15T10:03:00.000Z";

        assertEquals("14030625", DataConverter.toPersianDate(utcInput));

    }

    @Test
    void toPersianUtcDate() {
        String persianDate = "1403/06/31";
        assertEquals("2024-09-21T00:00:00.000Z", DataConverter.toPersianUtcDate(persianDate));

    }

    @Test
    void toPersianUtcDateTime() {
        String persianDate = "1403/07/01";
        String persianTime = "18:02:59";

        assertEquals("2024-09-22T14:32:59.000Z", DataConverter.toPersianUtcDateTime(persianDate, persianTime));

    }
}