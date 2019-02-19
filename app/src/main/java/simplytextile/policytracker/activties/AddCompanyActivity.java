package simplytextile.policytracker.activties;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.R;
import simplytextile.policytracker.Utills;
import simplytextile.policytracker.VolleyCallback;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.companyresponse.Compres;

public class AddCompanyActivity extends AppCompatActivity
{
    String a1[];
    String cname,companyName;
    int k,id;
    ArrayList ll=new ArrayList();
    Set ss=new LinkedHashSet();
    Spinner add_company_name,add_company_bid_spinner;
    EditText add_company_license_number;
    Button addcommpany_btn_save;
    LinearLayout data_loading_screen_layoutss;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        initParams();
    }
    public void initParams()
    {
        add_company_name=(Spinner)findViewById(R.id.add_company_name);
        add_company_bid_spinner=(Spinner)findViewById(R.id.add_company_bid_spinner);
        add_company_license_number=(EditText) findViewById(R.id.add_company_license_number);
        data_loading_screen_layoutss=(LinearLayout)findViewById(R.id.data_loading_screen_layout);
        addcommpany_btn_save=(Button) findViewById(R.id.addcommpany_btn_save);


        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Compres> call = service.getCompanies();
        call.enqueue(new Callback<Compres>()
        {
            @Override
            public void onResponse(Call<Compres> call, final Response<Compres> response)
            {
                k=response.body().getData().getCompany_list().size();
                a1=new String[k];
                for(int i=0;i<k;i++)
                {

                    a1[i]=response.body().getData().getCompany_list().get(i).getPolicy_type().getName();



                }
                ArrayAdapter aa=new ArrayAdapter(AddCompanyActivity.this,android.R.layout.simple_spinner_dropdown_item,a1);
                add_company_bid_spinner.setAdapter(aa);
                add_company_bid_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        cname=adapterView.getItemAtPosition(i).toString().trim();

                        if (cname.equals("General"))
                        {

                            for (int j=0;j<k;j++)
                            {
                                if (response.body().getData().getCompany_list().get(j).getPolicy_type().getName().equals("General"))
                                {
                                    ll.add(response.body().getData().getCompany_list().get(j).getBusiness_name());
                                    id=response.body().getData().company_list.get(j).getId();
                                }
                            }
                        }
                        else if (cname.equals("Health"))
                        {
                            for (int j=0;j<k;j++)
                            {
                                if (response.body().getData().getCompany_list().get(j).getPolicy_type().getName().equals("Health"))
                                {
                                    ll.add(response.body().getData().getCompany_list().get(j).getBusiness_name());
                                    id=response.body().getData().company_list.get(j).getId();
                                }
                            }
                        }
                        else if (cname.equals("Life Insurance"))
                        {
                            for (int j=0;j<k;j++)
                            {
                                if (response.body().getData().getCompany_list().get(j).getPolicy_type().getName().equals("Life Insurance"))
                                {
                                    ll.add(response.body().getData().getCompany_list().get(j).getBusiness_name());
                                    id=response.body().getData().company_list.get(j).getId();
                                }
                            }
                        }
                        ArrayAdapter as=new ArrayAdapter(AddCompanyActivity.this,android.R.layout.simple_spinner_dropdown_item,ll);
                        add_company_name.setAdapter(as);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView)
                    {

                    }
                });

            }

            @Override
            public void onFailure(Call<Compres> call, Throwable t)
            {

            }
        });
        add_company_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                companyName=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        addcommpany_btn_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                readData();
            }
        });
    }
    public void readData()
    {

        String cmpLicenseNUmber=add_company_license_number.getText().toString().trim();

        if (cmpLicenseNUmber.isEmpty())
        {
            add_company_license_number.requestFocus();
            add_company_license_number.setError("enter licence");
        }
        else {
            data_loading_screen_layoutss.setVisibility(View.VISIBLE);

            JSONObject main = new JSONObject();
            JSONArray jr1 = new JSONArray();
            JSONObject jro = new JSONObject();
            try {
                jro.put("id", "" + id);
                jro.put("activation_date", "");
                jro.put("business_name", companyName);
                jro.put("license_number", cmpLicenseNUmber);

                JSONObject jptype = new JSONObject();
                jptype.put("id", "");
                jptype.put("name", "");
                jptype.put("description", "");
                jptype.put("parent_id", "");
                jptype.put("is_renewable", "");
                jro.put("policy_type", jptype);
                jr1.put(jro);
                main.put("company_list", jr1);
                Utills.getVolleyResponseJson(AddCompanyActivity.this, Request.Method.POST, "http://dev.simplytextile.com:9081/api/subscribers/id/companies", main, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {

                        JSONObject jb = null;
                        try {
                            data_loading_screen_layoutss.setVisibility(View.GONE);
                            jb = new JSONObject(result);
                            String msg = jb.getString("message");
                            Toast.makeText(AddCompanyActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (JSONException e) {
                data_loading_screen_layoutss.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                startActivity(new Intent(this,CompaniesListAct.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
