package name.ruhkopf.jaxrs.server.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PreconditionTest
{
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shouldPass()
	{
		Precondition.checkNotBlank("some string");
		Precondition.checkNotBlank("some string", "some error message");
		Precondition.checkNotBlank("some string", "some error message with {}", "some error arg");
	}
	
	@Test
	public void shouldThrowExceptionWhenEmpty()
	{
		expectedException.expect(IllegalArgumentException.class);	
		Precondition.checkNotBlank("");
	}
	
	@Test
	public void shouldThrowExceptionWhenNull()
	{
		expectedException.expect(IllegalArgumentException.class);	
		Precondition.checkNotBlank(null);
	}
	
	@Test
	public void shouldThrowExceptionWithMessage()
	{
		expectedException.expect(IllegalArgumentException.class);	
		expectedException.expectMessage("some error message");
		Precondition.checkNotBlank(null, "some error message");
	}
	
	@Test
	public void shouldThrowExceptionWithMessageAndArg()
	{
		expectedException.expect(IllegalArgumentException.class);	
		expectedException.expectMessage("some error message with some arg and 10");
		Precondition.checkNotBlank(null, "some error message with {} and {}", "some arg", 10);
	}
	
}
