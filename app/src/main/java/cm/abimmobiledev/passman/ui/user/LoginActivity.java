package cm.abimmobiledev.passman.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.usefull.Util;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        TextView openAccountCreation = findViewById(R.id.no_account_sign_up);
        openAccountCreation.setOnClickListener(v -> Navigator.openAccountCreationPage(LoginActivity.this));

        TextInputEditText login = findViewById(R.id.input_username);
        TextInputEditText password = findViewById(R.id.input_password);

        Button signIn = findViewById(R.id.sign_in);
        signIn.setOnClickListener(v -> {

            String username = login.getText().toString();
            String pass = password.getText().toString();

            if (usernameAndPassFilled(username, pass)){
                Toast.makeText(LoginActivity.this, "coming up", Toast.LENGTH_SHORT).show();
                return;
            }

            login.setError("Verifiez que le nom d'utilisateur est bien rempli");
            password.setError("Verifiez que le nom d'utilisateur est bien rempli");

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