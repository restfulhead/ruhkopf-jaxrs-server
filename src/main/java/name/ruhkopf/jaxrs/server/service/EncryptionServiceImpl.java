package name.ruhkopf.jaxrs.server.service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;

/**
 * Inspired by https://crackstation.net/hashing-security.htm
 * 
 * @author Patrick Ruhkopf
 */
@Component("encryptionService")
public class EncryptionServiceImpl implements EncryptionService
{
	protected static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	// The following constants may be changed without breaking existing hashes.
	public static final int SALT_BYTE_SIZE = 24;
	public static final int HASH_BYTE_SIZE = 24;
	public static final int PBKDF2_ITERATIONS = 1000;

	public static final int ITERATION_INDEX = 0;
	public static final int SALT_INDEX = 1;
	public static final int PBKDF2_INDEX = 2;

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.EncryptionService#createHash(java.lang.String)
	 */
	@Override
	public String createHash(String str)
	{
		return createHash(str.toCharArray());
	}

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.EncryptionService#createHash(char[])
	 */
	@Override
	public String createHash(char[] str) 
	{
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);

		// Hash the password
		byte[] hash = pbkdf2(str, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		// format iterations:salt:hash
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.EncryptionService#validate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(String str, String hashWithSalt) 
	{
		return validate(str.toCharArray(), hashWithSalt);
	}

	/* (non-Javadoc)
	 * @see name.ruhkopf.jaxrs.server.service.EncryptionService#validatePassword(char[], java.lang.String)
	 */
	@Override
	public boolean validate(char[] str, String hashWithSalt)
	{
		// Decode the hash into its parameters
		String[] params = hashWithSalt.split(":");
		int iterations = Integer.parseInt(params[ITERATION_INDEX]);
		byte[] salt = fromHex(params[SALT_INDEX]);
		byte[] hash = fromHex(params[PBKDF2_INDEX]);
		// Compute the hash of the provided password, using the same salt,
		// iteration count, and hash length
		byte[] testHash = pbkdf2(str, salt, iterations, hash.length);
		// Compare the hashes in constant time. The password is correct if
		// both hashes match.
		return slowEquals(hash, testHash);
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method
	 * is used so that password hashes cannot be extracted from an on-line
	 * system using a timing attack and then attacked off-line.
	 * 
	 * @param a the first byte array
	 * @param b the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	protected boolean slowEquals(byte[] a, byte[] b)
	{
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	/**
	 * Computes the PBKDF2 hash of a password.
	 * 
	 * @param password the password to hash.
	 * @param salt the salt
	 * @param iterations the iteration count (slowness factor)
	 * @param bytes the length of the hash to compute in bytes
	 * @return the PBDKF2 hash of the password
	 */
	protected byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) 
	{
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf;
		try
		{
			skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
			return skf.generateSecret(spec).getEncoded();
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e)
		{
			Throwables.propagate(e);
			return null; // NOPMD never happens
		}
	}

	/**
	 * Converts a string of hexadecimal characters into a byte array.
	 * 
	 * @param hex the hex string
	 * @return the hex string decoded into a byte array
	 */
	protected byte[] fromHex(String hex)
	{
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++)
		{
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	/**
	 * Converts a byte array into a hexadecimal string.
	 * 
	 * @param array the byte array to convert
	 * @return a length*2 character string encoding the byte array
	 */
	protected String toHex(byte[] array)
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
	}
}
