package cm.abimmobiledev.passman.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"username"}, unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "names")
    public final String names;

    @ColumnInfo(name = "username")
    public final String username;

    @ColumnInfo(name = "password")
    public final String password;

    @ColumnInfo(name = "encryptionKey")
    public final String encryptionKey;

    @ColumnInfo(name = "phone")
    public final String phone;

    @ColumnInfo(name = "email")
    public final String email;

    @ColumnInfo(name = "fullAccount")
    public final boolean fullAccount; //is true if is using online and offline account. false if only offline is used

    public User(String names, String username, String password, String encryptionKey, String phone, String email, boolean fullAccount) {
        this.names = names;
        this.username = username;
        this.password = password;
        this.encryptionKey = encryptionKey;
        this.phone = phone;
        this.email = email;
        this.fullAccount = fullAccount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNames() {
        return names;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isFullAccount() {
        return fullAccount;
    }
}
