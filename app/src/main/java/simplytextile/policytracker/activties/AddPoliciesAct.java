package simplytextile.policytracker.activties;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.R;
import simplytextile.policytracker.Utills;
import simplytextile.policytracker.VolleyCallback;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.response.AgentsResponse;
import simplytextile.policytracker.response.CustomerResponse;

public class AddPoliciesAct extends AppCompatActivity
{
    Spinner select_agent_spinner,select_customer_spinner,select_company_type_spinner,select_company_spinner,select_policy_type_spinner,add_policy_premium_freq;
    ImageView add_date_imageview,add_date_imageview1,add_date_imageview2,add_date_imageview3;
    EditText add_policy_num_number,add_policy_insured_value,add_policy_tenure,add_policy_grace_period,add_customer_billing_date_input,
            add_policy_premium_ten,add_policy_pre_ten_end_date,add_policy_premium_amount,add_policy_last_prepaid_date,add_policy_nxt_pre_pay_date,
            add_policy_comission_amount,add_policy_benfy_info,add_policy_email,add_policy_phone,add_policy_insured_details;
    CheckBox sms_notification,email_notification;
    Button canel,save,save_new;
    List agentNamesList=new ArrayList();
    List customerNameList=new ArrayList();
    Calendar c;
    String premium_freq[]={"One Time","Monthly","Half Yearly","Quarterly","Yearly"};
    int yr,mon,day;
    String S_id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_policies);
        initParams();

    }

    public void initParams()
    {
        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
        S_id = mPrefs.getString("key", "");
        select_agent_spinner=(Spinner)findViewById(R.id.select_agent_spinner);
        select_customer_spinner=(Spinner)findViewById(R.id.select_customer_spinner);
        select_company_type_spinner=(Spinner)findViewById(R.id.select_company_type_spinner);
        select_company_spinner=(Spinner)findViewById(R.id.select_company_spinner);
        select_policy_type_spinner=(Spinner)findViewById(R.id.select_policy_type_spinner);
        add_policy_premium_freq=(Spinner)findViewById(R.id.add_policy_premium_freq);

        add_date_imageview=(ImageView)findViewById(R.id.add_date_imageview);
        add_date_imageview1=(ImageView)findViewById(R.id.add_date_imageview1);
        add_date_imageview2=(ImageView)findViewById(R.id.add_date_imageview2);
        add_date_imageview3=(ImageView)findViewById(R.id.add_date_imageview3);

        add_policy_num_number=(EditText)findViewById(R.id.add_policy_num_number);
        add_policy_insured_value=(EditText)findViewById(R.id.add_policy_insured_value);
        add_policy_tenure=(EditText)findViewById(R.id.add_policy_tenure);
        add_policy_grace_period=(EditText)findViewById(R.id.add_policy_grace_period);
        add_customer_billing_date_input=(EditText)findViewById(R.id.add_customer_billing_date_input);
        add_policy_premium_ten=(EditText)findViewById(R.id.add_policy_premium_ten);
        add_policy_pre_ten_end_date=(EditText)findViewById(R.id.add_policy_pre_ten_end_date);
        add_policy_premium_amount=(EditText)findViewById(R.id.add_policy_premium_amount);
        add_policy_last_prepaid_date=(EditText)findViewById(R.id.add_policy_last_prepaid_date);
        add_policy_nxt_pre_pay_date=(EditText)findViewById(R.id.add_policy_nxt_pre_pay_date);
        add_policy_comission_amount=(EditText)findViewById(R.id.add_policy_comission_amount);
        add_policy_benfy_info=(EditText)findViewById(R.id.add_policy_benfy_info);
        add_policy_email=(EditText)findViewById(R.id.add_policy_email);
        add_policy_phone=(EditText)findViewById(R.id.add_policy_phone);
        add_policy_insured_details=(EditText)findViewById(R.id.add_policy_insured_details);

        sms_notification=(CheckBox)findViewById(R.id.sms_notification);
        email_notification=(CheckBox)findViewById(R.id.email_notification);

        canel=(Button)findViewById(R.id.canel);
        save=(Button)findViewById(R.id.save);
        save_new=(Button)findViewById(R.id.save_new);

        c=Calendar.getInstance();
        yr=c.get(Calendar.YEAR);
        mon=c.get(Calendar.MONTH);
        day=c.get(Calendar.DAY_OF_WEEK);
        add_date_imageview.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dp=new DatePickerDialog(AddPoliciesAct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        add_customer_billing_date_input.setText(""+(dayOfMonth+"/"+(month+1)+"/"+year));
                    }
                },yr,mon,day);
                dp.show();
            }
        });

        add_date_imageview1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dps=new DatePickerDialog(AddPoliciesAct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        add_policy_pre_ten_end_date.setText(""+(dayOfMonth+"/"+(month+1)+"/"+year));
                    }
                },yr,mon,day);
                dps.show();
            }
        });

        add_date_imageview2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dpp=new DatePickerDialog(AddPoliciesAct.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        add_policy_last_prepaid_date.setText(""+(dayOfMonth+"/"+(month+1)+"/"+year));
                    }
                },yr,mon,day);
                dpp.show();
            }
        });


        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,premium_freq);
        add_policy_premium_freq.setAdapter(aa);


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //here code is to execute the data
                dataOP();
            }
        });

        save_new.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //here code is to execute data and make edittexts as empty
                dataOP();
                //and set all the edittexts as empty
            }
        });
        canel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //here code to back the policies list
                onBackPressed();
            }
        });

        //adding the agents to the spinners
        ApiService ser=ApiClient.getClient().create(ApiService.class);
        Call<AgentsResponse> ares= ser.getAgents(S_id);
        ares.enqueue(new Callback<AgentsResponse>()
        {
            @Override
            public void onResponse(Call<AgentsResponse> call, Response<AgentsResponse> response)
            {
                agentNamesList.add("-- select agent --");
                if (response.body().getStatuscode()==0)
                {
                    for (int i=0;i<response.body().getData().getAgentList().size();i++)
                    {
                        agentNamesList.add(response.body().getData().getAgentList().get(i).getFirst_name()+" "+response.body().getData().getAgentList().get(i).getLast_name());
                    }
                    ArrayAdapter aa=new ArrayAdapter(AddPoliciesAct.this,android.R.layout.simple_spinner_dropdown_item,agentNamesList);
                    select_agent_spinner.setAdapter(aa);
                }
            }

            @Override
            public void onFailure(Call<AgentsResponse> call, Throwable t)
            {

            }
        });

        //inflating the customer details to the spinner
        Call<CustomerResponse> cresp=ser.getCustomers(S_id);
        cresp.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response)
            {
                customerNameList.add("-- select customer --");
                if (response.body().getStatuscode()==0)
                {
                    for (int k=0;k<response.body().getData().getCustomer_list().size();k++)
                    {
                        customerNameList.add(response.body().getData().getCustomer_list().get(k).getFirst_name()+" "+response.body().getData().getCustomer_list().get(k).getLast_name());
                    }
                    ArrayAdapter sp=new ArrayAdapter(AddPoliciesAct.this,android.R.layout.simple_spinner_dropdown_item,customerNameList);
                    select_customer_spinner.setAdapter(sp);
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {

            }
        });





    }

    public void dataOP()
    {

        String number=add_policy_num_number.getText().toString().trim();
        String insuredValue=add_policy_insured_value.getText().toString().trim();
        String policyTenure=add_policy_tenure.getText().toString().trim();
        String policyGracePeriod=add_policy_grace_period.getText().toString().trim();
        String custBillingDate=add_customer_billing_date_input.getText().toString().trim();
        String policyPremiumTenure=add_policy_premium_ten.getText().toString().trim();
        String policyPremiumTenEndDate=add_policy_pre_ten_end_date.getText().toString().trim();
        String policyPremAmount=add_policy_premium_amount.getText().toString().trim();
        String policyLastPrepaiddate=add_policy_last_prepaid_date.getText().toString().trim();
        String policyNxtPayDate=add_policy_nxt_pre_pay_date.getText().toString().trim();
        String policyComissionAmount=add_policy_comission_amount.getText().toString().trim();
        String policyBeneficaryInfo=add_policy_benfy_info.getText().toString().trim();
        String policyEmail=add_policy_email.getText().toString().trim();
        String policy_Phone=add_policy_phone.getText().toString().trim();
        String policyInsuredDetails=add_policy_insured_details.getText().toString().trim();

        if (sms_notification.isChecked())
        {
            String smsNotifi=sms_notification.getText().toString();
        }else if (email_notification.isChecked())
        {
            String emailNotif=email_notification.getText().toString().trim();
        }

        JSONObject main = new JSONObject();
        JSONObject sub_main = new JSONObject();
        try
        {
            sub_main.put("id", "");
            sub_main.put("description", "");
            sub_main.put("policy_number", 1234);
            sub_main.put("commission_amount", "");
            sub_main.put("beneficiary_information", "");
            sub_main.put("grace_days", 30);
            sub_main.put("parent_id", "");

            JSONObject json_customer = new JSONObject();
            json_customer.put("id", 10063);
            json_customer.put("business_name", "");
            json_customer.put("first_name", "");
            json_customer.put("last_name", "");
            json_customer.put("aadhar_number", "");
            json_customer.put("govt_id_number", "");
            json_customer.put("date_of_birth", "");

            JSONObject json_cust_addrs = new JSONObject();
            json_cust_addrs.put("address1", "");
            json_cust_addrs.put("address2", "");
            json_cust_addrs.put("address3", "");
            json_cust_addrs.put("city", "");
            json_cust_addrs.put("state", "");
            json_cust_addrs.put("zip", "");
            json_cust_addrs.put("email1", "");
            json_cust_addrs.put("phone1", "");
            json_cust_addrs.put("email2", "");
            json_cust_addrs.put("phone2", "");
            json_customer.put("address", json_cust_addrs);




            JSONObject json_agent = new JSONObject();
            json_agent.put("id", 10059);
            json_agent.put("business_name", "");
            json_agent.put("first_name", "");
            json_agent.put("last_name", "");
            json_agent.put("aadhar_number", "");
            json_agent.put("govt_id_number", "");

            JSONObject json_agent_addres = new JSONObject();
            json_agent_addres.put("address1", "");
            json_agent_addres.put("address2", "");
            json_agent_addres.put("address3", "");
            json_agent_addres.put("city", "");
            json_agent_addres.put("state", "");
            json_agent_addres.put("zip", "");
            json_agent_addres.put("email1", "");
            json_agent_addres.put("phone1", "");
            json_agent_addres.put("email2", "");
            json_agent_addres.put("phone2", "");
            json_agent.put("address", json_agent_addres);


            JSONObject json_company = new JSONObject();
            json_company.put("id", 10038);
            json_company.put("first_name", "");
            json_company.put("last_name", "");
            json_company.put("business_name", "jbj");
            json_company.put("status_id", "");
            json_company.put("irdai_number", "");
            json_company.put("govt_id_number", "");
            json_company.put("created", "");
            json_company.put("last_updated", "");
            json_company.put("update_counter", 0);

            JSONObject json_comp_address = new JSONObject();

            json_comp_address.put("id", 10038);
            json_comp_address.put("address1", "");
            json_comp_address.put("address2", "");
            json_comp_address.put("address3", "");
            json_comp_address.put("city", "");
            json_comp_address.put("state", "");
            json_comp_address.put("zip", "");
            json_comp_address.put("email1", "");
            json_comp_address.put("phone1", "");
            json_comp_address.put("email2", "");
            json_comp_address.put("phone2", "");
            json_comp_address.put("update_counter", "");
            json_comp_address.put("created", 344);
            json_comp_address.put("last_updated", "");
            json_company.put("address", json_comp_address);

            JSONObject jmmore = new JSONObject();
            jmmore.put("license_number",3838);
            json_company.put("more", jmmore);

            JSONObject json_comp_policytyep = new JSONObject();
            json_comp_policytyep.put("id", 5302);
            json_comp_policytyep.put("name", "");
            json_comp_policytyep.put("description", "");
            json_comp_policytyep.put("parent_id", "");
            json_comp_policytyep.put("is_renewable", "");
            json_comp_policytyep.put("renewal_notice_days", "");

            json_company.put("policy_type", json_comp_policytyep);

            JSONObject json_insuredInfo = new JSONObject();
            json_insuredInfo.put("id", 6000);
            json_insuredInfo.put("value", 20000);
            json_insuredInfo.put("identification", "");

            JSONObject json_premium_info = new JSONObject();
            json_premium_info.put("id", 5000);
            json_premium_info.put("period_number", "");
            json_premium_info.put("next_payment_due_date", 20);
            json_premium_info.put("last_payment_date", 2017);
            json_premium_info.put("amount", 500);
            json_premium_info.put("end_date", 2022);
            json_premium_info.put("renewal_amount", "");

            JSONObject json_covergInfo = new JSONObject();
            json_covergInfo.put("id", 5001);
            json_covergInfo.put("period_number", 2);
            json_covergInfo.put("start_date", 113);
            json_covergInfo.put("value", 34);
            json_covergInfo.put("end_date", 2022);

            JSONObject json_policyStatus = new JSONObject();
            json_policyStatus.put("id", "");
            json_policyStatus.put("name", "");
            json_policyStatus.put("description", "");


            JSONObject json_policyTYpe = new JSONObject();
            json_policyTYpe.put("id", 5302);
            json_policyTYpe.put("name", "");
            json_policyTYpe.put("description", "");
            json_policyTYpe.put("parent_id", "");
            json_policyTYpe.put("is_renewable", "");

            JSONObject json_ptype_sub = new JSONObject();
            json_ptype_sub.put("id", 530205);
            json_ptype_sub.put("name", "");
            json_ptype_sub.put("description", "");
            json_ptype_sub.put("parent_id", "");


            JSONObject json_more = new JSONObject();
            json_more.put("vehicle_make", "");
            json_more.put("vehicle_model", "");
            json_more.put("vehicle_year", "");
            json_more.put("vehicle_license_plate", "");

            JSONObject json_notification = new JSONObject();
            json_notification.put("emails", "");
            json_notification.put("phone", "");
            json_notification.put("phone_flag", "");
            json_notification.put("email_flag", "");


            sub_main.put("notification_info", json_notification);
            sub_main.put("more", json_more);
            sub_main.put("policy_type_sub", json_ptype_sub);
            sub_main.put("policy_type", json_policyTYpe);
            sub_main.put("policy_status", json_policyStatus);
            sub_main.put("coverage_info", json_covergInfo);
            sub_main.put("premium_info", json_premium_info);
            sub_main.put("insured_info", json_insuredInfo);
            sub_main.put("company", json_company);
            sub_main.put("agent", json_agent);
            sub_main.put("customer", json_customer);
            main.put("policy", sub_main);
            Log.i("", String.valueOf(main));

            Utills.getVolleyResponseJson(AddPoliciesAct.this, Request.Method.POST, "http://dev.simplytextile.com:9081/api/policies", main, new VolleyCallback() {
                @Override


                public void onSuccessResponse(String result) {
                    JSONObject jb = null;
                    try {
                        jb = new JSONObject(result);
                        String msg = jb.getString("message");
                        Log.e("errs",""+msg);
                        Toast.makeText(AddPoliciesAct.this, " " + msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                startActivity(new Intent(this,PoliciesActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
