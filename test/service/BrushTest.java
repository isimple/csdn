/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import domain.ResourceInfo;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yang
 */
public class BrushTest {

    public BrushTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLogin() {
        System.out.println("login");
        Brush instance = new Brush();
        boolean expResult = true;
        boolean result = instance.login();
        assertEquals(expResult, result);
    }

    


}