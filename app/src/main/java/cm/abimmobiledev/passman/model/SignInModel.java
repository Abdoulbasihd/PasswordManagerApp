package cm.abimmobiledev.passman.model;

import androidx.annotation.Nullable;

public class SignInModel {

    @Nullable
    String username;

    @Nullable
    String password;

    public SignInModel(@Nullable String username, @Nullable String password) {
        this.username = username;
        this.password = password;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

}
