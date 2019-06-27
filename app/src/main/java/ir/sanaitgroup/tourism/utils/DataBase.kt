package ir.sanaitgroup.tourism.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.sanaitgroup.tourism.models.MainServiceLevel

class DataBase(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Tourism.db"
        const val TABLE_SERVICE = "service_level"
        const val TABLE_FREE_SEATS_TABLE = "free_seats"
        const val TABLE_RESERVED_SEATS_TABLE = "reserved_seats"
        const val TABLE_ROW_COL_TABLE = "row_col"
        const val COLUMN_BASE_ID = "_base_id"
        const val COLUMN_ID = "_id"
        const val COLUMN_ROW = "row_id"
        const val COLUMN_COL = "col_id"
        const val COLUMN_CAPACITY = "capacity"
        const val COLUMN_CLASS_MODEL = "class_model"
        const val COLUMN_DISCOUNT_PERCENT = "discount_percent"
        const val COLUMN_START_TIME = "start_time"
        const val COLUMN_END_TIME = "end_time"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_PRICE = "price"
        const val COLUMN_RATE = "rate"
        const val COLUMN_SALONS_ID = "salons_id"
        const val COLUMN_SALONS = "salon"
        const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SERVICE_TABLE = ("CREATE TABLE " + TABLE_SERVICE + "("
                + COLUMN_BASE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ID + " INTEGER, "
                + COLUMN_CAPACITY + " INTEGER, "
                + COLUMN_CLASS_MODEL + " TEXT, "
                + COLUMN_DISCOUNT_PERCENT + " INTEGER, "
                + COLUMN_START_TIME + " TEXT, "
                + COLUMN_END_TIME + " TEXT, "
                + COLUMN_GENDER + " INTEGER, "
                + COLUMN_PRICE + " INTEGER, "
                + COLUMN_RATE + " INTEGER, "
                + COLUMN_SALONS_ID + " INTEGER, "
                + COLUMN_SALONS + " TEXT, "
                + COLUMN_DATE + " TEXT)" )

        val CREATE_FREE_SEATS_TABLE = ("CREATE TABLE " + TABLE_FREE_SEATS_TABLE + "("
                + COLUMN_BASE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ID + " INTEGER, "
                + COLUMN_ROW + " INTEGER, "
                + COLUMN_COL + " INTEGER)" )

        val CREATE_RESERVED_SEATS_TABLE = ("CREATE TABLE " + TABLE_RESERVED_SEATS_TABLE + "("
                + COLUMN_BASE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ID + " INTEGER, "
                + COLUMN_ROW + " INTEGER, "
                + COLUMN_COL + " INTEGER) " )

        val CREATE_ROW_COL_TABLE = ("CREATE TABLE " + TABLE_ROW_COL_TABLE + "("
                + COLUMN_BASE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ID + " INTEGER, "
                + COLUMN_ROW + " INTEGER, "
                + COLUMN_COL + " INTEGER) " )

        db.execSQL(CREATE_SERVICE_TABLE)
        db.execSQL(CREATE_FREE_SEATS_TABLE)
        db.execSQL(CREATE_RESERVED_SEATS_TABLE)
        db.execSQL(CREATE_ROW_COL_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FREE_SEATS_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RESERVED_SEATS_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ROW_COL_TABLE")
        onCreate(db)
    }

    fun addServiceLevel(service: List<MainServiceLevel.ServicesLevel>) {
        val values = ContentValues()
        val db = this.writableDatabase
        val size = service.size-1
        for (i in 0..size){
            values.put(COLUMN_ID, service[i].id)
            values.put(COLUMN_CAPACITY, service[i].capacity)
            values.put(COLUMN_CLASS_MODEL, service[i].class_model)
            values.put(COLUMN_DISCOUNT_PERCENT, service[i].discount_percent)
            values.put(COLUMN_START_TIME, service[i].start_time)
            values.put(COLUMN_END_TIME, service[i].end_time)
            values.put(COLUMN_GENDER, service[i].gender)
            values.put(COLUMN_PRICE, service[i].price)
            values.put(COLUMN_RATE, service[i].rate)
            values.put(COLUMN_SALONS_ID, service[i].salons_id)
            values.put(COLUMN_SALONS, service[i].salon)
            val date = "${service[i].details_date!!.weekday} ${service[i].details_date!!.day} ${service[i].details_date!!.month} ${service[i].details_date!!.year}"
            values.put(COLUMN_DATE, date)
            db.insert(TABLE_SERVICE, null, values)
            addFreeSeats(service[i].free_seats!!,service[i].id)
            addReservedSeats(service[i].reserve_seats!!,service[i].id)
            addRowCol(service[i].scheme!!,service[i].id)
        }
        db.close()
    }
    fun getAllServiceHeader(): List<MainServiceLevel.ServicesLevel.ServicesLevel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT($COLUMN_SALONS_ID),$COLUMN_ID, $COLUMN_SALONS,$COLUMN_CLASS_MODEL,$COLUMN_DATE  FROM $TABLE_SERVICE ", null)

        val result: ArrayList<MainServiceLevel.ServicesLevel.ServicesLevel> =  ArrayList()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
            do {
                val list : MainServiceLevel.ServicesLevel.ServicesLevel = MainServiceLevel.ServicesLevel.ServicesLevel()
                list.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                list.salon = cursor.getString(cursor.getColumnIndex(COLUMN_SALONS))
                list.salons_id = cursor.getInt(cursor.getColumnIndex(COLUMN_SALONS_ID))
                list.class_model = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_MODEL))
                list.date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

                result.add(list)
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        // return category list
        return result
    }
    fun getAllServiceContent(): List<MainServiceLevel.ServicesLevel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SERVICE", null)

        val result: ArrayList<MainServiceLevel.ServicesLevel> =  ArrayList()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
            do {
                val list :MainServiceLevel.ServicesLevel= MainServiceLevel.ServicesLevel()
                list.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                list.capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY))
                list.class_model = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_MODEL))
                list.discount_percent = cursor.getInt(cursor.getColumnIndex(COLUMN_DISCOUNT_PERCENT))
                list.start_time = cursor.getString(cursor.getColumnIndex(COLUMN_START_TIME))
                list.end_time = cursor.getString(cursor.getColumnIndex(COLUMN_END_TIME))
                list.gender = cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER))
                list.price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
                list.rate = cursor.getInt(cursor.getColumnIndex(COLUMN_RATE))
                list.salons_id = cursor.getInt(cursor.getColumnIndex(COLUMN_SALONS_ID))
                list.salon = cursor.getString(cursor.getColumnIndex(COLUMN_SALONS))
                list.date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

                result.add(list)
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        // return category list
        return result
    }
    fun getServicesContent(salon_id : Int): List<MainServiceLevel.ServicesLevel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SERVICE WHERE $COLUMN_SALONS_ID = $salon_id", null)

        val result: ArrayList<MainServiceLevel.ServicesLevel> =  ArrayList()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
            do {
                val list :MainServiceLevel.ServicesLevel= MainServiceLevel.ServicesLevel()
                list.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                list.capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY))
                list.class_model = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_MODEL))
                list.discount_percent = cursor.getInt(cursor.getColumnIndex(COLUMN_DISCOUNT_PERCENT))
                list.start_time = cursor.getString(cursor.getColumnIndex(COLUMN_START_TIME))
                list.end_time = cursor.getString(cursor.getColumnIndex(COLUMN_END_TIME))
                list.gender = cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER))
                list.price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
                list.rate = cursor.getInt(cursor.getColumnIndex(COLUMN_RATE))
                list.salons_id = cursor.getInt(cursor.getColumnIndex(COLUMN_SALONS_ID))
                list.salon = cursor.getString(cursor.getColumnIndex(COLUMN_SALONS))
                list.date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

                result.add(list)
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        // return category list
        return result
    }

    fun getSchedule(time_id : Int): MainServiceLevel.ServicesLevel {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SERVICE WHERE $COLUMN_ID = $time_id", null)

        val list: MainServiceLevel.ServicesLevel= MainServiceLevel.ServicesLevel()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
            list.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            list.capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY))
            list.class_model = cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_MODEL))
            list.discount_percent = cursor.getInt(cursor.getColumnIndex(COLUMN_DISCOUNT_PERCENT))
            list.start_time = cursor.getString(cursor.getColumnIndex(COLUMN_START_TIME))
            list.end_time = cursor.getString(cursor.getColumnIndex(COLUMN_END_TIME))
            list.gender = cursor.getInt(cursor.getColumnIndex(COLUMN_GENDER))
            list.price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))
            list.rate = cursor.getInt(cursor.getColumnIndex(COLUMN_RATE))
            list.salons_id = cursor.getInt(cursor.getColumnIndex(COLUMN_SALONS_ID))
            list.salon = cursor.getString(cursor.getColumnIndex(COLUMN_SALONS))
            list.date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
        }

        db.close()
        cursor.close()
        // return category list
        return list
    }
    fun deleteAllService() {
        val query = "DROP TABLE IF EXISTS $TABLE_SERVICE"
        val db = this.readableDatabase
        db.execSQL(query)
        deleteAllFreeSeats()
        deleteAllReservedSeats()
        deleteAllRowCol()
        onCreate(db)
    }

    fun addFreeSeats(seats: List<MainServiceLevel.ServicesLevel.Free_Seats>, id: Int) {
        val values = ContentValues()
        val db = this.writableDatabase
        val size = seats.size - 1
        for (i in 0..size){
            val row_col = seats[i].position_id.split(",")
            values.put(COLUMN_ID, id)
            values.put(COLUMN_ROW, row_col[0])
            values.put(COLUMN_COL, row_col[1])
            db.insert(TABLE_FREE_SEATS_TABLE, null, values)
        }
    }
    fun addRowCol(table: MainServiceLevel.ServicesLevel.Scheme, id: Int) {
        val values = ContentValues()
        val db = this.writableDatabase
        values.put(COLUMN_ID, id)
        values.put(COLUMN_ROW, table.row)
        values.put(COLUMN_COL, table.col)
        db.insert(TABLE_ROW_COL_TABLE, null, values)
    }
    fun addReservedSeats(seats: List<MainServiceLevel.ServicesLevel.Reserve_Seats>, id: Int) {
        val values = ContentValues()
        val db = this.writableDatabase
        val size = seats.size - 1
        for (i in 0..size){
            val row_col = seats[i].position_id.split(",")
            values.put(COLUMN_ID, id)
            values.put(COLUMN_ROW, row_col[0])
            values.put(COLUMN_COL, row_col[1])
            db.insert(TABLE_RESERVED_SEATS_TABLE, null, values)
        }
    }

    fun getAllFreeSeats(id: Int): List<MainServiceLevel.ServicesLevel.Free_Seats> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_FREE_SEATS_TABLE WHERE $COLUMN_ID=$id", null)

        val result: ArrayList<MainServiceLevel.ServicesLevel.Free_Seats> =  ArrayList()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
            do {
                val list :MainServiceLevel.ServicesLevel.Free_Seats = MainServiceLevel.ServicesLevel.Free_Seats()
                list.position_id = "${cursor.getInt(cursor.getColumnIndex(COLUMN_ROW))},${cursor.getInt(cursor.getColumnIndex(
                    COLUMN_COL
                ))}"
                result.add(list)
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        // return category list
        return result
    }
    fun getAllReservedSeats(id: Int): List<MainServiceLevel.ServicesLevel.Reserve_Seats> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_RESERVED_SEATS_TABLE WHERE $COLUMN_ID=$id", null)

        val result: ArrayList<MainServiceLevel.ServicesLevel.Reserve_Seats> =  ArrayList()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
            do {
                val list :MainServiceLevel.ServicesLevel.Reserve_Seats = MainServiceLevel.ServicesLevel.Reserve_Seats()
                list.position_id = "${cursor.getInt(cursor.getColumnIndex(COLUMN_ROW))},${cursor.getInt(cursor.getColumnIndex(
                    COLUMN_COL
                ))}"
                result.add(list)
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        // return category list
        return result
    }
    fun getRowCol(id: Int): MainServiceLevel.ServicesLevel.Scheme {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ROW_COL_TABLE WHERE $COLUMN_ID=$id", null)

        val result: MainServiceLevel.ServicesLevel.Scheme =  MainServiceLevel.ServicesLevel.Scheme()

        // looping through all rows and adding to list
        if (cursor!!.moveToFirst()) {
                result.col = cursor.getInt(cursor.getColumnIndex(COLUMN_COL))
                result.row = cursor.getInt(cursor.getColumnIndex(COLUMN_ROW))
        }

        db.close()
        cursor.close()
        // return category list
        return result
    }

    fun deleteAllFreeSeats() {
        val query = "DROP TABLE IF EXISTS $TABLE_FREE_SEATS_TABLE"
        val db = this.readableDatabase
        db.execSQL(query)
    }
    fun deleteAllReservedSeats() {
        val query = "DROP TABLE IF EXISTS $TABLE_RESERVED_SEATS_TABLE"
        val db = this.readableDatabase
        db.execSQL(query)
    }
    fun deleteAllRowCol() {
        val query = "DROP TABLE IF EXISTS $TABLE_ROW_COL_TABLE"
        val db = this.readableDatabase
        db.execSQL(query)
    }
}