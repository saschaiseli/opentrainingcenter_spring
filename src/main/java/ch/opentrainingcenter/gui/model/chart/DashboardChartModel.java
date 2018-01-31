package ch.opentrainingcenter.gui.model.chart;

public class DashboardChartModel {

	private final DashboardChartDataModel xyData;
	private final String legend;
	private final String title;

	public DashboardChartModel(final DashboardChartDataModel xyData, final String legend, final String title) {
		this.xyData = xyData;
		this.legend = legend;
		this.title = title;
	}

	public DashboardChartDataModel getXyData() {
		return xyData;
	}

	public String getLegend() {
		return legend;
	}

	public String getTitle() {
		return title;
	}

}
