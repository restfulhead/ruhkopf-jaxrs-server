package name.ruhkopf.jaxrs.server.service;

public class LoginAlreadyExistsException extends BusinessException
{
	private static final long serialVersionUID = 2376902803650612815L;

	public static final String CODE = "2000";
	public static final String MSG = "The login already exists";

	public LoginAlreadyExistsException(String userToken)
	{
		super(CODE, MSG, userToken);
	}

}
