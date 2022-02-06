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
    private String logo;

    @Nullable
    private boolean isSavedOnline;

    public ApplicationModel(@Nullable String name, @Nullable String description, @Nullable String password, @Nullable String logo, boolean isSavedOnline) {
        this.name = name;
        this.description = description;
        this.password = password;
        this.logo = logo;
        this.isSavedOnline = isSavedOnline;
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
}
