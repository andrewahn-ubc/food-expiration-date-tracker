package model;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static model.Item.dateToStr;
import static model.Item.strToDate;
import static org.junit.jupiter.api.Assertions.*;

// Unit test for the Item class.
public class ItemTest {
    @Test
    public void testConstructor() {
        Calendar c1 = Calendar.getInstance();
        c1.set(123 + 1900, Calendar.SEPTEMBER, 5);

        Date date = c1.getTime();
        Item i = new Item("Beef", date, "Meat");

        String oldName = i.getName();
        assertEquals("Beef", oldName);

        Date oldDate = i.getExpDate();
        assertEquals(date, oldDate);

        String oldCat = i.getCategory();
        assertEquals("Meat", oldCat);
    }

    @Test
    public void testDateConversions() {
        Item item = null;
        String strExpDate = null;
        Date correctDateDate = null;

        try {
            item = new Item("toast", "01/01/2020", "carb");
            strExpDate = dateToStr(item.getExpDate());
            correctDateDate = strToDate(strExpDate);
        } catch (ParseException p) {
            fail("ParseException shouldn't have been thrown.");
        }

        assertEquals("01/01/2020", strExpDate);
        assertEquals(correctDateDate, item.getExpDate());
    }
}
