package org.jcramb.test.collectionperformance.gwt.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;

public class Home implements EntryPoint, ClickHandler {

    private static int DEFAULT_NUMBER_OF_TEST_ITEMS = 100;

    private List<PerformanceTestResults> results;

    private Button testPerformance;

    private Button testListPerformance;

    private Button testSetPerformance;

    private TextBox inputTextBox;

    @Override
    public void onModuleLoad() {

        // Create the buttons
        testPerformance = new Button("Test Performance of both");
        testPerformance.addClickHandler(this);

        testListPerformance = new Button("Test Performance of List");
        testListPerformance.addClickHandler(this);

        testSetPerformance = new Button("Test Performance of Set");
        testSetPerformance.addClickHandler(this);

        // Create the Output table
        CellTable<PerformanceTestResults> table = new CellTable<PerformanceTestResults>();
        table.setPageSize(1000);
        initTableColumns(table);

        // Create the data provider and bind it to the table
        ListDataProvider<PerformanceTestResults> dataProvider = new ListDataProvider<PerformanceTestResults>();
        dataProvider.addDataDisplay(table);

        // Get the data providers collection to manipulate
        results = dataProvider.getList();

        // Create number of elements input
        Label inputLabel = new Label("Number of elements:");
        inputLabel.getElement().getStyle().setDisplay(Display.INLINE);
        inputTextBox = new TextBox();
        inputTextBox.setText("" + DEFAULT_NUMBER_OF_TEST_ITEMS);

        // Add Elements to the screen
        RootPanel.get().add(inputLabel);
        RootPanel.get().add(inputTextBox);
        RootPanel.get().add(testPerformance);
        RootPanel.get().add(testListPerformance);
        RootPanel.get().add(testSetPerformance);
        RootPanel.get().add(table);
    }

    private void initTableColumns(CellTable<PerformanceTestResults> table) {

        // Create list columns
        TextColumn<PerformanceTestResults> listOverallColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getListResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getListResults().getOverallTime());
            }
        };

        TextColumn<PerformanceTestResults> listCreateColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getListResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getListResults().getCreateTime());
            }
        };

        TextColumn<PerformanceTestResults> listFindColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getListResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getListResults().getFindTime());
            }
        };

        TextColumn<PerformanceTestResults> listDeleteColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getListResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getListResults().getDeleteTime());
            }
        };

        // Create set columns
        TextColumn<PerformanceTestResults> setOverallColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getSetResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getSetResults().getOverallTime());
            }
        };

        TextColumn<PerformanceTestResults> setCreateColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getSetResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getSetResults().getCreateTime());
            }
        };

        TextColumn<PerformanceTestResults> setFindColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getSetResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getSetResults().getFindTime());
            }
        };

        TextColumn<PerformanceTestResults> setDeleteColumn = new TextColumn<PerformanceTestResults>() {

            @Override
            public String getValue(PerformanceTestResults performanceTestResults) {

                if (performanceTestResults.getSetResults() == null) {
                    return "Not Run";
                }

                return String.valueOf(performanceTestResults.getSetResults().getDeleteTime());
            }
        };

        // Add columns to table
        table.addColumn(listOverallColumn, "List Overall");
        table.addColumn(setOverallColumn, "Set Overall");
        table.addColumn(listCreateColumn, "List Create");
        table.addColumn(setCreateColumn, "Set Create");
        table.addColumn(listFindColumn, "List Find");
        table.addColumn(setFindColumn, "Set Find");
        table.addColumn(listDeleteColumn, "List Delete");
        table.addColumn(setDeleteColumn, "Set Delete");
    }

    @Override
    public void onClick(ClickEvent event) {

        int numberOfItems = 0;

        try {
            numberOfItems = Integer.parseInt(inputTextBox.getText());
        } catch (NumberFormatException nfex) {
            Window.alert("Please use a valid Integer for the number of elements!");
            return;
        }

        if (event.getSource().equals(testPerformance)) {
            results.add(new PerformanceTestResults(testListPerformance(numberOfItems),
                    testSetPerformance(numberOfItems)));
        } else if (event.getSource().equals(testListPerformance)) {
            results.add(new PerformanceTestResults(testListPerformance(numberOfItems), null));
        }
        if (event.getSource().equals(testSetPerformance)) {
            results.add(new PerformanceTestResults(null, testSetPerformance(numberOfItems)));
        }
    }

    private TimeResult testListPerformance(int numberOfItems) {

        // Start the overall timer
        Date overallTimeStart = new Date(System.currentTimeMillis());

        // Create test objects
        Date createTimeStart = new Date(System.currentTimeMillis());

        List<Employee> employeesList = new ArrayList<Employee>();

        for (int i = 0; i < numberOfItems; i++) {
            employeesList.add(new Employee("" + (i + 1)));
        }

        Date createTimeStop = new Date(System.currentTimeMillis());

        // Find each object in the list
        Date findTimeStart = new Date(System.currentTimeMillis());

        for (int i = 0; i < numberOfItems; i++) {
            Employee employeeToFind = new Employee("" + (i + 1));

            if (!employeesList.contains(employeeToFind)) {
                throw new RuntimeException("Employee did not exist in the list!");
            }
        }

        Date findTimeStop = new Date(System.currentTimeMillis());

        // Delete every second item in the list
        Date deleteTimeStart = new Date(System.currentTimeMillis());

        for (int i = 0; i < numberOfItems; i += 2) {
            Employee employeeToDelete = new Employee("" + (i + 1));

            if (!employeesList.remove(employeeToDelete)) {
                throw new RuntimeException("Employee did not exist in the list!");
            }
        }

        Date deleteTimeStop = new Date(System.currentTimeMillis());

        // Stop the overall timer
        Date overallTimeEnd = new Date(System.currentTimeMillis());

        // Calculate durations
        long overallTime = overallTimeEnd.getTime() - overallTimeStart.getTime();
        long createTime = createTimeStop.getTime() - createTimeStart.getTime();
        long findTime = findTimeStop.getTime() - findTimeStart.getTime();
        long deleteTime = deleteTimeStop.getTime() - deleteTimeStart.getTime();

        // Return the result
        return new TimeResult(createTime, findTime, deleteTime, overallTime);
    }

    private TimeResult testSetPerformance(int numberOfItems) {

        // Start the overall timer
        Date overallTimeStart = new Date(System.currentTimeMillis());

        // Create test objects
        Date createTimeStart = new Date(System.currentTimeMillis());

        Set<Employee> employeesList = new HashSet<Employee>();

        for (int i = 0; i < numberOfItems; i++) {
            employeesList.add(new Employee("" + (i + 1)));
        }

        Date createTimeStop = new Date(System.currentTimeMillis());

        // Find each object in the list
        Date findTimeStart = new Date(System.currentTimeMillis());

        for (int i = 0; i < numberOfItems; i++) {
            Employee employeeToFind = new Employee("" + (i + 1));

            if (!employeesList.contains(employeeToFind)) {
                throw new RuntimeException("Employee did not exist in the list!");
            }
        }

        Date findTimeStop = new Date(System.currentTimeMillis());

        // Delete every second item in the list
        Date deleteTimeStart = new Date(System.currentTimeMillis());

        for (int i = 0; i < numberOfItems; i += 2) {
            Employee employeeToDelete = new Employee("" + (i + 1));

            if (!employeesList.remove(employeeToDelete)) {
                throw new RuntimeException("Employee did not exist in the list!");
            }
        }

        Date deleteTimeStop = new Date(System.currentTimeMillis());

        // Stop the overall timer
        Date overallTimeEnd = new Date(System.currentTimeMillis());

        // Calculate durations
        long overallTime = overallTimeEnd.getTime() - overallTimeStart.getTime();
        long createTime = createTimeStop.getTime() - createTimeStart.getTime();
        long findTime = findTimeStop.getTime() - findTimeStart.getTime();
        long deleteTime = deleteTimeStop.getTime() - deleteTimeStart.getTime();

        // Return the result
        return new TimeResult(createTime, findTime, deleteTime, overallTime);
    }
}
