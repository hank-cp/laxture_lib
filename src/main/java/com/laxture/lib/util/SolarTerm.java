package com.laxture.lib.util;

/**
 * Created with IntelliJ IDEA.
 * User: yulongli
 * Date: 13-3-7
 * Time: 下午8:56
 * To change this template use File | Settings | File Templates.
 */

import java.util.Calendar;
import java.util.Date;

public class SolarTerm {

    /**
     * 数组存放每年的二十四节气对应的阳历日期,每年的二十四节气对应的阳历日期几乎固定，平均分布于十二个月中
     * 1月   小寒 大寒
     * 2月   立春 雨水
     * 3月   惊蛰 春分
     * 4月   清明 谷雨
     * 5月   立夏 小满
     * 6月   芒种 夏至
     * 7月   小暑 大暑
     * 8月   立秋 处暑
     * 9月   白露 秋分
     * 10月   寒露 霜降
     * 11月   立冬 小雪
     * 12月   大雪 冬至
     *
     * 节气无任何确定规律,所以只好存表,要节省空间，所以搞出这张表
     * 数据格式说明:
     *
     如1901年的节气为
     1月     	2月     3月   	4月    	5月   	6月   	7月    	8月   	9月    	10月  	11月   	12月
     6,21, 	4,19,  6,21, 	5,21, 	6,22, 	6,22, 	8,23, 	8,24, 	8,24, 	8,24, 	8,23, 	8,22

     9,6,  	11,4,  9,6,  	10,6,  	9,7,  	9,7,  	7,8,  	7,9,  	7,9, 	7,9, 	7,8, 	7,7

     *  上面第一行数据为每月节气对应日期,15减去每月第一个节气,每月第二个节气减去15得第二行
     *  这样每月两个节气对应数据都小于16,每月用一个字节存放,高位存放第一个节气数据,低位存放第二个节气的数据,可得下表
     *
     *  最权威的数据是通过紫金山天文台发布的数据为准。http://www.pmo.jsinfo.net/2009/24.htm
     *
     */


    static final int[] SOLOR_YEARS = {
            0x96B496A6,0x97977879,0x79697877,	//1901
            0x96A49696,0x97877979,0x79697878,	//1902
            0x96A58796,0x87877969,0x69697878,	//1903
            0x86A596A5,0x96978878,0x78797887,	//1904
            0x96B496A6,0x97977879,0x79697877,	//1905
            0x96A49696,0x97977979,0x79697878,	//1906
            0x96A58796,0x87877969,0x69697878,	//1907
            0x86A596A5,0x96978878,0x78697887,	//1908
            0x96B496A6,0x97977879,0x79697877,	//1909
            0x96A49696,0x97977979,0x79697878,	//1910
            0x96A58796,0x87877969,0x69697878,	//1911
            0x86A596A5,0x96978878,0x78697887,	//1912
            0x95B496A6,0x97977879,0x79697877,	//1913
            0x96B496A6,0x97977979,0x79697878,	//1914
            0x96A59796,0x97877979,0x69697878,	//1915
            0x96A596A5,0x96978878,0x78797787,	//1916
            0x95B496A6,0x96977879,0x78697887,	//1917
            0x96B496A6,0x97977979,0x79697877,	//1918
            0x96A59796,0x97877979,0x69697878,	//1919
            0x96A596A5,0x96978878,0x78797787,	//1920
            0x95B496A5,0x96977879,0x78697887,	//1921
            0x96B496A6,0x97977979,0x79697877,	//1922
            0x96A49696,0x97877979,0x69697878,	//1923
            0x96A596A5,0x96978878,0x78797787,	//1924
            0x95B496A5,0x96977879,0x78697887,	//1925
            0x96B496A6,0x97977879,0x79697877,	//1926
            0x96A49696,0x97877979,0x79697878,	//1927
            0x96A596A5,0x96968878,0x78788787,	//1928
            0x95B496A5,0x96978878,0x78797787,	//1929
            0x96B496A6,0x97977879,0x79697877,	//1930
            0x96A49696,0x97877979,0x79697878,	//1931
            0x96A596A5,0x96968878,0x78788787,	//1932
            0x95B496A5,0x96978878,0x78697887,	//1933
            0x96B496A6,0x97977879,0x79697877,	//1934
            0x96A49696,0x97977979,0x79697878,	//1935
            0x96A596A5,0x96968878,0x78788787,	//1936
            0x95B496A5,0x96978878,0x78697887,	//1937
            0x96B496A6,0x97977879,0x79697877,	//1938
            0x96A49696,0x97977979,0x79697878,	//1939
            0x96A596A5,0x96968878,0x78788787,	//1940
            0x95B496A5,0x96978878,0x78697887,	//1941
            0x96B496A6,0x97977879,0x79697877,	//1942
            0x96A49696,0x97977979,0x79697878,	//1943
            0x96A596A5,0xA6968878,0x78788787,	//1944
            0x95B496A5,0x96978878,0x78797787,	//1945
            0x95B496A6,0x97977879,0x78697877,	//1946
            0x96B496A6,0x97977979,0x79697878,	//1947
            0x96A5A6A5,0xA6968888,0x78788787,	//1948
            0xA5B496A5,0x96978879,0x78797787,	//1949
            0x95B496A5,0x96977879,0x78697877,	//1950
            0x96B496A6,0x97977979,0x79697878,	//1951
            0x96A5A6A5,0xA6968888,0x78788787,	//1952
            0xA5B496A5,0x96978878,0x78797787,	//1953
            0x95B496A5,0x96977879,0x78687887,	//1954
            0x96B496A6,0x97977879,0x79697877,	//1955
            0x96A5A5A5,0xA6968888,0x78788787,	//1956
            0xA5B496A5,0x96978878,0x78797787,	//1957
            0x95B496A5,0x96978878,0x78697887,	//1958
            0x96B496A6,0x97977879,0x79697877,	//1959
            0x96A4A5A5,0xA6968888,0x88788787,	//1960
            0xA5B496A5,0x96968878,0x78788787,	//1961
            0x96B496A5,0x96978878,0x78697887,	//1962
            0x96B496A6,0x97977879,0x79697877,	//1963
            0x96A4A5A5,0xA6968888,0x88788787,	//1964
            0xA5B496A5,0x96968878,0x78788787,	//1965
            0x95B496A5,0x96978878,0x78697887,	//1966
            0x96B496A6,0x97977879,0x79697877,	//1967
            0x96A4A5A5,0xA6A68888,0x88788787,	//1968
            0xA5B496A5,0x96968878,0x78788787,	//1969
            0x95B496A5,0x96978878,0x78697887,	//1970
            0x96B496A6,0x97977879,0x79697877,	//1971
            0x96A4A5A5,0xA6A68888,0x88788787,	//1972
            0xA5B596A5,0xA6968878,0x78788787,	//1973
            0x95B496A5,0x96978878,0x78697887,	//1974
            0x96B496A6,0x97977879,0x78697877,	//1975
            0x96A4A5B5,0xA6A68889,0x88788787,	//1976
            0xA5B496A5,0x96968888,0x78788787,	//1977
            0x95B496A5,0x96978878,0x78797887,	//1978
            0x96B496A6,0x96977879,0x78697877,	//1979
            0x96A4A5B5,0xA6A68888,0x88788787,	//1980
            0xA5B496A5,0xA6968888,0x78787787,	//1981
            0x95B496A5,0x96978878,0x78797787,	//1982
            0x95B496A5,0x96977879,0x78697877,	//1983
            0x96B4A5B5,0xA6A68788,0x88788787,	//1984
            0xA5B4A6A5,0xA6968888,0x78788787,	//1985
            0xA5B496A5,0x96978878,0x78797787,	//1986
            0x95B496A5,0x96978879,0x78697887,	//1987
            0x96B4A5B5,0xA6A68788,0x88788786,	//1988
            0xA5B4A5A5,0xA6968888,0x88788787,	//1989
            0xA5B496A5,0x96968878,0x78797787,	//1990
            0x95B496A5,0x86978878,0x78697887,	//1991
            0x96B4A5B5,0xA6A68788,0x88788786,	//1992
            0xA5B3A5A5,0xA6968888,0x88788787,	//1993
            0xA5B496A5,0x96968878,0x78788787,	//1994
            0x95B496A5,0x96978876,0x78697887,	//1995
            0x96B4A5B5,0xA6A68788,0x88788786,	//1996
            0xA5B3A5A5,0xA6A68888,0x88788787,	//1997
            0xA5B496A5,0x96968878,0x78788787,	//1998
            0x95B496A5,0x96978878,0x78697887,	//1999
            0x96B4A5B5,0xA6A68788,0x88788786,	//2000
            0xA5B3A5A5,0xA6A68888,0x88788787,	//2001
            0xA5B496A5,0x96968878,0x78788787,	//2002
            0x95B496A5,0x96978878,0x78697887,	//2003
            0x96B4A5B5,0xA6A68788,0x88788786,	//2004
            0xA5B3A5A5,0xA6A68888,0x88788787,	//2005
            0xA5B496A5,0xA6968888,0x78788787,	//2006
            0x95B496A5,0x96978878,0x78697887,	//2007
            0x96B4A5B5,0xA6A68788,0x87788786,	//2008
            0xA5B3A5B5,0xA6A68888,0x88788787,	//2009
            0xA5B496A5,0xA6968888,0x78788787,	//2010
            0x95B496A5,0x96978878,0x78797887,	//2011
            0x96B4A5B5,0xA5A68788,0x87788786,	//2012
            0xA5B3A5B5,0xA6A68788,0x88788787,	//2013
            0xA5B496A5,0xA6968888,0x78788787,	//2014
            0x95B496A5,0x96978878,0x78797787,	//2015
            0x95B4A5B4,0xA5A68788,0x87788786,	//2016
            0xA5C3A5B5,0xA6A68788,0x88788787,	//2017
            0xA5B4A6A5,0xA6968888,0x78788787,	//2018
            0xA5B496A5,0x96968878,0x78797787,	//2019
            0x95B4A5B4,0xA5A69787,0x87788786,	//2020
            0xA5C3A5B5,0xA6A68788,0x88788786,	//2021
            0xA5B4A5A5,0xA6968888,0x88788787,	//2022
            0xA5B496A5,0x96968878,0x78797787,	//2023
            0x95B4A5B4,0xA5A69787,0x87788796,	//2024
            0xA5C3A5B5,0xA6A68788,0x88788786,	//2025
            0xA5B3A5A5,0xA6A68888,0x88788787,	//2026
            0xA5B496A5,0x96968878,0x78788787,	//2027
            0x95B4A5B4,0xA5A69787,0x87788796,	//2028
            0xA5C3A5B5,0xA6A68788,0x88788786,	//2029
            0xA5B3A5A5,0xA6A68888,0x88788787,	//2030
            0xA5B496A5,0x96968878,0x78788787,	//2031
            0x95B4A5B4,0xA5A69787,0x87788796,	//2032
            0xA5C3A5B5,0xA6A68888,0x88788786,	//2033
            0xA5B3A5A5,0xA6A68878,0x88788787,	//2034
            0xA5B496A5,0xA6968888,0x78788787,	//2035
            0x95B4A5B4,0xA5A69787,0x87788796,	//2036
            0xA5C3A5B5,0xA6A68788,0x88788786,	//2037
            0xA5B3A5A5,0xA6A68888,0x88788787,	//2038
            0xA5B496A5,0xA6968888,0x78788787,	//2039
            0x95B4A5B4,0xA5A69787,0x87788796,	//2040
            0xA5C3A5B5,0xA5A68788,0x87788786,	//2041
            0xA5B3A5B5,0xA6A68888,0x88788787,	//2042
            0xA5B496A5,0xA6968888,0x78788787,	//2043
            0x95B4A5B4,0xA5A69787,0x87888796,	//2044
            0xA5C3A5B4,0xA5A68788,0x87788786,	//2045
            0xA5B3A5B5,0xA6A68788,0x88788787,	//2046
            0xA5B496A5,0xA6968888,0x78788787,	//2047
            0x95B4A5B4,0xA5A59787,0x87888696,	//2048
            0xA4C3A5A5,0xA5A69787,0x87788786,	//2049
            0xA5C3A5B5,0xA6A68788,0x78788787	//2050
    };



    private static final String[] SolarTerm = new String[] { "小寒", "大寒", "立春", "雨水",
            "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋",
            "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};

  /*  private final static int[] STermInfo = new int[] { 0, 21208, 42467, 63836, 85337,
        107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343,
        285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795,
        462224, 483532, 504758 };
    */

    public static String  getSoralTerm(Date Date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date);

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);

        return getSoralTerm(y, m, d);
    }



    public static String getSoralTerm(int y, int m, int d){

        String solarTerms = null;

        if (y < 1901 || y > 2050) return null;
        if (m < 1  || m > 12)  return null;

        int sy = y - 1901;

        int st = SOLOR_YEARS[sy * 3 + (m-1) / 4];

        int md = (st >> ((3-(m-1)%4)*8)) & 0xFF;

        if (15 - (md >> 4 ) == d){
            return SolarTerm[(m-1) * 2];
        }
        if ((md & 0x0F) + 15 == d){
            return SolarTerm[(m-1) * 2 + 1];
        }

        return  solarTerms;
    }


    /*public static String getSoralTerm(int y, int m, int d){
        String solarTerms = null;
        if (d == sTerm(y, (m - 1) * 2))
            solarTerms = SolarTerm[(m - 1) * 2];
        else if (d == sTerm(y, (m - 1) * 2 + 1))
            solarTerms = SolarTerm[(m - 1) * 2 + 1];
        else{
            //到这里说明非节气时间
        }
        return  solarTerms;
    }
    */


    //  ===== y年的第n个节气为几日(从0小寒起算)
 /*   private static int sTerm(int y, int n) {
        Calendar cal = Calendar.getInstance();
        cal.set(1900, 0, 6, 2, 5, 0);
        long temp = cal.getTime().getTime();
        cal.setTime(new Date(
           (long) ((31556925974.7 * (y - 1900) + STermInfo[n] * 60000L) + temp)));

        return cal.get(Calendar.DAY_OF_MONTH);
    }
*/
}
