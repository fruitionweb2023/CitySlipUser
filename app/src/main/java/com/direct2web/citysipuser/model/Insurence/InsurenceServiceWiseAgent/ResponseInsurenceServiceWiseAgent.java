package com.direct2web.citysipuser.model.Insurence.InsurenceServiceWiseAgent;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInsurenceServiceWiseAgent {

@SerializedName("error")
@Expose
private Boolean error;
@SerializedName("message")
@Expose
private String message;
@SerializedName("agent_list")
@Expose
private List<Agent> agentList = null;

public Boolean getError() {
return error;
}

public void setError(Boolean error) {
this.error = error;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Agent> getAgentList() {
return agentList;
}

public void setAgentList(List<Agent> agentList) {
this.agentList = agentList;
}

}