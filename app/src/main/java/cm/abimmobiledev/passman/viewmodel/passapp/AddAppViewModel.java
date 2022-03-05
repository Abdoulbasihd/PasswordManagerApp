package cm.abimmobiledev.passman.viewmodel.passapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cm.abimmobiledev.passman.BR;
import cm.abimmobiledev.passman.model.ApplicationModel;

public class AddAppViewModel extends BaseObservable {

    private ApplicationModel applicationModel;

    @Bindable
    private String getName(){
        return applicationModel.getName();
    };

    private void setName(String name) {
        applicationModel.setName(name);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    private String getDescription (){
        return applicationModel.getDescription();
    };

    private void setDescription(String desc) {
        applicationModel.setDescription(desc);
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    private String getPassword(){
        return applicationModel.getPassword();
    };

    private void setPassword(String password){
        applicationModel.setPassword(password);
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    private String getLogo(){
        return applicationModel.getLogo();
    };

    private void setLogo(String logo){
        applicationModel.setLogo(logo);
        //
    }

}
