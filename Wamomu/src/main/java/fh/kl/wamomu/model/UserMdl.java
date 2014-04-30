package fh.kl.wamomu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMdl implements Serializable {
	
	@JsonProperty("id")
	private Long id;
	@JsonProperty("vorname")
	private String vorname;
	@JsonProperty("nachname")
	private String nachname;
    @JsonProperty("user")
    private String user;
    @JsonProperty("password")
    private String password;
	@JsonProperty("geburtstag")
	private DateTime geburtstag;


	public UserMdl() { }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public DateTime getGeburtstag() {
		
		return geburtstag;
	}
	public void setGeburtstag(DateTime geburtstag) {
								
		this.geburtstag = new DateTime(geburtstag.getMillis());
	}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserMdl{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", geburtstag=" + geburtstag +
                '}';
    }

}
