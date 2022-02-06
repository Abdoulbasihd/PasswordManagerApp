package cm.abimmobiledev.passman.viewmodel;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cm.abimmobiledev.passman.BR;
import cm.abimmobiledev.passman.model.SignInModel;

public class SignInViewModel extends BaseObservable {

    private SignInModel signInModel;

    private String successMessage = "Login successful";
    private String errorMessage = "Email or Password is not valid";


    @Bindable
    public String getUsername(){
        return signInModel.getUsername();
    }

    public void setUsername(String username){
        signInModel.setUsername(username);
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public  String getPassword(){
        return signInModel.getPassword();
    }

    public void setPassword(String pass){
        signInModel.setPassword(pass);
        notifyPropertyChanged(BR.password);
    }

    public SignInViewModel(){
        signInModel = new SignInModel("", "");
    }

    public void onSignInButtonClicked(){
        //TODO
    }

    public void onGotoSignUpTextClicked(){
        //TODO
    }



}
