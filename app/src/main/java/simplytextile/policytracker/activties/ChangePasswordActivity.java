package simplytextile.policytracker.activties;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.R;
import simplytextile.policytracker.adapters.CustomerListAdapter;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.changepasswordresponse.Changespassword;
import simplytextile.policytracker.response.CustomerResponse;

public class ChangePasswordActivity extends AppCompatActivity {
EditText loginname,oldpassword,newpassword;
Button submit;
String S_id="8a672fbf361b11e9ab4daa3a52b410b4";
String LoginName,OldPassword,NewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity_);

        initview();
    }

    private void initview()
    {
        loginname=(EditText)findViewById(R.id.user_login);
        oldpassword=(EditText)findViewById(R.id.user_oldpassword);
        newpassword=(EditText)findViewById(R.id.new_newpassword);
        submit=(Button)findViewById(R.id.btn_Submit);


        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intialization();
            }
        });


    }

    private void intialization()
    {
        LoginName=loginname.getText().toString().trim();
        OldPassword=oldpassword.getText().toString().trim();
        NewPassword=newpassword.getText().toString().trim();

        if(LoginName.isEmpty())
        {
            loginname.requestFocus();
            loginname.setError("Enter Login Name");
        }
        else if(OldPassword.isEmpty())
        {
            oldpassword.requestFocus();
            oldpassword.setError("Enter Old password");
        }
        else if(NewPassword.isEmpty())
        {
            newpassword.requestFocus();
            newpassword.setError("Enter Old password");
        }
        else
        {
            changepass();
        }

    }

    private void changepass()
    {
        SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
       // String S_id = mPrefs.getString("key", "");
        ApiService planView = ApiClient.getClient().create(ApiService.class);
        Call<Changespassword> changepassword=planView.userchangePassword(S_id,LoginName,OldPassword,NewPassword);
        changepassword.enqueue(new Callback<Changespassword>()
        {
            @Override
            public void onResponse(Call<Changespassword> call, Response<Changespassword> response)
            {
                if (response.body().getStatuscode()==0)
                {
                    Toast.makeText(ChangePasswordActivity.this, ""+response.body().getStatuscode(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(ChangePasswordActivity.this, "from else"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Changespassword> call, Throwable t)
            {
                Toast.makeText(ChangePasswordActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
