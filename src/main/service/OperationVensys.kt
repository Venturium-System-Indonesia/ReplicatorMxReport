package service

import com.prowidesoftware.swift.model.mx.MxPacs00800108
import conn.DBconnection
import sql.SQLTransaksi

class OperationVensys {

    val dBconnection = DBconnection()
    val sqlTransaksi = SQLTransaksi(dBconnection.connection)


    fun operation(){

        // Ambil data terkahir dari record
        val dataLatest = sqlTransaksi.getDataLatest()

        val listOfDataNotRecord = sqlTransaksi.getLatestData(dataLatest)


        for (data in listOfDataNotRecord){

            val stringDataMx = data.xmltext_data

            if (data.mesg_identifier.contains("pacs.008", true)){

                val mxPacs00800108 = MxPacs00800108.parse(stringDataMx)

                sqlTransaksi.insertDataVsyDb(data, mxPacs00800108.toJson())


            }

        }


        if (listOfDataNotRecord.isNotEmpty()){
            // todo : delete data latest
            sqlTransaksi.deleteDataLatest()

            // Insert ke data latast data terkahir
            // karena sudah di order by
            val newDataLatest = listOfDataNotRecord.get(0)
            sqlTransaksi.insertDataLatest(newDataLatest)


        }
    }


}