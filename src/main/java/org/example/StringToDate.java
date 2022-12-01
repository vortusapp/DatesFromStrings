package org.example;

import java.time.*;
import java.time.temporal.TemporalAdjusters;




public class StringToDate {

    private boolean yearSet = false;
    private boolean monthSet = false;
    private Clock clock;
    private int year;
    private int month;
    private int day;

    StringToDate(){
        setClock(Clock.systemDefaultZone());
    }

    StringToDate(Clock clock){
        setClock(clock);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
        year = LocalDate.now(clock).getYear();
        month = LocalDate.now(clock).getMonthValue();
        day= LocalDate.now(clock).getDayOfMonth();
    }

    public LocalDate getStringFromDate(String s) throws IllegalArgumentException, DateTimeException{
        String[] stringArray = getRegexSplit(s);
        for (int i = 0; i < stringArray.length; i++) {
            if (i == 0) {
                useFirstString(stringArray[i]);
            } else if (i == 1) {
                useSecondString(stringArray[i]);
            } else if (i == 2) {
                useThirdString(stringArray[i]);
            } else {
                throw new IllegalArgumentException("Too many arguments in date string");
            }
        }
       return LocalDate.of(year, month,day);
    }

    private String[] getRegexSplit(String s) {
        return s.split("\\p{P}+\\s+|\\s+\\p{P}+\\s+|\\p{P}+|\\s+");
    }

    private void useFirstString(String firstString) {
        if (isWords(firstString)) {
            setFromWords(firstString);
        } else if (isYear(firstString)) {
            setYear(firstString);
        } else {
            setDay(firstString);
        }
    }

    private void useSecondString(String secondString) {
        if (isWords(secondString)) {
            setFromWords(secondString);
        } else if (monthSet) {
            setYear(secondString);
        } else {
            setMonth(secondString);
        }
    }
    private void useThirdString(String thirdString) {
        if (yearSet){
            setDay(thirdString);
        } else {

              setYear(thirdString);}
        }


    private void setFromWords(String wordsString) {
        int monthFromString = getMonthFromMonthWord(wordsString);
        if (isMonthValid(monthFromString)) {
            setMonth(monthFromString+"");
        } else {
            setDateFromDayOfWeekWord(wordsString);
        }
    }

    private boolean isMonthValid(int monthFromString) {
        return !(monthFromString == -1);
    }

    private void setDateFromDayOfWeekWord(String dayOfTheWeekWord) {
        LocalDate date = LocalDate.now(clock);
        DayOfWeek dayOfTheWeek = DayOfWeek.valueOf(dayOfTheWeekWord.toUpperCase());
        date = date.with(TemporalAdjusters.nextOrSame(dayOfTheWeek));
        setDay(date.getDayOfMonth() + "");
        setYear(date.getYear() + "");
        setMonth(date.getMonthValue() + "");
    }


    private void setDay(String dayString){
        day = Integer.parseInt(dayString);
    }

    private void setMonth(String monthString){
        month = Integer.parseInt(monthString);
        monthSet = true;
    }

    private void setYear(String yearString) {
        if (yearString.length() == 1) {
            yearString = "0" + yearString;
        }
        if (yearString.length() == 2) {
            String dateFirstPart = (LocalDateTime.now(clock).getYear() + "").substring(0,2); //You're not going to get me Y2.1K bug
            yearString = dateFirstPart + yearString;
            if (Integer.parseInt(yearString) > LocalDateTime.now(clock).getYear() + 50) {
                yearString = (Integer.parseInt(yearString) - 100) + "";
            }
        }
        year = Integer.parseInt(yearString);
        yearSet = true;
    }

    private boolean isYear(String dayString) {
        return dayString.length() == 4 | Integer.parseInt(dayString) > 31;
    }

    private boolean isWords(String dayString) {
        return dayString.matches("[a-zA-Z]+");
    }

    private int getMonthFromMonthWord(String splitStrings) {
        return switch (splitStrings.toLowerCase()) {
            case "jan", "january", "ja" -> 1;
            case "feb", "february", "fe" -> 2;
            case "mar", "march", "mr" -> 3;
            case "apr", "april", "ap" -> 4;
            case "may", "my" -> 5;
            case "jun", "june", "jn" -> 6;
            case "jul", "july", "jl" -> 7;
            case "aug", "august", "au" -> 8;
            case "sep", "sept", "september", "se" -> 9;
            case "oct", "october", "oc" -> 10;
            case "nov", "november", "nv" -> 11;
            case "dec", "december", "de" -> 12;
            default -> -1;
        };
    }


}