package simplytextile.policytracker.activties;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import simplytextile.policytracker.R;

public class UserProfileActivity extends AppCompatActivity
{
    EditText first_name_update_profile,last_name_update_profile,business_name_update_profile,
            email_update_profile,phone_update_profile,aadar_number_update_profile,
            pan_number_update_profile,gst_update_profile,address_update_profile,city_update_profile,
            state_update_profile,postal_code_update_profile;
    Button update_profile;
    TextView change_password;
public static String Fname1,Lname,Phone,Adharnumber,city,postalcode,Email,businessCode,address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity_);

        Fname1=LoginActivity.FirstName;
        Lname=LoginActivity.LastName;
        Phone=LoginActivity.Phone;
        Adharnumber=LoginActivity.AdhaarNaumber;
        city=LoginActivity.City;
        postalcode=LoginActivity.PostalCode;
        Email=LoginActivity.Email;
        businessCode=LoginActivity.businessName;
        address=LoginActivity.Address;
        initViews();
    }

    private void initViews()
    {
        change_password=findViewById(R.id.change_password);

        first_name_update_profile=findViewById(R.id.first_name_update_profile);
        last_name_update_profile=findViewById(R.id.last_name_update_profile);
        business_name_update_profile=findViewById(R.id.business_name_update_profile);
        email_update_profile=findViewById(R.id.email_update_profile);
        phone_update_profile=findViewById(R.id.phone_update_profile);
        aadar_number_update_profile=findViewById(R.id.aadar_number_update_profile);
        pan_number_update_profile=findViewById(R.id.pan_number_update_profile);
        gst_update_profile=findViewById(R.id.gst_update_profile);
        address_update_profile=findViewById(R.id.address_update_profile);
        city_update_profile=findViewById(R.id.city_update_profile);
        state_update_profile=findViewById(R.id.state_update_profile);
        postal_code_update_profile=findViewById(R.id.postal_code_update_profile);
        update_profile=findViewById(R.id.update_profile);
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //working with on clicks
                updateProfile();
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call change password activity
                startActivity(new Intent(UserProfileActivity.this,ChangePasswordActivity.class));
            }
        });

        first_name_update_profile.setText(""+Fname1);
        last_name_update_profile.setText(""+Lname);
        business_name_update_profile.setText(""+businessCode);

        email_update_profile.setText(""+Email);
        phone_update_profile.setText(""+Phone);
        aadar_number_update_profile.setText(""+Adharnumber);
        city_update_profile.setText(""+city);
        postal_code_update_profile.setText(""+postalcode);


    }

    public void updateProfile()
    {


        String fname=first_name_update_profile.getText().toString().trim();
        String lname=last_name_update_profile.getText().toString().trim();
        String bname=business_name_update_profile.getText().toString().trim();
        String emails=email_update_profile.getText().toString().trim();
        String phone=phone_update_profile.getText().toString().trim();
        String aadar=aadar_number_update_profile.getText().toString().trim();
        String pans=pan_number_update_profile.getText().toString().trim();
        String gsts=gst_update_profile.getText().toString().trim();
        String addrwess=address_update_profile.getText().toString().trim();
        String city=city_update_profile.getText().toString().trim();
        String state=state_update_profile.getText().toString().trim();
        String postal=postal_code_update_profile.getText().toString().trim();



        if (!checkConditions(fname,lname,bname,emails,phone,aadar,pans,gsts,addrwess,city,state,postal))
        {
            //code to update the profile
        }

    }

    private boolean checkConditions(String fname,String lname,String bname,String emails,String phone,String aadar,String pans,String gsts,
                                    String addrwess,String city,String state,String postal)
    {
        if (fname.isEmpty())
        {
            first_name_update_profile.requestFocus();
            first_name_update_profile.setError("enter first name");
            return true;
        }
        if (lname.isEmpty())
        {
            last_name_update_profile.requestFocus();
            last_name_update_profile.setError("enter last name");
            return true;
        }
        if (bname.isEmpty())
        {
            business_name_update_profile.requestFocus();
            business_name_update_profile.setError("enter business name");
            return true;
        }
        if (emails.isEmpty())
        {
            email_update_profile.requestFocus();
            email_update_profile.setError("enter emails");
            return true;
        }
        if (phone.isEmpty())
        {
            phone_update_profile.setError("enter phone number");
            phone_update_profile.requestFocus();
            return true;
        }
        if (aadar.isEmpty())
        {
            aadar_number_update_profile.requestFocus();
            aadar_number_update_profile.setError("enter aadar number");
            return true;
        }
        if (pans.isEmpty())
        {
            pan_number_update_profile.setError("enter pan number");
            pan_number_update_profile.requestFocus();
            return true;
        }if (gsts.isEmpty())
    {
        gst_update_profile.requestFocus();
        gst_update_profile.setError("enter gst number");
        return true;
    }
    if (addrwess.isEmpty())
    {
        address_update_profile.requestFocus();
        address_update_profile.setError("enter address");
        return true;
    }
    if (city.isEmpty())
    {
        city_update_profile.setError("enter city");
        city_update_profile.requestFocus();
        return true;
    }
    if (state.isEmpty())
    {
        state_update_profile.requestFocus();
        state_update_profile.setError("enter state");

        return true;
    }
    if (postal.isEmpty())
    {
        postal_code_update_profile.requestFocus();
        postal_code_update_profile.setError("enter postal code");
        return true;
    }
        return false;
    }



}
