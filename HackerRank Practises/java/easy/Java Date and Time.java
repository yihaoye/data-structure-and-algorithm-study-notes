/*
The Calendar class is an abstract class that provides methods for converting between a specific instant in time and a set of calendar fields such as YEAR, MONTH, DAY_OF_MONTH, HOUR, and so on, and for manipulating the calendar fields, such as getting the date of the next week.

You are given a date. You just need to write the method, getDay, which returns the day on that date. To simplify your task, we have provided a portion of the code in the editor.

For example, if you are given the date Aug 14th 2017, the method should return Monday as the day on that date.

![](https://s3.amazonaws.com/hr-assets/0/1514458312-c097047ed4-calendar_class.png)

Input Format

A single line of input containing the space separated month, day and year, respectively, in MM DD YYYY format.

Constraints
* 2000 < year < 3000

Output Format

Output the correct day in capital letters.

Sample Input

08 05 2015
Sample Output

WEDNESDAY
Explanation

The day on August 5th 2015 was WEDNESDAY.
*/



// My Solution:
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'findDay' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER month
     *  2. INTEGER day
     *  3. INTEGER year
     */

    public static String findDay(int month, int day, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1); // Because the month is zero based, so should have subtracted 1 from the month.
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int month = Integer.parseInt(firstMultipleInput[0]);

        int day = Integer.parseInt(firstMultipleInput[1]);

        int year = Integer.parseInt(firstMultipleInput[2]);

        String res = Result.findDay(month, day, year);

        bufferedWriter.write(res);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}



// 题外补充：
import java.util.Date;
import java.util.Calendar;

public class DateDemo {
   public static void main(String args[]) {
      // Instantiate a Date object
      Date date = new Date();

      // long millis = System.currentTimeMillis();  
      // Date date = new Date(millis);

      // Calendar calendar = Calendar.getInstance();
      // calendar.set(2009, 5, 4, 9, 51, 52);
      // Date date = calendar.getTime();

      // display time and date using toString()
      System.out.println(date.toString());
   }
}
// This will produce the following result −
// "on May 04 09:51:52 CDT 2009"

/*
Date 或 Calendar 的 compare 案例
*/
// Date
public class DateCompareDemo {
    Date historyDate = new Date();
    Date todayDate = new Date();
    Date futureDate = new Date();

    todayDate.after(historyDate);
    todayDate.before(futureDate);
    todayDate.equals(todayDate);

    todayDate.compareTo(historyDate); // > 0 
    todayDate.compareTo(futureDate); // < 0
    todayDate.compareTo(todayDate); // == 0
}

// Calendar
public class CalendarCompareDemo {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    if(cal1.after(cal2)){
        System.out.println("Date1 is after Date2");
    }
    if(cal1.before(cal2)){
        System.out.println("Date1 is before Date2");
    }
    if(cal1.equals(cal2)){
        System.out.println("Date1 is equal Date2");
    }
}



/*
LocalDate

The LocalDate class represents a date-only value without time-of-day and without time zone.
It is advised migrate to LocalDate from Joda-Time or java.util.Date.
java.util.Date VS java.time.LocalDate: https://github.com/yihaoye/data-structure-and-algorithm-study-notes/blob/master/HackerRank%20Practises/java/Java-8-Date-and-LocalDate.png

A time zone is crucial in determining a date. 
For any given moment, the date varies around the globe by zone. 
For example, a few minutes after midnight in Paris France is a new day while still “yesterday” in Montréal Québec.

The java.time framework is built into Java 8 and later. 
These classes supplant the troublesome old legacy date-time classes such as java.util.Date, Calendar, & SimpleDateFormat.
*/
import java.util.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.time.ZonedDateTime;
//import java.time.ZoneId;

public class LocalDateDemo {

   public static void main(String args[]) {
      LocalDate localDate = LocalDate.now(); // Create a date object
      // LocalDate localDate = LocalDate.of(2004, 7, 18);
      // ZoneId zoneId = ZoneId.of("Asia/Singapore");
      // ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

      LocalDateTime localDateTime = LocalDateTime.now();
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      
      Date dNow = new Date(); // build-in java.util.Date
      // TimeZone tz = TimeZone.getTimeZone("UTC"); // java.util.TimeZone
      // DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
      // df.setTimeZone(tz); // strip timezone
      // df.format(dNow);
      SimpleDateFormat ft1 = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
      SimpleDateFormat ft2 = new SimpleDateFormat ("yyyy-MM-dd");

      System.out.println(localDate);
      System.out.println(localDateTime.format(dateTimeFormatter));
      System.out.println("Current Date: " + ft1.format(dNow));
      System.out.println(ft2.format(dNow));
   }
}
// This will produce the following result −
// "2004-07-18"
// "18-07-2004 04:14:09"
// "Current Date: Sun 2004.07.18 at 04:14:09 PM PDT"
// "2004-07-18"

import java.time.LocalDate;
import java.time.LocalDateRange;
import java.time.ZoneId;
import java.time.DateTimeFormatter;

public class LocalDateCompareDemo {
    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate start = LocalDate.parse("22-02-2010", f);
    LocalDate stop = LocalDate.parse("25-12-2010", f);
    LocalDate today = LocalDate.now(ZoneId.of("America/Montreal"));

    // intervalContainsToday
    Boolean isBetween = 
        (!today.isBefore(start))  // “not-before” is short for “is-equal-to or later-than”.
        &&
        today.isBefore(stop);
}
