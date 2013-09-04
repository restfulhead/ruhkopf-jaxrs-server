package name.ruhkopf.jaxrs.server.service;

import java.util.Arrays;


public class BusinessException extends RuntimeException
{
	private static final long serialVersionUID = -6294228016281233108L;

	/** The id (error code) of this exception. */
	private final String id;

	/** Optional arguments. */
	private final String[] args;

	/**
	 * Instantiates a new business exception.
	 * 
	 * @param id the id
	 * @param message the message
	 */
	public BusinessException(String id, String message, Object... arguments)
	{
		super(message);
		this.id = id;

		if (arguments == null)
		{
			args = null;
		}
		else
		{
			this.args = new String[arguments.length];
			for (int i = 0; i < arguments.length; i++)
			{
				args[i] = String.valueOf(arguments[i]);
			}
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage()
	{
		return "[" + getId() + "] " + super.getMessage();
	}



	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BusinessException [id=");
		builder.append(id);
		builder.append(", args=");
		builder.append(Arrays.toString(args));
		builder.append(", getId()=");
		builder.append(getId());
		builder.append("]");
		return builder.toString();
	}

}
