package com.example.crm.data.local

import android.content.Context
import androidx.room.*
import com.example.crm.data.DATABASE_NAME
import com.example.crm.data.DataConverter
import com.example.crm.model.Customer

/**
 * Created by Nesma Yasser
 *
 * CRMDBFactory abstract class to setting up and build the database
 *
 */
@Database(
    entities = [Customer::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DataConverter::class)
abstract class CRMDBFactory : RoomDatabase() {

    abstract fun crmDao(): CRMDao

    companion object {
        @Volatile
        private var crmDBInstance: CRMDBFactory? = null

        /**
         * function to create instance from database
         */
        fun getInstance(context: Context): CRMDBFactory {
            if (crmDBInstance == null) {
                synchronized(this) {
                    crmDBInstance = buildDataBase(context)
                }
            }

            return crmDBInstance!!
        }

        // function to build the database
        fun buildDataBase(context: Context): CRMDBFactory {
            return Room.databaseBuilder(context, CRMDBFactory::class.java, DATABASE_NAME).build()
        }
    }
}