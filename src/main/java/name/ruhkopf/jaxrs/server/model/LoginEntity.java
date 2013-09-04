package name.ruhkopf.jaxrs.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="logins", uniqueConstraints= {
@UniqueConstraint(columnNames = {"userToken", "accessType"}) })
public class LoginEntity extends AbstractEntity
{
   @Column(nullable = false)
   private String userToken;

   @Column(nullable = false)
   private String accessToken;

   @Column(nullable = false)
   private String accessType;

   /**
    * Instantiates a new login.
    */
   public LoginEntity()
   {
   	super();
   }
   
   
	/**
	 * Instantiates a new login.
	 *
	 * @param userToken the user token
	 * @param accessToken the access token
	 * @param accessType the access type
	 */
	public LoginEntity(String userToken, String accessToken, String accessType)
	{
		super();
		this.userToken = userToken;
		this.accessToken = accessToken;
		this.accessType = accessType;
	}



	/**
	 * @return the name
	 */
	public String getAccessToken()
	{
		return accessToken;
	}

	/**
	 * @param name the name to set
	 */
	public void setAccessToken(String name)
	{
		this.accessToken = name;
	}

	/**
	 * @return the userToken
	 */
	public String getUserToken()
	{
		return userToken;
	}

	/**
	 * @param userToken the userToken to set
	 */
	public void setUserToken(String userToken)
	{
		this.userToken = userToken;
	}

	/**
	 * @return the accessType
	 */
	public String getAccessType()
	{
		return accessType;
	}

	/**
	 * @param accessType the accessType to set
	 */
	public void setAccessType(String accessType)
	{
		this.accessType = accessType;
	}
   
}
