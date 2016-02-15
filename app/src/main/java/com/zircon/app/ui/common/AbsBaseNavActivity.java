package com.zircon.app.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zircon.app.R;
import com.zircon.app.ui.assets.AssetsNavActivity;
import com.zircon.app.ui.complaint.AllComplaintsActivity;
import com.zircon.app.ui.complaint.ComplaintActivity;
import com.zircon.app.ui.home.MainNavActivity;
import com.zircon.app.ui.login.LoginActivity;
import com.zircon.app.ui.profile.ProfileActivity;
import com.zircon.app.ui.residents.MembersNavActivity;
import com.zircon.app.utils.SessionManager;

public abstract class AbsBaseNavActivity extends AbsBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected RelativeLayout mFragmentLayout;
    protected AbsFragment mFragment;

    private ImageView mChangeProfileBtn;
    private TextView mNameTextView;
    private ImageView mProfileImageView;
    private TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mNameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name);
        mEmailTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        mProfileImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_pic);

        String name = SessionManager.getLoggedInUser().firstname + " " +(SessionManager.getLoggedInUser().lastname != null ? SessionManager.getLoggedInUser().lastname:"");
        String email = SessionManager.getLoggedInUser().email;
        String profileImage = SessionManager.getLoggedInUser().profilePic;

        ImageLoader.getInstance().displayImage(profileImage, mProfileImageView);


        mNameTextView.setText(name);
        mEmailTextView.setText(email);

        mChangeProfileBtn = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.edit_profile);
        mChangeProfileBtn.setClickable(true);
        mChangeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbsBaseNavActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mFragmentLayout = (RelativeLayout)findViewById(R.id.fragment_container);

        mFragment = getFragment();

        if (mFragment != null)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();



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
            intent = new Intent(AbsBaseNavActivity.this, MainNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_rwa_members) {
            intent = new Intent(AbsBaseNavActivity.this, com.zircon.app.ui.panel.MembersNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (id == R.id.nav_assets) {
            intent = new Intent(AbsBaseNavActivity.this, AssetsNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        } else if (id == R.id.nav_residents) {
            intent = new Intent(AbsBaseNavActivity.this, MembersNavActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if (id == R.id.nav_logout){
            SessionManager.logoutUser();
            intent = new Intent(AbsBaseNavActivity.this, LoginActivity.class);
            isFinishCurrActivity = true;
        }else if (id == R.id.nav_complaint_new) {
            intent = new Intent(AbsBaseNavActivity.this, ComplaintActivity.class);
        } else if (id == R.id.nav_complaint_track) {
            intent = new Intent(AbsBaseNavActivity.this, AllComplaintsActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (intent != null){
            startActivity(intent);
            if (isFinishCurrActivity)
                finish();
        }

        return true;
    }

    abstract int getLayoutResID();

    protected abstract AbsFragment getFragment();
}
