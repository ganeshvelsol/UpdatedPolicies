package simplytextile.policytracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplytextile.policytracker.R;
import simplytextile.policytracker.Utills;
import simplytextile.policytracker.VolleyCallback;
import simplytextile.policytracker.activties.UpdateCustomer;
import simplytextile.policytracker.activties.UpdateUserProfileActivity;
import simplytextile.policytracker.models.CustomerList;

/**
 * Created by shmahe on 21-09-2018.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolderss> implements Filterable
{
    List<CustomerList> customer_list;
     List<CustomerList> customer_listFiltered;
    Context context;
    public  static  String delid;


    public CustomerListAdapter(final List<CustomerList> customer_list, Context context)
    {

        this.context = context;
        if(this.customer_list ==null)
        {
            this.customer_list = customer_list;
            this.customer_listFiltered = customer_list;
            notifyItemChanged(0, customer_listFiltered.size());
        }
        else
        {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback()
            {
                @Override
                public int getOldListSize()
                {
                    return CustomerListAdapter.this.customer_list.size();
                }

                @Override
                public int getNewListSize()
                {
                    return customer_list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
                {
                    return CustomerListAdapter.this.customer_list.get(oldItemPosition).getLast_name() == customer_list.get(newItemPosition).getLast_name();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    CustomerList newMovie = CustomerListAdapter.this.customer_list.get(oldItemPosition);

                    CustomerList oldMovie = customer_list.get(newItemPosition);

                    return newMovie.getLast_name() == oldMovie.getLast_name();
                }
            });
            this.customer_list = customer_list;
            this.customer_listFiltered = customer_list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public CustomerListAdapter.ViewHolderss onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_details_admin, viewGroup, false);
        return new ViewHolderss(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerListAdapter.ViewHolderss viewHolderss, final int i)
    {

        //viewHolderss.mtext.setText(""+customer_list.get(i).getId());
        viewHolderss.lastname_customer.setText(""+customer_list.get(i).getLast_name());
        viewHolderss.mobile.setText(""+customer_list.get(i).getAddress().getPhone1());
        viewHolderss.customer_email.setText(""+customer_list.get(i).getAddress().getEmail1());
     //   viewHolderss.customer_dob.setText(""+customer_list.get(i).getDate_of_birth());
        viewHolderss.edit_bill_details.setOnClickListener(new View.OnClickListener()
                {
            @Override
            public void onClick(View v)
            {
                Intent ss=new Intent(context, UpdateCustomer.class);

                 delid= String.valueOf(customer_list.get(i).getId());
                Toast.makeText(context,""+delid , Toast.LENGTH_SHORT).show();

                ss.putExtra("bname",""+customer_list.get(i).getBusiness_name());
                ss.putExtra("fname",""+customer_list.get(i).getFirst_name());
                ss.putExtra("lname",""+customer_list.get(i).getLast_name());
                ss.putExtra("dob",""+customer_list.get(i).getDate_of_birth());
                Toast.makeText(context,""+customer_list.get(i).getDate_of_birth() , Toast.LENGTH_SHORT).show();

                ss.putExtra("email",""+customer_list.get(i).getAddress().getEmail1());
                ss.putExtra("p1",""+customer_list.get(i).getAddress().getPhone1());
                ss.putExtra("p2",""+customer_list.get(i).getAddress().getPhone2());
                Toast.makeText(context,""+customer_list.get(i).getAddress().getPhone2() , Toast.LENGTH_SHORT).show();

                ss.putExtra("aadar",""+customer_list.get(i).getAadhar_number());
                ss.putExtra("pan",""+customer_list.get(i).getGovt_id_number());
                ss.putExtra("add1",""+customer_list.get(i).getAddress().getAddress1());
                ss.putExtra("city",""+customer_list.get(i).getAddress().getCity());
                ss.putExtra("state",""+customer_list.get(i).getAddress().getState());
                ss.putExtra("pin",""+customer_list.get(i).getAddress().getZip());
                context.startActivity(ss);
            }
        });

        viewHolderss.delete_bill_details.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                try
                {
                    delid= String.valueOf(customer_list.get(i).getId());
                    Toast.makeText(context,""+delid , Toast.LENGTH_SHORT).show();
                    JSONObject jmain = new JSONObject();
                    JSONObject jsub1 = new JSONObject();
                    JSONObject jmore1 = new JSONObject();
                    JSONObject jmore2 = new JSONObject();
                    jsub1.put("id", 0);
                    jsub1.put("business_name", "");
                    jsub1.put("first_name", customer_list.get(i).getFirst_name());
                    jsub1.put("last_name", customer_list.get(i).getLast_name());
                    jsub1.put("aadhar_number", customer_list.get(i).getAadhar_number());
                    jsub1.put("govt_id_number", customer_list.get(i).getGovt_id_number());
                    jsub1.put("date_of_birth", customer_list.get(i).getDate_of_birth());
                    jsub1.put("status_id", "");
                    jsub1.put("created","");
                    jsub1.put("last_updated","");
                    jsub1.put("update_counter","");

                    JSONObject jsub2 = new JSONObject();
                    jsub2.put("address1", customer_list.get(i).getAddress().getAddress1());
                    jsub2.put("address2", "");
                    jsub2.put("address3", "");
                    jsub2.put("city", customer_list.get(i).getAddress().getCity());
                    jsub2.put("state", customer_list.get(i).getAddress().getState());
                    jsub2.put("zip", customer_list.get(i).getAddress().getZip());
                    jsub2.put("email1", customer_list.get(i).getAddress().getEmail1());
                    jsub2.put("phone1", customer_list.get(i).getAddress().getPhone1());
                    jsub2.put("email2", "");
                    jsub2.put("phone2", customer_list.get(i).getAddress().getPhone2());
                    jsub2.put("created","");
                    jsub2.put("last_updated","");
                    jsub2.put("update_counter","");

                    jsub1.put("address", jsub2);

                    JSONObject jsubAgent = new JSONObject();
                    jsubAgent.put("id", 10059);
                    jsubAgent.put("business_name", "");
                    jsubAgent.put("first_name", "");
                    jsubAgent.put("last_name", "");
                    jsubAgent.put("aadhar_number", "");
                    jsubAgent.put("govt_id_number", "");
                    jsubAgent.put("created","");
                    jsubAgent.put("last_updated","");
                    jsubAgent.put("notes","");


                    JSONObject jsub3 = new JSONObject();
                    jsub3.put("id", 0);
                    jsub3.put("first_name", customer_list.get(i).getFirst_name());
                    jsub3.put("last_name", customer_list.get(i).getLast_name());
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
                    jsub3.put("created","");
                    jsub3.put("last_updated","");
                    jsub3.put("update_counter","");

                    jsubAgent.put("more", jmore1);
                    jsubAgent.put("address", jsub3);
                    jsub1.put("agent", jsubAgent);
                    jsub1.put("more", jmore2);
                    jmain.put("customer", jsub1);

                    Utills.getVolleyResponseJson(context, Request.Method.DELETE, "http://dev.simplytextile.com:9081/api/customers/"+customer_list.get(i).getId(), jmain, new VolleyCallback() {
                        @Override
                        public void onSuccessResponse(String result) {
                            JSONObject jb = null;
                            try {
                                jb = new JSONObject(result);
                                String msg = jb.getString("message");


                                Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (Exception e)
                {

                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return customer_list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customer_listFiltered = customer_list;
                } else {
                    List<CustomerList> filteredList = new ArrayList<>();
                    for (CustomerList movie : customer_list) {
                        if (movie.getLast_name().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    customer_listFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customer_listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                customer_listFiltered = (ArrayList<CustomerList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolderss extends RecyclerView.ViewHolder
    {
        TextView mtext,lastname_customer,mobile,customer_id_proof_proof,customer_address,customer_email,customer_dob;
        ImageView edit_bill_details,delete_bill_details;

        public ViewHolderss(@NonNull View itemView)
        {
            super(itemView);
            //mtext=(TextView)itemView.findViewById(R.id.mtext_customer_id_name);
            lastname_customer=(TextView)itemView.findViewById(R.id.lastname_customer);
            mobile=(TextView)itemView.findViewById(R.id.mobile);

         //   customer_address=(TextView)itemView.findViewById(R.id.customer_address);
            customer_email=(TextView)itemView.findViewById(R.id.customer_email);
            edit_bill_details=(ImageView)itemView.findViewById(R.id.edit_bill_details);
            delete_bill_details=(ImageView)itemView.findViewById(R.id.delete_bill_details);
        //    customer_dob=(TextView)itemView.findViewById(R.id.customer_dob);
        }
    }
}
