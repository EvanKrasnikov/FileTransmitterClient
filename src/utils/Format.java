package utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Format {
    public static  String getFormattedSizeOfFile(long size){ // форматированный размер файла
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;

        if (size > sizeMb) return decimalFormat.format(size / sizeMb) + " Mb";
        else if (size > sizeGb) return decimalFormat.format(size / sizeGb) + " Gb";
        return decimalFormat.format(size / sizeKb) + " Kb";
    }

    public static String getFormattedDate(long date){ //форматированая дата
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }
}
