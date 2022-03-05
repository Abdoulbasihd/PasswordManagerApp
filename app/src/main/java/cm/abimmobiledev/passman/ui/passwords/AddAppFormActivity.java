package cm.abimmobiledev.passman.ui.passwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cm.abimmobiledev.passman.MainActivity;
import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.database.PassManAppDatabase;
import cm.abimmobiledev.passman.database.entity.Application;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivityAddAppFormBinding;
import cm.abimmobiledev.passman.databinding.ActivityAppsListBinding;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.ui.passwords.adapter.PwdAdapter;
import cm.abimmobiledev.passman.usefull.Util;

public class AddAppFormActivity extends AppCompatActivity {

    ActivityAddAppFormBinding addAppFormBinding;
    private static final String TAG_ADD_APP = "PM_ADD_APP";
    ProgressDialog dialogAddAppData;
    AlertDialog.Builder appAddedDialog;
    User ownerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAppFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_app_form);
        addAppFormBinding.executePendingBindings();

        if (getIntent()!=null) ownerUser = (User) getIntent().getSerializableExtra(Navigator.USER_INTENT);
        else {
            Toast.makeText(AddAppFormActivity.this, "You must send intent data...", Toast.LENGTH_SHORT).show();
            finish();
        }

        dialogAddAppData = new ProgressDialog(AddAppFormActivity.this);
        dialogAddAppData.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogAddAppData.setCanceledOnTouchOutside(false);
        dialogAddAppData.setTitle(getString(R.string.adding_the_new_app));

        appAddedDialog = Util.getDialog(AddAppFormActivity.this, "Ajout Application", "Opération Effectuée avec succès, merci");
        appAddedDialog.setPositiveButton("OK", (dialog, which) -> {
           //just close

        });

        addAppFormBinding.buttonAdd.setOnClickListener(v -> {
            String name = addAppFormBinding.newAppNameInput.getText().toString();
            String desc = addAppFormBinding.newAppDescInput.getText().toString();
            String uname = addAppFormBinding.newAppUsernameInput.getText().toString();
            String pwd = addAppFormBinding.newAppPwdInput.getText().toString();
            String cnfPwd = addAppFormBinding.newAppConfirmPwdInput.getText().toString();
            dialogAddAppData.show();

            if (!Util.passwordConfirmationOk( pwd, cnfPwd)) {
                dialogAddAppData.dismiss();
                addAppFormBinding.newAppPwdInput.setError(getString(R.string.pwd_and_confirmation_must_be_equals));
                addAppFormBinding.newAppConfirmPwdInput.setError(getString(R.string.pwd_and_confirmation_must_be_equals));
                return;
            }

            if (name.isEmpty()){
                dialogAddAppData.dismiss();
                addAppFormBinding.newAppNameInput.setText(getString(R.string.required_field));
                return;
            }

            if (uname.isEmpty()){
                dialogAddAppData.dismiss();
                addAppFormBinding.newAppUsernameInput.setText(getString(R.string.required_field));
                return;
            }

            try {
               // String cipheredPwd = Util.encrypt(ownerUser.getEncryptionKey(), pwd);
                Application app = new Application(ownerUser.userId, name, desc, uname, pwd, null, false);
                saveNewApp(app);
            } catch (Exception e) {
                Log.d(TAG_ADD_APP, e.getLocalizedMessage(), e);
            }
        });

        addAppFormBinding.floatingBack.setOnClickListener(v -> Navigator.openAppsPage(AddAppFormActivity.this, ownerUser));

    }

    @Override
    public void onBackPressed(){
        Navigator.openAppsPage(AddAppFormActivity.this, ownerUser);
    }



    public void  saveNewApp(Application app){
        ExecutorService addAppExecService = Executors.newSingleThreadExecutor();
        addAppExecService.execute(() -> {

            PassManAppDatabase passManAppDatabaseAddApp =
                    PassManAppDatabase.getInstance(getApplicationContext());

            passManAppDatabaseAddApp.applicationDAO().insert(app);

            //post execute case 2
            runOnUiThread(() -> {
                dialogAddAppData.dismiss();
                Snackbar.make(addAppFormBinding.buttonAdd.getRootView(), getString(R.string.new_app_added), Snackbar.LENGTH_LONG).show();
                appAddedDialog.show();
            });

        });
    }


}