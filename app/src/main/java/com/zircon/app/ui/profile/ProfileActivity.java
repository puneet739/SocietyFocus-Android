package com.zircon.app.ui.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zircon.app.R;
import com.zircon.app.model.LoginCredentials;
import com.zircon.app.model.User;
import com.zircon.app.model.request.UploadImage;
import com.zircon.app.model.response.BaseResponse;
import com.zircon.app.model.response.UploadImageResponse;
import com.zircon.app.model.response.UserResponse;
import com.zircon.app.ui.common.activity.nonav.BaseABNoNavActivity;
import com.zircon.app.utils.AuthCallBack;
import com.zircon.app.utils.HTTP;
import com.zircon.app.utils.Log;
import com.zircon.app.utils.SessionManager;
import com.zircon.app.utils.PermissionCheck;

import java.io.ByteArrayOutputStream;
import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikoobaruah on 09/02/16.
 */
public class ProfileActivity extends BaseABNoNavActivity {

    EditText mFirstNameView;
    EditText mAddressView;
    EditText mPhoneNoView;
    EditText mAboutYourselfView, mOccupationView;

    ImageView mProfileImageView;
    TextView mChangeImageView, mRemoveImageView;

    Button mChangePasswordButton;
    EditText mOldPassView;
    EditText mNewPassView;
    EditText mConfirmNewPassView;

    String firstName;
    String address;
    String phoneNo;
    String aboutyourself, occupation;

    String oldPass;
    String newPass;
    String newPassConfirm;
    String imgDecodableString;

    User user;

    int REQUEST_CAMERA = 3;
    int SELECT_FILE = 2;
    int RESULT_CROP = 200;
    String userChoosenTask;
    int isImageChanged = 0;
    Bitmap Imagebitmap;
    private View mProgressView;

    @Override
    protected int getContentViewResID() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initViews() {

        setTitle("Edit Profile");
        mProgressView = findViewById(R.id.login_progress);

        mChangeImageView = (TextView) findViewById(R.id.changepic);
//        mProfileImageView = (ImageView) findViewById(R.id.image);

        mOccupationView = (EditText) findViewById(R.id.profile_occupation);
        mFirstNameView = (EditText) findViewById(R.id.profile_name);
        mAddressView = (EditText) findViewById(R.id.profile_address);
        mPhoneNoView = (EditText) findViewById(R.id.profile_phoneno);
        mAboutYourselfView = (EditText) findViewById(R.id.profile_aboutyourself);

        mChangePasswordButton = (Button) findViewById(R.id.changepassword);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Changepassword.class);
                startActivity(intent);
            }
        });

        user = SessionManager.getLoggedInUser();

        mFirstNameView.setText(user.firstname);
        mAddressView.setText((user.address == null ? "" : user.address));
        mPhoneNoView.setText((user.contactNumber == null ? "" : user.contactNumber));
        mAboutYourselfView.setText((user.description == null ? "" : user.description));
        mOccupationView.setText((user.occupation == null ? "" : user.occupation));

        mChangeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeImage();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void onChangeImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionCheck.checkPermission(ProfileActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionCheck.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.temp, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            // When an Image is picked
            if (null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Imagebitmap = BitmapFactory.decodeFile(imgDecodableString);
                isImageChanged = 1;
                Log.e("ImageBytes", "Image: " + imgDecodableString);
                UploadImagetoServer();
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        Imagebitmap = thumbnail;
        isImageChanged = 1;
        UploadImagetoServer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action:
                onChangeProfileSubmit(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onChangeProfileSubmit(View view) {
        firstName = mFirstNameView.getText().toString().trim();
        address = mAddressView.getText().toString().trim();
        phoneNo = mPhoneNoView.getText().toString().trim();
        aboutyourself = mAboutYourselfView.getText().toString().trim();
        occupation = mOccupationView.getText().toString().trim();

        boolean isDataChanged = false;
        if (isImageChanged == 1) {
            isDataChanged = true;
        } else if (isImageChanged == -1) {
            isDataChanged = true;
            user.profilePic = null;
        }

        if ((firstName != null) && (firstName.length() > 0) && (!firstName.equals(user.firstname))) {
            user.firstname = firstName;
            isDataChanged = true;
        }
        if ((address != null) && (address.length() > 0) && (!address.equals(user.address))) {
            user.address = address;
            isDataChanged = true;
        }
        if ((phoneNo != null) && (phoneNo.length() > 0) && (!phoneNo.equals(user.contactNumber))) {
            user.contactNumber = phoneNo;
            isDataChanged = true;
        }
        if ((aboutyourself != null) && (aboutyourself.length() > 0) && (!aboutyourself.equals(user.description))) {
            user.description = aboutyourself;
            isDataChanged = true;
        }
        if ((occupation != null) && (occupation.length() > 0) && (!occupation.equals(user.occupation))) {
            user.occupation = occupation;
            isDataChanged = true;
        }

        if (!isDataChanged)
            return;

        Call<UserResponse> call = HTTP.getAPI().modifyUser(SessionManager.getToken(), user);
        call.enqueue(new AuthCallBack<UserResponse>() {
            @Override
            protected void onAuthError() {
                //TODO handle this
            }

            @Override
            protected void parseSuccessResponse(Response<UserResponse> response) {
                if (response.isSuccess()) {
                    SessionManager.setLoggedInUser(user);
                    Toast.makeText(ProfileActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Temporary", "error message " + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }

    private void UploadImagetoServer() {
        showProgress(true);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Imagebitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        String encodedImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
        UploadImage image = new UploadImage("data:image/jpeg;base64," + encodedImage, "filename.jpg");

        Call<UploadImageResponse> call = HTTP.getAPI().uploadimage(image);

        call.enqueue(new AuthCallBack<UploadImageResponse>() {


            @Override
            protected void onAuthError() {
                showProgress(false);
            }

            @Override
            protected void parseSuccessResponse(Response<UploadImageResponse> response) {
                if (response.isSuccess()) {
                    showProgress(false);
                    if (response.body().getBody() != null) {
                        Log.e("Imageresponse", "ImageURL " + response.body().getBody());
                        user.profilePic = "https://"+response.body().getBody();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showProgress(false);
                t.getLocalizedMessage();
            }
        });
    }

    public void onChangePassword() {
        mOldPassView = (EditText) findViewById(R.id.oldpassword);
        mNewPassView = (EditText) findViewById(R.id.newpassword);
        mConfirmNewPassView = (EditText) findViewById(R.id.confirmpassword);

        oldPass = mOldPassView.getText().toString();
        newPass = mNewPassView.getText().toString();
        newPassConfirm = mConfirmNewPassView.getText().toString();

        if (TextUtils.isEmpty(oldPass)) {
            mOldPassView.setError("Enter this field");
            mOldPassView.requestFocus();
        } else if (!oldPass.equals(SessionManager.getLoginCredentials().password)) {
            mOldPassView.setError("Invalid Password");
            mOldPassView.requestFocus();

        } else if (TextUtils.isEmpty(newPass)) {
            mNewPassView.setError("Enter this field");
            mNewPassView.requestFocus();

        } else if (!newPass.equals(newPassConfirm)) {
            mConfirmNewPassView.setError("Passwords donot match");
            mConfirmNewPassView.requestFocus();

        } else {

            Call<BaseResponse> call = HTTP.getAPI().modifyPassword(SessionManager.getToken(), oldPass, newPass, SessionManager.getLoggedInUser().email);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Response<BaseResponse> response) {
                    if (response.isSuccess()) {
                        LoginCredentials credentials = SessionManager.getLoginCredentials();
                        credentials.password = newPass;
                        SessionManager.setLoginCredentials(credentials);
                        Toast.makeText(ProfileActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Profile", "test " + response.message() + " " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.getLocalizedMessage();
                    Log.e("Profile", "test " + t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    protected View.OnClickListener getFABClickListener() {
        return null;
    }
}
