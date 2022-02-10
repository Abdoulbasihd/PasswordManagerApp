package cm.abimmobiledev.passman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import cm.abimmobiledev.passman.databinding.ActivityMainBinding;
import cm.abimmobiledev.passman.nav.Navigator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN = "PM_MAIN";
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        //no model for main
        activityMainBinding.executePendingBindings();

        activityMainBinding.cardMyPassword.setOnClickListener(v -> {
            Navigator.openAppsPage(MainActivity.this, "", "");
        });

        activityMainBinding.cardMyAccount.setOnClickListener(v -> {
            //TODO
        });

        activityMainBinding.cardAboutApp.setOnClickListener(v -> {
            //TODO
        });

        activityMainBinding.cardOtherSettings.setOnClickListener(v -> {
            //TODO
        });
    }
}