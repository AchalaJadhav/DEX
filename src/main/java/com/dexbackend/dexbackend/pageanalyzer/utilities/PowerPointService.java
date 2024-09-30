package com.dexbackend.dexbackend.pageanalyzer.utilities;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.ShapeType;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.plot.dial.ArcDialFrame;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialLayer;
import org.jfree.chart.plot.dial.DialLayerChangeListener;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.UnitType;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

@Service
public class PowerPointService {
	@Autowired
	private Environment enve ;
	private String env = "extra/DEX_Report.pptx";
	private XMLSlideShow ppt; // Declare ppt object at the class level

//	public String getDomainTemplatePath() {
//		return env.getProperty("ppt.dxreportdomain.template");
//	}
	
    public void createPresentationMetered(String applicationName, String buildId, double value, JSONObject responseBody, JSONObject textResponseBody, JSONObject wcvResponseBody, String cased) throws IOException {

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


    private void addAccessibilitySlide(XMLSlideShow ppt, double value, JSONObject responseBody, JSONObject textResponseBody) throws IOException {
        XSLFSlide slide = ppt.createSlide();
        XSLFTextBox textBox = slide.createTextBox();
        XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
        XSLFTextRun run = paragraph.addNewTextRun();
        run.setText("How inclusive are your digital assets to your consumer");
        run.setFontSize(28.0);
        run.setBold(true);
        run.setFontFamily(HSSFFont.FONT_ARIAL);
        textBox.setAnchor(new Rectangle(25, 15, 650, 50));

          
       // data for accessibility pie chart
   		DefaultPieDataset datasetSeverity = new DefaultPieDataset();
   		JSONArray buckets = responseBody
                .getJSONObject("aggregations")
                .getJSONObject("2")
                .getJSONArray("buckets");
   		

   		for (int i = 0; i < buckets.length(); i++) {
            JSONObject bucket = buckets.getJSONObject(i);
            String severityBuckets = bucket.getString("key");
            int docCount = bucket.getInt("doc_count");
            
            switch (severityBuckets) {
            case "serious", "moderate", "minor", "critical" -> datasetSeverity.setValue(severityBuckets, docCount);
            default -> throw new IllegalArgumentException("Unexpected value: " + severityBuckets);
            }

   		}
   		
   	

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
        meterplot.setRange(new Range(0,100));
        meterplot.addInterval(new MeterInterval("normal", new Range(0, 60),
        		new Color(255, 78, 66), new BasicStroke(7.0F), null));
        meterplot.addInterval(new MeterInterval("warning", new Range(60,85),
        		new Color(255, 164, 0), new BasicStroke(7.0F), null));
        meterplot.addInterval(new MeterInterval("critical", new Range(85,100),
        		new Color(12, 206, 107), new BasicStroke(7.0F), null));

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
        
        JFreeChart chartMeter = new JFreeChart("Overall Accessibility Coverage",
            JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
		
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
        
        // TextBox for Accessibility Slide
//        createTextBox(textResponseBody);
     // data for accessibility pie chart
        DefaultPieDataset datasetTextBox = new DefaultPieDataset();
        JSONArray bucketsTextBox = textResponseBody
                .getJSONObject("aggregations")
                .getJSONObject("0")
                .getJSONArray("buckets");

        Map<String, Map<String, Integer>> keyToKeyAsStringMap = new HashMap<>();

        for (int i = 0; i < bucketsTextBox.length(); i++) {
            JSONObject bucket = bucketsTextBox.getJSONObject(i);
            String key = bucket.getString("key");
            JSONArray subBuckets = bucket.getJSONObject("1").getJSONArray("buckets");

            Map<String, Integer> keyAsStringMap = new HashMap<>();
            for (int j = 0; j < subBuckets.length(); j++) {
                JSONObject subBucket = subBuckets.getJSONObject(j);
                String keyAsString = subBucket.getString("key_as_string");
                Integer docCount = subBucket.getInt("doc_count");
                keyAsStringMap.put(keyAsString, docCount);
            }

            keyToKeyAsStringMap.put(key, keyAsStringMap);
        }
         

        // Initial position for the first text box
        int xPosition = 30;
        int yPosition = 330;
        int count1 = 0;
        // Add the key and their associated key_as_string and doc_count values to a text box
        for (Map.Entry<String, Map<String, Integer>> entry : keyToKeyAsStringMap.entrySet()) {
        	if (count1 >= 2) break; // Only include the first 2 entries
            String key = entry.getKey();
            Map<String, Integer> keyAsStringMap = entry.getValue();

            // Create a new text box for each key
            XSLFTextBox box = slide.createTextBox();
            box.setFillColor(new Color(236, 236, 225));
            box.setAnchor(new Rectangle(xPosition, yPosition, 250, 195));
            XSLFTextParagraph paragraph1 = box.addNewTextParagraph();
            XSLFTextRun run1 = paragraph1.addNewTextRun();
            run1.setText("Analyzed Page URL: ");
            run1.setFontSize(10.0);
            run1.setFontFamily("Arial");
            run1.setBold(true);

            XSLFTextParagraph paragraph2 = box.addNewTextParagraph();
            XSLFTextRun run2 = paragraph2.addNewTextRun();
            run2.setText(key);
            run2.setFontSize(10.0);
            run2.setFontFamily("Arial");

            int count = 0;
            for (Map.Entry<String, Integer> subEntry : keyAsStringMap.entrySet()) {
                if (count >= 3) break; // Only include the first 3 entries
                String keyAsString = subEntry.getKey();
                Integer docCount = subEntry.getValue();

                // Add key_as_string and doc_count to the text box
                XSLFTextParagraph subParagraph = box.addNewTextParagraph();
                XSLFTextRun subRun = subParagraph.addNewTextRun();
                subRun.setText("Reviewed On: ");
                subRun.setFontSize(10.0);
                subRun.setFontFamily("Arial");
                subRun.setBold(true);

                XSLFTextParagraph subParagraph1 = box.addNewTextParagraph();
                XSLFTextRun subRun1 = subParagraph1.addNewTextRun();
                subRun1.setText(keyAsString);
                subRun1.setFontSize(10.0);
                subRun1.setFontFamily("Arial");

                XSLFTextParagraph subParagraph2 = box.addNewTextParagraph();
                XSLFTextRun subRun2 = subParagraph2.addNewTextRun();
                subRun2.setText("Total Pages Accessed: ");
                subRun2.setFontSize(10.0);
                subRun2.setFontFamily("Arial");
                subRun2.setBold(true);

                XSLFTextParagraph subParagraph3 = box.addNewTextParagraph();
                XSLFTextRun subRun3 = subParagraph3.addNewTextRun();
                subRun3.setText("" + docCount);
                subRun3.setFontSize(10.0);
                subRun3.setFontFamily("Arial");
                count++;
            }

            // Increment x position for the next text box
            xPosition += 260;
            count1++;
        }
        
    }
    
    
    public void createIssuesTable(JSONObject responseBody) {
        XSLFSlide slide = ppt.createSlide();

        // Set slide title
        XSLFTextShape title = slide.createTextBox();
        title.setText("Issues Table");
        title.setAnchor(new java.awt.Rectangle(50, 20, 600, 50));
        XSLFTextParagraph titleParagraph = title.addNewTextParagraph();
        XSLFTextRun titleRun = titleParagraph.addNewTextRun();
        titleRun.setFontColor(Color.BLACK);
        titleRun.setFontSize(24.0);
        titleRun.setBold(true);
        titleParagraph.setTextAlign(TextAlign.CENTER);

        JSONArray buckets = responseBody.getJSONObject("rawResponse")
                .getJSONObject("aggregations")
                .getJSONObject("2")
                .getJSONArray("buckets");

        int rows = buckets.length();
        int cols = 5; // Code Snippet, Impact, WCAG Standard, Rule Explanation, Count
        XSLFTable table = slide.createTable(rows + 1, cols);

        // Set table headers
        String[] headers = {"Code Snippet", "Impact", "WCAG Standard", "Rule Explanation", "Count"};
        XSLFTableRow headerRow = table.addRow();
        for (String header : headers) {
            XSLFTableCell headerCell = headerRow.addCell();
            headerCell.setText(header);

            // Set header cell fill color
            headerCell.setFillColor(new Color(79, 129, 189));

            // Get the first text paragraph and run
            XSLFTextParagraph paragraph = headerCell.getTextParagraphs().get(0);
            XSLFTextRun run = paragraph.getTextRuns().get(0);

            // Set header text color
            run.setFontColor(Color.WHITE);

            // Set font size and style
            run.setFontSize(12.0);  // Example font size
            run.setBold(true);      // Example bold text

            // Center align the text in the header cells
            paragraph.setTextAlign(TextAlign.CENTER);

            // Optionally, set vertical alignment
            headerCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        }

        // Iterate over the buckets to populate table rows
        for (int i = 0; i < buckets.length(); i++) {
            JSONObject bucket = buckets.getJSONObject(i);
            JSONArray severityBuckets = bucket.getJSONObject("3").getJSONArray("buckets");

            for (int j = 0; j < severityBuckets.length(); j++) {
                JSONObject severityBucket = severityBuckets.getJSONObject(j);
                JSONArray issueBuckets = severityBucket.getJSONObject("4").getJSONArray("buckets");

                for (int k = 0; k < issueBuckets.length(); k++) {
                    JSONObject issueBucket = issueBuckets.getJSONObject(k);
                    JSONArray subIssueBuckets = issueBucket.getJSONObject("5").getJSONArray("buckets");

                    for (int l = 0; l < subIssueBuckets.length(); l++) {
                        JSONObject subIssueBucket = subIssueBuckets.getJSONObject(l);

                        // Get the issue details
                        String codeSnippet = subIssueBucket.getString("key");
                        String impact = ""; // Get impact value from subIssueBucket if available
                        String wcagStandard = ""; // Get WCAG standard from subIssueBucket if available
                        String ruleExplanation = ""; // Get rule explanation from subIssueBucket if available
                        int docCount = subIssueBucket.getInt("doc_count");

                        // Create a new row in the table
                        XSLFTableRow row = table.addRow();

                        // Set the issue details
                        row.addCell().setText(codeSnippet);
                        row.addCell().setText(impact);
                        row.addCell().setText(wcagStandard);
                        row.addCell().setText(ruleExplanation);
                        row.addCell().setText(String.valueOf(docCount));

                        // Set row text color to black
                        for (XSLFTableCell cell : row.getCells()) {
                            cell.setFillColor(Color.WHITE);
                            for (XSLFTextParagraph p : cell.getTextParagraphs()) {
                                for (XSLFTextRun r : p.getTextRuns()) {
                                    r.setFontColor(Color.BLACK);
                                }
                            }
                        }

                        i++;
                    }
                }
            }
        }
    }

    
   
    //Performance
    
    private void addPerformanceSlide(XMLSlideShow ppt, double value, JSONObject responseBody) throws IOException {
        XSLFSlide slide = ppt.createSlide();
        XSLFTextBox textBox = slide.createTextBox();
        XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
        XSLFTextRun run = paragraph.addNewTextRun();
        run.setText("High Performance Assets: Are your end-users getting the performance and user experience they deserve?");
        run.setFontSize(28.0);
        run.setBold(true);
        run.setFontFamily(HSSFFont.FONT_ARIAL);
        textBox.setAnchor(new Rectangle(25, 15, 650, 50));

          
          // Mime Pie Chart
      		DefaultPieDataset datasetSeverity = new DefaultPieDataset();
      		JSONArray buckets = responseBody
                   .getJSONObject("aggregations")
                   .getJSONObject("2")
                   .getJSONArray("buckets");
      		

      		for (int i = 0; i < buckets.length(); i++) {
               JSONObject bucket = buckets.getJSONObject(i);
               String severityBuckets = bucket.getString("key");
               int docCount = bucket.getInt("doc_count");
               
               datasetSeverity.setValue(severityBuckets, docCount);
      		}
      		
      	

      		RingPlot plotSeverity = new RingPlot(datasetSeverity);
      		JFreeChart chartSeverity = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plotSeverity, true);
      		chartSeverity.setBackgroundPaint(
      				new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.WHITE));
      		chartSeverity.setTitle("Analyzer Compare - MimeType Pie Chart");

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

     	// Adjust width and height here
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
           meterplot.setRange(new Range(0,100));
           meterplot.addInterval(new MeterInterval("normal", new Range(0, 60),
           		new Color(255, 78, 66), new BasicStroke(7.0F), null));
           meterplot.addInterval(new MeterInterval("warning", new Range(60,85),
           		new Color(255, 164, 0), new BasicStroke(7.0F), null));
           meterplot.addInterval(new MeterInterval("critical", new Range(85,100),
           		new Color(12, 206, 107), new BasicStroke(7.0F), null));

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
           
           JFreeChart chartMeter = new JFreeChart("Overall Performance Coverage",
               JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
   		
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
    

    
    //Performance Slide 2
    
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
    
    
    //SEO
    
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
           meterplot.setRange(new Range(0,100));
           meterplot.addInterval(new MeterInterval("normal", new Range(0, 60),
           		new Color(255, 78, 66), new BasicStroke(7.0F), null));
           meterplot.addInterval(new MeterInterval("warning", new Range(60,85),
           		new Color(255, 164, 0), new BasicStroke(7.0F), null));
           meterplot.addInterval(new MeterInterval("critical", new Range(85,100),
           		new Color(12, 206, 107), new BasicStroke(7.0F), null));

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
           
           JFreeChart chartMeter = new JFreeChart("Overall SEO Coverage",
               JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
   		
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
       		JSONArray buckets = responseBody
                    .getJSONObject("aggregations")
                    .getJSONObject("2")
                    .getJSONArray("buckets");
       		

       		for (int i = 0; i < buckets.length(); i++) {
                JSONObject bucket = buckets.getJSONObject(i);
                String severityBuckets = bucket.getString("key");
                int docCount = bucket.getInt("doc_count");
                
                datasetSeverity.setValue(severityBuckets, docCount);
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

    //Best Practices
    
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
           meterplot.setRange(new Range(0,100));
           meterplot.addInterval(new MeterInterval("normal", new Range(0, 60),
           		new Color(255, 78, 66), new BasicStroke(7.0F), null));
           meterplot.addInterval(new MeterInterval("warning", new Range(60,85),
           		new Color(255, 164, 0), new BasicStroke(7.0F), null));
           meterplot.addInterval(new MeterInterval("critical", new Range(85,100),
           		new Color(12, 206, 107), new BasicStroke(7.0F), null));

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
           
           JFreeChart chartMeter = new JFreeChart("Overall Best Practices Coverage",
               JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
   		
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
       		JSONArray buckets = responseBody
                    .getJSONObject("aggregations")
                    .getJSONObject("2")
                    .getJSONArray("buckets");
       		

       		for (int i = 0; i < buckets.length(); i++) {
                JSONObject bucket = buckets.getJSONObject(i);
                String severityBuckets = bucket.getString("key");
                int docCount = bucket.getInt("doc_count");
                
                datasetSeverity.setValue(severityBuckets, docCount);
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

//
//      		LegendTitle legend1 = chartSeverity.getLegend();
//      		legend1.setPosition(RectangleEdge.BOTTOM);

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

    
    private Map<String, Map<String, Integer>> createTextBox(JSONObject textResponseBody) {
        // data for accessibility pie chart
        DefaultPieDataset datasetSeverity = new DefaultPieDataset();
        JSONArray buckets = textResponseBody
                .getJSONObject("aggregations")
                .getJSONObject("0")
                .getJSONArray("buckets");

        Map<String, Map<String, Integer>> keyToKeyAsStringMap = new HashMap<>();

        for (int i = 0; i < buckets.length(); i++) {
            JSONObject bucket = buckets.getJSONObject(i);
            String key = bucket.getString("key");
            JSONArray subBuckets = bucket.getJSONObject("1").getJSONArray("buckets");

            Map<String, Integer> keyAsStringMap = new HashMap<>();
            for (int j = 0; j < subBuckets.length(); j++) {
                JSONObject subBucket = subBuckets.getJSONObject(j);
                String keyAsString = subBucket.getString("key_as_string");
                Integer docCount = subBucket.getInt("doc_count");
                keyAsStringMap.put(keyAsString, docCount);
            }

            keyToKeyAsStringMap.put(key, keyAsStringMap);
        }
        System.out.println(keyToKeyAsStringMap);
        return keyToKeyAsStringMap;
    }

    private Map<String, Map<String, Double>> getWCVValues(JSONObject textResponseBody) {
    	JSONArray buckets = textResponseBody
                .getJSONObject("aggregations")
                .getJSONObject("2")
                .getJSONArray("buckets");
   		

    	Map<String, Map<String, Double>> keyToKeyAsStringMap = new HashMap<>();

        for (int i = 0; i < buckets.length(); i++) {
            JSONObject bucket = buckets.getJSONObject(i);
            String key = bucket.getString("key");
            double value = bucket.getJSONObject("1").getDouble("value");
            int docCount = bucket.getInt("doc_count");

            Map<String, Double> keyAsStringMap = new HashMap<>();
            keyAsStringMap.put("value", value);
            keyAsStringMap.put("doc_count", (double) docCount); // Cast to Double

            keyToKeyAsStringMap.put(key, keyAsStringMap);
        }

        return keyToKeyAsStringMap;
    }

}