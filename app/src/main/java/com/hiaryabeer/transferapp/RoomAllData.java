package com.hiaryabeer.transferapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hiaryabeer.transferapp.Interfaces.ItemDao;
import com.hiaryabeer.transferapp.Interfaces.ReplacementDao;
import com.hiaryabeer.transferapp.Interfaces.SerialTransfersDao;
import com.hiaryabeer.transferapp.Interfaces.SerialTransfersDao;
import com.hiaryabeer.transferapp.Interfaces.SerialsDao;
import com.hiaryabeer.transferapp.Interfaces.SettingDao;
import com.hiaryabeer.transferapp.Interfaces.StoreDao;
import com.hiaryabeer.transferapp.Interfaces.ZoneDao;
import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.ItemSerialTransfer;
import com.hiaryabeer.transferapp.Models.SerialsModel;


@Database(entities = {AllItems.class, ZoneModel.class, ReplacementModel.class, appSettings.class, Store.class, ItemSerialTransfer.class, SerialsModel.class}, version = 22, exportSchema = false)
public abstract class RoomAllData extends RoomDatabase {
    private static RoomAllData database;
    public static String dataBaseName = "DBRoomTransfer";

    public abstract ZoneDao zoneDao();

    public abstract ReplacementDao replacementDao();

    public abstract SettingDao settingDao();

    public abstract ItemDao itemDao();

    public abstract StoreDao storeDao();

    public abstract SerialTransfersDao serialTransfersDao();

    public abstract SerialsDao serialsDao();

    /////////////////


    static final Migration MIGRATION_17_22 = new Migration(17, 22) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");
            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Has_Serial TEXT");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN vSerial INTEGER DEFAULT 0 NOT NULL");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Category TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Kind TEXT");


        }
    };

    static final Migration MIGRATION_18_22 = new Migration(18, 22) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Has_Serial TEXT");
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN vSerial INTEGER DEFAULT 0 NOT NULL");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Category TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Kind TEXT");


        }
    };

    static final Migration MIGRATION_19_22 = new Migration(19, 22) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN vSerial INTEGER DEFAULT 0 NOT NULL");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Category TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Kind TEXT");

        }
    };

    static final Migration MIGRATION_20_22 = new Migration(20, 22) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE SETTINGS_TABLE ADD COLUMN Rawahneh_Add_Item TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Category TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Kind TEXT");

        }
    };

    static final Migration MIGRATION_21_22 = new Migration(21, 22) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {


            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Category TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Item_Kind TEXT");

        }
    };


    public static synchronized RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomAllData.class, dataBaseName)
                    .addMigrations(
                            MIGRATION_17_22,
                            MIGRATION_18_22,
                            MIGRATION_19_22,
                            MIGRATION_20_22,
                            MIGRATION_21_22)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return database;
    }


}








