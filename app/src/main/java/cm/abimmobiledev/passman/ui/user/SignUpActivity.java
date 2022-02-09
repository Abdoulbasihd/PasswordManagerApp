package cm.abimmobiledev.passman.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.database.PassManAppDatabase;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivitySignUpBinding;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.usefull.Util;
import cm.abimmobiledev.passman.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    private static final String TAG_SIGN = "PM_SIGN_UP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_sign_up);
        activitySignUpBinding.setSignUpViewModel(new SignUpViewModel());
        activitySignUpBinding.executePendingBindings();

        activitySignUpBinding.goToSignIn.setOnClickListener(v -> Navigator.openSignInPage(SignUpActivity.this));


        activitySignUpBinding.signUp.setOnClickListener(v -> {
            SignUpViewModel signUpViewModel = activitySignUpBinding.getSignUpViewModel();

            boolean fullAcc = !activitySignUpBinding.radioLocalOnly.isChecked();

            if (!signUpViewModel.getPassword().equals(signUpViewModel.getConfirmPassword())) {
                activitySignUpBinding.inputPassword.setText(getString(R.string.password_and_confirm_password_must_be_identical));
                activitySignUpBinding.inputConfirmPassword.setText(getString(R.string.password_and_confirm_password_must_be_identical));
                return;
            }

            try {
                String hashedPass = Util.computeHash(signUpViewModel.getPassword());
                String hashedKey = Util.computeHash(signUpViewModel.getEncryptionNotHashedKey());
                User user = new User(signUpViewModel.getNames(),
                        signUpViewModel.getUsername(), hashedPass, hashedKey,
                        signUpViewModel.getPhone(), signUpViewModel.getEmail(), fullAcc
                );

                //passManAppDatabase.userDAO().insertAll(user);
                InsertUserTaskTask insertUserTaskTask = new InsertUserTaskTask();
                insertUserTaskTask.execute(user);

            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Log.d(TAG_SIGN, "adding user to db: "+e.getLocalizedMessage(), e);
            }

        });
    }

    private class InsertUserTaskTask extends AsyncTask<User, Integer, Boolean> {


        protected Boolean doInBackground(User... users) {
            // code that will run in the background
            PassManAppDatabase passManAppDatabase;
            passManAppDatabase = PassManAppDatabase.getInstance(getApplicationContext());

            if (users[0]==null )
                return false;

            User test = passManAppDatabase.userDAO().findByUsername(users[0].getUsername());

            if (test==null){
                //doesn't exist
                passManAppDatabase.userDAO().insertAll(users[0]);
                return true;

            } else {
                Log.d(TAG_SIGN, "doInBackground: user already exist, username :"+test.getUsername());
                return false;
            }

        }

        protected void onProgressUpdate(Integer... progress) {
// receive progress updates from doInBackground
        }

        protected void onPostExecute(Boolean result) {
            // update the UI after background processes completes
        }
    }

}