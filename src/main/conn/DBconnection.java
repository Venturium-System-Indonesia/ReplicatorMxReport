/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conn;

import attribute.AKoneksiData;
import com.enterprisedt.util.debug.Logger;
import model.MKoneksiData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author M Abdul AplDev2
 */
public class DBconnection {

    Connection conn = null;
    AKoneksiData atribute = new AKoneksiData();
    MKoneksiData data = new MKoneksiData();
    Logger log = Logger.getLogger(DBconnection.class);

    public DBconnection() {
        data = atribute.getAtributeKoneksiData();
        try {
            Class.forName(data.getDriver());
        } catch (ClassNotFoundException ex) {
            log.error("DBconnection():" + ex.toString());
        }
        try {
            conn = DriverManager.getConnection(data.getUrl(), data.getUser(), data.getPassword());
        } catch (SQLException ex) {
            log.error("DBconnection():" + ex.toString());
        }
    }

    public Connection getConnection() {
//        System.out.println("DBconnection getConnection()");
        log.info("getConnection() is successfully");
        return this.conn;
    }

    public void closeConnection() {
        if (this.conn != null) {
            try {
//                System.out.println("DBconnection closeConnection()");
                log.info("closeConnection() is successfully");
                this.conn.close();
            } catch (Exception ex) {
//                System.err.println(ex);
                log.error("closeConnection():" + ex.toString());
            }
        }
    }
//    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
//        // TODO code application logic here
//        new DBconnection();
//    }
}
