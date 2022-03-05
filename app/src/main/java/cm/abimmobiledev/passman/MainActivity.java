package cm.abimmobiledev.passman;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cm.abimmobiledev.passman.database.PassManAppDatabase;
import cm.abimmobiledev.passman.database.entity.Application;
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.databinding.ActivityMainBinding;
import cm.abimmobiledev.passman.nav.Navigator;
import cm.abimmobiledev.passman.usefull.Util;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN = "PM_MAIN";
    ActivityMainBinding activityMainBinding;
    private User connectedUser;
    ProgressDialog progressDataExport;
    AlertDialog.Builder appExportedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        //no model for main
        activityMainBinding.executePendingBindings();

        //initializing data with bundle
        getMainBundleElements(getIntent());

        appExportedDialog = Util.getDialog(this, getString(R.string.csv_export), getString(R.string.your_apps_data_have_been_exported));
        progressDataExport = Util.progressDialogInit(this, getString(R.string.csv_import_export));
        appExportedDialog.setPositiveButton("OK", (dialog, which) -> {
            //just close
        });
        activityMainBinding.cardMyPassword.setOnClickListener(v -> Navigator.openAppsPage(MainActivity.this, connectedUser));

        activityMainBinding.cardMyAccount.setOnClickListener(v -> {
            //TODO
        });

        activityMainBinding.cardAboutApp.setOnClickListener(v -> {
            BottomSheetDialog aboutBottomDialog = new BottomSheetDialog(MainActivity.this);
            aboutBottomDialog.setContentView(R.layout.bottom_sheet_about_dialog_layout);
            aboutBottomDialog.show();
        });

        activityMainBinding.cardOtherSettings.setOnClickListener(v -> {
            BottomSheetDialog otherBottomDialog = new BottomSheetDialog(MainActivity.this);

            otherBottomDialog.setContentView(R.layout.bottom_sheet_setting_layout);

             CardView exportCsv = otherBottomDialog.findViewById(R.id.export_csv);
             CardView importCsv = otherBottomDialog.findViewById(R.id.import_csv);

            assert exportCsv != null;
            exportCsv.setOnClickListener(viewExp -> {
                 Toast.makeText(MainActivity.this, "Coming up", Toast.LENGTH_SHORT).show();

                 //set progress visible before getting data from base and write in file
                progressDataExport.show();

                //room db should be executed in a different thread
                ExecutorService importExportExec = Executors.newSingleThreadExecutor();
                importExportExec.execute(() -> {

                    String exportedFilePath = exportDatabase(connectedUser.getUserId());

                    if (exportedFilePath==null) {
                        appExportedDialog.setMessage(R.string.an_error_occurred_while_exporting_to_csv);
                    }

                    runOnUiThread(() -> {
                        String msg = getString(R.string.your_apps_data_have_been_exported)+" \n"+exportedFilePath;
                        appExportedDialog.setMessage(msg);
                        appExportedDialog.show();
                        progressDataExport.dismiss();
                    });
                });


             });


            assert importCsv != null;
            importCsv.setOnClickListener(v1 -> Toast.makeText(MainActivity.this, "Coming up", Toast.LENGTH_SHORT).show());

            otherBottomDialog.show();
        });

    }

    @Override
    public void onBackPressed(){
        Navigator.openSignInPage(MainActivity.this);
    }

    public void getMainBundleElements(Intent intentBundle){

        if (intentBundle==null)
            return; // todo : throw exception here

        connectedUser = (User) intentBundle.getSerializableExtra(Navigator.USER_INTENT);

    }

    public String exportDatabase(int ownerId) {

        /* *First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */

        File exportDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG_MAIN, "exportDatabase directory: "+exportDir.getAbsolutePath());
        if (!exportDir.exists()) {
            boolean fileCreated = exportDir.mkdirs();
            if(!fileCreated)
                return null; //should be replaced by an exception
        }

        File file;
        PrintWriter printWriter = null;
        try {
            file = new File(exportDir, "MyCSVFile.csv");
            boolean created = file.createNewFile();
            Log.d(TAG_MAIN, "exportDatabase: created file csv path "+file.getAbsolutePath());
            if(!created)
                return null; //TODO replace by an exception
            printWriter = new PrintWriter(new FileWriter(file));

            /* *This is our database connector class that reads the data from the database.
             * The code of this class is omitted for brevity.
             */

            PassManAppDatabase passManAppDatabaseAddApp =
                    PassManAppDatabase.getInstance(getApplicationContext());


            //Get apps of a given user
            List<Application> userApps = passManAppDatabaseAddApp.applicationDAO().findApplicationsForUser(ownerId);
            //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
            printWriter.println("FIRST TABLE OF THE DATABASE");
            printWriter.println("app_id,owner_user_id,app_desc,app_pass,app_username,app_logo,saved_on_line");

            for(int line=0; line<userApps.size(); line++) {
                /* *Create the line to write in the .csv file.
                 **We need a String where values are comma separated.
                 **/
                Application appLine = userApps.get(line);
                printWriter.println(formatCsvRecord(appLine)); //write the record in the .csv file
            }


        }

        catch(Exception exc) {
            //if there are any exceptions, return false
            Log.d(TAG_MAIN, "exportDatabase: "+exc.getLocalizedMessage(), exc);
            return null; //TODO replace by an exception throw
        }
        finally {
            if(printWriter != null) printWriter.close();
        }

        //If there are no errors, return true.
        return file.getAbsolutePath();

    }

    public static String formatCsvRecord(Application app) {
        return app.getAppId() + "," + app.getOwnerUserId() + "," + app.getDescription() + "," + app.getPassword() + "," + app.getUsername() + "," + app.getLogo() + "," + app.isSavedOnline();
    }

}