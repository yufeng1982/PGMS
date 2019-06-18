package com.photo.bas.core.vo.ui;

import java.util.Date;

import com.photo.bas.core.utils.PageInfo;

@SuppressWarnings("rawtypes")
public class DashboardQueryInfo extends PageInfo {
	private Date fiscalDate;

	public void setFiscalDate(Date fiscalDate) {
		this.fiscalDate = fiscalDate;
	}

	public Date getFiscalDate() {
		return fiscalDate;
	}

}
