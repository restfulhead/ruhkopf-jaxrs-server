package name.ruhkopf.jaxrs.server.util;

import org.apache.commons.lang3.StringUtils;


/**
 * A simple class to check function arguments.
 * 
 * @author Patrick Ruhkopf
 */
public class Precondition
{
	public static void checkNotBlank(String str)
	{
		if (StringUtils.isBlank(str))
		{
			throw new IllegalArgumentException();
		}
	}

	public static void checkNotBlank(String str, String errorMessage)
	{
		if (StringUtils.isBlank(str))
		{
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void checkNotBlank(String str, String errorMessage, Object... errorMessageArgs)
	{
		if (StringUtils.isBlank(str))
		{
			throw new IllegalArgumentException(format(errorMessage, errorMessageArgs));
		}
	}

	static String format(String template, Object... args)
	{
		template = String.valueOf(template); // null -> "null"

		// start substituting the arguments into the '{}' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
		int templateStart = 0;
		int i = 0;
		while (i < args.length)
		{
			int placeholderStart = template.indexOf("{}", templateStart);
			if (placeholderStart == -1)
			{
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		// if we run out of placeholders, append the extra args in square braces
		if (i < args.length)
		{
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length)
			{
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}


}
