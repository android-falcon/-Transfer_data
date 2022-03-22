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


@Database(entities = {AllItems.class, ZoneModel.class, ReplacementModel.class, appSettings.class, Store.class, ItemSerialTransfer.class, SerialsModel.class}, version = 19, exportSchema = false)
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

    //    static final Migration MIGRATION_1_2 = new Migration(19, 21) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
////            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
////                    + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };

    ///B
//    static final Migration MIGRATION_7_11 = new Migration(7, 11) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE SETTINGS_TABLE "
//                    + " ADD COLUMN Check_Qty TEXT");
//
//            database.execSQL("ALTER TABLE REPLACEMENT_TABLE "
//                    + " ADD COLUMN availableQty TEXT");
//        }
//    };
//    static final Migration MIGRATION_8_11 = new Migration(8, 11) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE SETTINGS_TABLE "
//                    + " ADD COLUMN Check_Qty TEXT");
//
//            database.execSQL("ALTER TABLE REPLACEMENT_TABLE "
//                    + " ADD COLUMN availableQty TEXT");
//        }
//    };
//    static final Migration MIGRATION_9_11 = new Migration(9, 11) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE SETTINGS_TABLE "
//                    + " ADD COLUMN Check_Qty TEXT");
//        }
//    };
//    static final Migration MIGRATION_10_11 = new Migration(10, 11) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE SETTINGS_TABLE "
//                    + " ADD COLUMN Check_Qty TEXT");
//        }
//    };
//    static final Migration MIGRATION_11_12 = new Migration(11, 12) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
////            database.execSQL("CREATE TABLE IF NOT EXISTS ITEM_SERIAL_TRANSFERS (" +
////                    "ID INTEGER PRIMARY KEY AUTOINCREMENT, Voucher_No TEXT, Device_Id TEXT, Item_Code TEXT, Serial_No TEXT)");
//            database.execSQL("DROP TABLE IF EXISTS `ITEM_SERIAL_TRANSFERS`");
//            database.execSQL("CREATE TABLE IF NOT EXISTS `ITEM_SERIAL_TRANSFERS` (`ID` INTEGER NOT NULL, `Voucher_No` TEXT, `Device_Id` TEXT, `Item_Code` TEXT, `Serial_No` TEXT, PRIMARY KEY(`ID`))");
//        }
//    };
//
//    static final Migration MIGRATION_12_13 = new Migration(12, 13) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Qty TEXT");
//
//        }
//    };
//
//    static final Migration MIGRATION_11_13 = new Migration(11, 13) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("DROP TABLE IF EXISTS `ITEM_SERIAL_TRANSFERS`");
//            database.execSQL("CREATE TABLE IF NOT EXISTS `ITEM_SERIAL_TRANSFERS` " +
//                    "(`ID` INTEGER NOT NULL, `Voucher_No` TEXT, " +
//                    "`Device_Id` TEXT, `Item_Code` TEXT, `Serial_No` TEXT, PRIMARY KEY(`ID`))");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Qty TEXT");
//        }
//    };
//
//    static final Migration MIGRATION_13_14 = new Migration(13, 14) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Added_To_Rep TEXT");
//        }
//    };
//
//    static final Migration MIGRATION_11_14 = new Migration(11, 14) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("DROP TABLE IF EXISTS `ITEM_SERIAL_TRANSFERS`");
//            database.execSQL("CREATE TABLE IF NOT EXISTS `ITEM_SERIAL_TRANSFERS` " +
//                    "(`ID` INTEGER NOT NULL, `Voucher_No` TEXT, " +
//                    "`Device_Id` TEXT, `Item_Code` TEXT, `Serial_No` TEXT, PRIMARY KEY(`ID`))");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Qty TEXT");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Added_To_Rep TEXT");
//        }
//    };

//    static final Migration MIGRATION_14_16 = new Migration(14, 16) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Date TEXT");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN From_Store TEXT");
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");
//        }
//    };
//
//
//    static final Migration MIGRATION_13_16 = new Migration(13, 16) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Added_To_Rep TEXT");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Date TEXT");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN From_Store TEXT");
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");
//
//        }
//    };
//
//    static final Migration MIGRATION_12_16 = new Migration(12, 16) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Qty TEXT");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Added_To_Rep TEXT");
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Date TEXT");
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN From_Store TEXT");
//
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");
//
//
//        }
//    };
//
//    static final Migration MIGRATION_11_16 = new Migration(11, 16) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS `ITEM_SERIAL_TRANSFERS` " +
//                    "(`ID` INTEGER NOT NULL, `Voucher_No` TEXT, " +
//                    "`Device_Id` TEXT, `Item_Code` TEXT, `Serial_No` TEXT, " +
//                    "`Qty` TEXT, `Added_To_Rep` TEXT, `Date` TEXT, `From_Store` TEXT," +
//                    "`Posted` TEXT, " +
//                    " PRIMARY KEY(`ID`))");
//
//        }
//    };
//
//    static final Migration MIGRATION_15_16 = new Migration(15, 16) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");
//        }
//    };

    /////////////////

    static final Migration MIGRATION_14_18 = new Migration(14, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Date TEXT");
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN From_Store TEXT");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN BARCODE TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Serials_Table` " +
                    "(`ID` INTEGER NOT NULL, `Store` TEXT, " +
                    "`Item_No` TEXT, `Serial_No` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");

        }
    };


    static final Migration MIGRATION_13_18 = new Migration(13, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Added_To_Rep TEXT");
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Date TEXT");
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN From_Store TEXT");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN BARCODE TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Serials_Table` " +
                    "(`ID` INTEGER NOT NULL, `Store` TEXT, " +
                    "`Item_No` TEXT, `Serial_No` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");


        }
    };

    static final Migration MIGRATION_12_18 = new Migration(12, 18) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Qty TEXT");
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Added_To_Rep TEXT");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Date TEXT");
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN From_Store TEXT");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN BARCODE TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Serials_Table` " +
                    "(`ID` INTEGER NOT NULL, `Store` TEXT, " +
                    "`Item_No` TEXT, `Serial_No` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");


        }
    };

    static final Migration MIGRATION_11_18 = new Migration(11, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `ITEM_SERIAL_TRANSFERS` " +
                    "(`ID` INTEGER NOT NULL, `Voucher_No` TEXT, " +
                    "`Device_Id` TEXT, `Item_Code` TEXT, `Serial_No` TEXT, " +
                    "`Qty` TEXT, `Added_To_Rep` TEXT, `Date` TEXT, `From_Store` TEXT," +
                    "`Posted` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN BARCODE TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Serials_Table` " +
                    "(`ID` INTEGER NOT NULL, `Store` TEXT, " +
                    "`Item_No` TEXT, `Serial_No` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");

        }
    };

    static final Migration MIGRATION_15_18 = new Migration(15, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN Posted TEXT");

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN BARCODE TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Serials_Table` " +
                    "(`ID` INTEGER NOT NULL, `Store` TEXT, " +
                    "`Item_No` TEXT, `Serial_No` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");
        }
    };

    static final Migration MIGRATION_16_18 = new Migration(16, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN BARCODE TEXT");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Serials_Table` " +
                    "(`ID` INTEGER NOT NULL, `Store` TEXT, " +
                    "`Item_No` TEXT, `Serial_No` TEXT, " +
                    " PRIMARY KEY(`ID`))");

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");

        }
    };

    static final Migration MIGRATION_17_18 = new Migration(17, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");

        }
    };


    static final Migration MIGRATION_17_19 = new Migration(17, 19) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_SERIAL_TRANSFERS ADD COLUMN To_Store TEXT");
            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Has_Serial TEXT");

        }
    };

    static final Migration MIGRATION_18_19 = new Migration(18, 19) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE ITEM_TABLE ADD COLUMN Has_Serial TEXT");

        }
    };


    public static synchronized RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomAllData.class, dataBaseName)
                    .addMigrations(MIGRATION_11_18, MIGRATION_12_18,
                            MIGRATION_13_18, MIGRATION_14_18,
                            MIGRATION_15_18, MIGRATION_16_18, MIGRATION_17_18,
                            MIGRATION_17_19, MIGRATION_18_19)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return database;
    }


}








