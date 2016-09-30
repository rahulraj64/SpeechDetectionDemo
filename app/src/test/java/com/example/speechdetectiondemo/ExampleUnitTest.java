package com.example.speechdetectiondemo;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testCompareStrings() throws Exception {

        LevenshteinDistance distance = new LevenshteinDistance();
        int accuracy = distance.apply("this is a frog", "this is frog");
        System.out.println("accuracy = " + accuracy);
    }
}