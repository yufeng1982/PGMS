package com.photo.bas.core.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.measure.unit.Unit;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * 
 * for the JScience Unit
 */
@SuppressWarnings("rawtypes")
public class UnitUserType implements UserType {
	private static final int[] SqlTypes = new int[] { Types.VARCHAR };

	public UnitUserType() {
		super();
	}

	public Object assemble(Serializable state, Object owner)
			throws HibernateException {
		return state;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y) || (x != null && y != null && (x.equals(y)));
	}

	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		String id = rs.getString(names[0]);
		if (Strings.isEmpty(id))
			return null;
		return Unit.valueOf(id);
	}

	
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if (value != null) {
			st.setString(index, ((Unit) value).toString());
		} else {
			st.setNull(index, Types.VARCHAR);
		}
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	public Class returnedClass() {
		return Unit.class;
	}

	public int[] sqlTypes() {
		return SqlTypes;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		
		String id = rs.getString(names[0]);
		if (Strings.isEmpty(id))
			return null;
		return Unit.valueOf(id);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		
		if (value != null) {
			st.setString(index, ((Unit) value).toString());
		} else {
			st.setNull(index, Types.VARCHAR);
		}
		
	}
}
