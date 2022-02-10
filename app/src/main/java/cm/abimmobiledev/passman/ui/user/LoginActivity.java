package cm.abimmobiledev.passman.ui.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.database.PassManAppDatabase;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivityLoginBinding;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.usefull.Util;
import cm.abimmobiledev.passman.viewmodel.SignInViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    private static final String TAG_LOG = "PM_LOG";
    ProgressDialog dialogLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setSignInViewModel(new SignInViewModel());
        activityLoginBinding.executePendingBindings();


        activityLoginBinding.noAccountSignUp
                            .setOnClickListener(v -> Navigator.openAccountCreationPage(LoginActivity.this));

        activityLoginBinding.signIn.setOnClickListener(v -> {

            SignInViewModel signInViewModel = activityLoginBinding.getSignInViewModel();

            String username = signInViewModel.getUsername();
            String pass = signInViewModel.getPassword();

            if (usernameAndPassFilled(username, pass)){

                dialogLogin = new ProgressDialog(LoginActivity.this);
                dialogLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialogLogin.setCanceledOnTouchOutside(false);
                dialogLogin.setTitle("Vérification du compte en cours...");
                dialogLogin.show();
                databaseAccountVerifier(username, pass);


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

        return !pass.contains("\"") && !pass.contains("'");

        //return pass.length() >= 8;
    }

    public boolean accountVerified(User user, String password) {

        try {
            if (user ==null || user.getPassword()==null || password==null) {
                if (user==null)
                    Log.d(TAG_LOG, "accountVerified: user is null");
                else if (user.getPassword()==null)
                    Log.d(TAG_LOG, "accountVerified: user's pass is null");
                else
                    Log.d(TAG_LOG, "accountVerified: password is null");


                return  false;
            }

            String computedPass = Util.computeHash(password);
            if (user.getPassword().equals(computedPass)) {
                Log.d(TAG_LOG, "accountVerified: correct pass");
                return true;
            }
            Log.d(TAG_LOG, "accountVerified: computed pass for filled : "+computedPass);
            Log.d(TAG_LOG, "accountVerified: saved user's pass : "+user.getPassword());

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.d(TAG_LOG, "accountVerified: "+e.getLocalizedMessage(), e);
        }
        return false;
    }

    public void databaseAccountVerifier(String username, String password){
        ExecutorService accountVerifierService = Executors.newSingleThreadExecutor();
        accountVerifierService.execute(() -> {
            // on pre execute : no thing really to do here. code of loader move up before calling this methode

            //do in background ??
            // code that will run in the background
            PassManAppDatabase passManAppDatabaseLG =
                    PassManAppDatabase.getInstance(getApplicationContext());

            Log.d(TAG_LOG, "doInBackground: params 0, "+username);
            Log.d(TAG_LOG, "doInBackground: params 1, "+password);
            User user = passManAppDatabaseLG.userDAO().findByUsername(username);

            if (user==null || !accountVerified(user, password)){
                Log.d(TAG_LOG, "doInBackground: user not found ");
                //post execute case 1
                runOnUiThread(() -> {
                    setLoginPasswordInputError(getString(R.string.username_or_password_incorrect));
                    dialogLogin.dismiss();
                });
                return;
            }


            //post execute case 2
            runOnUiThread(() -> {
                dialogLogin.dismiss();
                Navigator.openMainMenuPage(LoginActivity.this, user);
            });

        });

    }

    private void setLoginPasswordInputError(String error) {
        activityLoginBinding.inputUsername.setError(error);
        activityLoginBinding.inputPassword.setError(error);
    }


}