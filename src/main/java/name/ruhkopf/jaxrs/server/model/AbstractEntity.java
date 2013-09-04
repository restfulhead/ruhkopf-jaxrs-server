package name.ruhkopf.jaxrs.server.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@MappedSuperclass
public abstract class AbstractEntity
{
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof LoginEntity))
			return false;

		if (this.getId() == null)
		{
			return super.equals(obj);
		}

		LoginEntity rhs = (LoginEntity) obj;
		if (rhs.getId() == null)
		{
			return super.equals(obj);
		}

		return new EqualsBuilder().append(id, rhs.getId()).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if (this.getId() == null)
		{
			return super.hashCode();
		}
		else
		{
			return new HashCodeBuilder(17, 31).append(id).toHashCode();
		}
	}



}
