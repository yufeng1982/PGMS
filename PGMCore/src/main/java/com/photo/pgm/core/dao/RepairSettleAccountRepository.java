/**
 * 
 */
package com.photo.pgm.core.dao;

import org.springframework.data.jpa.repository.Query;

import com.photo.bas.core.dao.entity.AbsCodeNameEntityRepository;
import com.photo.pgm.core.model.RepairSettleAccount;

/**
 * @author FengYu
 *
 */
public interface RepairSettleAccountRepository extends AbsCodeNameEntityRepository<RepairSettleAccount> {

	@Query(value = "select sum(rsa.settle_account) from project.repair_settle_account rsa"
			+ " left join project.repair_order ro on rsa.repair_order = ro.id"
			+ " where rsa.corporation = ?1 and rsa.active='T' and ro.repair_type <> 'Small' "
			+ " and rsa.repair_settle_account_status ='Closed'" , nativeQuery = true)
	public Double findTotalSettleAccount4(String corporation);
}
