package com.dianping.cat.report.page.storage;

import org.unidal.web.mvc.ActionContext;
import org.unidal.web.mvc.payload.annotation.FieldMeta;

import com.dianping.cat.helper.TimeHelper;
import com.dianping.cat.report.ReportPage;
import com.dianping.cat.report.page.AbstractReportPayload;

public class Payload extends AbstractReportPayload<Action> {

	private ReportPage m_page;

	@FieldMeta("op")
	private Action m_action;

	@FieldMeta("operations")
	private String m_operations;

	@FieldMeta("project")
	private String m_project;

	@FieldMeta("sort")
	private String m_sort = "domain";

	@FieldMeta("minute")
	private String m_minute;

	@FieldMeta("refresh")
	private boolean m_refresh = false;

	@FieldMeta("fullScreen")
	private boolean m_fullScreen = false;

	@FieldMeta("frequency")
	private int m_frequency = 10;

	public Payload() {
		super(ReportPage.STORAGE);
	}

	@Override
	public Action getAction() {
		return m_action;
	}

	public long getCurrentTimeMillis() {
		return System.currentTimeMillis() - TimeHelper.ONE_MINUTE * 1;
	}

	@Override
	public long getCurrentDate() {
		long timestamp = getCurrentTimeMillis();

		return timestamp - timestamp % TimeHelper.ONE_HOUR;
	}

	@Override
	public long getDate() {
		long current = getCurrentDate();
		long extra = m_step * TimeHelper.ONE_HOUR;

		if (m_date <= 0) {
			return current + extra;
		} else {
			long result = m_date + extra;

			if (result > current) {
				return current;
			}
			return result;
		}
	}

	public int getFrequency() {
		return m_frequency;
	}

	public String getMinute() {
		return m_minute;
	}

	public String getOperations() {
		return m_operations;
	}

	@Override
	public ReportPage getPage() {
		return m_page;
	}

	public String getProject() {
		return m_project;
	}

	public String getSort() {
		return m_sort;
	}

	public boolean isFullScreen() {
		return m_fullScreen;
	}

	public boolean isRefresh() {
		return m_refresh;
	}

	public void setAction(String action) {
		m_action = Action.getByName(action, Action.HOURLY_DATABASE);
	}

	public void setFrequency(int frequency) {
		m_frequency = frequency;
	}

	public void setFullScreen(boolean fullScreen) {
		m_fullScreen = fullScreen;
	}

	public void setMinute(String minute) {
		m_minute = minute;
	}

	public void setOperations(String operations) {
		m_operations = operations;
	}

	@Override
	public void setPage(String page) {
		m_page = ReportPage.getByName(page, ReportPage.STORAGE);
	}

	public void setProject(String project) {
		m_project = project;
	}

	public void setRefresh(boolean refresh) {
		m_refresh = refresh;
	}

	public void setSort(String sort) {
		m_sort = sort;
	}

	@Override
	public void validate(ActionContext<?> ctx) {
		if (m_action == null) {
			m_action = Action.HOURLY_DATABASE;
		}
	}
}
