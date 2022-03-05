package cm.abimmobiledev.passman;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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

    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILE_CHOOSER = 2000;

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
            importCsv.setOnClickListener(viewImp -> {
                Toast.makeText(MainActivity.this, "Coming up", Toast.LENGTH_SHORT).show();
                askPermissionAndBrowseFile();
            });

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
            //"FIRST TABLE OF THE DATABASE" : Ze title
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

    private void askPermissionAndBrowseFile()  {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_REQUEST_CODE_PERMISSION
                );
                return;
            }
        }
        this.doBrowseFile();
    }

    private void doBrowseFile()  {
        Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFileIntent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Selectionner le fichier CSV de vos mots de passe");
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILE_CHOOSER);
    }

    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        if (requestCode == MY_REQUEST_CODE_PERMISSION) {// Note: If request is cancelled, the result arrays are empty.
            // Permissions granted (CALL_PHONE).
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG_MAIN, "Permission granted!");
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                this.doBrowseFile();
            }
            // Cancelled or denied.
            else {
                Log.i(TAG_MAIN, "Permission denied!");
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_RESULT_CODE_FILE_CHOOSER) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri fileUri = data.getData();
                    Log.i(TAG_MAIN, "Uri: " + fileUri);

                    String filePath = null;
                    try {
                        filePath =fileUri.getPath();
                    } catch (Exception e) {
                        Log.e(TAG_MAIN, "Error: " + e);
                        Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                    }

                    readCsvFromFile(filePath);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void readCsvFromFile(String filePath) {
        Log.d(TAG_MAIN, "readCsvFromFile: "+filePath);
    }

}