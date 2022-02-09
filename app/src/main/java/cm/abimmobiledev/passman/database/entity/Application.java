package cm.abimmobiledev.passman.database.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "apps",
        foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "user_id",
        childColumns = "owner_user_id",
        onDelete = CASCADE))
public class Application {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "app_id")
    public int appId;

    @ColumnInfo(name = "owner_user_id")
    public final int ownerUserId;

    @ColumnInfo(name = "app_name")
    public final String name;

    @ColumnInfo(name = "app_desc")
    public final String description;

    @ColumnInfo(name = "app_pass")
    public final String password;

    @ColumnInfo(name = "app_logo")
    public final String logo;

    @ColumnInfo(name = "saved_on_line")
    public final boolean savedOnline;

    public Application(final int ownerUserId, String name, String description, String password, String logo, boolean savedOnline) {
        this.ownerUserId = ownerUserId;
        this.name = name;
        this.description = description;
        this.password = password;
        this.logo = logo;
        this.savedOnline = savedOnline;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public String getLogo() {
        return logo;
    }

    public boolean isSavedOnline() {
        return savedOnline;
    }
}
