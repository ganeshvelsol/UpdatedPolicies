package simplytextile.policytracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;


import simplytextile.policytracker.activties.AgentsListActivity;
import simplytextile.policytracker.activties.CompaniesListAct;
import simplytextile.policytracker.activties.CustomerActivity;
import simplytextile.policytracker.activties.HomeActivity;
import simplytextile.policytracker.activties.LoginActivity;
import simplytextile.policytracker.activties.NotificationActivity;
import simplytextile.policytracker.activties.PoliciesActivity;
import simplytextile.policytracker.activties.UserProfileActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ImageView profileimage;
    TextView uname,profilename;
    SharedPreferences mPrefs;
    SharedPreferences.Editor editor;
    String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        uname=(TextView)findViewById(R.id.username);
        uname.setText((LoginActivity.FirstName));
        setSupportActionBar(toolbar);
//        profileimage=(ImageView)findViewById(R.id.profileimage);
//        profileimage.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent updateprofile=new Intent(MainActivity.this, UpdateUserProfileActivity.class);
//                startActivity(updateprofile);
//            }
//        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab);
//        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter()
//        {
//            @Override
//            public boolean onPrepareMenu(NavigationMenu navigationMenu)
//            {
//                // TODO: Do something with yout menu items, or return false if you don't want to show them
//                return true;
//            }
//            @Override
//            public boolean onMenuItemSelected(MenuItem menuItem)
//            {
//                //TODO: Start some activity
//                return false;
//            }
//        });


      //  BarChart chart = (BarChart) findViewById(R.id.chart);
//        // PieChart pieChart=(PieChart)findViewById(R.id.piechart);
//
//        List<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(0f, 30f));
//        entries.add(new BarEntry(2f, 10f));
//        entries.add(new BarEntry(4f, 60f));
//        BarDataSet set = new BarDataSet(entries, "Policy count by Type");
//        set.setColors(ColorTemplate.COLORFUL_COLORS);
//        chart.animateY(5000);
//
//        BarData data = new BarData(set);
//        data.setBarWidth(1.0f); // set custom bar width
//        chart.setData(data);
//        chart.setFitBars(true); // make the x-axis fit exactly all bars
//        chart.invalidate(); // refresh



//        List<PieEntry> entries1 = new ArrayList<>();
//
//        entries1.add(new PieEntry(18.5f, "Green"));
//        entries1.add(new PieEntry(26.7f, "Yellow"));
//        entries1.add(new PieEntry(24.0f, "Red"));
//        entries1.add(new PieEntry(30.8f, "Blue"));
//
//        PieDataSet set1 = new PieDataSet(entries1, "Election Results");
//        PieData data1 = new PieData(set1);
//        pieChart.setData(data1);
//        pieChart.invalidate();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         tid=LoginActivity.typeid;
        if(tid.equals("6501"))
        {


            navigationView.getMenu().findItem(R.id.agent).setVisible(true);

        }
        else
        {
            navigationView.getMenu().findItem(R.id.agent).setVisible(false);
        }
//        navigationView.getMenu().findItem(R.id.agent).setVisible(true);
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);


        View headerview = navigationView.getHeaderView(0);
         profilename = (TextView) headerview.findViewById(R.id.username);

        profilename.setText(LoginActivity.FirstName);
        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent updateprofile=new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(updateprofile);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home)
        {
            // Handle the camera action
            Intent home =new Intent(MainActivity.this, HomeActivity.class);
            startActivity(home);
        }
        else if (id == R.id.company)
        {
            Intent company =new Intent(MainActivity.this, CompaniesListAct.class);
            startActivity(company);
        }
        else if (id == R.id.policies)
        {
            Intent policies =new Intent(MainActivity.this, PoliciesActivity.class);
            startActivity(policies);
        }
        else if(id==R.id.agent)
        {
            Intent agent =new Intent(MainActivity.this, AgentsListActivity.class);
            startActivity(agent);

        }


        else if (id == R.id.customers)
        {
            Intent customer =new Intent(MainActivity.this, CustomerActivity.class);
            startActivity(customer);

        }
        else if (id == R.id.notifications)
        {
            Intent  notification=new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(notification);
        }
        else if (id == R.id.logout)
        {
            editor.clear();
            editor.commit();
            Intent logout=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(logout);
            SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
            String S_id = mPrefs.getString("key", "");


        }
//        else if (id == R.id.nav_share)
//        {
//
//        }
//        else if (id == R.id.nav_send)
//        {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
