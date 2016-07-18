package com.zircon.app.ui.common.activity.nav;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.zircon.app.BuildConfig;
import com.zircon.app.Changepassword;
import com.zircon.app.R;
import com.zircon.app.model.User;
import com.zircon.app.ui.assets.browse.AssetsNavActivity;
import com.zircon.app.ui.assets.browsebooking.BrowseAssetBookingActivity;
import com.zircon.app.ui.common.activity.AbsBaseActivity;
import com.zircon.app.ui.common.fragment.AbsFragment;
import com.zircon.app.ui.complaint.AllComplaintsActivity;
import com.zircon.app.ui.complaint.ComplaintActivity;
import com.zircon.app.ui.home.MainNavActivity;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.ui.profile.ProfileActivity;
import com.zircon.app.ui.residents.MembersNavActivity;
import com.zircon.app.utils.SessionManager;

public abstract class BaseNavActivity extends AbsBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected RelativeLayout mFragmentLayout;
    protected AbsFragment mFragment;
    protected ImageView mNavHeaderView;

    private TextView mSocietyNameTextView;
    private ImageView mChangeProfileBtn;
    private TextView mNameTextView;
    private ImageView mProfileImageView;
    private TextView mEmailTextView, mPhoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(getLayoutResID());
        setupFAB(getFABClickListener());

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mSocietyNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.society_name);
        mNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name);
        mEmailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        mPhoneTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.phone);
        mNavHeaderView =(ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_background);

        mProfileImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_pic);


        mChangeProfileBtn = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.edit_profile);
        mChangeProfileBtn.setClickable(true);
        mChangeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseNavActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mFragmentLayout = (RelativeLayout) findViewById(R.id.fragment_container);

        mFragment = getFragment();

        if (mFragment != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();

    }


    @Override
    protected void onResume() {
        super.onResume();
        User loggedInUser = SessionManager.getLoggedInUser();
        String societyName = SessionManager.getLoggedInSociety().name;
        String societyPic=SessionManager.getLoggedInSociety().societypic;

        String name = loggedInUser.firstname + " " + (loggedInUser.lastname != null ? loggedInUser.lastname : "");
        String email = loggedInUser.email;
        String phone=loggedInUser.contactNumber;
        String profileImage = loggedInUser.profilePic;

        if (!TextUtils.isEmpty(profileImage))
            Picasso.with(this).load(profileImage).into(mProfileImageView);

        if (!TextUtils.isEmpty(societyPic))
            Picasso.with(this).load(societyPic).into(mNavHeaderView);

        mPhoneTextView.setText((phone==null?"":phone));
        mSocietyNameTextView.setText(societyName);
        mNameTextView.setText(name);
        mEmailTextView.setText(email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        boolean isFinishCurrActivity = false;

        if (id == R.id.nav_home) {
            intent = new Intent(BaseNavActivity.this, MainNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_rwa_members) {
            intent = new Intent(BaseNavActivity.this, com.zircon.app.ui.panel.MembersNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_assets) {
            intent = new Intent(BaseNavActivity.this, AssetsNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_asset_booking) {
            intent = new Intent(BaseNavActivity.this, BrowseAssetBookingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_residents) {
            intent = new Intent(BaseNavActivity.this, MembersNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_logout) {
            SessionManager.logoutUser();
            LoginManager.getInstance().logOut();
            intent = new Intent(BaseNavActivity.this, LoginActivity.class);
            isFinishCurrActivity = true;
        } else if (id == R.id.nav_complaint_new) {
            intent = new Intent(BaseNavActivity.this, ComplaintActivity.class);
        } else if (id == R.id.nav_complaint_track) {
            intent = new Intent(BaseNavActivity.this, AllComplaintsActivity.class);
        }else if (id == R.id.nav_changepassword) {
            intent = new Intent(BaseNavActivity.this, Changepassword.class);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (intent != null) {
            startActivity(intent);
            if (isFinishCurrActivity)
                finish();
        }
        return true;
    }

    abstract int getLayoutResID();

    protected abstract AbsFragment getFragment();
}
