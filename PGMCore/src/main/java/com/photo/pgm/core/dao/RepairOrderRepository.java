/**
 * 
 */
package com.photo.pgm.core.dao;

import org.springframework.data.jpa.repository.Query;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.pgm.core.model.RepairOrder;

/**
 * @author FengYu
 *
 */
public interface RepairOrderRepository extends AbsCodeNameEntityRepository<RepairOrder> {
	@Query(value = "select max(seq) from project.repair_order where corporation = ?1 and active='T'", nativeQuery = true)
	public Integer findMaxSeq(String corporation);
	
	@Query(value = "select sum(actual_amount) from project.repair_order where corporation = ?1 and active='T' and repair_type = 'Small' and repair_status ='Closed'" , nativeQuery = true)
	public Double findActualAmount4Small(String corporation);
}
