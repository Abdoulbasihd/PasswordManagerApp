package cm.abimmobiledev.passman.ui.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.database.PassManAppDatabase;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivitySignUpBinding;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.usefull.Util;
import cm.abimmobiledev.passman.viewmodel.user.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    private static final String TAG_SIGN = "PM_SIGN_UP";
    ProgressDialog userInsertProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_sign_up);
        activitySignUpBinding.setSignUpViewModel(new SignUpViewModel());
        activitySignUpBinding.executePendingBindings();

        activitySignUpBinding.goToSignIn.setOnClickListener(v -> Navigator.openSignInPage(SignUpActivity.this));


        activitySignUpBinding.signUp.setOnClickListener(v -> {
            userInsertProgress = new ProgressDialog(SignUpActivity.this);
            userInsertProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            userInsertProgress.setCanceledOnTouchOutside(false);
            userInsertProgress.setTitle("Sauvegarde du nouveau compte en cours...");
            userInsertProgress.show();

            SignUpViewModel signUpViewModel = activitySignUpBinding.getSignUpViewModel();

            boolean fullAcc = !activitySignUpBinding.radioLocalOnly.isChecked();

            if (!signUpViewModel.getPassword().equals(signUpViewModel.getConfirmPassword())) {
                activitySignUpBinding.inputPassword.setText(getString(R.string.password_and_confirm_password_must_be_identical));
                activitySignUpBinding.inputConfirmPassword.setText(getString(R.string.password_and_confirm_password_must_be_identical));
                userInsertProgress.dismiss();
                return;
            }

            try {


                String hashedPass = Util.computeHash(signUpViewModel.getPassword());
                String hashedKey = Util.computeHash(signUpViewModel.getEncryptionNotHashedKey());
                User user = new User(signUpViewModel.getNames(),
                        signUpViewModel.getUsername(), hashedPass, hashedKey,
                        signUpViewModel.getPhone(), signUpViewModel.getEmail(), fullAcc
                );


                insertNewUser(user);

            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Log.d(TAG_SIGN, "adding user to db: "+e.getLocalizedMessage(), e);
            }

        });
    }

    private void insertNewUser(User user){
        ExecutorService userInsertService = Executors.newSingleThreadExecutor();
        userInsertService.execute((Runnable) () -> {

            // on pre execute

            //do in background
            PassManAppDatabase  passManAppDatabase =
                    PassManAppDatabase.getInstance(getApplicationContext());

            User testUserExistence = passManAppDatabase.userDAO().findByUsername(user.getUsername());

            if (testUserExistence==null){
                //doesn't exist
                passManAppDatabase.userDAO().insertAll(user);

            } else {
                Log.d(TAG_SIGN, "doInBackground: user already exist, username :"+testUserExistence.getUsername());
                runOnUiThread(() -> Snackbar.make(activitySignUpBinding.getRoot(), "Le nom d'utilisateur est déjà pris, bien vouloir choisir un autre", Snackbar.LENGTH_LONG).show());
            }


            // on post execute
            runOnUiThread(() -> {
                userInsertProgress.dismiss();
                Navigator.openSignInPage(SignUpActivity.this);
            });
        });

    }

}