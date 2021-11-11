package com.hiaryabeer.transferapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hiaryabeer.transferapp.Interfaces.ItemDao;
import com.hiaryabeer.transferapp.Interfaces.ReplacementDao;
import com.hiaryabeer.transferapp.Interfaces.SettingDao;
import com.hiaryabeer.transferapp.Interfaces.StoreDao;
import com.hiaryabeer.transferapp.Interfaces.ZoneDao;
import com.hiaryabeer.transferapp.Models.AllItems;


@Database(entities = {AllItems.class, ZoneModel.class, ReplacementModel.class, appSettings.class, Store.class}, version = 11, exportSchema = false)
public abstract class RoomAllData extends RoomDatabase {
    private static RoomAllData database;
    public static String dataBaseName = "DBRoomTransfer";

    public abstract ZoneDao zoneDao();

    public abstract ReplacementDao replacementDao();

    public abstract SettingDao settingDao();

    public abstract ItemDao itemDao();

    public abstract StoreDao storeDao();

    //    static final Migration MIGRATION_1_2 = new Migration(19, 21) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
////            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
////                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };

    ///B
    static final Migration MIGRATION_7_11 = new Migration(7, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SETTINGS_TABLE "
                    + " ADD COLUMN Check_Qty TEXT");

            database.execSQL("ALTER TABLE REPLACEMENT_TABLE "
                    + " ADD COLUMN availableQty TEXT");
        }
    };
    static final Migration MIGRATION_8_11 = new Migration(8, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SETTINGS_TABLE "
                    + " ADD COLUMN Check_Qty TEXT");

            database.execSQL("ALTER TABLE REPLACEMENT_TABLE "
                    + " ADD COLUMN availableQty TEXT");
        }
    };
    static final Migration MIGRATION_9_11 = new Migration(9, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SETTINGS_TABLE "
                    + " ADD COLUMN Check_Qty TEXT");
        }
    };
    static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SETTINGS_TABLE "
                    + " ADD COLUMN Check_Qty TEXT");
        }
    };



    public static synchronized RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomAllData.class, dataBaseName)
                    .addMigrations(MIGRATION_7_11, MIGRATION_8_11,
                            MIGRATION_9_11, MIGRATION_10_11)
                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
                    .build();

        }
        return database;
    }


}








