package cm.abimmobiledev.passman.ui.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
                Toast.makeText(LoginActivity.this, "coming up", Toast.LENGTH_SHORT).show();

                UserVerificationTaskTask userVerificationTaskTask = new UserVerificationTaskTask();
                userVerificationTaskTask.execute(username, pass);


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

        //return pass.length() >= 8;
        return true;
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

    private class UserVerificationTaskTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... params) {
            // code that will run in the background
            PassManAppDatabase passManAppDatabaseLG;
            passManAppDatabaseLG = PassManAppDatabase.getInstance(getApplicationContext());

            Log.d(TAG_LOG, "doInBackground: params 0, "+params[0]);
            Log.d(TAG_LOG, "doInBackground: params 1, "+params[1]);
            User user = passManAppDatabaseLG.userDAO().findByUsername(params[0]);

            if (user==null){
                Log.d(TAG_LOG, "doInBackground: user not found ");
                return false;
            }

            if (accountVerified(user, params[1])) {
                Toast.makeText(getApplicationContext(), "Opening main... coming up", Toast.LENGTH_SHORT).show();
                //TODO : opening menu
                return true;
            }

            return true;
        }

        protected void onProgressUpdate(Integer... progress) {
            // receive progress updates from doInBackground
        }

        protected void onPostExecute(Boolean result) {
            // update the UI after background processes completes
            if (!result){
               // activityLoginBinding.inputUsername.setError("");
               // activityLoginBinding.inputPassword.setError("");
               // Toast.makeText(getApplicationContext(), "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                //TODO : open main here
            }
        }
    }

}