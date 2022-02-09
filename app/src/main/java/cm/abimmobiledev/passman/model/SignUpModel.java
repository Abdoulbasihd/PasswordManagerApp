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
    private String confirmPassword;

    @Nullable
    private String encryptionNotHashedKey;

    @Nullable
    private String phone;

    @Nullable
    private String email;

    private boolean fullAccount = false; //is true if is using online and offline account. false if only offline is used

    /**
     *
     * @param names String user's complete names
     * @param username String user's username
     * @param password String password
     * @param confirmPassword String password confirmation
     * @param encryptionNotHashedKey String not hashed key to be hashed and used as encryption key. Default same as password
     * @param phone String user's phone number
     * @param email String user's mail address
     */
    public SignUpModel(@Nullable String names, @Nullable String username, @Nullable String password, @Nullable String confirmPassword, @Nullable String encryptionNotHashedKey, @Nullable String phone, @Nullable String email) {
        this.names = names;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.email = email;
        this.encryptionNotHashedKey = encryptionNotHashedKey;
    }

    public boolean isFullAccount() {
        return fullAccount;
    }

    public void setFullAccount(boolean fullAccount) {
        this.fullAccount = fullAccount;
    }

    @Nullable
    public String getEncryptionNotHashedKey() {
        return encryptionNotHashedKey;
    }

    public void setEncryptionNotHashedKey(@Nullable String encryptionNotHashedKey) {
        this.encryptionNotHashedKey = encryptionNotHashedKey;
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

    @Nullable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@Nullable String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
