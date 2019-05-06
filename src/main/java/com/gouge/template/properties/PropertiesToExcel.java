package com.gouge.template.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author GouGe
 * @data 2019/5/6 14:47
 */
public class PropertiesToExcel {

    static int num = 0;

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\properties");
        transformProperitesToExcel(file);
    }

    public static void readPropertiesToExecl(File file, String path) throws IOException {
        Properties p = new Properties();
        InputStream inStream;
        inStream = new FileInputStream(file);
        p.load(inStream);
        Enumeration e = p.propertyNames();
        System.out.println(" 开始导出Excel文件 ");
        XLSExport ee = new XLSExport(path);

        int i = 0;
        ee.createRow(i);
        ee.setCell(0, "KEY");
        ee.setCell(1, "VALUE");
        while (e.hasMoreElements()) {
            String ele = (String) e.nextElement();
            ee.createRow(++i);
            ee.setCell(0, ele);
            ee.setCell(1, p.getProperty(ele));
        }
        System.out.println(file.getName() + "文件总计" + i + "行");
        try {
            ee.exportXLS(path);
            System.out.println(" 导出Excel文件[成功] ");
        } catch (Exception e1) {
            System.out.println(" 导出Excel文件[失败] ");
            e1.printStackTrace();
        } finally {
            System.out.println("--------------------------" + num + "--------------------------");
        }

    }

    public static void transformProperitesToExcel(File file) throws IOException {
        if (file.isDirectory()) {
            File[] subFile = file.listFiles();
            if (subFile.length == 0) {
                file.delete();
            }
            for (int i = 0; i < subFile.length; i++) {
                transformProperitesToExcel(subFile[i]);
            }
        } else {
            if ((!file.getName().endsWith(".PROPERTIES") && !file.getName().endsWith(".properties"))) {
                /*小心误删
                file.delete();*/
            } else {
                String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\'));
                System.out.println("转换第\t" + ++num + "\t个文件：" + file.getAbsolutePath());
                path = path + "\\" + file.getName().substring(0, file.getName().indexOf(".")) + ".xls";
                readPropertiesToExecl(file, path);
            }
        }
    }
}
