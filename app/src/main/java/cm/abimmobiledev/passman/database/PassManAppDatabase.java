package cm.abimmobiledev.passman.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import cm.abimmobiledev.passman.database.dao.ApplicationDAO;
import cm.abimmobiledev.passman.database.dao.UserDAO;
import cm.abimmobiledev.passman.database.entity.Application;
import cm.abimmobiledev.passman.database.entity.User;

@Database(entities = {User.class, Application.class}, version = 1)
public abstract class PassManAppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract ApplicationDAO applicationDAO();
}
