package model;

public class ExecutionConfiguration {
	
	boolean saveCSV; 
	boolean saveLUT; 
	boolean showPreview;
	boolean autoCalcAggregationOder;
	boolean generateLinearLut;
	boolean addTimestamp;
	String inputCsvPath;
	int aggregationOrder;
	double deadZoneEnhancement;
	int peakReduction;
	
	public ExecutionConfiguration() {
		this.saveCSV = false;
		this.saveLUT = false;
		this.showPreview = false;
		this.autoCalcAggregationOder = false;
		this.generateLinearLut = false;
		this.addTimestamp = true;
	};

	public boolean isSaveCSV() {
		return saveCSV;
	}

	public void setSaveCSV(boolean saveCSV) {
		this.saveCSV = saveCSV;
	}

	public boolean isSaveLUT() {
		return saveLUT;
	}

	public void setSaveLUT(boolean saveLUT) {
		this.saveLUT = saveLUT;
	}

	public boolean isShowPreview() {
		return showPreview;
	}

	public void setShowPreview(boolean showPreview) {
		this.showPreview = showPreview;
	}
	
	public boolean isAutoCalcAggregationOder() {
		return autoCalcAggregationOder;
	}

	public void setAutoCalcAggregationOder(boolean autoAggregationOder) {
		this.autoCalcAggregationOder = autoAggregationOder;
	}

	public String getInputCsvPath() {
		return inputCsvPath;
	}

	public void setInputCsvPath(String inputCsvPath) {
		this.inputCsvPath = inputCsvPath;
	}

	public int getAggregationOrder() {
		return aggregationOrder;
	}

	public void setAggregationOrder(int aggregationValue) {
		this.aggregationOrder = aggregationValue;
	}

	public double getDeadZoneEnhancement() {
		return deadZoneEnhancement;
	}

	public void setDeadZoneEnhancement(double deadZoneEnhancement) {
		this.deadZoneEnhancement = deadZoneEnhancement;
	}

	public boolean isGenerateLinearLut() {
		return generateLinearLut;
	}

	public void setGenerateLinearLut(boolean deadZoneCorrectionOnly) {
		this.generateLinearLut = deadZoneCorrectionOnly;
	}

	public boolean isAddTimestamp() {
		return addTimestamp;
	}

	public void setAddTimestamp(boolean addTimestamp) {
		this.addTimestamp = addTimestamp;
	}

	public int getPeakReduction() {
		return peakReduction;
	}

	public void setPeakReduction(int peakReduction) {
		this.peakReduction = peakReduction;
	}

}
