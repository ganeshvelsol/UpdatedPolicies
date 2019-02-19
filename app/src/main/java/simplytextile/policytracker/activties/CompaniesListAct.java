package simplytextile.policytracker.activties;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.R;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.companyresponse.CompanyList;
import simplytextile.policytracker.companyresponse.Compres;

public class CompaniesListAct extends AppCompatActivity
{

    RecyclerView get_companies_recycler;
    FloatingActionButton adding_employee_fab_btn;
    LinearLayoutManager llm;
    LinearLayout data_loading_screen_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list2);
        initParams();
    }

    public void initParams()
    {
        get_companies_recycler=(RecyclerView)findViewById(R.id.get_companies_recycler);
        llm=new LinearLayoutManager(this);
        data_loading_screen_layout=(LinearLayout)findViewById(R.id.data_loading_screen_layout);
        adding_employee_fab_btn=(FloatingActionButton) findViewById(R.id.adding_employee_fab_btn);
        adding_employee_fab_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(CompaniesListAct.this,AddCompanyActivity.class));
            }
        });
        data_loading_screen_layout.setVisibility(View.VISIBLE);
        ApiService ps = ApiClient.getClient().create(ApiService.class);
        Call<Compres> clist= ps.getCompanies();
        clist.enqueue(new Callback<Compres>()
        {
            @Override
            public void onResponse(Call<Compres> call, Response<Compres> response)
            {
                data_loading_screen_layout.setVisibility(View.GONE);
                if (response.body().getStatuscode().equals(0))
                {
                    CompaniesListAdapters ada=new CompaniesListAdapters(response.body().getData().getCompany_list(),CompaniesListAct.this);
                    get_companies_recycler.setAdapter(ada);
                    get_companies_recycler.setLayoutManager(llm);
                }
                else
                {
                    Toast.makeText(CompaniesListAct.this, ""+response.body().getStatuscode(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Compres> call, Throwable t)
            {
                data_loading_screen_layout.setVisibility(View.GONE);
                Toast.makeText(CompaniesListAct.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CompaniesListAdapters extends RecyclerView.Adapter<CompaniesListAdapters.ViewH>
    {
        ArrayList<CompanyList> company_list;
        Context context;

        public CompaniesListAdapters(ArrayList<CompanyList> company_list, Context context)
        {
            this.company_list = company_list;
            this.context = context;
        }

        @NonNull
        @Override
        public CompaniesListAdapters.ViewH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_list_adaptersssss, viewGroup, false);
            return new CompaniesListAdapters.ViewH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CompaniesListAdapters.ViewH viewH, int i)
        {
            viewH.company_id.setText(""+company_list.get(i).getId());
            viewH.company_name.setText(""+company_list.get(i).getBusiness_name());

        }

        @Override
        public int getItemCount() {
            return company_list.size();
        }
        class ViewH extends RecyclerView.ViewHolder
        {
            TextView company_id,company_name;
            public ViewH(@NonNull View itemView)
            {
                super(itemView);
                company_id=(TextView)itemView.findViewById(R.id.company_id);
                company_name=(TextView)itemView.findViewById(R.id.company_name);
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
                onBackPressed();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
