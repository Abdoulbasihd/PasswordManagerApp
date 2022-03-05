package cm.abimmobiledev.passman.model;

import androidx.annotation.Nullable;

public class ApplicationModel {

    @Nullable
    private String name;

    @Nullable
    private String description;

    @Nullable
    private String password;

    @Nullable
    private String username;

    @Nullable
    private String logo;

    private boolean isSavedOnline = false;

    public ApplicationModel(@Nullable String name, @Nullable String description, @Nullable String username,  @Nullable String password, @Nullable String logo) {
        this.name = name;
        this.description = description;
        this.password = password;
        this.logo = logo;
        this.username = username;
    }

    public boolean isSavedOnline() {
        return isSavedOnline;
    }

    public void setSavedOnline(boolean savedOnline) {
        isSavedOnline = savedOnline;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nullable
    public String getLogo() {
        return logo;
    }

    public void setLogo(@Nullable String logo) {
        this.logo = logo;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }
}
