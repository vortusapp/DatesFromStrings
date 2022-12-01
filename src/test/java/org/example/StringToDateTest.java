package org.example;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

class StringToDateTest {
    @Test
    void TestsWork() {
        assertTrue(true);
    }

    @Test
    void stringToDate_returnsDate() {
        LocalDate date = new StringToDate().getStringFromDate("1");
        assertNotNull(date);
    }

@Test
    void stringToDateContains1Integer_returnsDateAsDayOfThisMonth() {
        LocalDate date = new StringToDate().getStringFromDate("1");
        assertEquals(LocalDate.now().getMonth(), date.getMonth());
        assertEquals(LocalDate.now().getYear(), date.getYear());
        assertEquals(1, date.getDayOfMonth());
    }
@Test
    void stringToDateContains2Integers_returnsDateAsDayAndMonthOfThisYear() {
        LocalDate date = new StringToDate().getStringFromDate("1-1");
        assertEquals(LocalDate.now().getYear(), date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

    @Test
    void stringToDateContains3Integers_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1-1-2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

    @Test
    void stringToDateWithIntsDelimitedByDots_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1.1.2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }
    @Test
    void stringToDateWithIntsDelimitedBySpaces_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 1 2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

    @Test
    void intStringInt_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 Feb 2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(2, date.getMonthValue());
    }

    @Test
    void intLongStringInt_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 February 2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(2, date.getMonthValue());
    }

    @Test
    void intShortStringInt_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 DE 2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(12, date.getMonthValue());
    }

    @Test
    void intShortStringIntWithDotDelimiter_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 DE. 2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(12, date.getMonthValue());
    }

    @Test
    void threeIntsWithSpaceAndPunctuation_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 , 1 , 2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

    @Test
    void intInt2Int_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 12 20");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(12, date.getMonthValue());
    }

    @Test
    void intString2Int_returnsDateAsDayMonthAndYear() {
        LocalDate date = new StringToDate().getStringFromDate("1 DE 20");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(12, date.getMonthValue());
    }


    @Test
    void StringOfMonthOnly_ReturnsTodayInThatMonth() {
        LocalDate date = new StringToDate().getStringFromDate("March");
        assertEquals(LocalDate.now().getYear(), date.getYear());
        assertEquals(LocalDate.now().getDayOfMonth(), date.getDayOfMonth());
        assertEquals(3, date.getMonthValue());
    }
    @Test
    void StringOfMonthOnlyWithDot_ReturnsTodayInThatMonth() {
        LocalDate date = new StringToDate().getStringFromDate("March.");
        assertEquals(LocalDate.now().getYear(), date.getYear());
        assertEquals(LocalDate.now().getDayOfMonth(), date.getDayOfMonth());
        assertEquals(3, date.getMonthValue());
    }

    @Test
    void IntString_returnsDateInThisYear(){
        LocalDate date = new StringToDate().getStringFromDate("1 June");
        assertEquals(LocalDate.now().getYear(), date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(6, date.getMonthValue());
    }

    @Test
    void YearIntMonthString_returnsTodayInThatMonthAndYear(){
        LocalDate date = new StringToDate().getStringFromDate("2020 June");
        assertEquals(2020, date.getYear());
        assertEquals(LocalDate.now().getDayOfMonth(), date.getDayOfMonth());
        assertEquals(6, date.getMonthValue());
    }

    @Test
    void YearInt31MonthStringJune_ThrowsException(){
        assertThrows(DateTimeException.class, () -> new StringToDate().getStringFromDate("31 June"));
    }

    @Test
    void If4IntsAreGiven_ThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new StringToDate().getStringFromDate("1 1 1 1"));
    }

    @Test
    void If3IntsWithExtraSpaces_ReturnsDate(){
        LocalDate date = new StringToDate().getStringFromDate("1   1    2020");
        assertEquals(2020, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

    @Test
    void StringDayOfWeek_ReturnsClosestDayOfWeek(){
        LocalDate date = new StringToDate().getStringFromDate("Monday");
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        assertEquals(monday, date);

    }
    @Test
    void TestDateIn22stCentury_ReturnsDate(){
        Clock clock = Clock.fixed(Instant.parse("2118-01-01T00:00:00.00Z"), ZoneId.of("UTC"));

        StringToDate stringToDate = new StringToDate(clock);
        LocalDate date = stringToDate.getStringFromDate("1 1 18");
        assertEquals(2118, date.getYear());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

    @Test
    void Year4IntMonthStringDayInt_ReturnsDate(){
        LocalDate date = new StringToDate().getStringFromDate("2020 June 8");
        assertEquals(2020, date.getYear());
        assertEquals(8, date.getDayOfMonth());
        assertEquals(6, date.getMonthValue());
    }

    @Test
    void month3stringYear4int_returnsDate(){
        LocalDate date = new StringToDate().getStringFromDate("Jan 2020");
        assertEquals(2020, date.getYear());
        assertEquals(LocalDate.now().getDayOfMonth(), date.getDayOfMonth());
        assertEquals(1, date.getMonthValue());
    }

   @Test
    void year2Int_returnsDate(){
        LocalDate date = new StringToDate().getStringFromDate("99");
        assertEquals(1999, date.getYear());
        assertEquals(LocalDate.now().getDayOfMonth(), date.getDayOfMonth());
        assertEquals(LocalDate.now().getMonthValue(), date.getMonthValue());
    }
}