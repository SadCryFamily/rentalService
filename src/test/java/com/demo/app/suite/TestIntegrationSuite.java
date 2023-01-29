package com.demo.app.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;

@RunWith(org.junit.runners.Suite.class)
@SelectPackages({"com.demo.app.controller"})
@Suite
public class TestIntegrationSuite {
}
