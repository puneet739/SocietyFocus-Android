package com.zircon.app.ui.common.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zircon.app.R;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.utils.Log;

/**
 * Created by jikoobaruah on 09/02/16.
 */
    public abstract class AbsBaseActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;
    private static final int REQUEST_SMS = 3;
    public static final int REQUEST_LOGIN = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupFAB(View.OnClickListener clickListener){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab == null)
            return;
        fab.setVisibility(clickListener == null ? View.GONE:View.VISIBLE);
        fab.setOnClickListener(clickListener);
    }

    protected abstract View.OnClickListener getFABClickListener();


    Intent callIntent;
    public void call(String number) {
        callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }else{
                startActivity(callIntent);
            }
        }else {
            startActivity(callIntent);
        }
    }

    Intent smsIntent;
    public void sms(String number) {
        smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  ,number);
        smsIntent.putExtra("sms_body"  , "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},REQUEST_SMS);
            }else{
                startActivity(smsIntent);
            }
        }else {
            startActivity(smsIntent);
        }
    }
    Intent emailIntent;
    public void sendEmail(String emailid) {
        emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailid});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AbsBaseActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int size = permissions.length;
        for (int i =0 ; i < size ; i++){
            if (requestCode == REQUEST_PHONE_CALL && permissions[i].equals(Manifest.permission.CALL_PHONE) && grantResults[i] == PackageManager.PERMISSION_GRANTED){
                startActivity(callIntent);
            }
            if (requestCode == REQUEST_SMS && permissions[i].equals(Manifest.permission.SEND_SMS) && grantResults[i] == PackageManager.PERMISSION_GRANTED){
                startActivity(smsIntent);
            }
        }
    }

    IAuthCallback authCallback;
    public void onAuthError(IAuthCallback authCallback) {
        if (authCallback == null)
            throw new NullPointerException("authcallback cannot be null");
        this.authCallback = authCallback;

        Intent intent = new Intent(AbsBaseActivity.this, LoginActivity.class);
        intent.putExtra("requestcode",REQUEST_LOGIN);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_LOGIN:
                    authCallback.onAuthSuccess();
                    break;
            }
        }
    }

    public interface IAuthCallback{
        public void onAuthSuccess();
    }
}
