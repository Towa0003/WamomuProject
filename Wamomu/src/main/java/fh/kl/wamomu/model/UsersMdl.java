package fh.kl.wamomu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersMdl implements Serializable {
	
	@JsonProperty("users")
	List<UserMdl> users;

	public List<UserMdl> getUsers() {
		return users;
	}

	public void setUsers(List<UserMdl> userList) {
		this.users = userList;
	}
	
	

}
