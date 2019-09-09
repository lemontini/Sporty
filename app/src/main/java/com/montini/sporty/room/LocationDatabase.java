package com.montini.sporty.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.montini.sporty.R;
import com.montini.sporty.model.Location;
import com.montini.sporty.model.Player;

import static com.montini.sporty.MainActivity.getUriForResource;

@Database(entities = {Location.class, Player.class}, version = 1, exportSchema = false)
@TypeConverters({UriTypeConverter.class})
public abstract class LocationDatabase extends RoomDatabase {

    private static LocationDatabase instance;

    public abstract LocationDao locationDao();
    public abstract PlayerDao playerDao();

    public static synchronized LocationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class, "location_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private LocationDao locationDao;
        private PlayerDao playerDao;

        private PopulateDbAsyncTask(LocationDatabase db) {
            locationDao = db.locationDao();
            playerDao = db.playerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.insert(new Location("SEB arena", "Ąžuolyno g. 7, Vilnius", 4, getUriForResource(R.drawable.logo_seb_arena)));
            locationDao.insert(new Location("Delfi Sporto Centras", "Ozo g. 14C, Vilnius", 8, getUriForResource(R.drawable.logo_delfi_sporto_centras)));
            locationDao.insert(new Location("Zambia", "Africa", 1, Uri.parse("https://d2lo9qrcc42lm4.cloudfront.net/Images/News/_contentLarge/Main-girls-out-of-school.jpg?mtime=20170426205135")));
            locationDao.insert(new Location("a", "b", 1, Uri.parse("file:///storage/emulated/0/Pictures/Instagram/IMG_20190630_210003_297.jpg")));
            playerDao.insert(new Player("montini", Uri.parse("file:///storage/emulated/0/Pictures/Messenger/received_10156738141132792.jpeg")));
            return null;
        }
    }
}