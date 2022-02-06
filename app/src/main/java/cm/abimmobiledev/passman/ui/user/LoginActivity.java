package cm.abimmobiledev.passman.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.databinding.ActivityLoginBinding;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.usefull.Util;
import cm.abimmobiledev.passman.viewmodel.SignInViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setSignInViewModel(new SignInViewModel());
        activityLoginBinding.executePendingBindings();


        activityLoginBinding.noAccountSignUp
                            .setOnClickListener(v -> Navigator.openAccountCreationPage(LoginActivity.this));

        activityLoginBinding.signIn.setOnClickListener(v -> {

            String username = activityLoginBinding.inputUsername.getText().toString();
            String pass = activityLoginBinding.inputPassword.getText().toString();

            if (usernameAndPassFilled(username, pass)){
                Toast.makeText(LoginActivity.this, "coming up", Toast.LENGTH_SHORT).show();
                return;
            }

            activityLoginBinding.inputUsername.setError("Verifiez que le nom d'utilisateur est bien rempli");
            activityLoginBinding.inputPassword.setError("Verifiez que le nom d'utilisateur est bien rempli");

        });

    }

    public boolean usernameAndPassFilled(String userName, String pass){
        if (Util.stringNotFilled(userName))
            return false;

        if (Util.stringNotFilled(pass))
            return false;

        if (userName.contains("\"") || userName.contains("'"))
            return false;

        if (pass.contains("\"") || pass.contains("'"))
            return false;

        return pass.length() >= 8;
    }

}