package simplytextile.policytracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.activties.AgentsListActivity;
import simplytextile.policytracker.activties.CompaniesListAct;
import simplytextile.policytracker.activties.CustomerActivity;
import simplytextile.policytracker.activties.HomeActivity;
import simplytextile.policytracker.activties.LoginActivity;
import simplytextile.policytracker.activties.NotificationActivity;
import simplytextile.policytracker.activties.PoliciesActivity;
import simplytextile.policytracker.activties.UserProfileActivity;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.dashboardresponse.DashBoardResposne;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnChartValueSelectedListener {


    ImageView profileimage;
    TextView uname,profilename;
    TextView poli_count,poli_desc;
    TextView lapsedcount,lapseddesc;
    SharedPreferences mPrefs;
    String graph3;
    String graph2;
    float bar1;
    SharedPreferences.Editor editor;
    String tid;
    com.github.clans.fab.FloatingActionButton adding_employee_fab,adding_category_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        uname=(TextView)findViewById(R.id.username);
        poli_count=(TextView)findViewById(R.id.count_customer);
        poli_desc=(TextView)findViewById(R.id.desc_customer);
        lapsedcount=(TextView)findViewById(R.id.count_lappolicy);
        lapseddesc=(TextView)findViewById(R.id.desc_lappolicy);

        uname.setText((LoginActivity.FirstName));
        adding_employee_fab=(com.github.clans.fab.FloatingActionButton)findViewById(R.id.adding_employee_fab);
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


        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        String S_id = mPrefs.getString("key", "");
        ApiService planView = ApiClient.getClient().create(ApiService.class);
        Call<DashBoardResposne> customers=planView.dashBoard(S_id);
        customers.enqueue(new Callback<DashBoardResposne>()
        {
            @Override
            public void onResponse(Call<DashBoardResposne> call, Response<DashBoardResposne> response)
            {
                if (response.body().getStatuscode()==0)
                {
                  /*  Toast.makeText(MainActivity.this, ""+response.body().getData().getDashboard().getDataset().get(9).getRowlist().get(0).getCount(), Toast.LENGTH_SHORT).show();
                    String policy_count= String.valueOf(response.body().getData().getDashboard().getDataset().get(9).getRowlist().get(0).getCount());
                    String policy_desc=String.valueOf(response.body().getData().getDashboard().getDataset().get(9).getRowlist().get(0).getDescription());
                    poli_count.setText(policy_count);
                    poli_desc.setText(policy_desc);
                    String lapse_count= String.valueOf(response.body().getData().getDashboard().getDataset().get(9).getRowlist().get(2).getCount());
                    String lapse_desc=String.valueOf(response.body().getData().getDashboard().getDataset().get(9).getRowlist().get(2).getDescription());
                    lapsedcount.setText(lapse_count);
                    lapseddesc.setText(lapse_desc);
                    String bar_0_text=String.valueOf(response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(0).getName());
                    String bar_1_text=String.valueOf(response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(1).getName());
                    String bar_2_text=String.valueOf(response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(2).getName());

                    graph3= String.valueOf(response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(0).getCount());
                    float bar0=response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(0).getCount();
                    float bar1=response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(1).getCount();
                    float bar2=response.body().getData().getDashboard().getDataset().get(0).getRowlist().get(2).getCount();

                    String bar_policycounttypename_0=String.valueOf(response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(0).getName());
                    String bar_policycounttypename_1=String.valueOf(response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(1).getName());
                    String bar_policycounttypename_2=String.valueOf(response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(2).getName());
                    String bar_policycounttypename_3=String.valueOf(response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(3).getName());
                    float bar_policycount_0=response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(0).getCount();
                    float bar_policycount_1=response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(1).getCount();
                    float bar_policycount_2=response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(2).getCount();
                    float bar_policycount_3=response.body().getData().getDashboard().getDataset().get(2).getRowlist().get(3).getCount();





                    Toast.makeText(MainActivity.this, ""+graph3, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, ""+response.body().getData().getDashboard().getDataset().get(9).getRowlist().get(0).getDescription(), Toast.LENGTH_SHORT).show();


                    String pie_0_text=String.valueOf(response.body().getData().getDashboard().getDataset().get(1).getRowlist().get(0).getName());
                    String pie_1_text=String.valueOf(response.body().getData().getDashboard().getDataset().get(1).getRowlist().get(1).getName());

                    float pie0=response.body().getData().getDashboard().getDataset().get(1).getRowlist().get(0).getCount();
                    float pie1=response.body().getData().getDashboard().getDataset().get(1).getRowlist().get(1).getCount();



                    BarChart chart = (BarChart) findViewById(R.id.chart);
                    ArrayList<BarEntry> entries = new ArrayList();
                    entries.add(new BarEntry(bar0, 0));
                    entries.add(new BarEntry(bar1, 1));
                    entries.add(new BarEntry(bar2, 2));
                    BarDataSet dataset = new BarDataSet(entries, "Policy Count By Company");
                    ArrayList<String> labels = new ArrayList();
                    labels.add(bar_0_text);
                    labels.add(bar_1_text);
                    labels.add(bar_2_text);
                    BarData data = new BarData(labels, dataset);
                    chart.setData(data); // set the data and list of lables into chart<br />



                    BarChart chart2 = (BarChart) findViewById(R.id.barchart_policycountbytype);
                    ArrayList<BarEntry> entries2 = new ArrayList();
                    entries2.add(new BarEntry(12, 0));
                    entries2.add(new BarEntry(1, 1));
                    entries2.add(new BarEntry(5, 2));
                    BarDataSet dataset2 = new BarDataSet(entries2, "Policy count by SubType");
                    ArrayList<String> labels2 = new ArrayList();
                    labels2.add(bar_policycounttypename_0);
                    labels2.add(bar_policycounttypename_1);
                    labels2.add(bar_policycounttypename_2);
                    labels2.add(bar_policycounttypename_3);
                    BarData data2 = new BarData(labels2, dataset2);
                    chart2.setData(data2);

                    PieChart pieChart = (PieChart) findViewById(R.id.piechart);
                    //pieChart.setUsePercentValues(true);
                    ArrayList<Entry> yvalues = new ArrayList<Entry>();
                    yvalues.add(new Entry(pie0, 0));
                    yvalues.add(new Entry(pie1, 1));
                    PieDataSet dataSet = new PieDataSet(yvalues, "Policy Count By Type");
                    ArrayList<String> xVals = new ArrayList<String>();
                    xVals.add(pie_0_text);
                    xVals.add(pie_1_text);
                    PieData data1 = new PieData(xVals, dataSet);
                    data.setValueFormatter(new PercentFormatter());
                    pieChart.setData(data1);
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setTransparentCircleRadius(25f);
                    pieChart.setHoleRadius(15f);
                    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                    data.setValueTextSize(15f);
                    data.setValueTextColor(Color.DKGRAY);
                //    pieChart.setOnChartValueSelectedListener(this);
                    pieChart.animateXY(1400, 1400);


*/
                }
                else
                {
                    Toast.makeText(MainActivity.this, "from else"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DashBoardResposne> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        adding_employee_fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "add employee", Toast.LENGTH_SHORT).show();
            }
        });


//
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h)
    {

    }

    @Override
    public void onNothingSelected()
    {

    }
}
