package simplytextile.policytracker.activties;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.NotificationResponse.Notresponse;
import simplytextile.policytracker.R;
import simplytextile.policytracker.adapters.NotificationAdapter;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.response.AgentsResponse;
import simplytextile.policytracker.response.CustomerResponse;
import simplytextile.policytracker.response.PoliciesResponse;

public class NotificationActivity extends AppCompatActivity
{
    RecyclerView notification_recycler;
    LinearLayoutManager llm;
    LinearLayout data_loading_screen_layout,liner_notifi,recycler_linear;
    String S_id,customerName,agentName;
    List agentNamesList=new ArrayList();
    List customerNameList=new ArrayList();
    Spinner spinner_customer_notif,spinner_agent_notif,spinner_sent_via_notif,spinner_type_notif;
    EditText policy_number_notif,policy_start_notif,policy_end_notif;
    ImageView start_date_img,end_date_img;
    Button cancel_btn,ok_btn;
    int year,month,day,agentId,custId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        notification_recycler=(RecyclerView)findViewById(R.id.notificationrecylers);

        data_loading_screen_layout=(LinearLayout) findViewById(R.id.data_loading_screen_layout);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);
        llm=new LinearLayoutManager(this);
        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
         S_id = mPrefs.getString("key", "");

        liner_notifi=(LinearLayout)findViewById(R.id.liner_notifi);
        recycler_linear=(LinearLayout)findViewById(R.id.recycler_linear);

        spinner_customer_notif=(Spinner)findViewById(R.id.spinner_customer_notif);
        spinner_agent_notif=(Spinner)findViewById(R.id.spinner_agent_notif);
        spinner_sent_via_notif=(Spinner)findViewById(R.id.spinner_sent_via_notif);
        spinner_type_notif=(Spinner)findViewById(R.id.spinner_type_notif);
        policy_number_notif=findViewById(R.id.policy_number_notif);
        policy_start_notif=findViewById(R.id.policy_start_notif);
        policy_end_notif=findViewById(R.id.policy_end_notif);

        start_date_img=(ImageView)findViewById(R.id.start_date_img);
        end_date_img=(ImageView)findViewById(R.id.end_date_img);
        cancel_btn=(Button)findViewById(R.id.cancel_btn);
        ok_btn=(Button) findViewById(R.id.ok_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liner_notifi.setVisibility(View.GONE);
            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (LoginActivity.typeid.equals("6500"))
                {
                    //pushing the agent notification filter
                    agentFiltersPush();

                }else
                {
                    //pushing manager notification filter data to server
                    pushToServer();
                }

            }
        });

        displayNotifications();
    }
public void  displayNotifications()
{
    data_loading_screen_layout.setVisibility(View.VISIBLE);
    ApiService planView = ApiClient.getClient().create(ApiService.class);
    Call<Notresponse> policResponse= planView.getNotification(S_id);
    policResponse.enqueue(new Callback<Notresponse>()
    {
        @Override
        public void onResponse(Call<Notresponse> call, Response<Notresponse> response)
        {
            data_loading_screen_layout.setVisibility(View.GONE);
            if (response.body().getStatuscode()==0)
            {
                NotificationAdapter adapter=new NotificationAdapter(response.body().getData().getNotification_list(),NotificationActivity.this);
                notification_recycler.setAdapter(adapter);
                notification_recycler.setLayoutManager(llm);
            }
            else
            {
                Toast.makeText(NotificationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Notresponse> call, Throwable t)
        {
            data_loading_screen_layout.setVisibility(View.GONE);
            Toast.makeText(NotificationActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {


        int id=item.getItemId();
        if (R.id.notification_filter==id)
        {

            recycler_linear.setVisibility(View.GONE);
            liner_notifi.setVisibility(View.VISIBLE);

            if (LoginActivity.typeid.equals("6500"))
            {
                callAgentFilter();
            }
            else
            {
                displayLineraLayouts();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void callAgentFilter()
    {
        spinner_agent_notif.setVisibility(View.GONE);

        start_date_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dp=new DatePickerDialog(NotificationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        policy_start_notif.setText(""+year+"/"+(month-1)+"/"+dayOfMonth);
                    }
                },year,month,day);
                dp.show();
            }
        });


        end_date_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dp=new DatePickerDialog(NotificationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        policy_end_notif.setText(""+year+"/"+(month-1)+"/"+dayOfMonth);
                    }
                },year,month,day);
                dp.show();
            }
        });

        ApiService ser=ApiClient.getClient().create(ApiService.class);
        Call<CustomerResponse> cresp=ser.getCustomers(S_id);
        cresp.enqueue(new Callback<CustomerResponse>()
        {
            @Override
            public void onResponse(Call<CustomerResponse> call, final Response<CustomerResponse> response)
            {
                customerNameList.add("-- select customer --");
                if (response.body().getStatuscode()==0)
                {
                    for (int k=0;k<response.body().getData().getCustomer_list().size();k++)
                    {
                        customerNameList.add(response.body().getData().getCustomer_list().get(k).getFirst_name()+" "+response.body().getData().getCustomer_list().get(k).getLast_name());
                    }
                    ArrayAdapter sp=new ArrayAdapter(NotificationActivity.this,android.R.layout.simple_spinner_dropdown_item,customerNameList);
                    spinner_customer_notif.setAdapter(sp);
                }
                spinner_customer_notif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (position==0)
                        {

                        }else
                        {
                            customerName=parent.getSelectedItem().toString().trim();
                            custId=response.body().getData().getCustomer_list().get(position-1).getId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {

            }
        });




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_type_notif, android.R.layout.simple_spinner_item);
        spinner_sent_via_notif.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.select_types, android.R.layout.simple_spinner_item);
        spinner_type_notif.setAdapter(adapter1);


    }
    public void displayLineraLayouts()
    {


        start_date_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dp=new DatePickerDialog(NotificationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        policy_start_notif.setText(""+year+"/"+(month-1)+"/"+dayOfMonth);
                    }
                },year,month,day);
                dp.show();
            }
        });


        end_date_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dp=new DatePickerDialog(NotificationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        policy_end_notif.setText(""+year+"/"+(month-1)+"/"+dayOfMonth);
                    }
                },year,month,day);
                dp.show();
            }
        });


        ApiService ser=ApiClient.getClient().create(ApiService.class);
        Call<AgentsResponse> ares= ser.getAgents(S_id);
        ares.enqueue(new Callback<AgentsResponse>()
        {
            @Override
            public void onResponse(Call<AgentsResponse> call, final Response<AgentsResponse> response)
            {
                agentNamesList.add("-- select agent --");
                if (response.body().getStatuscode()==0)
                {
                    for (int i=0;i<response.body().getData().getAgentList().size();i++)
                    {
                        agentNamesList.add(response.body().getData().getAgentList().get(i).getFirst_name()+" "+response.body().getData().getAgentList().get(i).getLast_name());
                    }
                    ArrayAdapter aa=new ArrayAdapter(NotificationActivity.this,android.R.layout.simple_spinner_dropdown_item,agentNamesList);
                    spinner_agent_notif.setAdapter(aa);
                }
                spinner_agent_notif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (parent.getSelectedItem().toString().trim()!="-- select agent --")
                        {
                            Toast.makeText(NotificationActivity.this, ""+response.body().getData().getAgentList().get(position-1).getId(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<AgentsResponse> call, Throwable t)
            {

            }
        });


        Call<CustomerResponse> cresp=ser.getCustomers(S_id);
        cresp.enqueue(new Callback<CustomerResponse>()
        {
            @Override
            public void onResponse(Call<CustomerResponse> call, final Response<CustomerResponse> response)
            {
                customerNameList.add("-- select customer --");
                if (response.body().getStatuscode()==0)
                {
                    for (int k=0;k<response.body().getData().getCustomer_list().size();k++)
                    {
                        customerNameList.add(response.body().getData().getCustomer_list().get(k).getFirst_name()+" "+response.body().getData().getCustomer_list().get(k).getLast_name());
                    }
                    ArrayAdapter sp=new ArrayAdapter(NotificationActivity.this,android.R.layout.simple_spinner_dropdown_item,customerNameList);
                    spinner_customer_notif.setAdapter(sp);
                }
                spinner_customer_notif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (position==0)
                        {

                        }else
                        {
                            customerName=parent.getSelectedItem().toString().trim();
                            custId=response.body().getData().getCustomer_list().get(position-1).getId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {

            }
        });




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_type_notif, android.R.layout.simple_spinner_item);
        spinner_sent_via_notif.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.select_types, android.R.layout.simple_spinner_item);
        spinner_type_notif.setAdapter(adapter1);

    }

    public void pushToServer()
    {
        String pNUmber=policy_number_notif.getText().toString().trim();
        String sent=spinner_sent_via_notif.getSelectedItem().toString().trim();
        String type=spinner_type_notif.getSelectedItem().toString().trim();
        String policy_start_notifs=policy_start_notif.getText().toString().trim();
        String policy_end_notifs=policy_end_notif.getText().toString().trim();

        ApiService ser=ApiClient.getClient().create(ApiService.class);
        Call<Notresponse> notifiFilter=ser.getNotificationFilter(LoginActivity.Sid,pNUmber,sent,type,policy_start_notifs,policy_end_notifs,custId,agentId);
        notifiFilter.enqueue(new Callback<Notresponse>()
        {
            @Override
            public void onResponse(Call<Notresponse> call, Response<Notresponse> response)
            {
                //here we have to display the data as per response
                if (response.body().getStatuscode()==0)
                {
                    Toast.makeText(NotificationActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(NotificationActivity.this, "else case", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Notresponse> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "failure"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void agentFiltersPush()
    {
        String pNUmber=policy_number_notif.getText().toString().trim();
        String sent=spinner_sent_via_notif.getSelectedItem().toString().trim();
        String type=spinner_type_notif.getSelectedItem().toString().trim();
        String policy_start_notifs=policy_start_notif.getText().toString().trim();
        String policy_end_notifs=policy_end_notif.getText().toString().trim();

        ApiService servs=ApiClient.getClient().create(ApiService.class);
        Call<Notresponse> agenFilter=servs.getAgentNotificationFilter(LoginActivity.Sid,pNUmber,sent,type,policy_start_notifs,policy_end_notifs,custId);
        agenFilter.enqueue(new Callback<Notresponse>() {
            @Override
            public void onResponse(Call<Notresponse> call, Response<Notresponse> response)
            {
                if (response.body().getStatuscode()==0)
                {
                    Toast.makeText(NotificationActivity.this, "success", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(NotificationActivity.this, "failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Notresponse> call, Throwable t)
            {
                Toast.makeText(NotificationActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
