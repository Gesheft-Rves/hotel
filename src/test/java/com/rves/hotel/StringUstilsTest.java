package com.rves.hotel;

import com.rves.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUstilsTest {

    @Test
    public void test(){
        String empty1 = "";
        String empty2 = null;
        String notEmpty = "Not empty";

        Assert.assertTrue(StringUtils.isEmpty(empty1));
        Assert.assertTrue(StringUtils.isEmpty(empty2));
        Assert.assertFalse(StringUtils.isEmpty(notEmpty));
    }
}
