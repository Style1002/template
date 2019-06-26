package com.gouge.template.buildData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * @author GouGe
 * @data 2019/6/20 10:35
 */
public class Maridb {

    private static String user = "root",
            pass = "111111",
            URL = "jdbc:mysql://192.168.10.204:3306/test";
    private static int num = 0,
            batchSize = 70000,
            list = 700000;

    /**
     * 70W数据耗时52min
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        //连接mysql数据库
        Connection conn = DriverManager.getConnection(URL, user, pass);
        conn.setAutoCommit(false);
        //向mysql中插入数据
        String sql = "insert into score(sc_id, s_id, c_id, score) values(?, ?, ?, ?)";
        //要执行sql语句的对象
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < list; i++) {
            ps.setInt(1, ++num);
            ps.setInt(2, new Random().nextInt(70000)+1);
            ps.setInt(3, new Random().nextInt(100)+1);
            ps.setDouble(4, new Random().nextInt(100));
            ps.addBatch();//再添加一次预定义参数
            if (i != 0 && ((i + 1) % batchSize == 0)) {
                ps.executeBatch();//执行批量执行
                ps.clearBatch();
                ps.close();
                System.out.println(System.currentTimeMillis() / 1000);
                ps = conn.prepareStatement(sql);
            }
        }
        if (list % batchSize != 0) {
            ps.executeBatch();
            ps.close();
        }
        conn.commit();
        ps.clearBatch();
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
