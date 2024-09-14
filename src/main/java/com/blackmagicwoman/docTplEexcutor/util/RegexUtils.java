package com.blackmagicwoman.docTplEexcutor.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**匹配段落下标*/
    public static String arrayRegex="^\\$\\(\\s*(\\w+(\\[\\d+])?(\\.\\w+(\\[\\d+])?)*)\\s*(,\\s*\\d+\\s*){0,2}\\)\\[\\{";
    /**匹配下标*/
    public static String indexRegex="(,\\s*\\d+\\s*){1,2}";
    /**匹配段落存在表达式*/
    public static String existsRegex="^\\$(?i)Exists\\(([^${}]*)\\)\\[\\{";
    /**字符串匹配表达式**/
    public static String argRegex="(?<=(\\b|\\W))[0-9A-Za-z_]*[A-Za-z][0-9A-Za-z_]*(?=(\\W|\\b))";
    /**匹配标签${}表达式**/
    public static String strRegexThree="\\$\\{[^${}]*?}";
    /**匹配标签@{}表达式**/
    public static String imgRegexThree="@\\{[^@{}]*?}";
    /**字符串匹配表达式 去掉字符串当中的\'转义字符**/
    public static String strRegexTwo="'[^']*'?";
    /**匹配字符串数据，去除标签当中的字符串**/
    public static String strRegexOne="\"[^\"]*\"?";
    /**
     * 公有方法，字符串当中的标签
     */
    public static String argMatcher(String tagExp, Set<String> set){
        /***剩余部分字符串进行匹配*/
        Pattern pattern = Pattern.compile(argRegex);
        Matcher matcher =pattern.matcher(tagExp);
        while (matcher.find()){
            /**获取标签*/
            String tagName =matcher.group().trim();
            /**添加标签*/
            if(set!=null){
                set.add(tagName);
            }
            tagExp=tagExp.replaceFirst(argRegex,"");
            matcher =pattern.matcher(tagExp);
        }
        return tagExp;
    }

    /**
     * 匹配类成员标签
     * */
    public static String argcRegex="(?<=(\\b|\\W))([0-9A-Za-z_]*[A-Za-z][0-9A-Za-z_]*(\\[\\d+])?)([.][0-9A-Za-z_]*[A-Za-z][0-9A-Za-z_]*(\\[\\d+])?)+(?=(\\W|\\b))";
    /**
     * 公有方法，字符串当中的标签
     */
    public static String argcMapMatcher(String tagExp,Set<String> set){
        /***剩余部分字符串进行匹配*/
        Pattern pattern = Pattern.compile(argcRegex);
        Matcher matcher =pattern.matcher(tagExp);
        while (matcher.find()){
            /**获取标签*/
            String tagName =matcher.group().trim();
            set.add(tagName);//类成员引用名称
            tagExp=tagExp.replace(tagName,"");
            matcher =pattern.matcher(tagExp);
        }
        return tagExp;
    }

    /**function()匹配表达式*/
    public static String functionRegex="(?<=\\W|^)([0-9A-Za-z]*[A-Za-z][0-9A-Za-z]*)\\((.*)\\)(?=\\W|$)";
    /**
     * 公有方法，匹配函数function()
     */
    public static String funMatcher(String tagExp,Set<String> set){
        Pattern pattern = Pattern.compile(functionRegex);
        Matcher matcher =pattern.matcher(tagExp);
        while(matcher.find()){
            /**获取function()函数名称*/
            String function =matcher.group();
            String funName =matcher.group(1);
            String funBody=function.substring(funName.length());
            /**去掉函数名*/
            tagExp=tagExp.replace(function,funBody);
            matcher =pattern.matcher(tagExp);
        }
        return tagExp;
    }


    /**
     * 公有方法，匹配dicDisp(tagName,tagType)
     */
    /**dicDisp()匹配表达式*/
    public static String dicDispRegex="(?<=\\W|^)tagDisp\\(\\s*([0-9A-Za-z._]+)\\s*,\\s*'(\\w+)'\\s*\\)(?=\\W|$)";

    public static String tagDispMatcher(String tagExp,int groupId){
        String tagValue=null;
        /***剩余部分字符串进行匹配*/
        Pattern pattern = Pattern.compile(dicDispRegex);
        Matcher matcher =pattern.matcher(tagExp.trim());
        if (matcher.find()){
            /**获取标签*/
            tagValue=matcher.group(groupId);
        }
        return tagValue;
    }

    /**
     * 公有方法，匹配字符串数据，去除标签当中的字符串
     */
    public static String strMatcher(String tagExp,Set<String> set){
        //首先去掉字符串当中在\"转义字符串
        tagExp =tagExp.replace("\\\"","");
        Pattern pattern = Pattern.compile(strRegexOne);
        Matcher matcher =pattern.matcher(tagExp);
        while(matcher.find()){
            String txt =matcher.group();
            /**去除字符串数据*/
            tagExp=tagExp.replace(txt,"");
            matcher =pattern.matcher(tagExp);
        }
        //首先去掉字符串当中在\'转义字符串
        tagExp =tagExp.replace("'\\'","");
        Pattern pattern_1 = Pattern.compile(strRegexTwo);
        Matcher matcher_1 =pattern_1.matcher(tagExp);
        while(matcher_1.find()){
            String txt =matcher_1.group();
            /**去除字符串数据*/
            tagExp=tagExp.replace(txt,"");
            matcher_1=pattern_1.matcher(tagExp);
        }
        return tagExp;
    }


    /**
     * 公有方法，匹配标签表达式${}, 返回标签表达式${}列表
     */
    public static List<String> matcher(String text){
        List<String> list = new ArrayList<>();
        strMatcher(list,text);
        return imgTagMatcher(list,text);
    }

    /**
     * 公有方法，匹配标签表达式${}, 返回标签表达式${}列表
     */
    public static List<String> strMatcher(List<String> list,String text){
        Pattern pattern = Pattern.compile(strRegexThree);
        Matcher matcher =pattern.matcher(text);
        while(matcher.find()){
            String txt =matcher.group();
            list.add(txt);
        }
        return list;
    }

    /**
     * 公有方法，匹配标签表达式@{}, 返回标签表达式@{}列表
     */
    public static List<String> imgTagMatcher(List<String> list,String text){
        Pattern pattern = Pattern.compile(imgRegexThree);
        Matcher matcher =pattern.matcher(text);
        while(matcher.find()){
            String txt =matcher.group();
            list.add(txt);
        }
        return list;
    }

    /**
     * 匹配图片标签 image or image(1,2)
     */
    public static String imageRegex="(?<=(\\b|\\w))([0-9A-Za-z_]*[A-Za-z][0-9A-Za-z_]*(\\[\\d+])?)"+
            "([.][0-9A-Za-z_]*[A-Za-z][0-9A-Za-z_]*(\\[\\d+])?)*((\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\))||(?=(\\w|\\b)))";
    /**
     * 共有方法，匹配标签当中的字符串
     */
    public static Set<String> imgeMatcher(String tagExp){
        Set<String>set = new HashSet<>();
        /***剩余部分字符串进行匹配*/
        Pattern pattern = Pattern.compile(imageRegex);
        Matcher matcher =pattern.matcher(tagExp);
        while(matcher.find()){
            /**获取标签*/
            String tagName = matcher.group().trim();
            set.add(tagName);
            tagExp = tagExp.replace(tagName,"");
            matcher = pattern.matcher(tagExp);
        }
        return set;
    }

    /**
     * 匹配图片高度
     */
    public static String imageWidthAndHeightRegex="(\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\))";
    /**
     * 匹配图片高度
     */
    public static String imageWidthAndHeightMatcher(String tagExp){
        String str = "";
        /***匹配*/
        Pattern pattern = Pattern.compile(imageWidthAndHeightRegex);
        Matcher matcher =pattern.matcher(tagExp);
        if(matcher.find()){
            /**获取高度**/
            return matcher.group().trim();
        }
        return str;
    }

}
