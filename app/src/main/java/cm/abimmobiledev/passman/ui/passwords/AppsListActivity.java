package cm.abimmobiledev.passman.ui.passwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.database.PassManAppDatabase;
import cm.abimmobiledev.passman.database.entity.Application;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivityAppsListBinding;
import cm.abimmobiledev.passman.model.ApplicationModel;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.ui.passwords.adapter.PwdAdapter;
import cm.abimmobiledev.passman.usefull.Util;

public class AppsListActivity extends AppCompatActivity {

    ActivityAppsListBinding appsListBinding;
    private static final String TAG_APPS = "PM_APPS";
    ProgressDialog dialogAppsData;
    List<Application> applications;
    User ownerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appsListBinding = DataBindingUtil.setContentView(this, R.layout.activity_apps_list);
        appsListBinding.executePendingBindings();

        if (getIntent()!=null)
            ownerUser = (User) getIntent().getSerializableExtra(Navigator.USER_INTENT);

        else {
            Toast.makeText(getApplicationContext(), "You must send intent data...", Toast.LENGTH_SHORT).show();
            finish();
        }

        dialogAppsData = new ProgressDialog(AppsListActivity.this);
        dialogAppsData.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogAppsData.setCanceledOnTouchOutside(false);
        dialogAppsData.setTitle(getString(R.string.getting_apps));
        dialogAppsData.show();
        dbGetApplications();

        appsListBinding.addApp.setOnClickListener(v -> {
            //TODO : replace by bottom sheet
            Navigator.openAddAppFormPage(AppsListActivity.this, ownerUser);
        });

        appsListBinding.floatingBack.setOnClickListener(v -> Navigator.openMainMenuPage(AppsListActivity.this, ownerUser));

    }

    @Override
    public void onBackPressed(){
        Navigator.openMainMenuPage(AppsListActivity.this, ownerUser);
    }


    private void dbGetApplications(){

        ExecutorService execServiceApps = Executors.newSingleThreadExecutor();
        execServiceApps.execute(() -> {

            PassManAppDatabase passManAppDatabaseApps =
                    PassManAppDatabase.getInstance(getApplicationContext());

            applications = passManAppDatabaseApps.applicationDAO().findApplicationsForUser(ownerUser.userId);

            //post execute case 2
            runOnUiThread(() -> {
                dialogAppsData.dismiss();
                // update recycler
                if (applications==null || applications.isEmpty())
                    appsListBinding.tvNoItem.setVisibility(View.VISIBLE);
                else {
                    try {
                        PwdAdapter adapter = new PwdAdapter(convertAppModelFromApp(applications, ownerUser), ownerUser);
                        appsListBinding.appsRecycler.setHasFixedSize(true);
                        appsListBinding.appsRecycler.setLayoutManager(new LinearLayoutManager(this));
                        appsListBinding.appsRecycler.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.d(TAG_APPS, "dbGetApplications: "+e.getLocalizedMessage(), e);
                    }
                }
            });
        });
    }


    public static List<ApplicationModel> convertAppModelFromApp(List<Application> applications, User oUser) throws Exception {

        List<ApplicationModel> applicationModels = new ArrayList<>();

        if (applications==null)
            return applicationModels;

        for (int count = 0; count < applications.size(); count ++) {
            Application app = applications.get(count);
            //app.setPassword(Util.decrypt(oUser.getEncryptionKey(), app.getPassword()));
            applicationModels.add(new ApplicationModel(app.getName(), app.getDescription(), app.getUsername(), app.getPassword(), app.getLogo()));
        }

        return applicationModels;
    }


}