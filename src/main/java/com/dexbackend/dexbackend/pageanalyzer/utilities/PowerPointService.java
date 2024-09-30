package com.dexbackend.dexbackend.pageanalyzer.utilities;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.UnitType;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class PowerPointService {
	@Autowired
	private Environment enve;
	private String env = "extra/DEX_Report.pptx";
	private XMLSlideShow ppt; // Declare ppt object at the class level


	public void createPresentationMetered(String applicationName, String buildId, double value, JSONObject responseBody,
			JSONObject textResponseBody, JSONObject wcvResponseBody, String cased) throws IOException {

		if (ppt == null) {
			ppt = new XMLSlideShow(new FileInputStream(env)); // Initialize ppt object if not already initialized
		}

		switch (cased) {
		case "performance":

			// Performance Slide
			addPerformanceSlide(ppt, value, responseBody);
			addPerformanceSlide2(ppt, value, responseBody, wcvResponseBody);
			break;

		case "accessibility":
			// Accessibility Slide
			addAccessibilitySlide(ppt, value, responseBody, textResponseBody);
			break;

		case "seo":
			// SEO Slide
			addSEOSlide(ppt, value, responseBody);
			break;

		case "bp":
			// SEO Slide
			addBPSlide(ppt, value, responseBody);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + cased);
		}

		// Save the PowerPoint presentation
		try (FileOutputStream out = new FileOutputStream("pptreports/" + applicationName + "_" + buildId + ".pptx")) {
			ppt.write(out);
		}
	}

	private void addAccessibilitySlide(XMLSlideShow ppt, double value, JSONObject responseBody,
			JSONObject textResponseBody) throws IOException {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox textBox = slide.createTextBox();
		XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
		XSLFTextRun run = paragraph.addNewTextRun();
		run.setText("How inclusive are your digital assets to your consumer");
		run.setFontSize(28.0);
		run.setBold(true);
		run.setFontFamily(HSSFFont.FONT_ARIAL);
		textBox.setAnchor(new Rectangle(25, 15, 650, 50));

		// Data for accessibility pie chart
		DefaultPieDataset datasetSeverity = new DefaultPieDataset();
		JSONArray resultsArray = responseBody.getJSONObject("data").getJSONArray("result");

		for (int i = 0; i < resultsArray.length(); i++) {
			JSONObject result = resultsArray.getJSONObject(i);
			String severity = result.getJSONObject("metric").getString("impact"); // Fetch the 'impact' field
			double docCount = Double.parseDouble(result.getJSONArray("value").getString(1)); // Fetch the actual value
																								// from the 'value'
																								// array

			// Handle known severity levels
			switch (severity) {
			case "serious", "moderate", "minor", "critical" -> datasetSeverity.setValue(severity, docCount);
			default -> throw new IllegalArgumentException("Unexpected value: " + severity);
			}
		}

		// Create the Pie Chart for accessibility impact
		RingPlot plotSeverity = new RingPlot(datasetSeverity);
   		JFreeChart chartSeverity = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plotSeverity, true);
   		chartSeverity.setBackgroundPaint(
   				new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.WHITE));
   		chartSeverity.setTitle("Analyzer - Accessibility Impact");

   		TextTitle t1 = chartSeverity.getTitle();
   		t1.setVerticalAlignment(org.jfree.chart.ui.VerticalAlignment.CENTER);
   		t1.setFont(new java.awt.Font("Arial", Font.BOLD, 11));

   		plotSeverity.setInsets(new RectangleInsets(0, 5, 5, 5));
  		// plot.setInteriorGap(0.50);

   		plotSeverity.setLabelGap(0.1);
   		plotSeverity.setBackgroundPaint(null);
   		plotSeverity.setOutlineVisible(false);
   		plotSeverity.setLabelGenerator(null);
  		// plot.setSectionDepth(0.60);
   		plotSeverity.setSectionOutlinesVisible(false);
   		plotSeverity.setSimpleLabels(true);
   		plotSeverity.setShadowPaint(null);
   		plotSeverity.setOuterSeparatorExtension(0);
   		plotSeverity.setInnerSeparatorExtension(0);
   		plotSeverity.setLabelGenerator(
  				new StandardPieSectionLabelGenerator("{1}", new DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
  		// labeloffset
   		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.36, 0.36, 0.36, 0.36));
   		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.18, 0.18, 0.18, 0.18));
   		plotSeverity.setSectionDepth(0.60);

   		plotSeverity.setLabelBackgroundPaint(null);
   		plotSeverity.setLabelOutlinePaint(null);
   		plotSeverity.setSectionPaint("PASS", new Color(1, 178, 124));
   		plotSeverity.setSectionPaint("FAIL", new Color(252, 106, 89));
   		plotSeverity.setLabelShadowPaint(null);

  		java.awt.Font font1 = new java.awt.Font("", 0, 16);
  		plotSeverity.setLabelFont(font1);

  		chartSeverity.getLegend().setFrame(BlockBorder.NONE);
  		chartSeverity.getLegend().setPosition(RectangleEdge.BOTTOM);
  		chartSeverity.setBackgroundPaint(Color.white);
  		chartSeverity.setPadding(new RectangleInsets(4, 8, 2, 2));

  		LegendTitle legend1 = chartSeverity.getLegend();
  		legend1.setPosition(RectangleEdge.BOTTOM);

  		legend1.setBorder(0, 0, 0, 0);
  		String filename1 = "extra/do_nut.jpg";

  		ChartUtils.saveChartAsJPEG(new File(filename1), chartSeverity, 270, 175);

  		File image1 = new File(filename1);
  		byte[] photo1 = IOUtils.toByteArray(new FileInputStream(image1));
  		
  		

        // Add the chart image to the PowerPoint slide
        XSLFPictureData pd1 = ppt.addPicture(photo1, PictureData.PictureType.PNG);
        XSLFPictureShape pictureShape1 = slide.createPicture(pd1);
        pictureShape1.setAnchor(new Rectangle(350, 120, 300, 200));
		// Accessibility Metered Chart
		String loc = "extra/gauge.jpg";
		DefaultValueDataset data = new DefaultValueDataset(value);

		MeterPlot meterplot = new MeterPlot(data);
		meterplot.setRange(new Range(0, 100));
		meterplot.addInterval(
				new MeterInterval("normal", new Range(0, 60), new Color(255, 78, 66), new BasicStroke(7.0F), null));
		meterplot.addInterval(
				new MeterInterval("warning", new Range(60, 85), new Color(255, 164, 0), new BasicStroke(7.0F), null));
		meterplot.addInterval(new MeterInterval("critical", new Range(85, 100), new Color(12, 206, 107),
				new BasicStroke(7.0F), null));

		meterplot.setNeedlePaint(Color.black);
		meterplot.setDialBackgroundPaint(Color.white);
		meterplot.setOutlineStroke(new BasicStroke(10));
		meterplot.setDialOutlinePaint(Color.black);
		meterplot.setDialShape(DialShape.CHORD);
		meterplot.setMeterAngle(180);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font("Arial", 1, 14));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.BLACK);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));
		meterplot.setUnits("");

		JFreeChart chartMeter = new JFreeChart("Overall Accessibility Coverage", JFreeChart.DEFAULT_TITLE_FONT,
				meterplot, true);

		chartMeter.setBackgroundPaint(Color.white);
		chartMeter.setBorderPaint(Color.white);
		chartMeter.removeLegend();

		ChartUtils.saveChartAsJPEG(new File(loc), chartMeter, 300, 200);

		File imageMeter = new File(loc);
		byte[] photoMeter = IOUtils.toByteArray(new FileInputStream(imageMeter));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pdMeter = ppt.addPicture(photoMeter, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShapeMeter = slide.createPicture(pdMeter);
		pictureShapeMeter.setAnchor(new Rectangle(30, 120, 200, 120));

		// TextBox for additional content
	    DefaultPieDataset datasetTextBox = new DefaultPieDataset();
	    JSONArray resultArray = textResponseBody.getJSONObject("data").getJSONArray("result");

	    Map<String, Integer> impactToCountMap = new HashMap<>();

	    for (int i = 0; i < resultArray.length(); i++) {
	        JSONObject result = resultArray.getJSONObject(i);
	        String impact = result.getJSONObject("metric").getString("requestedUrl");
	        int docCount = Integer.parseInt(result.getJSONArray("value").getString(1));

	        impactToCountMap.put(impact, docCount);
	    }

	    // Initial position for the first text box
	    int xPosition = 30;
	    int yPosition = 330;
	    int count1 = 0;

	    // Add the key and their associated key_as_string and doc_count values to a text box
	    for (Map.Entry<String, Integer> entry : impactToCountMap.entrySet()) {
	        if (count1 >= 2) {
	            break; // Only include the first 2 entries
	        }
	        String impact = entry.getKey();
	        Integer docCount = entry.getValue();

	        // Create a new text box for each impact level
	        XSLFTextBox box = slide.createTextBox();
	        box.setFillColor(new Color(236, 236, 225));
	        box.setAnchor(new Rectangle(xPosition, yPosition, 250, 195));
	        XSLFTextParagraph paragraph1 = box.addNewTextParagraph();
	        XSLFTextRun run1 = paragraph1.addNewTextRun();
	        run1.setText("Impact Level: ");
	        run1.setFontSize(10.0);
	        run1.setFontFamily("Arial");
	        run1.setBold(true);

	        XSLFTextParagraph paragraph2 = box.addNewTextParagraph();
	        XSLFTextRun run2 = paragraph2.addNewTextRun();
	        run2.setText(impact);
	        run2.setFontSize(10.0);
	        run2.setFontFamily("Arial");

	        XSLFTextParagraph subParagraph = box.addNewTextParagraph();
	        XSLFTextRun subRun = subParagraph.addNewTextRun();
	        subRun.setText("Total Count: ");
	        subRun.setFontSize(10.0);
	        subRun.setFontFamily("Arial");
	        subRun.setBold(true);

	        XSLFTextParagraph subParagraph1 = box.addNewTextParagraph();
	        XSLFTextRun subRun1 = subParagraph1.addNewTextRun();
	        subRun1.setText("" + docCount);
	        subRun1.setFontSize(10.0);
	        subRun1.setFontFamily("Arial");

	        // Increment x position for the next text box
	        xPosition += 260;
	        count1++;
	    }
	}

 

	// Performance

	private void addPerformanceSlide(XMLSlideShow ppt, double value, JSONObject responseBody) throws IOException {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox textBox = slide.createTextBox();
		XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
		XSLFTextRun run = paragraph.addNewTextRun();
		run.setText(
				"High Performance Assets: Are your end-users getting the performance and user experience they deserve?");
		run.setFontSize(28.0);
		run.setBold(true);
		run.setFontFamily(HSSFFont.FONT_ARIAL);
		textBox.setAnchor(new Rectangle(25, 15, 650, 50));

		// Create Pie Chart from Prometheus Data
		DefaultPieDataset datasetSeverity = new DefaultPieDataset();
		JSONArray resultsArray = responseBody.getJSONObject("data").getJSONArray("result");

		for (int i = 0; i < resultsArray.length(); i++) {
		    JSONObject result = resultsArray.getJSONObject(i);
		    
		    // Check if the "metric" object contains the "mimeType" field
		    JSONObject metric = result.getJSONObject("metric");
		    String mimeType = metric.optString("mimeType", "Unknown"); // Fetch 'mimeType' or default to 'Unknown'

		    // Fetch the actual value from the 'value' array
		    double docCount = Double.parseDouble(result.getJSONArray("value").getString(1));

		    // Set the value in the dataset
		    datasetSeverity.setValue(mimeType, docCount);
		}

		RingPlot plotSeverity = new RingPlot(datasetSeverity);
		JFreeChart chartSeverity = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plotSeverity, true);
		chartSeverity.setBackgroundPaint(
				new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.WHITE));
		chartSeverity.setTitle("Analyzer Compare - MimeType Pie Chart");

		TextTitle title = chartSeverity.getTitle();
		title.setVerticalAlignment(org.jfree.chart.ui.VerticalAlignment.CENTER);
		title.setFont(new java.awt.Font("Arial", Font.BOLD, 11));

		plotSeverity.setInsets(new RectangleInsets(0, 5, 5, 5));
		plotSeverity.setLabelGap(0.1);
		plotSeverity.setBackgroundPaint(null);
		plotSeverity.setOutlineVisible(false);
		plotSeverity.setLabelGenerator(null);
		plotSeverity.setSectionOutlinesVisible(false);
		plotSeverity.setSimpleLabels(true);
		plotSeverity.setShadowPaint(null);
		plotSeverity.setOuterSeparatorExtension(0);
		plotSeverity.setInnerSeparatorExtension(0);
		plotSeverity.setLabelGenerator(
				new StandardPieSectionLabelGenerator("{1}", new DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.36, 0.36, 0.36, 0.36));
		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.18, 0.18, 0.18, 0.18));
		plotSeverity.setSectionDepth(0.60);

		plotSeverity.setLabelBackgroundPaint(null);
		plotSeverity.setLabelOutlinePaint(null);
		plotSeverity.setSectionPaint("PASS", new Color(1, 178, 124));
		plotSeverity.setSectionPaint("FAIL", new Color(252, 106, 89));
		plotSeverity.setLabelShadowPaint(null);

		java.awt.Font font1 = new java.awt.Font("", 0, 16);
		plotSeverity.setLabelFont(font1);

		chartSeverity.getLegend().setFrame(BlockBorder.NONE);
		chartSeverity.getLegend().setPosition(RectangleEdge.BOTTOM);
		chartSeverity.setBackgroundPaint(Color.white);
		chartSeverity.setPadding(new RectangleInsets(4, 8, 2, 2));

		LegendTitle legend1 = chartSeverity.getLegend();
		legend1.setPosition(RectangleEdge.BOTTOM);
		legend1.setBorder(0, 0, 0, 0);

		String filename1 = "extra/do_nut.jpg";
		int chartWidth = 800;
		int chartHeight = 600;
		ChartUtils.saveChartAsJPEG(new File(filename1), chartSeverity, chartWidth, chartHeight);

		File image1 = new File(filename1);
		byte[] photo1 = IOUtils.toByteArray(new FileInputStream(image1));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pd1 = ppt.addPicture(photo1, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShape1 = slide.createPicture(pd1);
		pictureShape1.setAnchor(new Rectangle(150, 140, 540, 400));

		// Performance Metered Chart
		String loc = "extra/gauge.jpg";
		DefaultValueDataset data = new DefaultValueDataset(value);

		MeterPlot meterplot = new MeterPlot(data);
		meterplot.setRange(new Range(0, 100));
		meterplot.addInterval(
				new MeterInterval("normal", new Range(0, 60), new Color(255, 78, 66), new BasicStroke(7.0F), null));
		meterplot.addInterval(
				new MeterInterval("warning", new Range(60, 85), new Color(255, 164, 0), new BasicStroke(7.0F), null));
		meterplot.addInterval(new MeterInterval("critical", new Range(85, 100), new Color(12, 206, 107),
				new BasicStroke(7.0F), null));

		meterplot.setNeedlePaint(Color.black);
		meterplot.setDialBackgroundPaint(Color.white);
		meterplot.setOutlineStroke(new BasicStroke(10));
		meterplot.setDialOutlinePaint(Color.black);
		meterplot.setDialShape(DialShape.CHORD);
		meterplot.setMeterAngle(180);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font("Arial", 1, 14));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.BLACK);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));
		meterplot.setUnits("");

		JFreeChart chartMeter = new JFreeChart("Overall Performance Coverage", JFreeChart.DEFAULT_TITLE_FONT, meterplot,
				true);

		chartMeter.setBackgroundPaint(Color.white);
		chartMeter.setBorderPaint(Color.white);
		chartMeter.removeLegend();

		ChartUtils.saveChartAsJPEG(new File(loc), chartMeter, 300, 200);

		File imageMeter = new File(loc);
		byte[] photoMeter = IOUtils.toByteArray(new FileInputStream(imageMeter));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pdMeter = ppt.addPicture(photoMeter, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShapeMeter = slide.createPicture(pdMeter);
		pictureShapeMeter.setAnchor(new Rectangle(30, 160, 200, 120));
	}

	// Performance Slide 2

	private void addPerformanceSlide2(XMLSlideShow ppt, double value, JSONObject responseBody, JSONObject wcvResponseBody) throws IOException {
    	XSLFSlide slide = ppt.createSlide();
    	// Web Core Vitals Text
        
        // Load the image file
        String imagePath = "extra/webcorevitals.png";
        FileInputStream imageInputStream = new FileInputStream(imagePath);

        // Add the image to the presentation
        XSLFPictureData pictureData = ppt.addPicture(imageInputStream, PictureData.PictureType.PNG);
        XSLFPictureShape picture = slide.createPicture(pictureData);

        // Set the position and size of the image
        picture.setAnchor(new Rectangle(30, 50, 500, 120)); // Adjust the position and size as needed
        
        
        // WEB CORE VITALS CHARTS
        Map<String, Map<String, Double>> keyToKeyAsStringMap = getWCVValues(wcvResponseBody);
        // Initial position for the first box
        int xPosition = 30;
        int yPosition = 200;
        for (Map.Entry<String, Map<String, Double>> entry : keyToKeyAsStringMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Double> keyAsStringMap = entry.getValue();

            // Extract value and calculate percentage
            double value1 = keyAsStringMap.get("value");
            double remaining = 100 - value1;

            // Create dataset
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue(String.format("%.2fs", value1), value1);
            dataset.setValue(String.format("", remaining), remaining);

            // Create chart
            RingPlot plot = new RingPlot(dataset);
            JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            
            // Customize chart properties as needed
            
            chart.setBackgroundPaint(
       				new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.WHITE));
            chart.setTitle("Analyzer - " + key);

       		TextTitle t1 = chart.getTitle();
       		t1.setVerticalAlignment(org.jfree.chart.ui.VerticalAlignment.CENTER);
       		t1.setFont(new java.awt.Font("Arial", Font.BOLD, 34));

       		plot.setInsets(new RectangleInsets(0, 5, 5, 5));
      		// plot.setInteriorGap(0.50);

       		plot.setLabelGap(0.1);
       		plot.setBackgroundPaint(null);
       		plot.setOutlineVisible(false);
       		plot.setLabelGenerator(null);
      		// plot.setSectionDepth(0.60);
       		plot.setSectionOutlinesVisible(false);
       		plot.setSimpleLabels(true);
       		plot.setShadowPaint(null);
       		plot.setOuterSeparatorExtension(0);
       		plot.setInnerSeparatorExtension(0);
       		plot.setLabelGenerator(
      				new StandardPieSectionLabelGenerator("{1}", new DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
      		// labeloffset
       		plot.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.36, 0.36, 0.36, 0.36));
       		plot.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.18, 0.18, 0.18, 0.18));
       		plot.setSectionDepth(0.60);

       		plot.setLabelBackgroundPaint(null);
       		plot.setLabelOutlinePaint(null);
       		plot.setSectionPaint("value1", new Color(250, 15, 15, 1));
       		plot.setSectionPaint("remaining", new Color(1, 110, 115, 1));
       		plot.setLabelShadowPaint(null);

      		java.awt.Font font1 = new java.awt.Font("", 0, 30);
      		plot.setLabelFont(font1);

      		// Increase font size for the legend
            LegendTitle legend1 = chart.getLegend();
            legend1.setItemFont(new java.awt.Font("Arial", Font.PLAIN, 30));
            legend1.setFrame(BlockBorder.NONE);
            legend1.setPosition(RectangleEdge.BOTTOM);
            legend1.setBorder(0, 0, 0, 0);

            chart.setBackgroundPaint(Color.white);
            chart.setPadding(new RectangleInsets(4, 8, 2, 2));


      		legend1.setBorder(0, 0, 0, 0);
      		String filename1 = "extra/do_nut.jpg";

      		// Adjust width and height here
             int chartWidth = 800;
             int chartHeight = 600;
             ChartUtils.saveChartAsJPEG(new File(filename1), chart, chartWidth, chartHeight);


      		File image1 = new File(filename1);
      		byte[] photo1 = IOUtils.toByteArray(new FileInputStream(image1));
      		
      		
            XSLFPictureData pd = ppt.addPicture(photo1, PictureData.PictureType.PNG);
            XSLFPictureShape pictureShape = slide.createPicture(pd);
            pictureShape.setAnchor(new Rectangle(xPosition, yPosition, 200, 120));
            // Increment x position for the next text box
            xPosition += 200;
            

            // Update yPosition for the fourth and fifth boxes
            if (xPosition == 630 || xPosition == 830) {
                yPosition = 350; // Update yPosition for the fourth and fifth boxes
                xPosition += -500;
            }
        }
    }

	// SEO

	private void addSEOSlide(XMLSlideShow ppt, double value, JSONObject responseBody) throws IOException {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox textBox = slide.createTextBox();
		XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
		XSLFTextRun run = paragraph.addNewTextRun();
		run.setText("Optimizing Your Website: SEO");
		run.setFontSize(28.0);
		run.setBold(true);
		run.setFontFamily(HSSFFont.FONT_ARIAL);
		textBox.setAnchor(new Rectangle(25, 15, 650, 50));

		// SEO Metered Chart

		String loc = "extra/gauge.jpg";
		DefaultValueDataset data = new DefaultValueDataset(value);

		MeterPlot meterplot = new MeterPlot(data);
		meterplot.setRange(new Range(0, 100));
		meterplot.addInterval(
				new MeterInterval("normal", new Range(0, 60), new Color(255, 78, 66), new BasicStroke(7.0F), null));
		meterplot.addInterval(
				new MeterInterval("warning", new Range(60, 85), new Color(255, 164, 0), new BasicStroke(7.0F), null));
		meterplot.addInterval(new MeterInterval("critical", new Range(85, 100), new Color(12, 206, 107),
				new BasicStroke(7.0F), null));

		meterplot.setNeedlePaint(Color.black);
		meterplot.setDialBackgroundPaint(Color.white);
		meterplot.setOutlineStroke(new BasicStroke(10));
		meterplot.setDialOutlinePaint(Color.black);
		meterplot.setDialShape(DialShape.CHORD);
		meterplot.setMeterAngle(180);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font("Arial", 1, 14));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.BLACK);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));
		meterplot.setUnits("");

		JFreeChart chartMeter = new JFreeChart("Overall SEO Coverage", JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);

		chartMeter.setBackgroundPaint(Color.white);
		chartMeter.setBorderPaint(Color.white);
		chartMeter.removeLegend();

		ChartUtils.saveChartAsJPEG(new File(loc), chartMeter, 300, 200);

		File imageMeter = new File(loc);
		byte[] photoMeter = IOUtils.toByteArray(new FileInputStream(imageMeter));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pdMeter = ppt.addPicture(photoMeter, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShapeMeter = slide.createPicture(pdMeter);
		pictureShapeMeter.setAnchor(new Rectangle(30, 120, 200, 120));

		// Failed Audit Chart
		DefaultPieDataset datasetSeverity = new DefaultPieDataset();
		JSONArray resultsArray = responseBody.getJSONObject("data").getJSONArray("result");

		for (int i = 0; i < resultsArray.length(); i++) {
		    JSONObject result = resultsArray.getJSONObject(i);
		    JSONObject metric = result.getJSONObject("metric");
		    String groupBucket = metric.optString("group");
		    double groupValue = result.getJSONArray("value").getDouble(1);

		    // Add the group and its value to the dataset
		    datasetSeverity.setValue(groupBucket, groupValue);
		}

		RingPlot plotSeverity = new RingPlot(datasetSeverity);
		JFreeChart chartSeverity = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plotSeverity, true);
		chartSeverity.setBackgroundPaint(
				new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.WHITE));
		chartSeverity.setTitle("Analyzer - Application wise Fail Audits Categories");

		TextTitle t1 = chartSeverity.getTitle();
		t1.setVerticalAlignment(org.jfree.chart.ui.VerticalAlignment.CENTER);
		t1.setFont(new java.awt.Font("Arial", Font.BOLD, 22));

		plotSeverity.setInsets(new RectangleInsets(0, 5, 5, 5));
		// plot.setInteriorGap(0.50);

		plotSeverity.setLabelGap(0.1);
		plotSeverity.setBackgroundPaint(null);
		plotSeverity.setOutlineVisible(false);
		plotSeverity.setLabelGenerator(null);
		// plot.setSectionDepth(0.60);
		plotSeverity.setSectionOutlinesVisible(false);
		plotSeverity.setSimpleLabels(true);
		plotSeverity.setShadowPaint(null);
		plotSeverity.setOuterSeparatorExtension(0);
		plotSeverity.setInnerSeparatorExtension(0);
		plotSeverity.setLabelGenerator(
				new StandardPieSectionLabelGenerator("{1}", new DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
		// labeloffset
		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.36, 0.36, 0.36, 0.36));
		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.18, 0.18, 0.18, 0.18));
		plotSeverity.setSectionDepth(0.60);

		plotSeverity.setLabelBackgroundPaint(null);
		plotSeverity.setLabelOutlinePaint(null);
		plotSeverity.setSectionPaint("PASS", new Color(1, 178, 124));
		plotSeverity.setSectionPaint("FAIL", new Color(252, 106, 89));
		plotSeverity.setLabelShadowPaint(null);

		java.awt.Font font1 = new java.awt.Font("", 0, 22);
		plotSeverity.setLabelFont(font1);

		// Increase font size for the legend
		LegendTitle legend1 = chartSeverity.getLegend();
		legend1.setItemFont(new java.awt.Font("Arial", Font.PLAIN, 22));
		legend1.setFrame(BlockBorder.NONE);
		legend1.setPosition(RectangleEdge.BOTTOM);
		legend1.setBorder(0, 0, 0, 0);

		chartSeverity.setBackgroundPaint(Color.white);
		chartSeverity.setPadding(new RectangleInsets(4, 8, 2, 2));

		legend1.setBorder(0, 0, 0, 0);
		String filename1 = "extra/do_nut.jpg";

		// Adjust width and height here
		int chartWidth = 800;
		int chartHeight = 600;
		ChartUtils.saveChartAsJPEG(new File(filename1), chartSeverity, chartWidth, chartHeight);

		File image1 = new File(filename1);
		byte[] photo1 = IOUtils.toByteArray(new FileInputStream(image1));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pd1 = ppt.addPicture(photo1, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShape1 = slide.createPicture(pd1);
		pictureShape1.setAnchor(new Rectangle(250, 120, 300, 220));

	}

	// Best Practices

	private void addBPSlide(XMLSlideShow ppt, double value, JSONObject responseBody) throws IOException {
		XSLFSlide slide = ppt.createSlide();
		XSLFTextBox textBox = slide.createTextBox();
		XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
		XSLFTextRun run = paragraph.addNewTextRun();
		run.setText("Optimizing Your Website: Best Practices");
		run.setFontSize(28.0);
		run.setBold(true);
		run.setFontFamily(HSSFFont.FONT_ARIAL);
		textBox.setAnchor(new Rectangle(25, 15, 650, 50));

		// SEO Metered Chart

		String loc = "extra/gauge.jpg";
		DefaultValueDataset data = new DefaultValueDataset(value);

		MeterPlot meterplot = new MeterPlot(data);
		meterplot.setRange(new Range(0, 100));
		meterplot.addInterval(
				new MeterInterval("normal", new Range(0, 60), new Color(255, 78, 66), new BasicStroke(7.0F), null));
		meterplot.addInterval(
				new MeterInterval("warning", new Range(60, 85), new Color(255, 164, 0), new BasicStroke(7.0F), null));
		meterplot.addInterval(new MeterInterval("critical", new Range(85, 100), new Color(12, 206, 107),
				new BasicStroke(7.0F), null));

		meterplot.setNeedlePaint(Color.black);
		meterplot.setDialBackgroundPaint(Color.white);
		meterplot.setOutlineStroke(new BasicStroke(10));
		meterplot.setDialOutlinePaint(Color.black);
		meterplot.setDialShape(DialShape.CHORD);
		meterplot.setMeterAngle(180);
		meterplot.setTickLabelsVisible(true);
		meterplot.setTickLabelFont(new Font("Arial", 1, 14));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.BLACK);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));
		meterplot.setUnits("");

		JFreeChart chartMeter = new JFreeChart("Overall Best Practices Coverage", JFreeChart.DEFAULT_TITLE_FONT,
				meterplot, true);

		chartMeter.setBackgroundPaint(Color.white);
		chartMeter.setBorderPaint(Color.white);
		chartMeter.removeLegend();

		ChartUtils.saveChartAsJPEG(new File(loc), chartMeter, 300, 200);

		File imageMeter = new File(loc);
		byte[] photoMeter = IOUtils.toByteArray(new FileInputStream(imageMeter));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pdMeter = ppt.addPicture(photoMeter, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShapeMeter = slide.createPicture(pdMeter);
		pictureShapeMeter.setAnchor(new Rectangle(30, 120, 200, 120));

		// Failed Audit Chart
		DefaultPieDataset datasetSeverity = new DefaultPieDataset();
		JSONArray resultsArray = responseBody.getJSONObject("data").getJSONArray("result");

		for (int i = 0; i < resultsArray.length(); i++) {
			JSONObject result = resultsArray.getJSONObject(i);
			JSONObject metric = result.getJSONObject("metric");
			String severityBucket = metric.optString("group");
			double severityValue = result.getJSONArray("value").getDouble(1);

			datasetSeverity.setValue(severityBucket, severityValue);
		}

		RingPlot plotSeverity = new RingPlot(datasetSeverity);
		JFreeChart chartSeverity = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plotSeverity, true);
		chartSeverity.setBackgroundPaint(
				new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.WHITE));
		chartSeverity.setTitle("Analyzer - Application wise Fail Audits Categories");

		TextTitle t1 = chartSeverity.getTitle();
		t1.setVerticalAlignment(org.jfree.chart.ui.VerticalAlignment.CENTER);
		t1.setFont(new java.awt.Font("Arial", Font.BOLD, 22));

		plotSeverity.setInsets(new RectangleInsets(0, 5, 5, 5));
		// plot.setInteriorGap(0.50);

		plotSeverity.setLabelGap(0.1);
		plotSeverity.setBackgroundPaint(null);
		plotSeverity.setOutlineVisible(false);
		plotSeverity.setLabelGenerator(null);
		// plot.setSectionDepth(0.60);
		plotSeverity.setSectionOutlinesVisible(false);
		plotSeverity.setSimpleLabels(true);
		plotSeverity.setShadowPaint(null);
		plotSeverity.setOuterSeparatorExtension(0);
		plotSeverity.setInnerSeparatorExtension(0);
		plotSeverity.setLabelGenerator(
				new StandardPieSectionLabelGenerator("{1}", new DecimalFormat("#,##0"), new DecimalFormat("0.000%")));
		// labeloffset
		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.36, 0.36, 0.36, 0.36));
		plotSeverity.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.18, 0.18, 0.18, 0.18));
		plotSeverity.setSectionDepth(0.60);

		plotSeverity.setLabelBackgroundPaint(null);
		plotSeverity.setLabelOutlinePaint(null);
		plotSeverity.setSectionPaint("PASS", new Color(1, 178, 124));
		plotSeverity.setSectionPaint("FAIL", new Color(252, 106, 89));
		plotSeverity.setLabelShadowPaint(null);

		java.awt.Font font1 = new java.awt.Font("", 0, 22);
		plotSeverity.setLabelFont(font1);

		// Increase font size for the legend
		LegendTitle legend1 = chartSeverity.getLegend();
		legend1.setItemFont(new java.awt.Font("Arial", Font.PLAIN, 22));
		legend1.setFrame(BlockBorder.NONE);
		legend1.setPosition(RectangleEdge.BOTTOM);
		legend1.setBorder(0, 0, 0, 0);

		chartSeverity.setBackgroundPaint(Color.white);
		chartSeverity.setPadding(new RectangleInsets(4, 8, 2, 2));

		legend1.setBorder(0, 0, 0, 0);
		String filename1 = "extra/do_nut.jpg";

		// Adjust width and height here
		int chartWidth = 800;
		int chartHeight = 600;
		ChartUtils.saveChartAsJPEG(new File(filename1), chartSeverity, chartWidth, chartHeight);

		File image1 = new File(filename1);
		byte[] photo1 = IOUtils.toByteArray(new FileInputStream(image1));

		// Add the chart image to the PowerPoint slide
		XSLFPictureData pd1 = ppt.addPicture(photo1, PictureData.PictureType.PNG);
		XSLFPictureShape pictureShape1 = slide.createPicture(pd1);
		pictureShape1.setAnchor(new Rectangle(250, 120, 300, 220));

	}


	private Map<String, Map<String, Double>> getWCVValues(JSONObject textResponseBody) {
	    // Get the "result" array from the JSON response
	    JSONArray resultArray = textResponseBody
	            .getJSONObject("data")
	            .getJSONArray("result");

	    // Map to store the key and corresponding value and doc_count
	    Map<String, Map<String, Double>> keyToKeyAsStringMap = new HashMap<>();

	    // Iterate through the result array
	    for (int i = 0; i < resultArray.length(); i++) {
	        JSONObject result = resultArray.getJSONObject(i);
	        
	        // Extract the key from the "metric" object
	        String key = result.getJSONObject("metric").optString("title", "Unknown");

	        // Extract the value and document count from the "value" array
	        double value = Double.parseDouble(result.getJSONArray("value").getString(1));
	        double docCount = result.getJSONArray("value").getDouble(1); // Assuming docCount is the second value

	        // Map to store value and doc_count for each key
	        Map<String, Double> keyAsStringMap = new HashMap<>();
	        keyAsStringMap.put("value", value);
	        keyAsStringMap.put("doc_count", docCount); // Storing doc_count as Double

	        // Add the key and its corresponding values to the main map
	        keyToKeyAsStringMap.put(key, keyAsStringMap);
	    }

	    return keyToKeyAsStringMap;
	}


}