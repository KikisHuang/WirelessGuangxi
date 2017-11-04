package wlgx.com.kikis.utils;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import wlgx.com.kikis.bean.ChartsBean;

/**
 * Created by lian on 2017/9/15.
 * 线形图+柱状图工具类;
 */
public class ChartsUtils {

    /**
     * 线形图;
     *  @param lineData
     * @param chartTop
     * @param data
     */
    public static void generateInitialLineData(LineChartData lineData, LineChartView chartTop, List<ChartsBean> data) {

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < data.size(); ++i) {
            values.add(new PointValue(i, (float) data.get(i).getTotalFee()));
            axisValues.add(new AxisValue(i).setLabel(String.valueOf(data.get(i).getMonth())+"月"));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);
        line.setHasLabelsOnlyForSelected(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);
        lineData.setAxisXBottom(new Axis(axisValues).setHasLines(false).setTextColor(Color.WHITE).setTextSize(11));
        lineData.setAxisYLeft(new Axis().setHasLines(false).setMaxLabelChars(3).setName("成交金额").setTextColor(Color.WHITE));

        chartTop.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chartTop.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
//        Viewport v = new Viewport(0, 110, 6, 0);
//        chartTop.setMaximumViewport(v);
//        chartTop.setCurrentViewport(v);

        chartTop.setZoomType(ZoomType.HORIZONTAL);
    }

    /**
     * 柱状图图;
     * @param columnData
     * @param chartBottom
     * @param data
     */
    public static void generateColumnData(ColumnChartData columnData, ColumnChartView chartBottom, List<ChartsBean> data,int c) {

        int numSubcolumns = 1;
        int numColumns = data.size();

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) data.get(i).getBrowseShop(), c));
            }

            axisValues.add(new AxisValue(i).setLabel(String.valueOf(data.get(i).getMonth())+"月"));

            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(false).setTextColor(Color.WHITE).setTextSize(11));
        columnData.setAxisYLeft(new Axis().setHasLines(false).setMaxLabelChars(3).setName("浏览数").setTextColor(Color.WHITE));

        chartBottom.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.

        // Set selection mode to keep selected month column highlighted.
        chartBottom.setValueSelectionEnabled(true);

        chartBottom.setZoomType(ZoomType.HORIZONTAL);

    }
}
