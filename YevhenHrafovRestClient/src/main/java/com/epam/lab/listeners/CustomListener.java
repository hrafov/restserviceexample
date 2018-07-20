package com.epam.lab.listeners;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class CustomListener implements ITestListener, IReporter {
	
	private static final Logger log = Logger.getLogger(CustomListener.class);
    @Override
    public void onTestStart(ITestResult iTestResult) {
    	log.info("test starts " + iTestResult.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    	log.info("test passed " + iTestResult.getTestName() + " during time " + (iTestResult.getEndMillis() - iTestResult.getStartMillis()));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
    	log.info("test failed " + iTestResult.getTestName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    	log.info("test skipped " + iTestResult.getTestName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    	log.info("test failed but within success percentage: " + iTestResult.getTestName());
    }

    @Override
    public void onStart(ITestContext iTestContext) {
    	log.info("test starts " + iTestContext.getName() + " on " + iTestContext.getStartDate());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
    	log.info("Passed tests: " + iTestContext.getPassedTests());
    	log.info("Failed tests:" + iTestContext.getFailedTests());
    }

    @Override
    public void generateReport(List<XmlSuite> list, List<ISuite> list1, String s) {        
        for (ISuite suite : list1) {            
            String suiteName = suite.getName();            
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext tc = sr.getTestContext();
                log.info(
                        "Passed tests for suite '" + suiteName + "' is - " + tc.getPassedTests().getAllResults().size());
                log.info(
                        "Failed tests for suite '" + suiteName + "' is:" + tc.getFailedTests().getAllResults().size());
                log.info("Skipped tests for suite '" + suiteName + "' is:"
                        + tc.getSkippedTests().getAllResults().size());
            }
        }
    }
}