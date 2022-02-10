package cm.abimmobiledev.passman.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cm.abimmobiledev.passman.database.entity.User;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE user_id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    //    @Query("SELECT * FROM users WHERE username LIKE :username LIMIT 1")
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User findByUsername(String username);

    //may be you could add find by phone and by email ?

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

}
