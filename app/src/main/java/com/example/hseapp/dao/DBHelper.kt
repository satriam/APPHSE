package com.example.hseapp.dao

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.gson.Gson
import java.sql.Types.NULL

class  DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "my_database.db"
        const val STATUS_NOT_SENT = 0
        const val STATUS_SENT = 1
        private val COLUMN_ID = "id"

    }

    override fun onCreate(db: SQLiteDatabase) {
        // Membuat tabel table_jawaban
        db.execSQL(
            "CREATE TABLE jawaban (" +
                    "id INTEGER PRIMARY KEY," + "created_by_id INTEGER,"+ "nama_lokasi TEXT," +
                    "nama_loading TEXT," + "shift TEXT," + "grup TEXT," +"kondisi1 TEXT," + "Kode_bahaya1 TEXT," +
                    "Keterangan1 TEXT," + "kondisi2 TEXT," +
                    "Kode_bahaya2 TEXT," + "Keterangan2 TEXT," +
                    "kondisi3 TEXT," + "Kode_bahaya3 TEXT," +
                    "Keterangan3 TEXT," + "kondisi4 TEXT," +
                    "Kode_bahaya4 TEXT," + "Keterangan4 TEXT," + "kondisi5 TEXT," +
                    "Kode_bahaya5 TEXT," + "Keterangan5 TEXT," +
                    "kondisi6 TEXT," + "Kode_bahaya6 TEXT," +
                    "Keterangan6 TEXT," + "kondisi7 TEXT," +
                    "Kode_bahaya7 TEXT," + "Keterangan7 TEXT," +
                    "kondisi8 TEXT," + "Kode_bahaya8 TEXT," +
                    "Keterangan8 TEXT," + "kondisi9 TEXT," +
                    "Kode_bahaya9 TEXT," + "Keterangan9 TEXT," +
                    "kondisi10 TEXT," + "Kode_bahaya10 TEXT," +
                    "Keterangan10 TEXT," + "kondisi11 TEXT," +
                    "Kode_bahaya11 TEXT," + "Keterangan11 TEXT," +
                    "kondisi12 TEXT," + "Kode_bahaya12 TEXT," +
                    "Keterangan12 TEXT," + "kondisi13 TEXT," +
                    "Kode_bahaya13 TEXT," + "Keterangan13 TEXT," +
                    "kondisi14 TEXT," + "Kode_bahaya14 TEXT," +
                    "Keterangan14 TEXT," + "kondisi15 TEXT," +
                    "Kode_bahaya15 TEXT," + "Keterangan15 TEXT," +
                    "kondisi16 TEXT," + "Kode_bahaya16 TEXT," +
                    "Keterangan16 TEXT," + "kondisi17 TEXT," +
                    "Kode_bahaya17 TEXT," + "Keterangan17 TEXT," +
                    "kondisi18 TEXT," + "Kode_bahaya18 TEXT," +
                    "Keterangan18 TEXT," + "kondisi19 TEXT," +
                    "Kode_bahaya19 TEXT," + "Keterangan19 TEXT," +
                    "kondisi20 TEXT," + "Kode_bahaya20 TEXT," +
                    "Keterangan20 TEXT," + "kondisi21 TEXT," +
                    "Kode_bahaya21 TEXT," + "Keterangan21 TEXT," +
                    "kondisi22 TEXT," + "Kode_bahaya22 TEXT," +
                    "Keterangan22 TEXT," + "kondisi23 TEXT," +
                    "Kode_bahaya23 TEXT," + "Keterangan23 TEXT," +
                    "kondisi24 TEXT," + "Kode_bahaya24 TEXT," +
                    "Keterangan24 TEXT," + "kondisi25 TEXT," +
                    "Kode_bahaya25 TEXT," + "Keterangan25 TEXT," +
                    "kondisi26 TEXT," + "Kode_bahaya26 TEXT," + "Keterangan26 TEXT," +
                    "Created_at TEXT,"+"status TEXT,"+
                    "Nama_Pengawas TEXT,"+ "Image1 TEXT,"+"Tindakan1 TEXT,"+"Tindakan2 TEXT,"+"Image2 TEXT, "+" Nama_Supervisor )"
        )


        db.execSQL(
            "CREATE TABLE dumping (" +
                    "id INTEGER PRIMARY KEY," + "created_by_id INTEGER," + "nama_lokasi TEXT," +
                    "nama_dumping TEXT," + "shift TEXT," + "grup TEXT," +"kondisi1 TEXT," + "Kode_bahaya1 TEXT," +
                    "Keterangan1 TEXT," + "kondisi2 TEXT," +
                    "Kode_bahaya2 TEXT," + "Keterangan2 TEXT," +
                    "kondisi3 TEXT," + "Kode_bahaya3 TEXT," +
                    "Keterangan3 TEXT," + "kondisi4 TEXT," +
                    "Kode_bahaya4 TEXT," + "Keterangan4 TEXT," + "kondisi5 TEXT," +
                    "Kode_bahaya5 TEXT," + "Keterangan5 TEXT," +
                    "kondisi6 TEXT," + "Kode_bahaya6 TEXT," +
                    "Keterangan6 TEXT," + "kondisi7 TEXT," +
                    "Kode_bahaya7 TEXT," + "Keterangan7 TEXT," +
                    "kondisi8 TEXT," + "Kode_bahaya8 TEXT," +
                    "Keterangan8 TEXT," + "kondisi9 TEXT," +
                    "Kode_bahaya9 TEXT," + "Keterangan9 TEXT," +
                    "kondisi10 TEXT," + "Kode_bahaya10 TEXT," +
                    "Keterangan10 TEXT," + "kondisi11 TEXT," +
                    "Kode_bahaya11 TEXT," + "Keterangan11 TEXT," +
                    "kondisi12 TEXT," + "Kode_bahaya12 TEXT," +
                    "Keterangan12 TEXT," + "kondisi13 TEXT," +
                    "Kode_bahaya13 TEXT," + "Keterangan13 TEXT," +
                    "kondisi14 TEXT," + "Kode_bahaya14 TEXT," +
                    "Keterangan14 TEXT," + "kondisi15 TEXT," +
                    "Kode_bahaya15 TEXT," + "Keterangan15 TEXT," +
                    "kondisi16 TEXT," + "Kode_bahaya16 TEXT," +
                    "Keterangan16 TEXT," + "kondisi17 TEXT," +
                    "Kode_bahaya17 TEXT," + "Keterangan17 TEXT," +
                    "kondisi18 TEXT," + "Kode_bahaya18 TEXT," +
                    "Keterangan18 TEXT," + "kondisi19 TEXT," +
                    "Kode_bahaya19 TEXT," + "Keterangan19 TEXT," +
                    "kondisi20 TEXT," + "Kode_bahaya20 TEXT," +
                    "Keterangan20 TEXT," +
                    "Created_at TEXT,"+"status TEXT ,"+
                    "Nama_Pengawas TEXT,"+ "Image1 TEXT,"+"Tindakan1 TEXT,"+"Tindakan2 TEXT,"+"Image2 TEXT, "+" Nama_Supervisor )"
        )


        db.execSQL(
            "CREATE TABLE hauling (" +
                    "id INTEGER PRIMARY KEY," + "created_by_id INTEGER," + "nama_lokasi TEXT," +
                    "nama_hauling TEXT," + "shift TEXT," + "grup TEXT," +"kondisi1 TEXT," + "Kode_bahaya1 TEXT," +
                    "kondisi2 TEXT," + "Kode_bahaya2 TEXT," +"kondisi3 TEXT," + "Kode_bahaya3 TEXT," +
                    "kondisi4 TEXT," +"Kode_bahaya4 TEXT," + "kondisi5 TEXT," + "Kode_bahaya5 TEXT,"+"kondisi6 TEXT," +
                    "Kode_bahaya6 TEXT," +"kondisi7 TEXT," +"Kode_bahaya7 TEXT," + "kondisi8 TEXT," + "Kode_bahaya8 TEXT," +
                    "kondisi9 TEXT," +"Kode_bahaya9 TEXT,"+"kondisi10 TEXT," + "Kode_bahaya10 TEXT," +"kondisi11 TEXT," +
                    "Kode_bahaya11 TEXT,"+"kondisi12 TEXT," + "Kode_bahaya12 TEXT,"+ "kondisi13 TEXT," +"Kode_bahaya13 TEXT,"+
                    "kondisi14 TEXT," + "Kode_bahaya14 TEXT,"+ "kondisi15 TEXT," +"Kode_bahaya15 TEXT," +"kondisi16 TEXT," +
                    "Kode_bahaya16 TEXT," + "kondisi17 TEXT," +"Kode_bahaya17 TEXT," +"kondisi18 TEXT," +"Kode_bahaya18 TEXT,"+"kondisi19 TEXT," +
                    "Kode_bahaya19 TEXT," +"kondisi20 TEXT," + "Kode_bahaya20 TEXT," +"kondisi21 TEXT," + "Kode_bahaya21 TEXT," +
                    "kondisi22 TEXT," + "Kode_bahaya22 TEXT," +"kondisi23 TEXT," + "Kode_bahaya23 TEXT," +"kondisi24 TEXT," + "Kode_bahaya24 TEXT," +
                    "kondisi25 TEXT," + "Kode_bahaya25 TEXT," +"kondisi26 TEXT," + "Kode_bahaya26 TEXT," +"kondisi27 TEXT," + "Kode_bahaya27 TEXT," +
                    "kondisi28 TEXT," + "Kode_bahaya28 TEXT," +"kondisi29 TEXT," + "Kode_bahaya29 TEXT," +"kondisi30 TEXT," + "Kode_bahaya30 TEXT," +
                    "Created_at TEXT,"+"status TEXT,"+
                    "Nama_Pengawas TEXT,"+ "Image1 TEXT,"+"Tindakan1 TEXT,"+"Tindakan2 TEXT,"+"Image2 TEXT, "+" Nama_Supervisor  )"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS jawaban")
        db.execSQL("DROP TABLE IF EXISTS dumping")
        db.execSQL("DROP TABLE IF EXISTS hauling")
        onCreate(db)
    }

    fun getAllData(): List<AnswerEntity> {
        val dataList = mutableListOf<AnswerEntity>()
        val db = this.readableDatabase
        val query = "SELECT * FROM jawaban"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val iduser = cursor.getInt(cursor.getColumnIndex("created_by_id"))
                val lokasi = cursor.getString(cursor.getColumnIndex("nama_lokasi"))
                val loading = cursor.getString(cursor.getColumnIndex("nama_loading"))
                val shift = cursor.getString(cursor.getColumnIndex("shift"))
                val grup = cursor.getString(cursor.getColumnIndex("grup"))
                val ck1 = cursor.getString(cursor.getColumnIndex("kondisi1"))
                val kode1 = cursor.getString(cursor.getColumnIndex("Kode_bahaya1"))
                val ket1 = cursor.getString(cursor.getColumnIndex("Keterangan1"))
                val ck2 = cursor.getString(cursor.getColumnIndex("kondisi2"))
                val kode2 = cursor.getString(cursor.getColumnIndex("Kode_bahaya2"))
                val ket2 = cursor.getString(cursor.getColumnIndex("Keterangan2"))
                val ck3 = cursor.getString(cursor.getColumnIndex("kondisi3"))
                val kode3 = cursor.getString(cursor.getColumnIndex("Kode_bahaya3"))
                val ket3 = cursor.getString(cursor.getColumnIndex("Keterangan3"))
                val ck4 = cursor.getString(cursor.getColumnIndex("kondisi4"))
                val kode4 = cursor.getString(cursor.getColumnIndex("Kode_bahaya4"))
                val ket4 = cursor.getString(cursor.getColumnIndex("Keterangan4"))
                val ck5 = cursor.getString(cursor.getColumnIndex("kondisi5"))
                val kode5 = cursor.getString(cursor.getColumnIndex("Kode_bahaya5"))
                val ket5 = cursor.getString(cursor.getColumnIndex("Keterangan5"))
                val ck6 = cursor.getString(cursor.getColumnIndex("kondisi6"))
                val kode6 = cursor.getString(cursor.getColumnIndex("Kode_bahaya6"))
                val ket6 = cursor.getString(cursor.getColumnIndex("Keterangan6"))
                val ck7 = cursor.getString(cursor.getColumnIndex("kondisi7"))
                val kode7 = cursor.getString(cursor.getColumnIndex("Kode_bahaya7"))
                val ket7 = cursor.getString(cursor.getColumnIndex("Keterangan7"))
                val ck8 = cursor.getString(cursor.getColumnIndex("kondisi8"))
                val kode8 = cursor.getString(cursor.getColumnIndex("Kode_bahaya8"))
                val ket8 = cursor.getString(cursor.getColumnIndex("Keterangan8"))
                val ck9 = cursor.getString(cursor.getColumnIndex("kondisi9"))
                val kode9 = cursor.getString(cursor.getColumnIndex("Kode_bahaya9"))
                val ket9 = cursor.getString(cursor.getColumnIndex("Keterangan9"))
                val ck10 = cursor.getString(cursor.getColumnIndex("kondisi10"))
                val kode10 = cursor.getString(cursor.getColumnIndex("Kode_bahaya10"))
                val ket10 = cursor.getString(cursor.getColumnIndex("Keterangan10"))
                val ck11 = cursor.getString(cursor.getColumnIndex("kondisi11"))
                val kode11 = cursor.getString(cursor.getColumnIndex("Kode_bahaya11"))
                val ket11 = cursor.getString(cursor.getColumnIndex("Keterangan11"))
                val ck12 = cursor.getString(cursor.getColumnIndex("kondisi12"))
                val kode12 = cursor.getString(cursor.getColumnIndex("Kode_bahaya12"))
                val ket12 = cursor.getString(cursor.getColumnIndex("Keterangan12"))
                val ck13 = cursor.getString(cursor.getColumnIndex("kondisi13"))
                val kode13 = cursor.getString(cursor.getColumnIndex("Kode_bahaya13"))
                val ket13 = cursor.getString(cursor.getColumnIndex("Keterangan13"))
                val ck14 = cursor.getString(cursor.getColumnIndex("kondisi14"))
                val kode14 = cursor.getString(cursor.getColumnIndex("Kode_bahaya14"))
                val ket14 = cursor.getString(cursor.getColumnIndex("Keterangan14"))
                val ck15 = cursor.getString(cursor.getColumnIndex("kondisi15"))
                val kode15 = cursor.getString(cursor.getColumnIndex("Kode_bahaya15"))
                val ket15 = cursor.getString(cursor.getColumnIndex("Keterangan15"))
                val ck16 = cursor.getString(cursor.getColumnIndex("kondisi16"))
                val kode16 = cursor.getString(cursor.getColumnIndex("Kode_bahaya16"))
                val ket16 = cursor.getString(cursor.getColumnIndex("Keterangan16"))
                val ck17 = cursor.getString(cursor.getColumnIndex("kondisi17"))
                val kode17 = cursor.getString(cursor.getColumnIndex("Kode_bahaya17"))
                val ket17 = cursor.getString(cursor.getColumnIndex("Keterangan17"))
                val ck18 = cursor.getString(cursor.getColumnIndex("kondisi18"))
                val kode18 = cursor.getString(cursor.getColumnIndex("Kode_bahaya18"))
                val ket18 = cursor.getString(cursor.getColumnIndex("Keterangan18"))
                val ck19 = cursor.getString(cursor.getColumnIndex("kondisi19"))
                val kode19 = cursor.getString(cursor.getColumnIndex("Kode_bahaya19"))
                val ket19 = cursor.getString(cursor.getColumnIndex("Keterangan19"))
                val ck20 = cursor.getString(cursor.getColumnIndex("kondisi20"))
                val kode20 = cursor.getString(cursor.getColumnIndex("Kode_bahaya20"))
                val ket20 = cursor.getString(cursor.getColumnIndex("Keterangan20"))
                val ck21 = cursor.getString(cursor.getColumnIndex("kondisi21"))
                val kode21 = cursor.getString(cursor.getColumnIndex("Kode_bahaya21"))
                val ket21 = cursor.getString(cursor.getColumnIndex("Keterangan21"))
                val ck22 = cursor.getString(cursor.getColumnIndex("kondisi22"))
                val kode22 = cursor.getString(cursor.getColumnIndex("Kode_bahaya22"))
                val ket22 = cursor.getString(cursor.getColumnIndex("Keterangan22"))
                val ck23 = cursor.getString(cursor.getColumnIndex("kondisi23"))
                val kode23 = cursor.getString(cursor.getColumnIndex("Kode_bahaya23"))
                val ket23 = cursor.getString(cursor.getColumnIndex("Keterangan23"))
                val ck24 = cursor.getString(cursor.getColumnIndex("kondisi24"))
                val kode24 = cursor.getString(cursor.getColumnIndex("Kode_bahaya24"))
                val ket24 = cursor.getString(cursor.getColumnIndex("Keterangan24"))
                val ck25 = cursor.getString(cursor.getColumnIndex("kondisi25"))
                val kode25 = cursor.getString(cursor.getColumnIndex("Kode_bahaya25"))
                val ket25 = cursor.getString(cursor.getColumnIndex("Keterangan25"))
                val ck26 = cursor.getString(cursor.getColumnIndex("kondisi26"))
                val kode26 = cursor.getString(cursor.getColumnIndex("Kode_bahaya26"))
                val ket26 = cursor.getString(cursor.getColumnIndex("Keterangan26"))
                val tanggal = cursor.getString(cursor.getColumnIndex("Created_at"))
                val Pengawas = cursor.getString(cursor.getColumnIndex("Nama_Pengawas"))
                val Img1 = cursor.getString(cursor.getColumnIndex("Image1"))
                val Img2 = cursor.getString(cursor.getColumnIndex("Image2"))
                val tindakan1 = cursor.getString(cursor.getColumnIndex("Tindakan1"))
                val tindakan2 = cursor.getString(cursor.getColumnIndex("Tindakan2"))
                val spv = cursor.getString(cursor.getColumnIndex("Nama_Supervisor"))


                val myData = AnswerEntity(id,lokasi,loading,shift,grup,iduser,ck1,kode1,ket1,ck2,kode2,ket2,ck3,
                    kode3,ket3,ck4,kode4,ket4,ck5,kode5,ket5,ck6,kode6,ket6,ck7,kode7,
                    ket7,ck8,kode8,ket8,ck9,kode9,ket9,ck10,kode10,ket10,ck11,kode11,
                    ket11,ck12,kode12,ket12,ck13,kode13,ket13,ck14,kode14,ket14,ck15,
                    kode15,ket15,ck16,kode16,ket16,ck17,kode17,ket17,ck18,kode18,ket18,
                    ck19,kode19,ket19,ck20,kode20,ket20,ck21,kode21,ket21,ck22,kode22,
                    ket22,ck23,kode23,ket23,ck24,kode24,ket24,ck25,kode25,ket25,ck26,
                    kode26,ket26,tanggal,Pengawas,Img1,Img2,tindakan1,tindakan2,spv)
                dataList.add(myData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dataList
    }


    fun getAllDataDumping(): List<DumpingEntity> {
        val dataList = mutableListOf<DumpingEntity>()
        val db = this.readableDatabase
        val query = "SELECT * FROM dumping"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val iduser = cursor.getInt(cursor.getColumnIndex("created_by_id"))
                val lokasi = cursor.getString(cursor.getColumnIndex("nama_lokasi"))
                val dumping = cursor.getString(cursor.getColumnIndex("nama_dumping"))
                val shift = cursor.getString(cursor.getColumnIndex("shift"))
                val grup = cursor.getString(cursor.getColumnIndex("grup"))
                val ck1 = cursor.getString(cursor.getColumnIndex("kondisi1"))
                val kode1 = cursor.getString(cursor.getColumnIndex("Kode_bahaya1"))
                val ket1 = cursor.getString(cursor.getColumnIndex("Keterangan1"))
                val ck2 = cursor.getString(cursor.getColumnIndex("kondisi2"))
                val kode2 = cursor.getString(cursor.getColumnIndex("Kode_bahaya2"))
                val ket2 = cursor.getString(cursor.getColumnIndex("Keterangan2"))
                val ck3 = cursor.getString(cursor.getColumnIndex("kondisi3"))
                val kode3 = cursor.getString(cursor.getColumnIndex("Kode_bahaya3"))
                val ket3 = cursor.getString(cursor.getColumnIndex("Keterangan3"))
                val ck4 = cursor.getString(cursor.getColumnIndex("kondisi4"))
                val kode4 = cursor.getString(cursor.getColumnIndex("Kode_bahaya4"))
                val ket4 = cursor.getString(cursor.getColumnIndex("Keterangan4"))
                val ck5 = cursor.getString(cursor.getColumnIndex("kondisi5"))
                val kode5 = cursor.getString(cursor.getColumnIndex("Kode_bahaya5"))
                val ket5 = cursor.getString(cursor.getColumnIndex("Keterangan5"))
                val ck6 = cursor.getString(cursor.getColumnIndex("kondisi6"))
                val kode6 = cursor.getString(cursor.getColumnIndex("Kode_bahaya6"))
                val ket6 = cursor.getString(cursor.getColumnIndex("Keterangan6"))
                val ck7 = cursor.getString(cursor.getColumnIndex("kondisi7"))
                val kode7 = cursor.getString(cursor.getColumnIndex("Kode_bahaya7"))
                val ket7 = cursor.getString(cursor.getColumnIndex("Keterangan7"))
                val ck8 = cursor.getString(cursor.getColumnIndex("kondisi8"))
                val kode8 = cursor.getString(cursor.getColumnIndex("Kode_bahaya8"))
                val ket8 = cursor.getString(cursor.getColumnIndex("Keterangan8"))
                val ck9 = cursor.getString(cursor.getColumnIndex("kondisi9"))
                val kode9 = cursor.getString(cursor.getColumnIndex("Kode_bahaya9"))
                val ket9 = cursor.getString(cursor.getColumnIndex("Keterangan9"))
                val ck10 = cursor.getString(cursor.getColumnIndex("kondisi10"))
                val kode10 = cursor.getString(cursor.getColumnIndex("Kode_bahaya10"))
                val ket10 = cursor.getString(cursor.getColumnIndex("Keterangan10"))
                val ck11 = cursor.getString(cursor.getColumnIndex("kondisi11"))
                val kode11 = cursor.getString(cursor.getColumnIndex("Kode_bahaya11"))
                val ket11 = cursor.getString(cursor.getColumnIndex("Keterangan11"))
                val ck12 = cursor.getString(cursor.getColumnIndex("kondisi12"))
                val kode12 = cursor.getString(cursor.getColumnIndex("Kode_bahaya12"))
                val ket12 = cursor.getString(cursor.getColumnIndex("Keterangan12"))
                val ck13 = cursor.getString(cursor.getColumnIndex("kondisi13"))
                val kode13 = cursor.getString(cursor.getColumnIndex("Kode_bahaya13"))
                val ket13 = cursor.getString(cursor.getColumnIndex("Keterangan13"))
                val ck14 = cursor.getString(cursor.getColumnIndex("kondisi14"))
                val kode14 = cursor.getString(cursor.getColumnIndex("Kode_bahaya14"))
                val ket14 = cursor.getString(cursor.getColumnIndex("Keterangan14"))
                val ck15 = cursor.getString(cursor.getColumnIndex("kondisi15"))
                val kode15 = cursor.getString(cursor.getColumnIndex("Kode_bahaya15"))
                val ket15 = cursor.getString(cursor.getColumnIndex("Keterangan15"))
                val ck16 = cursor.getString(cursor.getColumnIndex("kondisi16"))
                val kode16 = cursor.getString(cursor.getColumnIndex("Kode_bahaya16"))
                val ket16 = cursor.getString(cursor.getColumnIndex("Keterangan16"))
                val ck17 = cursor.getString(cursor.getColumnIndex("kondisi17"))
                val kode17 = cursor.getString(cursor.getColumnIndex("Kode_bahaya17"))
                val ket17 = cursor.getString(cursor.getColumnIndex("Keterangan17"))
                val ck18 = cursor.getString(cursor.getColumnIndex("kondisi18"))
                val kode18 = cursor.getString(cursor.getColumnIndex("Kode_bahaya18"))
                val ket18 = cursor.getString(cursor.getColumnIndex("Keterangan18"))
                val ck19 = cursor.getString(cursor.getColumnIndex("kondisi19"))
                val kode19 = cursor.getString(cursor.getColumnIndex("Kode_bahaya19"))
                val ket19 = cursor.getString(cursor.getColumnIndex("Keterangan19"))
                val ck20 = cursor.getString(cursor.getColumnIndex("kondisi20"))
                val kode20 = cursor.getString(cursor.getColumnIndex("Kode_bahaya20"))
                val ket20 = cursor.getString(cursor.getColumnIndex("Keterangan20"))
                val tanggal = cursor.getString(cursor.getColumnIndex("Created_at"))
                val Pengawas = cursor.getString(cursor.getColumnIndex("Nama_Pengawas"))
                val Img1 = cursor.getString(cursor.getColumnIndex("Image1"))
                val Img2 = cursor.getString(cursor.getColumnIndex("Image2"))
                val tindakan1 = cursor.getString(cursor.getColumnIndex("Tindakan1"))
                val tindakan2 = cursor.getString(cursor.getColumnIndex("Tindakan2"))
                val spv = cursor.getString(cursor.getColumnIndex("Nama_Supervisor"))


                val myData = DumpingEntity(id,lokasi,dumping,shift,grup,iduser,ck1,kode1,ket1,ck2,kode2,ket2,ck3,
                    kode3,ket3,ck4,kode4,ket4,ck5,kode5,ket5,ck6,kode6,ket6,ck7,kode7,
                    ket7,ck8,kode8,ket8,ck9,kode9,ket9,ck10,kode10,ket10,ck11,kode11,
                    ket11,ck12,kode12,ket12,ck13,kode13,ket13,ck14,kode14,ket14,ck15,
                    kode15,ket15,ck16,kode16,ket16,ck17,kode17,ket17,ck18,kode18,ket18,
                    ck19,kode19,ket19,ck20,kode20,ket20,tanggal,Pengawas,Img1,Img2,tindakan1,tindakan2,spv)
                dataList.add(myData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dataList
    }

    fun getAllDataHauling(): List<HaulingEntity> {
        val dataList = mutableListOf<HaulingEntity>()
        val db = this.readableDatabase
        val query = "SELECT * FROM hauling"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val iduser = cursor.getInt(cursor.getColumnIndex("created_by_id"))
                val lokasi = cursor.getString(cursor.getColumnIndex("nama_lokasi"))
                val hauling = cursor.getString(cursor.getColumnIndex("nama_hauling"))
                val shift = cursor.getString(cursor.getColumnIndex("shift"))
                val grup = cursor.getString(cursor.getColumnIndex("grup"))
                val ck1 = cursor.getString(cursor.getColumnIndex("kondisi1"))
                val kode1 = cursor.getString(cursor.getColumnIndex("Kode_bahaya1"))
                val ck2 = cursor.getString(cursor.getColumnIndex("kondisi2"))
                val kode2 = cursor.getString(cursor.getColumnIndex("Kode_bahaya2"))
                val ck3 = cursor.getString(cursor.getColumnIndex("kondisi3"))
                val kode3 = cursor.getString(cursor.getColumnIndex("Kode_bahaya3"))
                val ck4 = cursor.getString(cursor.getColumnIndex("kondisi4"))
                val kode4 = cursor.getString(cursor.getColumnIndex("Kode_bahaya4"))
                val ck5 = cursor.getString(cursor.getColumnIndex("kondisi5"))
                val kode5 = cursor.getString(cursor.getColumnIndex("Kode_bahaya5"))
                val ck6 = cursor.getString(cursor.getColumnIndex("kondisi6"))
                val kode6 = cursor.getString(cursor.getColumnIndex("Kode_bahaya6"))
                val ck7 = cursor.getString(cursor.getColumnIndex("kondisi7"))
                val kode7 = cursor.getString(cursor.getColumnIndex("Kode_bahaya7"))
                val ck8 = cursor.getString(cursor.getColumnIndex("kondisi8"))
                val kode8 = cursor.getString(cursor.getColumnIndex("Kode_bahaya8"))
                val ck9 = cursor.getString(cursor.getColumnIndex("kondisi9"))
                val kode9 = cursor.getString(cursor.getColumnIndex("Kode_bahaya9"))
                val ck10 = cursor.getString(cursor.getColumnIndex("kondisi10"))
                val kode10 = cursor.getString(cursor.getColumnIndex("Kode_bahaya10"))
                val ck11 = cursor.getString(cursor.getColumnIndex("kondisi11"))
                val kode11 = cursor.getString(cursor.getColumnIndex("Kode_bahaya11"))
                val ck12 = cursor.getString(cursor.getColumnIndex("kondisi12"))
                val kode12 = cursor.getString(cursor.getColumnIndex("Kode_bahaya12"))
                val ck13 = cursor.getString(cursor.getColumnIndex("kondisi13"))
                val kode13 = cursor.getString(cursor.getColumnIndex("Kode_bahaya13"))
                val ck14 = cursor.getString(cursor.getColumnIndex("kondisi14"))
                val kode14 = cursor.getString(cursor.getColumnIndex("Kode_bahaya14"))
                val ck15 = cursor.getString(cursor.getColumnIndex("kondisi15"))
                val kode15 = cursor.getString(cursor.getColumnIndex("Kode_bahaya15"))
                val ck16 = cursor.getString(cursor.getColumnIndex("kondisi16"))
                val kode16 = cursor.getString(cursor.getColumnIndex("Kode_bahaya16"))
                val ck17 = cursor.getString(cursor.getColumnIndex("kondisi17"))
                val kode17 = cursor.getString(cursor.getColumnIndex("Kode_bahaya17"))
                val ck18 = cursor.getString(cursor.getColumnIndex("kondisi18"))
                val kode18 = cursor.getString(cursor.getColumnIndex("Kode_bahaya18"))
                val ck19 = cursor.getString(cursor.getColumnIndex("kondisi19"))
                val kode19 = cursor.getString(cursor.getColumnIndex("Kode_bahaya19"))
                val ck20 = cursor.getString(cursor.getColumnIndex("kondisi20"))
                val kode20 = cursor.getString(cursor.getColumnIndex("Kode_bahaya20"))
                val ck21 = cursor.getString(cursor.getColumnIndex("kondisi21"))
                val kode21 = cursor.getString(cursor.getColumnIndex("Kode_bahaya21"))
                val ck22 = cursor.getString(cursor.getColumnIndex("kondisi22"))
                val kode22 = cursor.getString(cursor.getColumnIndex("Kode_bahaya22"))
                val ck23 = cursor.getString(cursor.getColumnIndex("kondisi23"))
                val kode23 = cursor.getString(cursor.getColumnIndex("Kode_bahaya23"))
                val ck24 = cursor.getString(cursor.getColumnIndex("kondisi24"))
                val kode24 = cursor.getString(cursor.getColumnIndex("Kode_bahaya24"))
                val ck25 = cursor.getString(cursor.getColumnIndex("kondisi25"))
                val kode25 = cursor.getString(cursor.getColumnIndex("Kode_bahaya25"))
                val ck26 = cursor.getString(cursor.getColumnIndex("kondisi26"))
                val kode26 = cursor.getString(cursor.getColumnIndex("Kode_bahaya26"))
                val ck27 = cursor.getString(cursor.getColumnIndex("kondisi27"))
                val kode27 = cursor.getString(cursor.getColumnIndex("Kode_bahaya27"))
                val ck28 = cursor.getString(cursor.getColumnIndex("kondisi28"))
                val kode28 = cursor.getString(cursor.getColumnIndex("Kode_bahaya28"))
                val ck29 = cursor.getString(cursor.getColumnIndex("kondisi29"))
                val kode29 = cursor.getString(cursor.getColumnIndex("Kode_bahaya29"))
                val ck30 = cursor.getString(cursor.getColumnIndex("kondisi30"))
                val kode30 = cursor.getString(cursor.getColumnIndex("Kode_bahaya30"))
                val tanggal = cursor.getString(cursor.getColumnIndex("Created_at"))
                val Pengawas = cursor.getString(cursor.getColumnIndex("Nama_Pengawas"))
                val Img1 = cursor.getString(cursor.getColumnIndex("Image1"))
                val Img2 = cursor.getString(cursor.getColumnIndex("Image2"))
                val tindakan1 = cursor.getString(cursor.getColumnIndex("Tindakan1"))
                val tindakan2 = cursor.getString(cursor.getColumnIndex("Tindakan2"))
                val spv = cursor.getString(cursor.getColumnIndex("Nama_Supervisor"))


                val myData = HaulingEntity(id,lokasi,hauling,shift,grup,iduser,ck1,kode1,ck2,kode2,ck3,
                    kode3,ck4,kode4,ck5,kode5,ck6,kode6,ck7,kode7,ck8,kode8,ck9,kode9,ck10,kode10,
                    ck11,kode11,ck12,kode12,ck13,kode13,ck14,kode14,ck15, kode15,ck16,kode16,ck17,
                    kode17,ck18,kode18,ck19,kode19,ck20,kode20,ck21,kode21,ck22,kode22,ck23,kode23,
                    ck24,kode24,ck25,kode25,ck26,kode26,ck27,kode27,ck28,kode28,ck29,kode29,ck30,kode30,
                    tanggal,Pengawas,Img1,Img2,tindakan1,tindakan2,spv)
                dataList.add(myData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dataList
    }


    // Fungsi untuk mendapatkan daftar ID dari tabel 'jawaban'
    fun getAllIds(): List<Int> {
        val idList = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT id FROM jawaban"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                idList.add(id)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return idList
    }
    fun getAllIdsHauling(): List<Int> {
        val idList = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT id FROM hauling"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                idList.add(id)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return idList
    }

    fun getAllIdsDumping(): List<Int> {
        val idList = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT id FROM dumping"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                idList.add(id)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return idList
    }

    // Fungsi untuk menghapus data berdasarkan ID
    fun deleteDataById(id: Int): Boolean {
        val db = this.writableDatabase

        try {
            val whereClause = "id = ?"
            val whereArgs = arrayOf(id.toString())

            val rowsAffected = db.delete("jawaban", whereClause, whereArgs)

            db.close()

            // Jika berhasil menghapus satu atau lebih baris, kembalikan true
            return rowsAffected > 0
        } catch (e: Exception) {
            // Tangani kesalahan jika terjadi
            Log.e("DeleteData", "Error while deleting data: ${e.message}")
            db.close()
            return false
        }
    }

    fun deleteDataByIdDumping(id: Int): Boolean {
        val db = this.writableDatabase

        try {
            val whereClause = "id = ?"
            val whereArgs = arrayOf(id.toString())

            val rowsAffected = db.delete("dumping", whereClause, whereArgs)

            db.close()

            // Jika berhasil menghapus satu atau lebih baris, kembalikan true
            return rowsAffected > 0
        } catch (e: Exception) {
            // Tangani kesalahan jika terjadi
            Log.e("DeleteData", "Error while deleting data: ${e.message}")
            db.close()
            return false
        }
    }

    fun deleteDataByIdHauling(id: Int): Boolean {
        val db = this.writableDatabase

        try {
            val whereClause = "id = ?"
            val whereArgs = arrayOf(id.toString())

            val rowsAffected = db.delete("hauling", whereClause, whereArgs)

            db.close()

            // Jika berhasil menghapus satu atau lebih baris, kembalikan true
            return rowsAffected > 0
        } catch (e: Exception) {
            // Tangani kesalahan jika terjadi
            Log.e("DeleteData", "Error while deleting data: ${e.message}")
            db.close()
            return false
        }
    }


}