/**
 * 
 */
package com.photo.bas.core.service.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.predicate.InPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.photo.bas.core.dao.entity.AbsRepository;
import com.photo.bas.core.model.entity.IDelete;
import com.photo.bas.core.model.entity.IEntity;
import com.photo.bas.core.utils.Collections3;
import com.photo.bas.core.utils.PageInfo;
import com.photo.bas.core.utils.PropertyFilter;
import com.photo.bas.core.utils.PropertyFilter.MatchType;
import com.photo.bas.core.utils.ProxyTargetConverterUtils;
import com.photo.bas.core.utils.Strings;
import com.photo.bas.core.utils.ThreadLocalUtils;

/**
 * @author FengYu
 *
 */
public abstract class AbsService<T extends IEntity, P extends PageInfo<T>> {

//	@Autowired 
//	private ConversionService conversionService;
//	private static final ConversionService conversionService = new DefaultConversionService();
	
	protected abstract AbsRepository<T> getRepository();
	
	/**
	 * TRUE - company regardless
	 * @return
	 */
	public abstract boolean isCommonAccess();

	public T get(String id) {
		return getRepository().findOne(id);
	}

	public T save(T entity) {
		return getRepository().save(entity);
	}
	
	public Iterable<T> save(Iterable<T> entities) {
		return getRepository().save(entities);
	}
	
	public void delete(T entity) {
		Assert.notNull(entity);
		if(entity instanceof IDelete && !((IDelete) entity).isRealDelete()){
			((IDelete) entity).setToInactive();
			getRepository().save(entity);
		}else{
			getRepository().delete(entity);
		}
	}

	public T findOne(PropertyFilter... filters) {
		return getRepository().findOne(this.bySearchFilter(filters));
	}

//	TODO public abstract void flushAndClearSession();

	
	
	public Iterable<T> getByIds(Iterable<String> ids) {
		return getRepository().findAll(ids);
	}
	
	public Page<T> search(P page) {
		return getRepository().findAll(this.byPage(page), page);
	}

	public Iterable<T> search(PropertyFilter... filters) {
		return getRepository().findAll(this.bySearchFilter(filters));
	}
	
	public List<T> search(List<PropertyFilter> pfList) {
		return getRepository().findAll(this.bySearchFilter(pfList));
	}
	
//	public abstract Page<T> getAll(Page<T> page);
//	
//	public List<T> getAll(String[] orderByArray, String[] orderArray);
//	public List<T> getAllActiveByCorporation(String corporation, String[] orderByArray, String[] orderArray);
//	
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final String id) {
		
		return isPropertiesUnique(new String[]{propertyName}, new Object[]{newValue}, id);
	}
	public boolean isPropertiesUnique(final String[] propertyNames, final Object[] values, final String id) {
		Assert.notNull(propertyNames);
		Assert.notNull(values);
		int size = propertyNames.length;
		Assert.isTrue(size == values.length);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		for (int i = 0; i < size; i++) {
			Object value = ProxyTargetConverterUtils.getProxyImplementationObject(values[i]);
			MatchType mt = value instanceof String ? MatchType.CASEEQ : MatchType.EQ;
			filters.add(new PropertyFilter(propertyNames[i], value, mt));
		}
		if(id != null) filters.add(new PropertyFilter("id", id, MatchType.NEQ));
		
		Long totalQty = getRepository().count(this.bySearchFilter(filters));
		
		if(totalQty == null || totalQty == 0) {
			return true;
		}
		return false;
	}


	public Page<T> search(P p, String queryStr, String...propertyNames) {
		Assert.notNull(propertyNames);
		int size = propertyNames.length;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		for (int i = 0; i < size; i++) {
			filters.add(new PropertyFilter(propertyNames[i], queryStr, MatchType.LIKE));
		}
		return search(p, filters.toArray(new PropertyFilter[filters.size()]));
	}
	
	public Page<T> search(P p, final PropertyFilter... filters) {
		return getRepository().findAll(this.bySearchFilter(filters), p);
	}
	
	public Iterable<T> search(Sort sort, String queryStr, String...propertyNames) {
		Assert.notNull(propertyNames);
		int size = propertyNames.length;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		for (int i = 0; i < size; i++) {
			filters.add(new PropertyFilter(propertyNames[i], queryStr, MatchType.LIKE));
		}
		return search(sort, filters.toArray(new PropertyFilter[filters.size()]));
	}

	public Iterable<T> search(Sort sort, final PropertyFilter... filters) {
		return getRepository().findAll(this.bySearchFilter(filters), sort);
	}
	
	protected Specification<T> byPage(P page) {
		if(!this.isCommonAccess() && page.getSf_EQ_corporation() == null) {
			page.setSf_EQ_corporation(ThreadLocalUtils.getCurrentCorporation());
		}
		return bySearchFilter(page.toSearchFilters());
	}
	
	protected Specification<T> bySearchFilter(PropertyFilter...filters) {
		return bySearchFilter(Lists.newArrayList(filters));
	}
	
	protected Specification<T> bySearchFilter(final Collection<PropertyFilter> filters) {
		return new Specification<T>() {
			
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (Collections3.isNotEmpty(filters)) {

					List<Predicate> predicates = Lists.newArrayList();
					for (PropertyFilter filter : filters) {
						if(filter == null) continue;
						
						Predicate predicate = buildPredicate(root, builder, filter);
						
						if(predicate != null) predicates.add(predicate);
					}
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
		
	}
	
	protected Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, PropertyFilter pf) {
		if(pf.isMultiPropertyName()) {
			List<Predicate> predicates = new ArrayList<>();
			String propertyName = pf.getPropertyName();
			String[] pns = StringUtils.split(propertyName, ",");
			for (String pn : pns) {
				if(Strings.isEmpty(pn)) continue;
				
				Predicate p = buildPredicate(root, builder, pn, pf.getValue(), pf.getMatchType());
				if(p != null) predicates.add(p);
			}
			if(predicates.isEmpty()) return null;
			
			return builder.or(predicates.toArray(new Predicate[predicates.size()]));
		} else {
			return buildPredicate(root, builder, pf.getPropertyName(), pf.getValue(), pf.getMatchType());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Predicate buildPredicate(Root<T> root, CriteriaBuilder builder, String propertyName, Object value, MatchType matchType) {
		Assert.hasText(propertyName);
		
		Predicate predicate = null;
		
		String[] names = StringUtils.split(propertyName, ".");
		Path expression = root.get(names[0]);
		for (int i = 1; i < names.length; i++) {
			expression = expression.get(names[i]);
		}
		if (MatchType.NNULL.equals(matchType)) {
			return builder.isNotNull(expression);
		} else if (MatchType.NULL.equals(matchType)) {
			return builder.isNull(expression);
		}
		
		if(value == null) return null;

		// convert value
//		Class attributeClass = expression.getJavaType();
//		if (!attributeClass.equals(String.class) && value instanceof String && conversionService.canConvert(String.class, attributeClass)) {
//			value = conversionService.convert(value, attributeClass);
//		}
		
		switch (matchType) {
		case EQ:
			predicate = builder.equal(expression, value);
			break;
		case LIKE:
			predicate = builder.like(expression, "%" + value + "%");
			break;
		case GT:
			predicate = builder.greaterThan(expression, (Comparable) value);
			break;
		case LT:
			predicate = builder.lessThan(expression, (Comparable) value);
			break;
		case GTE:
			predicate = builder.greaterThanOrEqualTo(expression, (Comparable) value);
			break;
		case LTE:
			predicate = builder.lessThanOrEqualTo(expression, (Comparable) value);
			break;
		case CASEEQ:
			predicate = builder.equal(builder.upper(expression), ((String) value).toUpperCase());
			break;
		case NEQ:
			predicate = builder.notEqual(expression, value);
			break;
		case IN:
			predicate = new InPredicate((CriteriaBuilderImpl)builder, expression, value);
			break;
		case NIN:
			predicate = new InPredicate((CriteriaBuilderImpl)builder, expression, value).not();
			break;
		case NLIKE:
			predicate = builder.notLike(expression, "%" + value + "%");
			break;
		case END:
			predicate = builder.like(expression, "%" + value);
			break;
		case START:
			predicate = builder.like(expression, value + "%");
			break;
		case NEND:
			predicate = builder.notLike(expression, "%" + value);
			break;
		case NSTART:
			predicate = builder.notLike(expression, value + "%");
			break;
		default:
			break;
		}
		return predicate;
	}
	
}
