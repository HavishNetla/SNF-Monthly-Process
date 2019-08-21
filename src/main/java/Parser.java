import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class Parser {
    public static void main(String[] args) throws IOException {
        URL url = new URL(
                "https://data.medicare.gov/views/bg9k-emty/files/3d2f9d32-a5e2-484a-b10a-880b2b64acc9?content_type=application%2Fzip%3B%20charset%3Dbinary&filename=NursingHomeCompare_Revised_Flatfiles.zip");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream in = connection.getInputStream();
        FileOutputStream out = new FileOutputStream("NursingHomeCompare_Revised_Flatfiles.zip");
        copy(in, out, 1024);
        out.close();

        String source = "NursingHomeCompare_Revised_Flatfiles.zip";
        String dest = "NursingHomeCompare_Revised_Flatfiles";
        System.out.println("Extracting...");
        new ZipFile(source).extractAll(dest);

        File folder = new File("NursingHomeCompare_Revised_Flatfiles");
        System.out.println("Removing useless stuff...");
        removeUselessStuff(folder);

        File file = new File("NursingHomeCompare_Revised_Flatfiles/ProviderInfo_Download.csv");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYMMdd");
        String date = LocalDate.now().format(formatter); //Things might break in the server when its 2119, but your contract should have expired by then

        Format f = new SimpleDateFormat("hh:mm:ss");
        String strResult = f.format(new Date());
        String time = strResult.replace(":", ""); //its normal time so 6:21 pm is just 6:21

        file.renameTo(new File("P#MDE.IN.SNF.PRVDR.D" + date + ".T" + time));

        new File("NursingHomeCompare_Revised_Flatfiles").delete();
        new File("NursingHomeCompare_Revised_Flatfiles.zip").delete();
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buf = new byte[bufferSize];
        int n = input.read(buf);
        System.out.println("copying...");
        while (n >= 0) {
            output.write(buf, 0, n);
            n = input.read(buf);
        }
        output.flush();
    }

    public static void removeUselessStuff(File dir) {
        for (File file : dir.listFiles()) {
            if (file.getName().equals("ProviderInfo_Download.csv")) {
            } else {
                file.delete();
            }
        }
    }
}
