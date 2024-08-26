package sql

import com.enterprisedt.util.debug.Logger
import model.DataMessage
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import javax.xml.crypto.Data

class SQLTransaksi(val conn: Connection?) {


    var log = Logger.getLogger(SQLTransaksi::class.java)

    fun getDataLatest():DataMessage?{
        try {
            val sql = "select top(1) aid, xmltext_s_umidl ,xmltext_s_umidh, xmltext_mesg_order,mesg_crea_date_time from [vsyLatestData]"
            val st = conn!!.prepareStatement(sql)

            val resultSet : ResultSet = st.executeQuery()

            while (resultSet.next()){

                val dataMessage = DataMessage();
                dataMessage.aid = resultSet.getString("aid")
                dataMessage.xmltext_s_umidl = resultSet.getString("xmltext_s_umidl")
                dataMessage.xmltext_s_umidh = resultSet.getString("xmltext_s_umidh")
                dataMessage.xmltext_mesg_order = resultSet.getString("xmltext_mesg_order")
                dataMessage.mesg_crea_date_time = resultSet.getString("mesg_crea_date_time")
                return dataMessage;
            }

        } catch (e: SQLException) {

            log.error("addDataHeaderStatus():$e")
        }
        return null
    }

    fun deleteDataLatest(){
        try {
            val sql = "delete [vsyLatestData]"
            val st = conn!!.prepareStatement(sql)

            st.execute()

        } catch (e: SQLException) {

            log.error("Err ():$e")
        }
    }

    fun insertDataLatest(dataMessage : DataMessage){
        try {
            val sql = "insert into [vsyLatestData] (aid, xmltext_s_umidl ,xmltext_s_umidh, xmltext_mesg_order,mesg_crea_date_time) values (?,?,?,?,?)"
            val st = conn!!.prepareStatement(sql)
            st.setString(1, dataMessage.aid)
            st.setString(2, dataMessage.xmltext_s_umidl)
            st.setString(3, dataMessage.xmltext_s_umidh)
            st.setString(4, dataMessage.xmltext_mesg_order)
            st.setString(5, dataMessage.mesg_crea_date_time)

            st.execute()

        } catch (e: SQLException) {

            log.error("Err ():$e")
        }
    }


    fun insertDataVsyDb(dataMessage : DataMessage, json_tag:String){
        try {
            val sql = "insert into [vsyDb] (aid, xmltext_s_umidl ,xmltext_s_umidh, xmltext_mesg_order, json_tag , mesg_crea_date_time, mesg_identifier) " +
                    "values (?,?,?,?,?,?,?)"
            val st = conn!!.prepareStatement(sql)
            st.setString(1, dataMessage.aid)
            st.setString(2, dataMessage.xmltext_s_umidl)
            st.setString(3, dataMessage.xmltext_s_umidh)
            st.setString(4, dataMessage.xmltext_mesg_order)
            st.setString(5, json_tag)
            st.setString(6, dataMessage.mesg_crea_date_time)
            st.setString(7, dataMessage.mesg_identifier)

            st.execute()

        } catch (e: SQLException) {

            log.error("Err ():$e")
        }
    }



    fun getLatestData( dateTime:DataMessage?):List<DataMessage> {

        val listData = arrayListOf<DataMessage>()

        var where:String = "";
        if (dateTime != null){
            where = " WHERE mesg_crea_date_time > '${dateTime.mesg_crea_date_time}'"
        }
        try {
            val sql = "select aid, xmltext_s_umidl ,xmltext_s_umidh, xmltext_mesg_order, xmltext_data, \n" +
                    "mesg_crea_date_time, mesg_identifier  from rXMLTEXT\n" +
                    "${where}\n" +
                    "order by mesg_crea_date_time desc"
            val st = conn!!.prepareStatement(sql)

            val resultSet : ResultSet = st.executeQuery()

            while (resultSet.next()){

                val dataMessage = DataMessage();
                dataMessage.aid = resultSet.getString("aid")
                dataMessage.xmltext_s_umidl = resultSet.getString("xmltext_s_umidl")
                dataMessage.xmltext_s_umidh = resultSet.getString("xmltext_s_umidh")
                dataMessage.xmltext_mesg_order = resultSet.getString("xmltext_mesg_order")
                dataMessage.xmltext_data = resultSet.getString("xmltext_data")
                dataMessage.mesg_crea_date_time = resultSet.getString("mesg_crea_date_time")
                dataMessage.mesg_identifier = resultSet.getString("mesg_identifier")


                listData.add(dataMessage);
            }

        } catch (e: SQLException) {

            log.error("addDataHeaderStatus():$e")
        }
        return listData
    }

}