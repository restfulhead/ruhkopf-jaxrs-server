package name.ruhkopf.jaxrs.server.service;



public interface EncryptionService
{

	/**
	 * Returns a salted PBKDF2 hash of the password.
	 * 
	 * @param str the string to hash
	 * @return a salted PBKDF2 hash of the password
	 */
	public abstract String createHash(String str);

	/**
	 * Returns a salted PBKDF2 hash of the string.
	 * 
	 * @param str the string to hash
	 * @return a salted PBKDF2 hash of the password
	 */
	public abstract String createHash(char[] str);

	/**
	 * Validates the string and hash.
	 * 
	 * @param str the string to check
	 * @param hashWithSalt the hash of the string
	 * @return true if the hash and string are valid, false if not
	 */
	public abstract boolean validate(String str, String hashWithSalt);

	/**
	 * Validates the string and hash.
	 * 
	 * @param str the string to check
	 * @param hashWithSalt the hash of the string
	 * @return true if the hash and string are valid, false if not
	 */
	public abstract boolean validate(char[] str, String hashWithSalt);

}
