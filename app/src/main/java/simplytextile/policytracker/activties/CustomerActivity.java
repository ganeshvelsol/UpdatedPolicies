package simplytextile.policytracker.activties;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.FileReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.pdf.PdfActivty;
import simplytextile.policytracker.R;
import simplytextile.policytracker.adapters.CustomerListAdapter;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.response.CustomerResponse;

public class CustomerActivity extends AppCompatActivity
{
    public static final String ss="name";
    RecyclerView customer_recycler;
    ImageView imageView;
    LinearLayoutManager llm;
    CustomerListAdapter adapter;
    Button Go;
    EditText Querystring;
    String signature;
    String Filter;
    android.support.v7.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity);Querystring=(EditText)findViewById(R.id.querystring);
        Go=(Button)findViewById(R.id.go);







             llm=new LinearLayoutManager(this);
             SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
             String S_id = mPrefs.getString("key", "");
             ApiService planView = ApiClient.getClient().create(ApiService.class);
             Call<CustomerResponse> customers=planView.getCustomers(S_id);
             customers.enqueue(new Callback<CustomerResponse>()
             {
                 @Override
                 public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response)
                 {
                     if (response.body().getStatuscode()==0)
                     {

                         adapter=new CustomerListAdapter(response.body().getData().getCustomer_list(),CustomerActivity.this);;
                         customer_recycler.setAdapter(adapter);
                         customer_recycler.setLayoutManager(llm);
                     }
                     else
                     {
                         Toast.makeText(CustomerActivity.this, "from else"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onFailure(Call<CustomerResponse> call, Throwable t)
                 {
                     Toast.makeText(CustomerActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                 }
             });








//        searchView=(SearchView) findViewById(R.id.searchView);
//        searchView.setQueryHint("Search View");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//
//            @Override
//            public boolean onQueryTextSubmit(String client)
//            {
//                Toast.makeText(getBaseContext(), client, Toast.LENGTH_LONG).show();
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText)
//            {
//              //  Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
//                return false;
//            }
//
//
//
//        });



    customer_recycler=(RecyclerView)findViewById(R.id.customer_recycler);
        imageView=(ImageView)findViewById(R.id.image_addbutton);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent addcustomer=new Intent(CustomerActivity.this,AddCustomerActivity.class);
                startActivity(addcustomer);
            }
        });



        Go.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Filter=Querystring.getText().toString().trim();
//                llm=new LinearLayoutManager(this);
                SharedPreferences mPrefs = getSharedPreferences("IDvalue",0);
                String S_id = mPrefs.getString("key", "");
                ApiService planView = ApiClient.getClient().create(ApiService.class);
                Call<CustomerResponse> customers=planView.getCustomerfilter(S_id,"",Filter);
                customers.enqueue(new Callback<CustomerResponse>()
                {
                    @Override
                    public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response)
                    {
                        if (response.body().getStatuscode()==0)
                        {

                            adapter=new CustomerListAdapter(response.body().getData().getCustomer_list(),CustomerActivity.this);;
                            customer_recycler.setAdapter(adapter);
                            customer_recycler.setLayoutManager(llm);
                        }
                        else
                        {
                            Toast.makeText(CustomerActivity.this, "from else"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerResponse> call, Throwable t)
                    {
                        Toast.makeText(CustomerActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            case R.id.add_agents:
            {
                startActivity(new Intent(this,AddCustomerActivity.class));
            }

            case R.id.action_favorite:
            {

                startActivity(new Intent(this,PdfActivty.class));
                finish();

            }
            case   R.id.action_search:
            {
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
