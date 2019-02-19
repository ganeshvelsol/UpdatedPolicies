package simplytextile.pdf;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simplytextile.policytracker.R;
import simplytextile.policytracker.adapters.CustomerListAdapter;
import simplytextile.policytracker.apis.ApiClient;
import simplytextile.policytracker.apis.ApiService;
import simplytextile.policytracker.models.CustomerList;
import simplytextile.policytracker.response.CustomerResponse;
import static simplytextile.pdf.LogUtils.LOGE;
import static simplytextile.pdf.PermissionActivity.PERMISSION_REQUEST_CODE;
import static simplytextile.pdf.PermissionsChecker.REQUIRED_PERMISSION;

public class PdfActivty extends AppCompatActivity
{

    String text;
    Context mContext;
    RecyclerView customer_recycler;

    Document document;
    float mHeadingFontSize = 20.0f;
    float mValueFontSize = 26.0f;
    BaseFont urName;

    LinearLayoutManager llm;
    PermissionsChecker checker;
    List<CustomerList> customer_list;
    CustomerListAdapter adapter;
    String nam,nam2,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_activty);
        llm=new LinearLayoutManager(this);
        customer_recycler=(RecyclerView)findViewById(R.id.customer_recycler);
        mContext = getApplicationContext();
        text=CustomerListAdapter.Custname;
        //Toast.makeText(PdfActivty.this, "from else"+text, Toast.LENGTH_SHORT).show();
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

                    adapter=new CustomerListAdapter(response.body().getData().getCustomer_list(),PdfActivty.this);
                  //  String tot= String.valueOf(CustomerListAdapter.customer_list.get());
                  //  Toast.makeText(PdfActivty.this, ""+tot, Toast.LENGTH_SHORT).show();
                    customer_recycler.setAdapter(adapter);
                    customer_recycler.setLayoutManager(llm);
                    for(int i= 0;i<adapter.getItemCount();i++)
                   {
                       nam=CustomerListAdapter.customer_list.get(i).getLast_name();
                       mobile=CustomerListAdapter.customer_list.get(i).getAddress().getPhone1();
                       Toast.makeText(PdfActivty.this, ""+nam, Toast.LENGTH_SHORT).show();
                       Toast.makeText(PdfActivty.this, ""+mobile, Toast.LENGTH_SHORT).show();
                       createPdf(FileUtils.getAppPath(mContext) + "kasim.pdf");
                   }

                }
                else
                {
                    Toast.makeText(PdfActivty.this, "from else"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t)
            {
                Toast.makeText(PdfActivty.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        checker = new PermissionsChecker(this);
       // createPdf(FileUtils.getAppPath(mContext) + "kasim.pdf");
        /**
         *
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (checker.lacksPermissions(REQUIRED_PERMISSION))
                {
                    PermissionActivity.startActivityForResult(PdfActivty.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                }
                else
                {
                   // createPdf(FileUtils.getAppPath(mContext) + "kasim.pdf");
                }
            }
        });
    }

    public void createPdf(String dest)
    {

        if (new File(dest).exists())
        {
            new File(dest).delete();
        }

        try
        {
            /**
             * Creating Document
             */
             document = new Document();
             // Location to save
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            // Open to write
            document.open();
            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("PolicyTracker");
            document.addCreator("pdflist");

            /***
             * Variables for further use....
             */
            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);


            /**
             * How to USE FONT....
             */
             urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Font mOrderDetailsTitleFont1 = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk1 = new Chunk(nam, mOrderDetailsTitleFont1);
            Paragraph mOrderDetailsTitleParagraph1 = new Paragraph(mOrderDetailsTitleChunk1);
            mOrderDetailsTitleParagraph1.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph1);

            // Fields of Order Details...
            // Adding Chunks for Title and value
            Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderIdChunk = new Chunk("CustomerID"+mobile, mOrderIdFont);
            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
            document.add(mOrderIdParagraph);

            Font mOrderIdValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderIdValueChunk = new Chunk("CustomerID"+nam2, mOrderIdValueFont);
            Paragraph mOrderIdValueParagraph = new Paragraph(mOrderIdValueChunk);
            document.add(mOrderIdValueParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Font mOrderDateFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderDateChunk = new Chunk("", mOrderDateFont);
            Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
            document.add(mOrderDateParagraph);

            Font mOrderDateValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDateValueChunk = new Chunk("06/07/2017", mOrderDateValueFont);
            Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
            document.add(mOrderDateValueParagraph);

            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Font mOrderAcNameFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderAcNameChunk = new Chunk("Email", mOrderAcNameFont);
            Paragraph mOrderAcNameParagraph = new Paragraph(mOrderAcNameChunk);
            document.add(mOrderAcNameParagraph);

            Font mOrderAcNameValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderAcNameValueChunk = new Chunk("Pratik Butani", mOrderAcNameValueFont);
            Paragraph mOrderAcNameValueParagraph = new Paragraph(mOrderAcNameValueChunk);
            document.add(mOrderAcNameValueParagraph);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            Toast.makeText(mContext, "Created... :)", Toast.LENGTH_SHORT).show();

        }
        catch (IOException | DocumentException ie)
        {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        }
        catch (ActivityNotFoundException ae)
        {
            Toast.makeText(mContext, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
        try
        {
            FileUtils.openFile(mContext, new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PermissionActivity.PERMISSIONS_GRANTED)
        {
            Toast.makeText(mContext, "Permission Granted to Save", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext, "Permission not granted, Try again!", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

