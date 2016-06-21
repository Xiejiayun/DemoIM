package test.com.netease.corp.hzxiejiayun.common.util;

import com.netease.corp.hzxiejiayun.common.util.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * StringUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 20, 2016</pre>
 */
public class StringUtilsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: trim(String str)
     */
    @Test
    public void testTrim() throws Exception {
        String s = StringUtils.trim(" ");
        Assert.assertEquals(s, "");
        s = StringUtils.trim(" abc");
        Assert.assertEquals(s, "abc");
    }

    /**
     * Method: isEmpty(String str)
     */
    @Test
    public void testIsEmpty() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: isBlank(String str)
     */
    @Test
    public void testIsBlank() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


} 
