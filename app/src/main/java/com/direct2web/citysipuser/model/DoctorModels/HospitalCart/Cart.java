package com.direct2web.citysipuser.model.DoctorModels.HospitalCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

@SerializedName("id")
@Expose
private String id;
@SerializedName("qty")
@Expose
private String qty;
@SerializedName("price")
@Expose
private Integer price;
@SerializedName("doctor_name")
@Expose
private String doctorName;
@SerializedName("service_name")
@Expose
private String serviceName;
@SerializedName("product_id")
@Expose
private String productId;
@SerializedName("appointment_date")
@Expose
private String appointmentDate;
@SerializedName("appointment_time")
@Expose
private String appointmentTime;
@SerializedName("hospital_name")
@Expose
private String hospitalName;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getQty() {
return qty;
}

public void setQty(String qty) {
this.qty = qty;
}

public Integer getPrice() {
return price;
}

public void setPrice(Integer price) {
this.price = price;
}

public String getDoctorName() {
return doctorName;
}

public void setDoctorName(String doctorName) {
this.doctorName = doctorName;
}

public String getServiceName() {
return serviceName;
}

public void setServiceName(String serviceName) {
this.serviceName = serviceName;
}

public String getProductId() {
return productId;
}

public void setProductId(String productId) {
this.productId = productId;
}

public String getAppointmentDate() {
return appointmentDate;
}

public void setAppointmentDate(String appointmentDate) {
this.appointmentDate = appointmentDate;
}

public String getAppointmentTime() {
return appointmentTime;
}

public void setAppointmentTime(String appointmentTime) {
this.appointmentTime = appointmentTime;
}

public String getHospitalName() {
return hospitalName;
}

public void setHospitalName(String hospitalName) {
this.hospitalName = hospitalName;
}

}