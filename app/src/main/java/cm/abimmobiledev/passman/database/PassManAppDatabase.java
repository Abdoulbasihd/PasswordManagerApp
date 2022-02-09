package cm.abimmobiledev.passman.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cm.abimmobiledev.passman.database.dao.ApplicationDAO;
import cm.abimmobiledev.passman.database.dao.UserDAO;
import cm.abimmobiledev.passman.database.entity.Application;
import cm.abimmobiledev.passman.database.entity.User;

@Database(entities = {User.class, Application.class}, version = 1)
public abstract class PassManAppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract ApplicationDAO applicationDAO();

    private static PassManAppDatabase passManAppDatabaseInstance = null;


    public synchronized static PassManAppDatabase getInstance(Context appContext){
        if (passManAppDatabaseInstance==null) {
            passManAppDatabaseInstance = Room.databaseBuilder(appContext.getApplicationContext(),
                    PassManAppDatabase.class,
                    "pass-man-db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return  passManAppDatabaseInstance;
    }
}
