/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package attribute;

import com.enterprisedt.util.debug.Logger;
import model.MKoneksiData;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *
 * @author AplDev2
 */
public class AKoneksiData {

    Logger log = Logger.getLogger(AKoneksiData.class);

    public MKoneksiData getAtributeKoneksiData() {
        MKoneksiData data = new MKoneksiData();
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("properties/konfigurasi.properties"));
        } catch (IOException ex) {
            log.error("getAtributeKoneksiData():" + ex.toString());
        }
        data.setDriver(prop.getProperty("driver"));
        data.setUrl(prop.getProperty("url"));
        data.setUser(prop.getProperty("user"));
        data.setPassword(prop.getProperty("password"));

        return data;
    }
}
