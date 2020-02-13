package com.changgou.common.util;

import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Haotian
 * @Date: 2020/2/13 20:14
 * @Description: 取得给定汉字串的首字母串, 即声母串  注：只支持GB2312字符集中的汉字
 */
public class ChineseCharToEn {
    private static class SingletonClassInstance {
        private static final ChineseCharToEn INSTANCE = new ChineseCharToEn();
    }

    public static ChineseCharToEn getInstance() {
        return SingletonClassInstance.INSTANCE;
    }

    private ChineseCharToEn() {

    }

    private final static int[] LI_SEC_POS_VALUE =
            {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212,
                    3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925,
                    5249, 5590};

    private final static String[] LC_FIRST_LETTER =
            {"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o",
                    "p", "q", "r", "s", "t", "w", "x", "y", "z"};

    /**
     * 取得给定汉字串的首字母串,即声母串
     *
     * @param str 给定汉字串
     * @return 声母串
     */
    public String getAllFirstLetter(String str) {
        if (StrUtil.isBlank( str )) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append( this.getFirstLetter( str.substring( i, i + 1 ) ) );
        }

        return sb.toString();
    }

    /**
     * 取得给定汉字的首字母,即声母
     *
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    private String getFirstLetter(String chinese) {
        if (StrUtil.isBlank( chinese )) {
            return "";
        }
        chinese = this.conversionStr( chinese, "GB2312", "ISO8859-1" );

        // 判断是不是汉字
        if (chinese.length() > 1) {
            int liSectorCode = (int) chinese.charAt( 0 ); // 汉字区码
            int liPositionCode = (int) chinese.charAt( 1 ); // 汉字位码
            liSectorCode = liSectorCode - 160;
            liPositionCode = liPositionCode - 160;
            int liSecPosCode = liSectorCode * 100 + liPositionCode; // 汉字区位码
            if (liSecPosCode > 1600 && liSecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (liSecPosCode >= LI_SEC_POS_VALUE[i]
                            && liSecPosCode < LI_SEC_POS_VALUE[i + 1]) {
                        chinese = LC_FIRST_LETTER[i];
                        break;
                    }
                }
            } else
            // 非汉字字符,如图形符号或ASCII码
            {
                chinese = this.conversionStr( chinese, "ISO8859-1", "GB2312" );
                chinese = chinese.substring( 0, 1 );
            }
        }

        return chinese;
    }

    /**
     * 字符串编码转换
     *
     * @param str           要转换编码的字符串
     * @param charsetName   原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String( str.getBytes( charsetName ), toCharsetName );
        } catch (UnsupportedEncodingException ex) {
            System.out.println( "字符串编码转换异常：" + ex.getMessage() );
        }
        return str;
    }

    /**
     * 获取字符串大写首字母
     *
     * @param str 给定的字符串
     * @return 大写首字母
     */
    public String getFirstCapitalLetter(String str) {
        if (StrUtil.isEmpty( str )) {
            return "";
        }
        String firstLetter = this.getFirstLetter( str );

        return StrUtil.upperFirst( firstLetter );
    }

    public static void main(String[] args) {
        ChineseCharToEn cte = new ChineseCharToEn();
        System.out.println( "获取拼音首字母：" + cte.getAllFirstLetter( "测试功能" ) );
        String firstCapitalLetter = cte.getFirstCapitalLetter( "海尔" );
        System.out.println( "大写首字母为：" + firstCapitalLetter );
    }
}
