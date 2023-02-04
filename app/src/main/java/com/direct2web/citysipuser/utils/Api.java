package com.direct2web.citysipuser.utils;

import com.direct2web.citysipuser.model.DoctorModels.ResponseDoctorImageSlider;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorFavouriteHospital;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Account.ResponseUserDetails;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Account.ResponseUserDetailsSave;
import com.direct2web.citysipuser.adapters.RestaurentAdapter.Cart.ResponseDoctorApplyPromocode;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorAddressList;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorAllOfferList;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorUserDetails;
import com.direct2web.citysipuser.model.DoctorModels.Account.ResponseDoctorUserSaveDetails;
import com.direct2web.citysipuser.model.DoctorModels.DoctorImageAndVideo;
import com.direct2web.citysipuser.model.DoctorModels.DoctorReview;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval1.ResponseDoctorHelpAndFaqs;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.Leval3.ResponseDoctorHelpThird;
import com.direct2web.citysipuser.model.DoctorModels.HelpAndFaqs.leval2.ResponseDoctorHelpSecond;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorAppointStatus;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorCartItem;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorCartItemDelete;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorOfferList;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseDoctorSubmitReview;
import com.direct2web.citysipuser.model.DoctorModels.HospitalDetails.ResponseDoctorAddToCart;
import com.direct2web.citysipuser.model.DoctorModels.HospitalDetails.ResponseDoctorHospitalDetails;
import com.direct2web.citysipuser.model.DoctorModels.HospitalDetails.ResponseDoctorTime;
import com.direct2web.citysipuser.model.DoctorModels.HospitalDetails.ResponseDoctorWishList;
import com.direct2web.citysipuser.model.DoctorModels.HospitalList.ResponseDoctorHospitalList;
import com.direct2web.citysipuser.model.DoctorModels.HospitalReadMore.ResponseDoctorReadMore;
import com.direct2web.citysipuser.model.DoctorModels.ResponseDoctorSearch;
import com.direct2web.citysipuser.model.Insurence.Account.ResponseInsurenceAddressList;
import com.direct2web.citysipuser.model.Insurence.Account.ResponseInsurenceFavourite;
import com.direct2web.citysipuser.model.Insurence.Account.ResponseInsurenceUserDetails;
import com.direct2web.citysipuser.model.Insurence.Account.ResponseInsurenceUserSaveDetails;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level1.ResponseInsurenceHelpAndFaqs;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level2.ResponseInsurenceHelpSecond;
import com.direct2web.citysipuser.model.Insurence.HelpAndFaqs.Level3.ResponseInsurenceHelpThird;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceAddToCart;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceAgentDetails;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceGetTimeASlot;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceReadMore;
import com.direct2web.citysipuser.model.Insurence.InsurenceAgentDetails.ResponseInsurenceWishList;
import com.direct2web.citysipuser.model.Insurence.InsurenceCart.ResponseInsurenceSubmitReview;
import com.direct2web.citysipuser.model.Insurence.InsurenceList.ResponseInsurenceList;
import com.direct2web.citysipuser.model.Insurence.InsurenceServiceWiseAgent.ResponseInsurenceServiceWiseAgent;
import com.direct2web.citysipuser.model.Insurence.ReadMore.InsurenceReview;
import com.direct2web.citysipuser.model.Insurence.Search.ResponseInsurenceSearch;
import com.direct2web.citysipuser.model.LawyerModels.Account.ResponseLawyerAddressList;
import com.direct2web.citysipuser.model.LawyerModels.Account.ResponseLawyerAllOfferList;
import com.direct2web.citysipuser.model.LawyerModels.Account.ResponseLawyerFavourite;
import com.direct2web.citysipuser.model.LawyerModels.Account.ResponseLawyerUserDetails;
import com.direct2web.citysipuser.model.LawyerModels.Account.ResponseLawyerUserSaveDetails;
import com.direct2web.citysipuser.model.LawyerModels.HelpAndFaqs.Level1.ResponseLawyerHelpAndFaqs;
import com.direct2web.citysipuser.model.LawyerModels.HelpAndFaqs.Level2.ResponseLawyerHelpSecond;
import com.direct2web.citysipuser.model.LawyerModels.HelpAndFaqs.Level3.ResponseLawyerHelpThird;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerApplyPromocode;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerAppointStatus;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerCartItem;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerItemDelete;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerOfferList;
import com.direct2web.citysipuser.model.LawyerModels.LawyerCart.ResponseLawyerSubmitReview;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.LawyerImageAndVideo;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponseLawyerDetails;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponseLawyerList;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponseLawyerWishList;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponselawyerAddToCart;
import com.direct2web.citysipuser.model.LawyerModels.LawyerDetails.ResponselawyerTime;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.LawyerReview;
import com.direct2web.citysipuser.model.LawyerModels.ReadMore.ResponseLawyerReadMore;
import com.direct2web.citysipuser.model.LawyerModels.ResponseLawyerSearch;
import com.direct2web.citysipuser.model.RestaurentModels.Account.ResponseAddressList;
import com.direct2web.citysipuser.model.RestaurentModels.Account.ResponseRestaurentAllOfferList;
import com.direct2web.citysipuser.model.RestaurentModels.BusignessImagesAndVideos.BusinessImageAndVideo;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseAddToCart;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCart;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCartOperation;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessDetails.ResponseBusinessDetails;
import com.direct2web.citysipuser.model.RestaurentModels.BusinessList.BusinessList;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseCheckOut;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseOfferList;
import com.direct2web.citysipuser.model.DoctorModels.HospitalCart.ResponseRestaurentApplyPromocode;
import com.direct2web.citysipuser.model.RestaurentModels.Cart.ResponseReview;
import com.direct2web.citysipuser.model.RestaurentModels.Category.ResponseCategoryList;
import com.direct2web.citysipuser.model.RestaurentModels.CreateAccount.ResponseCreateAccount;
import com.direct2web.citysipuser.model.RestaurentModels.Help.level1.ResponseHelpMain;
import com.direct2web.citysipuser.model.RestaurentModels.Help.level2.ResponseHelpSecond;
import com.direct2web.citysipuser.model.RestaurentModels.Help.level3.ResponseHelpThird;
import com.direct2web.citysipuser.model.RestaurentModels.OtpSend.ResponseOtpSend;
import com.direct2web.citysipuser.model.RestaurentModels.OtpSend.ResponseVerifyMobile;
import com.direct2web.citysipuser.model.RestaurentModels.OtpSend.ResponseVerifyPassword;
import com.direct2web.citysipuser.model.RestaurentModels.Profile.ResponseSendProfile;
import com.direct2web.citysipuser.model.RestaurentModels.ReadMoreDetails.ReadMoreDetails;
import com.direct2web.citysipuser.model.RestaurentModels.ResponseFavRestaurent;
import com.direct2web.citysipuser.model.RestaurentModels.ResponseRestaurentSearch;
import com.direct2web.citysipuser.model.RestaurentModels.RestaurentRating.RestaurentReview;
import com.direct2web.citysipuser.model.RestaurentModels.WishList.WishList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    String imageUrl = "http://www.medicaiditpark.com/city_slip/";
    String url = "http://www.medicaiditpark.com/city_slip/api-firebase/";



    //==================================Common=========================================//


    @POST("user_side/get-categories.php")
    @FormUrlEncoded
    Call<ResponseCategoryList> getCategory(@Header("Authorization") String authHeader,
                                           @Field("accesskey") String accesskey,
                                           @Field("city_id") String city_id);

    @POST("user_side/verify-mobile.php")
    @FormUrlEncoded
    Call<ResponseVerifyMobile> sendVerifiMobile(@Header("Authorization") String authHeader,
                                                @Field("accesskey") String accesskey,
                                                @Field("mobile") String mobile);


    @POST("user_side/send-otp.php")
    @FormUrlEncoded
    Call<ResponseOtpSend> sendOtp(@Header("Authorization") String authHeader,
                                  @Field("accesskey") String accesskey,
                                  @Field("mobile") String mobile,
                                  @Field("otp") String otp);

    @POST("user_side/verify-password.php")
    @FormUrlEncoded
    Call<ResponseVerifyPassword> sendVerifyPassword(@Header("Authorization") String authHeader,
                                                    @Field("accesskey") String accesskey,
                                                    @Field("mobile") String mobile,
                                                    @Field("password") String password);

    @POST("user_side/create-account.php")
    @FormUrlEncoded
    Call<ResponseCreateAccount> sendCreateAccount(@Header("Authorization") String authHeader,
                                                  @Field("accesskey") String accesskey,
                                                  @Field("mobile") String mobile);

    @POST("user_side/save_profile.php")
    @FormUrlEncoded
    Call<ResponseSendProfile> sendProfile(@Header("Authorization") String authHeader,
                                          @Field("accesskey") String accesskey,
                                          @Field("user_id") String user_id,
                                          @Field("email_id") String email_id,
                                          @Field("name") String name,
                                          @Field("phone_no") String phone_no,
                                          @Field("reg_type") String reg_type,
                                          @Field("facebook_id") String facebook_id,
                                          @Field("google_id") String google_id,
                                          @Field("profile_image") String profile_image,
                                          @Field("address_as") String address_as,
                                          @Field("latitude") String latitude,
                                          @Field("longitude") String longitude,
                                          @Field("address_line_1") String address_line_1,
                                          @Field("address_line_2") String address_line_2);


    //==================================Restaurent=========================================//


    @POST("restaurant/user_side/business_list.php")
    @FormUrlEncoded
    Call<BusinessList> getRestaurentDetails(@Header("Authorization") String authHeader,
                                            @Field("accesskey") String accesskey,
                                            @Field("cat_id") String cat_id,
                                            @Field("latitude") String latitude,
                                            @Field("longitude") String longitude,
                                            @Field("sort_by") String sort_by,
                                            @Field("filter_by") String filter_by);


    @POST("restaurant/user_side/get_business_detail.php")
    @FormUrlEncoded
    Call<ResponseBusinessDetails> getBusinessDetails(@Header("Authorization") String authHeader,
                                                     @Field("accesskey") String accesskey,
                                                     @Field("user_id") String user_id,
                                                     @Field("business_id") String business_id,
                                                     @Field("latitude") String latitude,
                                                     @Field("longitude") String longitude);

    @POST("restaurant/user_side/get_read_more_detail.php")
    @FormUrlEncoded
    Call<ReadMoreDetails> getReadMoreDetails(@Header("Authorization") String authHeader,
                                             @Field("accesskey") String accesskey,
                                             @Field("business_id") String business_id);


    @POST("restaurant/user_side/wish_list.php")
    @FormUrlEncoded
    Call<WishList> getStatus(@Header("Authorization") String authHeader,
                             @Field("accesskey") String accesskey,
                             @Field("business_id") String business_id,
                             @Field("user_id") String user_id,
                             @Field("status") String status);


    @POST("restaurant/user_side/media_list.php")
    @FormUrlEncoded
    Call<BusinessImageAndVideo> getImagesAndVideos(@Header("Authorization") String authHeader,
                                                   @Field("accesskey") String accesskey,
                                                   @Field("business_id") String business_id);

    @POST("restaurant/user_side/review_rating.php")
    @FormUrlEncoded
    Call<RestaurentReview> getRestaurentReview(@Header("Authorization") String authHeader,
                                               @Field("accesskey") String accesskey,
                                               @Field("business_id") String business_id);


    @POST("restaurant/user_side/add_to_cart.php")
    @FormUrlEncoded
    Call<ResponseAddToCart> sendAddToCart(@Header("Authorization") String authHeader,
                                          @Field("accesskey") String accesskey,
                                          @Field("user_id") String user_id,
                                          @Field("item_id") String item_id);

    @POST("restaurant/user_side/cart_operation.php")
    @FormUrlEncoded
    Call<ResponseCartOperation> sendCartOperation(@Header("Authorization") String authHeader,
                                                  @Field("accesskey") String accesskey,
                                                  @Field("cart_id") String cart_id,
                                                  @Field("operation") String operation,
                                                  @Field("qty") String qty);

    @POST("restaurant/user_side/cart.php")
    @FormUrlEncoded
    Call<ResponseCart> getCartItem(@Header("Authorization") String authHeader,
                                   @Field("accesskey") String accesskey,
                                   @Field("user_id") String user_id);

    //ApplyCoupons-->
    @POST("restaurant/user_side/offer_list.php")
    @FormUrlEncoded
    Call<ResponseOfferList> getOfferList(@Header("Authorization") String authHeader,
                                         @Field("accesskey") String accesskey,
                                         @Field("product_list") String product_list);

    //OrderStatus-->
    @POST("restaurant/user_side/checkout.php")
    @FormUrlEncoded
    Call<ResponseCheckOut> sendCheckoutDetails(@Header("Authorization") String authHeader,
                                               @Field("accesskey") String accesskey,
                                               @Field("user_id") String user_id,
                                               @Field("total_amount") String total_amount,
                                               @Field("discount") String discount,
                                               @Field("coupan_code") String coupan_code,
                                               @Field("delivery_method") String delivery_method);

    //ServiceReview-->
    @POST("restaurant/user_side/submit_review.php")
    @FormUrlEncoded
    Call<ResponseReview> sendReview(@Header("Authorization") String authHeader,
                                    @Field("accesskey") String accesskey,
                                    @Field("user_id") String user_id,
                                    @Field("comment") String comment,
                                    @Field("rating") float rating);


    @POST("restaurant/user_side/all_offer_list.php")
    @FormUrlEncoded
    Call<ResponseRestaurentAllOfferList> getOffers(@Header("Authorization") String authHeader,
                                                   @Field("accesskey") String accesskey);


    @POST("restaurant/user_side/wish_list_business.php")
    @FormUrlEncoded
    Call<ResponseFavRestaurent> getFavouriteRestaurent(@Header("Authorization") String authHeader,
                                                       @Field("accesskey") String accesskey,
                                                       @Field("user_id") String user_id);


    @POST("restaurant/user_side/user_detail.php")
    @FormUrlEncoded
    Call<ResponseUserDetails> getUserDetails(@Header("Authorization") String authHeader,
                                             @Field("accesskey") String accesskey,
                                             @Field("user_id") String user_id);


    @POST("restaurant/user_side/address_list.php")
    @FormUrlEncoded
    Call<ResponseAddressList> getUserAddress(@Header("Authorization") String authHeader,
                                             @Field("accesskey") String accesskey,
                                             @Field("user_id") String user_id);


    //Help & FAQS -->
    @POST("restaurant/user_side/help_level_1.php")
    @FormUrlEncoded
    Call<ResponseHelpMain> getHelp(@Header("Authorization") String authHeader,
                                   @Field("accesskey") String accesskey);

    @POST("restaurant/user_side/help_level_2.php")
    @FormUrlEncoded
    Call<ResponseHelpSecond> getHelpsecondList(@Header("Authorization") String authHeader,
                                               @Field("accesskey") String accesskey,
                                               @Field("level_1_id") String level_1_id);

    @POST("restaurant/user_side/help_level_3.php")
    @FormUrlEncoded
    Call<ResponseHelpThird> getHelpThirdList(@Header("Authorization") String authHeader,
                                             @Field("accesskey") String accesskey,
                                             @Field("level_2_id") String level_2_id);

    @Multipart
    @POST("restaurant/user_side/save_profile.php")
    Call<ResponseUserDetailsSave> sendUserDetails(@Header("Authorization") String authorization,
                                                              @Part("accesskey") RequestBody accesskey,
                                                              @Part("user_id") RequestBody user_id,
                                                              @Part("name") RequestBody name,
                                                              @Part("email") RequestBody email,
                                                              @Part("mobile") RequestBody mobile,
                                                              @Part MultipartBody.Part image);

    @POST("restaurant/user_side/apply_promo.php")
    @FormUrlEncoded
    Call<ResponseRestaurentApplyPromocode> sendRestaurentPromocode(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("user_id") String user_id,
                                                         @Field("total_amount") String total_amount,
                                                         @Field("prmo_code") String prmo_code);


    @POST("restaurant/user_side/search.php")
    @FormUrlEncoded
    Call<ResponseRestaurentSearch> searchReataurent(@Header("Authorization") String authHeader,
                                                           @Field("accesskey") String accesskey,
                                                           @Field("cat_id") String cat_id,
                                                           @Field("keyword") String keyword);

    //==================================Doctor=========================================//


    @POST("doctor/user_side/business_list.php")
    @FormUrlEncoded
    Call<ResponseDoctorHospitalList> getHospitalListDetails(@Header("Authorization") String authHeader,
                                                            @Field("accesskey") String accesskey,
                                                            @Field("cat_id") String cat_id,
                                                            @Field("latitude") String latitude,
                                                            @Field("longitude") String longitude,
                                                            @Field("sort_by") String sort_by,
                                                            @Field("filter_by") String filter_by);

    @POST("doctor/user_side/get_business_detail.php")
    @FormUrlEncoded
    Call<ResponseDoctorHospitalDetails> getHospitalDetails(@Header("Authorization") String authHeader,
                                                           @Field("accesskey") String accesskey,
                                                           @Field("business_id") String business_id,
                                                           @Field("latitude") String latitude,
                                                           @Field("longitude") String longitude,
                                                           @Field("user_id") String user_id);

    @POST("doctor/user_side/wish_list.php")
    @FormUrlEncoded
    Call<ResponseDoctorWishList> getDoctorStatus(@Header("Authorization") String authHeader,
                                                 @Field("accesskey") String accesskey,
                                                 @Field("business_id") String business_id,
                                                 @Field("user_id") String user_id,
                                                 @Field("status") String status);


    @POST("doctor/user_side/get_read_more_detail.php")
    @FormUrlEncoded
    Call<ResponseDoctorReadMore> getHospitalDetailsReadMore(@Header("Authorization") String authHeader,
                                                            @Field("accesskey") String accesskey,
                                                            @Field("business_id") String business_id);


    @POST("doctor/user_side/cart.php")
    @FormUrlEncoded
    Call<ResponseDoctorCartItem> getDoctorCartItem(@Header("Authorization") String authHeader,
                                                   @Field("accesskey") String accesskey,
                                                   @Field("user_id") String user_id);


    @POST("doctor/user_side/delete_data.php")
    @FormUrlEncoded
    Call<ResponseDoctorCartItemDelete> sendDeleteCartItem(@Header("Authorization") String authHeader,
                                                          @Field("accesskey") String accesskey,
                                                          @Field("type") String type,
                                                          @Field("id") String id);


    //ApplyCoupons-->
    @POST("doctor/user_side/offer_list.php")
    @FormUrlEncoded
    Call<ResponseDoctorOfferList> getDoctorOfferList(@Header("Authorization") String authHeader,
                                                     @Field("accesskey") String accesskey,
                                                     @Field("product_list") String product_list);


    //Appointment Status-->
    @POST("doctor/user_side/checkout.php")
    @FormUrlEncoded
    Call<ResponseDoctorAppointStatus> sendDoctorCheckoutDetails(@Header("Authorization") String authHeader,
                                                                @Field("accesskey") String accesskey,
                                                                @Field("user_id") String user_id,
                                                                @Field("total_amount") String total_amount,
                                                                @Field("discount") String discount,
                                                                @Field("coupan_code") String coupan_code,
                                                                @Field("delivery_method") String delivery_method);


    //ServiceReview-->
    @POST("doctor/user_side/submit_review.php")
    @FormUrlEncoded
    Call<ResponseDoctorSubmitReview> sendDoctorReview(@Header("Authorization") String authHeader,
                                                      @Field("accesskey") String accesskey,
                                                      @Field("user_id") String user_id,
                                                      @Field("comment") String comment,
                                                      @Field("rating") float rating);

    //bottomsheet

    @POST("doctor/user_side/add_to_cart.php")
    @FormUrlEncoded
    Call<ResponseDoctorAddToCart> sendDoctorAddToCart(@Header("Authorization") String authHeader,
                                                      @Field("accesskey") String accesskey,
                                                      @Field("user_id") String user_id,
                                                      @Field("item_id") String item_id,
                                                      @Field("date") String date,
                                                      @Field("time") String time);

    //Help & FAQS -->
    @POST("doctor/user_side/help_level_1.php")
    @FormUrlEncoded
    Call<ResponseDoctorHelpAndFaqs> getDoctorHelp(@Header("Authorization") String authHeader,
                                                  @Field("accesskey") String accesskey);

    @POST("doctor/user_side/help_level_2.php")
    @FormUrlEncoded
    Call<ResponseDoctorHelpSecond> getDoctorHelpsecondList(@Header("Authorization") String authHeader,
                                                           @Field("accesskey") String accesskey,
                                                           @Field("level_1_id") String level_1_id);

    @POST("doctor/user_side/help_level_3.php")
    @FormUrlEncoded
    Call<ResponseDoctorHelpThird> getDoctorHelpThirdList(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("level_2_id") String level_2_id);


    @POST("doctor/user_side/get_time_slot.php")
    @FormUrlEncoded
    Call<ResponseDoctorTime> getDoctorTime(@Header("Authorization") String authHeader,
                                           @Field("accesskey") String accesskey,
                                           @Field("appointment_date") String appointment_date);


    @POST("doctor/user_side/user_detail.php")
    @FormUrlEncoded
    Call<ResponseDoctorUserDetails> getDocotorUserDetails(@Header("Authorization") String authHeader,
                                                          @Field("accesskey") String accesskey,
                                                          @Field("user_id") String user_id);

    @Multipart
    @POST("doctor/user_side/save_profile.php")
    Call<ResponseDoctorUserSaveDetails> sendDoctorUserDetails(@Header("Authorization") String authorization,
                                        @Part("accesskey") RequestBody accesskey,
                                        @Part("user_id") RequestBody user_id,
                                        @Part("name") RequestBody name,
                                        @Part("email") RequestBody email,
                                        @Part("mobile") RequestBody mobile,
                                        @Part MultipartBody.Part image);

    @POST("doctor/user_side/address_list.php")
    @FormUrlEncoded
    Call<ResponseDoctorAddressList> getDoctorUserAddress(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("user_id") String user_id);

    @POST("doctor/user_side/all_offer_list.php")
    @FormUrlEncoded
    Call<ResponseDoctorAllOfferList> getDoctorOffers(@Header("Authorization") String authHeader,
                                                     @Field("accesskey") String accesskey);

    @POST("doctor/user_side/wish_list_business.php")
    @FormUrlEncoded
    Call<ResponseDoctorFavouriteHospital> getFavouriteHospitalList(@Header("Authorization") String authHeader,
                                                                   @Field("accesskey") String accesskey,
                                                                   @Field("user_id") String user_id);

    @POST("doctor/user_side/review_rating.php")
    @FormUrlEncoded
    Call<DoctorReview> getDoctorreview(@Header("Authorization") String authHeader,
                                       @Field("accesskey") String accesskey,
                                       @Field("business_id") String business_id);


    @POST("doctor/user_side/media_list.php")
    @FormUrlEncoded
    Call<DoctorImageAndVideo> getImagesAndVideosHospital(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("business_id") String business_id);


    @POST("doctor/user_side/apply_promo.php")
    @FormUrlEncoded
    Call<ResponseDoctorApplyPromocode> sendPromocode(@Header("Authorization") String authHeader,
                                                                  @Field("accesskey") String accesskey,
                                                                  @Field("user_id") String user_id,
                                                                  @Field("total_amount") String total_amount,
                                                                  @Field("prmo_code") String prmo_code);

    @POST("doctor/user_side/search.php")
    @FormUrlEncoded
    Call<ResponseDoctorSearch> searchHospital(@Header("Authorization") String authHeader,
                                                @Field("accesskey") String accesskey,
                                                @Field("cat_id") String cat_id,
                                                @Field("keyword") String keyword);



    //==================================Lawyer=========================================//


    @POST("lawyer/user_side/business_list.php")
    @FormUrlEncoded
    Call<ResponseLawyerList> getLawyerListDetails(@Header("Authorization") String authHeader,
                                                    @Field("accesskey") String accesskey,
                                                    @Field("cat_id") String cat_id,
                                                    @Field("latitude") String latitude,
                                                    @Field("longitude") String longitude,
                                                    @Field("sort_by") String sort_by,
                                                    @Field("filter_by") String filter_by);

    @POST("lawyer/user_side/get_business_detail.php")
    @FormUrlEncoded
    Call<ResponseLawyerDetails> getLawyerDetails(@Header("Authorization") String authHeader,
                                                   @Field("accesskey") String accesskey,
                                                   @Field("business_id") String business_id,
                                                   @Field("latitude") String latitude,
                                                   @Field("longitude") String longitude,
                                                   @Field("user_id") String user_id);

    @POST("lawyer/user_side/wish_list.php")
    @FormUrlEncoded
    Call<ResponseLawyerWishList> getLawyerStatus(@Header("Authorization") String authHeader,
                                                 @Field("accesskey") String accesskey,
                                                 @Field("business_id") String business_id,
                                                 @Field("user_id") String user_id,
                                                 @Field("status") String status);


    @POST("lawyer/user_side/get_read_more_detail.php")
    @FormUrlEncoded
    Call<ResponseLawyerReadMore> getLawyerDetailsReadMore(@Header("Authorization") String authHeader,
                                                            @Field("accesskey") String accesskey,
                                                            @Field("business_id") String business_id);


    @POST("lawyer/user_side/cart.php")
    @FormUrlEncoded
    Call<ResponseLawyerCartItem> getLawyerCartItem(@Header("Authorization") String authHeader,
                                                   @Field("accesskey") String accesskey,
                                                   @Field("user_id") String user_id);


    @POST("lawyer/user_side/delete_data.php")
    @FormUrlEncoded
    Call<ResponseLawyerItemDelete> sendLawyerDeleteCartItem(@Header("Authorization") String authHeader,
                                                      @Field("accesskey") String accesskey,
                                                      @Field("type") String type,
                                                      @Field("id") String id);


    //ApplyCoupons-->
    @POST("lawyer/user_side/offer_list.php")
    @FormUrlEncoded
    Call<ResponseLawyerOfferList> getLawyerOfferList(@Header("Authorization") String authHeader,
                                                     @Field("accesskey") String accesskey,
                                                     @Field("product_list") String product_list);


    //Appointment Status-->
    @POST("lawyer/user_side/checkout.php")
    @FormUrlEncoded
    Call<ResponseLawyerAppointStatus> sendLawyerCheckoutDetails(@Header("Authorization") String authHeader,
                                                                @Field("accesskey") String accesskey,
                                                                @Field("user_id") String user_id,
                                                                @Field("total_amount") String total_amount,
                                                                @Field("discount") String discount,
                                                                @Field("coupan_code") String coupan_code,
                                                                @Field("delivery_method") String delivery_method);

    //ServiceReview-->
    @POST("lawyer/user_side/submit_review.php")
    @FormUrlEncoded
    Call<ResponseLawyerSubmitReview> sendLawyerReview(@Header("Authorization") String authHeader,
                                                      @Field("accesskey") String accesskey,
                                                      @Field("user_id") String user_id,
                                                      @Field("comment") String comment,
                                                      @Field("rating") float rating);

    //bottomsheet

    @POST("lawyer/user_side/add_to_cart.php")
    @FormUrlEncoded
    Call<ResponselawyerAddToCart> sendLawyerAddToCart(@Header("Authorization") String authHeader,
                                                      @Field("accesskey") String accesskey,
                                                      @Field("user_id") String user_id,
                                                      @Field("item_id") String item_id,
                                                      @Field("date") String date,
                                                      @Field("time") String time);

    //Help & FAQS -->
    @POST("lawyer/user_side/help_level_1.php")
    @FormUrlEncoded
    Call<ResponseLawyerHelpAndFaqs> getLawyerHelp(@Header("Authorization") String authHeader,
                                                  @Field("accesskey") String accesskey);

    @POST("lawyer/user_side/help_level_2.php")
    @FormUrlEncoded
    Call<ResponseLawyerHelpSecond> getLawyerHelpsecondList(@Header("Authorization") String authHeader,
                                                           @Field("accesskey") String accesskey,
                                                           @Field("level_1_id") String level_1_id);

    @POST("lawyer/user_side/help_level_3.php")
    @FormUrlEncoded
    Call<ResponseLawyerHelpThird> getLawyerHelpThirdList(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("level_2_id") String level_2_id);

    @POST("lawyer/user_side/get_time_slot.php")
    @FormUrlEncoded
    Call<ResponselawyerTime> getLawyerTime(@Header("Authorization") String authHeader,
                                           @Field("accesskey") String accesskey,
                                           @Field("appointment_date") String appointment_date);


    @POST("lawyer/user_side/user_detail.php")
    @FormUrlEncoded
    Call<ResponseLawyerUserDetails> getLawyerUserDetails(@Header("Authorization") String authHeader,
                                                          @Field("accesskey") String accesskey,
                                                          @Field("user_id") String user_id);

    @Multipart
    @POST("lawyer/user_side/save_profile.php")
    Call<ResponseLawyerUserSaveDetails> sendLawyerUserDetails(@Header("Authorization") String authorization,
                                                              @Part("accesskey") RequestBody accesskey,
                                                              @Part("user_id") RequestBody user_id,
                                                              @Part("name") RequestBody name,
                                                              @Part("email") RequestBody email,
                                                              @Part("mobile") RequestBody mobile,
                                                              @Part MultipartBody.Part image);

    @POST("lawyer/user_side/address_list.php")
    @FormUrlEncoded
    Call<ResponseLawyerAddressList> getLawyerUserAddress(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("user_id") String user_id);

    @POST("lawyer/user_side/all_offer_list.php")
    @FormUrlEncoded
    Call<ResponseLawyerAllOfferList> getLawyerOffers(@Header("Authorization") String authHeader,
                                                     @Field("accesskey") String accesskey);

    @POST("lawyer/user_side/wish_list_business.php")
    @FormUrlEncoded
    Call<ResponseLawyerFavourite> getLawyerFavouriteList(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("user_id") String user_id);

    @POST("lawyer/user_side/review_rating.php")
    @FormUrlEncoded
    Call<LawyerReview> getLawyerreview(@Header("Authorization") String authHeader,
                                       @Field("accesskey") String accesskey,
                                       @Field("business_id") String business_id);

    @POST("lawyer/user_side/media_list.php")
    @FormUrlEncoded
    Call<LawyerImageAndVideo> getImagesAndVideosLawyer(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("business_id") String business_id);


    @POST("lawyer/user_side/apply_promo.php")
    @FormUrlEncoded
    Call<ResponseLawyerApplyPromocode> sendLawyerPromocode(@Header("Authorization") String authHeader,
                                                     @Field("accesskey") String accesskey,
                                                     @Field("user_id") String user_id,
                                                     @Field("total_amount") String total_amount,
                                                     @Field("prmo_code") String prmo_code);

    @POST("lawyer/user_side/search.php")
    @FormUrlEncoded
    Call<ResponseLawyerSearch> searchLawyer(@Header("Authorization") String authHeader,
                                                @Field("accesskey") String accesskey,
                                                @Field("cat_id") String cat_id,
                                                @Field("keyword") String keyword);



    //==================================Insurence=========================================//


    @POST("insurance/user_side/agent_list.php")
    @FormUrlEncoded
    Call<ResponseInsurenceList> getInsurenceAgentList(@Header("Authorization") String authHeader,
                                                       @Field("accesskey") String accesskey,
                                                       @Field("cat_id") String cat_id,
                                                       @Field("latitude") String latitude,
                                                       @Field("longitude") String longitude,
                                                       @Field("sort_by") String sort_by,
                                                       @Field("filter_by") String filter_by);

    @POST("insurance/user_side/service_wise_agent.php")
    @FormUrlEncoded
    Call<ResponseInsurenceServiceWiseAgent> getInsurenceServiceWiseAgent(@Header("Authorization") String authHeader,
                                                               @Field("accesskey") String accesskey,
                                                               @Field("service_id") String service_id);

    @POST("insurance/user_side/search.php")
    @FormUrlEncoded
    Call<ResponseInsurenceSearch> searchInsurence(@Header("Authorization") String authHeader,
                                                  @Field("accesskey") String accesskey,
                                                  @Field("cat_id") String cat_id,
                                                  @Field("keyword") String keyword);


    @POST("insurance/user_side/get_agent_detail.php")
    @FormUrlEncoded
    Call<ResponseInsurenceAgentDetails> getAgentDetails(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("agent_id") String agent_id);

    @POST("insurance/user_side/wish_list.php")
    @FormUrlEncoded
    Call<ResponseInsurenceWishList> getInsurenceStatus(@Header("Authorization") String authHeader,
                                                    @Field("accesskey") String accesskey,
                                                    @Field("business_id") String business_id,
                                                    @Field("user_id") String user_id,
                                                    @Field("status") String status);

    @POST("insurance/user_side/get_time_slot.php")
    @FormUrlEncoded
    Call<ResponseInsurenceGetTimeASlot> getInsurenceTimeSloat(@Header("Authorization") String authHeader,
                                                      @Field("accesskey") String accesskey,
                                                      @Field("appointment_date") String appointment_date);


    @POST("insurance/user_side/get_read_more_detail.php")
    @FormUrlEncoded
    Call<ResponseInsurenceReadMore> getAgentReadMoreDetails(@Header("Authorization") String authHeader,
                                                          @Field("accesskey") String accesskey,
                                                          @Field("agent_id") String agent_id);



    //bottomsheet

    @POST("insurance/user_side/add_to_cart.php")
    @FormUrlEncoded
    Call<ResponseInsurenceAddToCart> sendInsurenceAddToCart(@Header("Authorization") String authHeader,
                                                            @Field("accesskey") String accesskey,
                                                            @Field("user_id") String user_id,
                                                            @Field("item_id") String item_id,
                                                            @Field("date") String date,
                                                            @Field("time") String time);

    //Help & FAQS -->
    @POST("insurance/user_side/help_level_1.php")
    @FormUrlEncoded
    Call<ResponseInsurenceHelpAndFaqs> getInsurenceHelp(@Header("Authorization") String authHeader,
                                                        @Field("accesskey") String accesskey);

    @POST("insurance/user_side/help_level_2.php")
    @FormUrlEncoded
    Call<ResponseInsurenceHelpSecond> getInsurenceHelpsecondList(@Header("Authorization") String authHeader,
                                                                 @Field("accesskey") String accesskey,
                                                                 @Field("level_1_id") String level_1_id);

    @POST("insurance/user_side/help_level_3.php")
    @FormUrlEncoded
    Call<ResponseInsurenceHelpThird> getInsurenceHelpThirdList(@Header("Authorization") String authHeader,
                                                               @Field("accesskey") String accesskey,
                                                               @Field("level_2_id") String level_2_id);


    @POST("insurance/user_side/user_detail.php")
    @FormUrlEncoded
    Call<ResponseInsurenceUserDetails> getInsurenceUserDetails(@Header("Authorization") String authHeader,
                                                               @Field("accesskey") String accesskey,
                                                               @Field("user_id") String user_id);


    @Multipart
    @POST("insurance/user_side/save_profile.php")
    Call<ResponseInsurenceUserSaveDetails> sendInsurenceUserDetails(@Header("Authorization") String authorization,
                                                                    @Part("accesskey") RequestBody accesskey,
                                                                    @Part("user_id") RequestBody user_id,
                                                                    @Part("name") RequestBody name,
                                                                    @Part("email") RequestBody email,
                                                                    @Part("mobile") RequestBody mobile,
                                                                    @Part MultipartBody.Part image);

    @POST("insurance/user_side/address_list.php")
    @FormUrlEncoded
    Call<ResponseInsurenceAddressList> getInsurenceUserAddress(@Header("Authorization") String authHeader,
                                                               @Field("accesskey") String accesskey,
                                                               @Field("user_id") String user_id);



    @POST("insurance/user_side/review_rating.php")
    @FormUrlEncoded
    Call<InsurenceReview> getInsurencereview(@Header("Authorization") String authHeader,
                                             @Field("accesskey") String accesskey,
                                             @Field("business_id") String business_id);

    //ServiceReview-->
    @POST("insurance/user_side/submit_review.php")
    @FormUrlEncoded
    Call<ResponseInsurenceSubmitReview> sendInsurenceReview(@Header("Authorization") String authHeader,
                                                         @Field("accesskey") String accesskey,
                                                         @Field("user_id") String user_id,
                                                         @Field("comment") String comment,
                                                         @Field("rating") float rating);

    @POST("insurance/user_side/wish_list_business.php")
    @FormUrlEncoded
    Call<ResponseInsurenceFavourite> getInsurenceFavouriteList(@Header("Authorization") String authHeader,
                                                               @Field("accesskey") String accesskey,
                                                               @Field("user_id") String user_id);





    @POST("doctor/user_side/slider_list.php")
    @FormUrlEncoded
    Call<ResponseDoctorImageSlider> getSliderList(@Header("Authorization") String authHeader,
                                                  @Field("accesskey") String accesskey,
                                                  @Field("cat_id") String cat_id);
}

