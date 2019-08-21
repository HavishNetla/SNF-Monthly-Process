import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        Format f = new SimpleDateFormat("hh:mm:ss");
        String strResult = f.format(new Date());
        System.out.println("Time = "+strResult);
    }
}
