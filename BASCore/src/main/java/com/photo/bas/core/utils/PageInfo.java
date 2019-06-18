/**
 * Copyright (c) 2005-20010 springside.org.cn
 * We change/add some extra methods in order to fulfill our project.
 */
package com.photo.bas.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.model.entity.IEnum;
import com.photo.bas.core.model.security.Corporation;
import com.photo.bas.core.utils.PropertyFilter.MatchType;

/**
 * @author FengYu
 *
 */
public class PageInfo<T> implements Pageable {
	private static Logger logger = LoggerFactory.getLogger(PageInfo.class);
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final String LENGTH_ASC = "length_asc";
	public static final String LENGTH_DESC = "length_desc";

	public static final int MIN_PAGESIZE = 2;
	public static final int MAX_PAGESIZE = 5000;
	public static final int DEFAULT_PAGESIZE = 50;

	protected int page = 1;
	protected int pageSize = MIN_PAGESIZE;
	protected String orderBy = null;
	protected String order = ASC;
	protected boolean autoCount = true;

	protected List<T> result = null;
	protected List<Object[]> objResult = null;
	protected long totalCount = -1;
	public int beginIndex;

	protected Corporation sf_EQ_corporation;
	private Boolean sf_EQ_active = Boolean.TRUE;
	private String sf_EQ_id;
	private String sf_LIKE_query;
	private String sortBy;
	private Sort sort;
	private String sumFields;
	private static Map<String, Object> sumFieldsMap = new HashMap<String, Object>();
	
	private List<Order> extraOrders = new ArrayList<Order>();
	
	public PageInfo() {
		this(DEFAULT_PAGESIZE, 0, true);
		this.setSf_EQ_corporation(ThreadLocalUtils.getCurrentCorporation());
		@SuppressWarnings("unchecked")
		Class<T> z = EntityReflectionUtils.getSuperClassGenricType(getClass());
		Object clazz = null;
		try {
			Constructor<?> c = z.getDeclaredConstructor();
			c.setAccessible(true);
			clazz = c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(clazz != null) {
			if(EntityReflectionUtils.getDeclaredField(clazz, "sequence") != null ){
				setOrder(ASC);
				setOrderBy("sequence");
			} else if(EntityReflectionUtils.getDeclaredField(clazz, "code") != null && EntityReflectionUtils.getDeclaredField(clazz, "name") != null) {
				setOrder(ASC+","+ASC);
				setOrderBy("code,name");
			} else if(EntityReflectionUtils.getDeclaredField(clazz, "code") != null ) {
				setOrder(ASC);
				setOrderBy("code");
			} else if(EntityReflectionUtils.getDeclaredField(clazz, "name") != null) {
				setOrder(ASC);
				setOrderBy("name");
			} else if(EntityReflectionUtils.getDeclaredField(clazz, "creationDate") != null ) {
				setOrder(DESC);
				setOrderBy("creationDate");
			}
		}
	}

	public PageInfo(final int pageSize, final int beginIndex) {
		this(pageSize, beginIndex, true);
	}
	public PageInfo(String orderBy, String order) {
		this();
		setOrder(order);
		setOrderBy(orderBy);
	}
	public PageInfo(final int pageSize, final int beginIndex, final boolean autoCount) {
		setPageConfig(pageSize,  beginIndex, autoCount);
	}

	public int getBeginIndex() {
		return beginIndex;
	}
	
	public int getFirst() {
		return ((page - 1) * pageSize);
	}

	public String getInverseOrder() {
		String[] orders = StringUtils.split(order, ',');

		for (int i = 0; i < orders.length; i++) {
			if (DESC.equals(orders[i])) {
				orders[i] = ASC;
			} else {
				orders[i] = DESC;
			}
		}
		return StringUtils.join(orders);
	}

	public int getNextPage() {
		if (isHasNext())
			return page + 1;
		else
			return page;
	}

	public List<Object[]> getObjResult() {
		if (objResult == null)
			return Collections.emptyList();
		return objResult;
	}
	
	public String getOrder() {
		return order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public int getPage() {
		return page;
	}

	public String getPageRequest() {
		return getPage() + "|" + StringUtils.defaultString(getOrderBy()) + "|" + getOrder();
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPrePage() {
		if (isHasPre())
			return page - 1;
		else
			return page;
	}

	public List<T> getResult() {
		if (result == null)
			return Collections.emptyList();
		return result;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public long getTotalPages() {
		if (totalCount < 0)
			return -1;

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	public boolean isAutoCount() {
		return autoCount;
	}

	public boolean isHasNext() {
		return (page + 1 <= getTotalPages());
	}

	public boolean isHasPre() {
		return (page - 1 >= 1);
	}

	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy) || CollectionUtils.isNotEmpty(extraOrders);
	}

	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}
	
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
		setPageConfig(pageSize,  beginIndex, autoCount);
	}

	public void setObjResult(final List<Object[]> objResult) {
		this.objResult = objResult;
	}

	protected void setOrder(final String order) {
		String[] orders = StringUtils.split(order, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equalsIgnoreCase(DESC, orderStr) && !StringUtils.equalsIgnoreCase(ASC, orderStr)
					 && !StringUtils.equalsIgnoreCase(LENGTH_ASC, orderStr)
					 && !StringUtils.equalsIgnoreCase(LENGTH_DESC, orderStr)) {
				
				throw new IllegalArgumentException("order by " + orderStr + " is in invalid.");
			}
		}

		this.order = order.toLowerCase();
	}

	protected void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	private void setPageConfig(final int pageSize, final int beginIndex, final boolean autoCount){
		setPageSize(pageSize);
		setPage(getBeginIndex() / pageSize + 1);
		this.autoCount = autoCount;
	}

	public void setPage(int page) {
		this.page = page;

		if (page < 1) {
			this.page = 1;
		}
	}

	/**
	 * page|orderBy|order.
	 */
	public void setPageRequest(final String pageRequest) {

		if (StringUtils.isBlank(pageRequest))
			return;

		String[] params = StringUtils.splitPreserveAllTokens(pageRequest, PropertyFilter.OR_SEPARATOR);

		if (StringUtils.isNumeric(params[0])) {
			setPage(Integer.valueOf(params[0]));
		}

		if (StringUtils.isNotBlank(params[1])) {
			setOrderBy(params[1]);
		}

		if (StringUtils.isNotBlank(params[2])) {
			setOrder(params[2]);
		}
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < MIN_PAGESIZE) {
			this.pageSize = MIN_PAGESIZE;
		}
		if (pageSize > MAX_PAGESIZE) {
			this.pageSize = MAX_PAGESIZE;
		}
	}

	public void setPageSizeDirect(final int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setResult(final List<T> result) {
		this.result = result;
	}

	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	public String getSf_LIKE_query() {
		return sf_LIKE_query;
	}

	public void setSf_LIKE_query(String sf_LIKE_query) {
		this.sf_LIKE_query = sf_LIKE_query;
	}

	public Corporation getSf_EQ_corporation() {
		return sf_EQ_corporation;
	}

	public void setSf_EQ_corporation(Corporation sf_EQ_corporation) {
		this.sf_EQ_corporation = sf_EQ_corporation;
	}

	public String getSf_EQ_id() {
		return sf_EQ_id;
	}

	public void setSf_EQ_id(String sf_EQ_id) {
		this.sf_EQ_id = sf_EQ_id;
	}

	public void setSortBy(String sortBy) {
		if(!Strings.isEmpty(sortBy)) {
			try {
				StringBuffer st = new StringBuffer(), sb = new StringBuffer();
				
				JSONArray a = new JSONArray(sortBy);
				int len = a.length();
				for (int i = 0; i < len; i++) {
					JSONObject o = a.getJSONObject(i);
					if(i != (len - 1)) {
						sb.append(o.get("property")).append(",");
						st.append(o.get("direction")).append(",");
					} else {
						sb.append(o.get("property"));
						st.append(o.get("direction"));
					}
				}
				if(len > 0) {
					setOrder(st.toString());
					setOrderBy(sb.toString());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		this.sortBy = sortBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getSumFields() {
		return sumFields;
	}
	
	public String[] getSumFieldArray() {
		if (Strings.isEmpty(sumFields)) return null;
		
		return sumFields.split(",");
	}
	
	public void setSumFields(String sumFields) {
		this.sumFields = sumFields;
	}

	public Map<String, Object> getSumFieldsMap() {
		return sumFieldsMap;
	}
	
	public List<Order> getExtraOrders(){
		return extraOrders;
	}

	public void addExtraOrders(Order extraOrder) {
		extraOrders.add(extraOrder);
	}

	@Override
	public int getPageNumber() {
		return page;
	}

	@Override
	public int getOffset() {
		return (page - 1) * pageSize;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		Class<?> subC = this.getClass();
		while(subC != null && !subC.equals(PageInfo.class)) {
			Field[] subFields = subC.getDeclaredFields();
			for (Field sf : subFields) {
				String name = sf.getName();
				Object value = EntityReflectionUtils.getFieldValue(this, name);
				if(value == null) continue;
				
				if(value instanceof IEntity) {
					jo.put(name, ((IEntity) value).getId());
					jo.put(name + "Text", ((IEntity) value).getDisplayString());
				} else if(value instanceof IEnum) {
					jo.put(name, ((IEnum) value).getName());
				} else if(value instanceof Date) {
					jo.put(name, FormatUtils.formatDate((Date) value));
				} else if(value instanceof Collection) {
					continue ;
				} else {
					jo.put(name, value);
				}
				
			}
			subC = subC.getSuperclass();
		}
		if(subC != null && subC.equals(PageInfo.class)) {
			jo.put("sf_EQ_id", this.getSf_EQ_id());
			jo.put("sf_LIKE_query", this.getSf_LIKE_query());
			jo.put("sf_EQ_corporation", FormatUtils.idString(getSf_EQ_corporation()));
			jo.put("sf_EQ_corporationText", FormatUtils.displayString(getSf_EQ_corporation()));
		}
		return jo;
	}
	
	public Collection<PropertyFilter> toSearchFilters() {
		List<PropertyFilter> filters = new ArrayList<>();
		Collection<Field> fields = getSearchFields();
		for (Field field : fields) {
			String name = field.getName();
			Object value = EntityReflectionUtils.getFieldValue(this, name);
			
			if(value == null) continue;
			
			Collection<PropertyFilter> newFilters = parseSearchFilter(name, value);
			if(newFilters != null) {
				filters.addAll(newFilters);
			}
		}
		return filters;
	}
	
	protected Collection<PropertyFilter> parseSearchFilter(String name, Object value) {
		String[] parameters = name.split("_");
		if(parameters == null || parameters.length != 3) return null;
		try {
			MatchType matchType = MatchType.valueOf(parameters[1]);
			return parseSearchFilter(parameters[2], value, matchType);
		} catch (Exception e) {
			logger.error(name + " is not a valid search filter name [" + e.getMessage() + "]");
			return null;
		}
	}
	
	protected Collection<PropertyFilter> parseSearchFilter(String name, Object value, MatchType matchType) {
		List<PropertyFilter> filters = new ArrayList<>();
		if("query".equals(name)) {
			filters.add(new PropertyFilter(getQueryFieldNames(), value, matchType));
		} else if("dateType".equals(name)) {
			filters.add(new PropertyFilter((String) value, EntityReflectionUtils.getFieldValue(this, "dateStart"), MatchType.GTE));
			filters.add(new PropertyFilter((String) value, EntityReflectionUtils.getFieldValue(this, "dateEnd"), MatchType.LTE));
		} else {
			filters.add(new PropertyFilter(name, value, matchType));
		}
		return filters;
	}
	
	/**
	 * you can override this method to make different fields for the query purpose
	 * @return
	 */
	protected String[] getQueryFieldNames() {
		return new String[]{"code", "name"};
	}
	
	protected Collection<Field> getSearchFields() {
		List<Field> fields = new ArrayList<>();
		Class<?> subC = this.getClass();
		while(subC != null && !subC.equals(Object.class)) {
			Field[] subFields = subC.getDeclaredFields();
			for (Field sf : subFields) {
				String name = sf.getName();
				if(name.startsWith("sf_")) {
					fields.add(sf);
				}
			}
			subC = subC.getSuperclass();
		}
		return fields;
	}

	@Override
	public Pageable first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pageable next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSf_EQ_active() {
		return sf_EQ_active;
	}

	public void setSf_EQ_active(boolean sf_EQ_active) {
		this.sf_EQ_active = sf_EQ_active;
	}

}