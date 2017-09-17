package test.tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class AVGdraw {
	private static final String CHART_PATH = "E:/test/";

	public static void main(String[] args) {
		double[][] data = new double[][] { { 672, 766, 223, 540, 126 },
				{ 325, 521, 210, 340, 106 }, { 332, 256, 523, 240, 526 } };
		String[] rowKeys = { "fix", "log10" };
		String[] columnKeys = { "北京", "上海", "广州", "成都", "深圳" };
		ArrayList<double[]> al = null;
		ArrayList<String> columnKs = null;
		int Rows = 20;
		int colNum = 0;
		String fileName="C:\\Users\\Administrator\\Desktop\\遗传算法数据\\运行结果\\minVals0005_avg.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					fileName)));
			al = new ArrayList<double[]>();
			columnKs = new ArrayList<String>();

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] vals = br.readLine().trim().split("\\W+");
				double[] one = new double[Rows / 2];
				for (int i = 0; i < Rows; i++) {
					one[i] = Double
							.valueOf(vals[vals.length - 1]);
				}
				al.add(one);
				double[] one1 = new double[Rows / 2];
				for (int i = 0; i < Rows; i++) {
					one1[i] = Double
							.valueOf(vals[vals.length - 1]);
				}
				al.add(one1);
			}
			for (int i = 0; i < Rows / 2; i++) {
				columnKs.add(i + "");
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data = al.toArray(new double[al.size()][]);
		columnKeys = columnKs.toArray(new String[columnKs.size()]);
		CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);
		JFreeChart c = createTimeXYChar("折线图", "x轴", "y轴", dataset,
				"lineAndShap.png", false);
		ChartFrame mChartFrame = new ChartFrame("折线图", c);
		mChartFrame.pack();
		mChartFrame.setVisible(true);
	}

	// 柱状图,折线图 数据集
	public static CategoryDataset getBarData(double[][] data, String[] rowKeys,
			String[] columnKeys) {
		return DatasetUtilities
				.createCategoryDataset(rowKeys, columnKeys, data);

	}

	private static void isChartPathExist(String chartPath) {
		File file = new File(chartPath);
		if (!file.exists()) {
			file.mkdirs();
			// log.info("CHART_PATH="+CHART_PATH+"create.");
		}
	}

	public static JFreeChart createTimeXYChar(String chartTitle, String x,
			String y, CategoryDataset xyDataset, String charName,
			boolean ifWriteFile) {

		JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y,
				xyDataset, PlotOrientation.VERTICAL, true, true, false);

		chart.setTextAntiAlias(false);
		chart.setBackgroundPaint(Color.WHITE);
		// 设置图标题的字体重新设置title
		Font font = new Font("隶书", Font.BOLD, 25);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		// 设置面板字体
		Font labelFont = new Font("隶书", Font.TRUETYPE_FONT, 12);

		chart.setBackgroundPaint(Color.WHITE);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);

		categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩

		categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩

		categoryplot.setBackgroundPaint(Color.lightGray);

		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		org.jfree.chart.axis.CategoryAxis domainAxis = categoryplot
				.getDomainAxis();

		domainAxis.setLabelFont(labelFont);// 轴标题

		domainAxis.setTickLabelFont(labelFont);// 轴数值

		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的
		// Lable
		// 45度倾斜
		// 设置距离图片左端距离

		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);

		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);

		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();

		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见

		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见

		// 显示折点数据
		// lineandshaperenderer
		// .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// lineandshaperenderer.setBaseItemLabelsVisible(true);

		if (ifWriteFile) {
			FileOutputStream fos_jpg = null;
			try {
				isChartPathExist(CHART_PATH);
				String chartName = CHART_PATH + charName;
				fos_jpg = new FileOutputStream(chartName);

				// 将报表保存为png文件
				ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 510);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos_jpg.close();
					System.out.println("create time-createTimeXYChar.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			return chart;
		}
		return chart;
	}

}
