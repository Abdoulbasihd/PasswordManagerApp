package cm.abimmobiledev.passman.model;

import androidx.annotation.Nullable;

public class SignUpModel {
    @Nullable
    private String names;

    @Nullable
    private String username;

    @Nullable
    private String password;

    @Nullable
    private String phone;

    @Nullable
    private String email;

    public SignUpModel(@Nullable String names, @Nullable String username, @Nullable String password, @Nullable String phone, @Nullable String email) {
        this.names = names;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    @Nullable
    public String getNames() {
        return names;
    }

    public void setNames(@Nullable String names) {
        this.names = names;
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

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }
}
