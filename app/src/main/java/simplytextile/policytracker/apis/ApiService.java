package simplytextile.policytracker.apis;

import java.util.ArrayList;
import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.http.Body;
        import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.GET;
        import retrofit2.http.Header;
        import retrofit2.http.Headers;
        import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import simplytextile.policytracker.NotificationResponse.Notresponse;
import simplytextile.policytracker.companyresponse.AddCmpResponse;
import simplytextile.policytracker.companyresponse.CompanyList;
        import simplytextile.policytracker.companyresponse.Compres;
        import simplytextile.policytracker.companyresponse.Data;
        import simplytextile.policytracker.response.AgentsResponse;
        import simplytextile.policytracker.response.CustomerResponse;
        import simplytextile.policytracker.response.PoliciesResponse;
        import simplytextile.policytracker.responses.loginresponses.LoginResponse;
import simplytextile.policytracker.changepasswordresponse.Changespassword;

public interface ApiService
{
//    @Headers("Content-Type: application/json")
//    @POST("api/subscribers/")
//    Call<SignUpResponse> signupdetails(@Body Subscriber subscriber);



    @FormUrlEncoded
    @POST("api/users/me/login")
    Call<LoginResponse> Logindetails(@Field("login_name") String login_name, @Field("password") String password);

    @FormUrlEncoded
    @PUT("api/users/me/changepassword")
    Call<Changespassword> userchangePassword(@Header("app_sid") String S_id ,
                                             @Field("login_name") String login_name,
                                             @Field("oldpassword") String oldpassword,
                                             @Field("newpassword") String newpassword);


    @GET("api/companies")
    Call<Compres> getCompanies( );

    @GET("api/companies")
    Call<List<CompanyList>> getCompaniesList();


    @Headers("Content-Type:application/json")
    @GET("api/customers")
    Call<CustomerResponse> getCustomers(@Header("app_sid") String S_id);




    @Headers("Content-Type:application/json")
    @GET("api/customers")
    Call<CustomerResponse> getCustomerfilter(@Header("app_sid") String S_id,@Query("agent_id") String client,@Query("search_text") String sigatunre);


    @Headers("Content-Type:application/json")
    @GET("api/agents/")
    Call<AgentsResponse> getAgents(@Header("app_sid") String S_id );



    @Headers("Content-Type:application/json")
    @GET("api/agents/")
    Call<AgentsResponse> getAgentsfilter(@Header("app_sid") String S_id, @Query("search_text") String search_text );


    //api/policies
    @Headers("Content-Type:application/json")
    @GET("api/policies")
    Call<PoliciesResponse> getPolicies(@Header("app_sid") String S_id );

    @Headers("Content-Type:application/json")
    @GET("/api/notifications")
    Call<Notresponse> getNotification(@Header("app_sid") String S_id);



    @Headers("Content-Type:application/json")
    @GET("/api/subscribers/id/companies")
    Call<AddCmpResponse> addCompany(@Header("app_sid") String S_id);


    @Headers("Content-Type:application/json")
    @GET("api/notifications")
    Call<Notresponse> getNotificationFilter(@Header("app_sid") String s_id,
                                            @Query("policy_number") String policy_number,
                                            @Query("format") String format,
                                            @Query("type") String type,
                                            @Query("date_from") String date_from,
                                            @Query("date_to") String date_to,
                                            @Query("customer_id") int customer_id,
                                            @Query("agent_id") int agent_id);

    @Headers("Content-Type:application/json")
    @GET("api/notifications")
    Call<Notresponse> getAgentNotificationFilter(@Header("app_sid") String s_id,
                                            @Query("policy_number") String policy_number,
                                            @Query("format") String format,
                                            @Query("type") String type,
                                            @Query("date_from") String date_from,
                                            @Query("date_to") String date_to,
                                            @Query("customer_id") int customer_id);













    //Call<SignUpResponse> signupdetails( Integer type_id,String first_name, String last_name, String email1, String phone1, String login_name, String password);



}
