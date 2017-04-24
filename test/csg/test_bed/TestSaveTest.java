/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dsli
 */
public class TestSaveTest {
    
    public TestSaveTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of testBuildHookComponents method, of class TestSave.
     */
    @Test
    public void testTestBuildHookComponents() {
        System.out.println("testBuildHookComponents");
        TestSave instance = new TestSave();
        instance.testBuildHookComponents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testSaveData method, of class TestSave.
     */
    @Test
    public void testTestSaveData() {
        System.out.println("testSaveData");
        TestSave instance = new TestSave();
        instance.testSaveData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildCellText method, of class TestSave.
     */
    @Test
    public void testBuildCellText() {
        System.out.println("buildCellText");
        int militaryHour = 0;
        String minutes = "";
        TestSave instance = new TestSave();
        String expResult = "";
        String result = instance.buildCellText(militaryHour, minutes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
