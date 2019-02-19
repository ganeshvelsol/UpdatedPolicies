package simplytextile.policytracker.activties;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import simplytextile.policytracker.R;
import simplytextile.policytracker.Utills;
import simplytextile.policytracker.VolleyCallback;
import simplytextile.policytracker.adapters.CustomerListAdapter;
import simplytextile.policytracker.models.CustomerList;

public class UpdateCustomer extends AppCompatActivity
{
Button update_cust_btn;
String updateid=CustomerListAdapter.delid;
EditText UpdatFirstname,UpdateLastanme,UpdateEmailAdress,UpdateAdharNumber,UPdatecity,UpdatePostal,UpdateState
            ,phone2_userprofile_input,phone_userprofile_input,pan_userprofile_input,dob_add_customer,address_userprofile_input;
String  cid,City,bname,fname,lname,dob,email,p1,p2,aadar,pan,add1,citys,state,pin;
Bundle b;
int year,mon,day,statusid;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        b=getIntent().getExtras();
        cid=b.getString("ids");
        bname=b.getString("bname");
        fname=b.getString("fname");
        lname=b.getString("lname");
        dob=b.getString("dob");
        email=b.getString("email");
        p1=b.getString("p1");
        p2=b.getString("p2");
        aadar=b.getString("aadar");
        pan=b.getString("pan");
        add1=b.getString("add1");
        citys=b.getString("city");
        state=b.getString("state");
        pin=b.getString("pin");
        statusid=b.getInt("statusid");
        Calendar cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        mon=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
        initviews();
    }

    private void initviews()
    {
        UpdatFirstname=(EditText)findViewById(R.id.firstname_userprofile_input);
        UpdateLastanme=(EditText)findViewById(R.id.lastname_userprofile_input);
        UpdateEmailAdress=(EditText)findViewById(R.id.emaail_userprofile_input);
        UpdateAdharNumber=(EditText)findViewById(R.id.aadhar_userprofile_input);
        UPdatecity=(EditText)findViewById(R.id.city_userprofile_input);
        UpdatePostal=(EditText)findViewById(R.id.postalcode_userprofile_input);
        UpdateState=(EditText)findViewById(R.id.user_select_state);
        phone2_userprofile_input=(EditText)findViewById(R.id.phone2_userprofile_input);
        phone_userprofile_input=(EditText)findViewById(R.id.phone_userprofile_input);
        pan_userprofile_input=(EditText)findViewById(R.id.pan_userprofile_input);
        dob_add_customer=(EditText)findViewById(R.id.dob_add_customer);
        address_userprofile_input=(EditText)findViewById(R.id.address_userprofile_input);
        update_cust_btn=(Button)findViewById(R.id.update_customer_profile_btn);
        update_cust_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                updateCustomer();

            }
        });
        UpdatFirstname.setText(""+fname);
        UpdateLastanme.setText(""+lname);
        dob_add_customer.setText(""+dob);
        UpdateEmailAdress.setText(""+email);
        phone_userprofile_input.setText(""+p1);
        phone2_userprofile_input.setText(""+p2);
        UpdateAdharNumber.setText(""+aadar);
        pan_userprofile_input.setText(""+pan);
        address_userprofile_input.setText(""+add1);
        UPdatecity.setText(""+citys);
        UpdateState.setText(""+state);
        UpdatePostal.setText(""+pin);

    }

    public void updateCustomer()
    {

        //String bnames=business_name_add_customer.getText().toString().trim();
         String fname=UpdatFirstname.getText().toString().trim();
        String lname=UpdateLastanme.getText().toString().trim();
        String bob=dob_add_customer.getText().toString().trim();
        String email=UpdateEmailAdress.getText().toString().trim();
        String phone1=phone_userprofile_input.getText().toString().trim();
        String phone2=phone2_userprofile_input.getText().toString().trim();
        String aadar=UpdateAdharNumber.getText().toString().trim();
        String pan=pan_userprofile_input.getText().toString().trim();
        String add1=address_userprofile_input.getText().toString().trim();
        String state=UpdateState.getText().toString().trim();
        String city=UPdatecity.getText().toString().trim();
        String postal=UpdatePostal.getText().toString().trim();

        if (fname.isEmpty())
        {
            UpdatFirstname.setError("enter name");
        }else if (lname.isEmpty())
        {
            UpdateLastanme.requestFocus();
            UpdateLastanme.setError("enter last name");
        }else if (bob.isEmpty())
        {
            dob_add_customer.setError("enter birth");
        }else if (email.isEmpty())
        {
            UpdateEmailAdress.setError("enter email id");
        }else if (phone1.isEmpty())
        {
            phone_userprofile_input.setError("enter mobile");
        }else if (aadar.isEmpty())
        {
            UpdateAdharNumber.setError("enter aadar number");
        }else if (pan.isEmpty())
        {
            pan_userprofile_input.setError("enter pan");
        }else if (add1.isEmpty())
        {
            address_userprofile_input.setError("enter address");
        }else if (state.isEmpty())
        {
            UpdateState.setError("enter state");
        }else if (city.isEmpty())
        {
            UPdatecity.setError("enter city");
        }else if (postal.isEmpty())
        {
            UpdatePostal.setError("enter postal code");
        }
        else {


            try
            {
                JSONObject jmain = new JSONObject();
                JSONObject jsub1 = new JSONObject();
                jsub1.put("id", cid);
                jsub1.put("business_name", "");
                jsub1.put("first_name", fname);
                jsub1.put("last_name", lname);
                jsub1.put("aadhar_number", aadar);
                jsub1.put("govt_id_number", pan);
                jsub1.put("date_of_birth", bob);
                jsub1.put("status_id", statusid);

                JSONObject jsub2 = new JSONObject();
                jsub2.put("address1", add1);
                jsub2.put("address2", "");
                jsub2.put("address3", "");
                jsub2.put("city", city);
                jsub2.put("state", state);
                jsub2.put("zip", postal);
                jsub2.put("email1", email);
                jsub2.put("phone1", phone1);
                jsub2.put("email2", "");
                jsub2.put("phone2", phone2);
                jsub1.put("address", jsub2);

                JSONObject jsubAgent = new JSONObject();
                jsubAgent.put("id", 0);
                jsubAgent.put("business_name", "bname");
                jsubAgent.put("business_name", "");
                jsubAgent.put("first_name", "");
                jsubAgent.put("last_name", "");
                jsubAgent.put("aadhar_number", "");
                jsubAgent.put("govt_id_number", "");

                JSONObject not = new JSONObject();
                not.put("email_flag", 1);
                not.put("emails", "bname");
                not.put("phone", "");
                not.put("phone_flag", "");


                JSONObject jsub3 = new JSONObject();
                jsub3.put("address1", "");
                jsub3.put("address2", "");
                jsub3.put("address3", "");
                jsub3.put("city", "");
                jsub3.put("state", "");
                jsub3.put("zip", "");
                jsub3.put("email1", "");
                jsub3.put("phone1", "");
                jsub3.put("email2", "");
                jsub3.put("phone2", "");
                jsubAgent.put("address", jsub3);
                jsub1.put("agent", jsubAgent);
                jsub1.put("notification_info",not);
                jmain.put("customer", jsub1);

                Utills.getVolleyResponseJson(UpdateCustomer.this, Request.Method.PUT, "http://dev.simplytextile.com:9081/api/customers/"+updateid, jmain, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result)
                    {
                        JSONObject jb = null;
                        try
                        {
                            jb = new JSONObject(result);
                            String msg = jb.getString("message");
                            Toast.makeText(UpdateCustomer.this, "" + msg, Toast.LENGTH_LONG).show();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (Exception e)
            {

            }
        }
    }
}
