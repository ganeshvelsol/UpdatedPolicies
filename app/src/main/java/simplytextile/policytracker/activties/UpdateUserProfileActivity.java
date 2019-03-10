package simplytextile.policytracker.activties;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import simplytextile.policytracker.R;
import simplytextile.policytracker.Utills;
import simplytextile.policytracker.VolleyCallback;
import simplytextile.policytracker.models.CustomerList;

public class UpdateUserProfileActivity extends AppCompatActivity
{
    Spinner selectcstate;
    String City,bname,fname,lname,dob,email,p1,p2,aadar,pan,add1,citys,state,pin;

    EditText UpdatFirstname,UpdateLastanme,UpdateEmailAdress,UpdateAdharNumber,UPdatecity,UpdatePostal,UpdateState
            ,phone2_userprofile_input,phone_userprofile_input,pan_userprofile_input,dob_add_customer,address_userprofile_input;
    ImageView select_date;
    Button changeuserpassword;
    Button update_register_signup;
    int year,mon,day;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_profile_activity);
        b=getIntent().getExtras();
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
        Calendar cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        mon=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
        initViews();
    }

    private void initViews()
    {

//        selectcstate=(Spinner)findViewById(R.id.user_select_state);
////        ArrayAdapter<CharSequence> selectstate = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
////        selectstate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        selectcstate.setAdapter(selectstate);

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
        update_register_signup=(Button) findViewById(R.id.update_register_signup);
        //changeuserpassword=(Button)findViewById(R.id.user_change_password);







        select_date=(ImageView) findViewById(R.id.select_date);
        select_date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dp=new DatePickerDialog(UpdateUserProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        dob_add_customer.setText(""+dayOfMonth+"/"+month+""+year);
                    }
                },year,mon,day);
                dp.show();
            }
        });


        changeuserpassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        update_register_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                    Intent changepassword = new Intent(UpdateUserProfileActivity.this, ChangePasswordActivity.class);
                    startActivity(changepassword);

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
        UPdatecity.setText(UserProfileActivity.city);
        UpdateAdharNumber.setText(UserProfileActivity.Adharnumber);
    }
    public void profiles()
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

            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObjectSub=new JSONObject();
            try
            {
                jsonObjectSub.put("id",0);
                jsonObjectSub.put("type_id",6500);
                jsonObjectSub.put("business_name","");
                jsonObjectSub.put("first_name",fname);
                jsonObjectSub.put("last_name",lname);
                jsonObjectSub.put("aadhar_number","");
                jsonObjectSub.put("govt_id_number","");
                jsonObjectSub.put("irdai_number","");
                JSONObject jsonObjectAdd=new JSONObject();
                jsonObjectAdd.put("address1","");
                jsonObjectAdd.put("address2","");
                jsonObjectAdd.put("address3","");
                jsonObjectAdd.put("city","");
                jsonObjectAdd.put("state","");
                jsonObjectAdd.put("zip","");
                jsonObjectAdd.put("email1",email);
                jsonObjectAdd.put("phone1",phone1);
                jsonObjectAdd.put("email2","");
                jsonObjectAdd.put("phone2","");
                jsonObjectSub.put("address",jsonObjectAdd);
                JSONObject jsonObjectUser=new JSONObject();
                jsonObjectUser.put("login_name","agent.abc");
                jsonObjectUser.put("password","rupesh");
                jsonObjectSub.put("user",jsonObjectUser);
                JSONArray comArray =new JSONArray();
                jsonObjectSub.put("company_list",comArray);
                jsonObject.put("subscriber",jsonObjectSub);

                Utills.getVolleyResponseJson(UpdateUserProfileActivity.this, Request.Method.POST, "http://dev.simplytextile.com:9081/api/subscribers", jsonObject, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result)
                    {
                        try
                        {
                            JSONObject jb =new JSONObject(result);
                            String   msg=jb.getString("message");
                            Toast.makeText(UpdateUserProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        Toast.makeText(UpdateUserProfileActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                        Intent mainactivity =new Intent(UpdateUserProfileActivity.this,LoginActivity.class);
                        startActivity(mainactivity);
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }        }
    }
}
