package antdp.demo.database_day11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MyUtils {
    public void copyFile(String src, String des) throws Exception{
        File srcF = new File(src);
        File desF = new File(des);
        FileChannel srcC = new FileInputStream(srcF).getChannel();
        FileChannel desC = new FileOutputStream(desF).getChannel();
        desC.transferFrom(srcC, 0, srcC.size());
    }
}
