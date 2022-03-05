package cm.abimmobiledev.passman.viewmodel.user;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cm.abimmobiledev.passman.BR;
import cm.abimmobiledev.passman.model.SignUpModel;

public class SignUpViewModel extends BaseObservable {

    private SignUpModel signUpModel;

    @Bindable
    public String getNames(){
        return signUpModel.getNames();
    }

    public void setNames(String names){
        signUpModel.setNames(names);
        notifyPropertyChanged(BR.names);
    }

    @Bindable
    public String getEmail(){
        return signUpModel.getEmail();
    }

    public void setEmail(String email){
        signUpModel.setEmail(email);
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhone(){
        return signUpModel.getPhone();
    }

    public void  setPhone(String phone) {
        signUpModel.setPhone(phone);
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getUsername(){
        return signUpModel.getUsername();
    }

    public void  setUsername(String username){
        signUpModel.setUsername(username);
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword(){
        return signUpModel.getPassword();
    }

    public void  setPassword(String password){
        signUpModel.setPassword(password);
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getConfirmPassword(){
        return  signUpModel.getConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassword){
        signUpModel.setConfirmPassword(confirmPassword);
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public  boolean isFullAccount(){
        return signUpModel.isFullAccount();
    }

    public void setFullAccount(boolean isFullAccount){
        signUpModel.setFullAccount(isFullAccount);
        notifyPropertyChanged(BR.fullAccount);
    }

    /*@Bindable
    public String getEncryptionNotHashedKey(){
        return signUpModel.getEncryptionNotHashedKey();
    }

    public void  setEncryptionNotHashedKey(String encryptionNotHashedKey){
        signUpModel.setEncryptionNotHashedKey(encryptionNotHashedKey);
        notifyPropertyChanged(BR.encryptionNotHashedKey);
    }*/

    public SignUpViewModel() {
        signUpModel = new SignUpModel("", "", "",  "","", "", "");
    }
}
