package cm.abimmobiledev.passman.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cm.abimmobiledev.passman.database.entity.Application;

@Dao
public interface ApplicationDAO {
    @Insert
    void insert(Application application);

    @Update
    void update(Application... applications);

    @Delete
    void delete(Application... applications);
    @Query("SELECT * FROM apps")
    List<Application> getAllRepos();

    @Query("SELECT * FROM apps WHERE owner_user_id=:ownerUserId")
    List<Application> findApplicationsForUser(final int ownerUserId);
}
