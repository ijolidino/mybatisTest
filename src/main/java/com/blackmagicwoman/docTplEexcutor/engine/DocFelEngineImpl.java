package com.blackmagicwoman.docTplEexcutor.engine;

import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import com.docTplEexcutor.util.RegexUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DocFelEngineImpl extends FelEngineImpl {
    /**标签转码*/
    private Map<String,Object> dicMap= new HashMap<>();
    /**要替换在标签*/
    private Map<String,Object> sourceMap;

    public DocFelEngineImpl(Map<String,Object>dicMap, Map sourceMap){
        super();
        this.dicMap.putAll(dicMap);
        this.sourceMap=sourceMap;
        super.addFun(dateFormat);
        super.addFun(tagDisp);
        super.addFun(nullText);
        super.addFun(sysDate);
        super.addFun(sysYear);
        super.addFun(sysMonth);
        super.addFun(sysDay);
        super.addFun(currencyFormat);//千分符转换
        super.addFun(percentage);//百分比转换
        super.addFun(roundNumber);//万元近似转换
        super.addFun(roundArea);//万元近似转换
        super.addFun(toNumber);//数字转化
    }

    /**自定义转码函数*/
    @Override
    public Object eval(String formula) {
        String funName=RegexUtils.tagDispMatcher(formula,0);
        while(funName!=null){
            String tagName=RegexUtils.tagDispMatcher(formula,1);
            String tagType=RegexUtils.tagDispMatcher(formula,2);
            Object object = this.dicMap.get(tagType);
            Object tagValue = this.sourceMap.get(tagName);
            if(object!=null){
                Map<String,Object> map=(Map<String,Object>)object;
                if(tagValue!=null){
                    String []tagArray = tagValue.toString().split(",");
                    String tagValues ="";
                    for(int i=0;i<tagArray.length;i++){
                        if(map.containsKey(tagArray[i])&&map.get(tagArray[i])!=null){
                            if(i==0){
                                tagValues = "" + map.get(tagArray[i]);
                            }else{
                                tagValues = tagValues+","+map.get(tagArray[i]);
                            }
                        }else{
                            if(i==0){
                                tagValues = tagArray[i];
                            }else{
                                tagValues = tagValues+","+tagArray[i];
                            }
                        }
                    }
                    formula=formula.replace(funName,"tagDisp('"+tagValues+"')");
                }
            }else{
                //否则原样保留
                formula=formula.replace(funName,"tagDisp("+tagName+")");
            }
            funName=RegexUtils.tagDispMatcher(formula,0);
        }
        return super.eval(formula);
    }

    @Override
    public FelContext getContext() {
        return super.getContext();
    }
    /**
     *默认值设置
     */
    Function nullText = new CommonFunction(){
        @Override
        public String getName(){
            return "nullText";
        }
        @Override
        public Object call(Object[]arguments){
            if(arguments!=null&&arguments.length>0){
                try{
                    if(arguments.length==1){
                        String txt = arguments[0].toString();
                        if(txt==null||"".equals(txt.trim())){
                            return "/";
                        }else{
                            return txt;
                        }
                    }else if(arguments.length==2){
                        String txt = arguments[0].toString();
                        String defaultValue = arguments[1].toString();
                        if(txt==null||"".equals(txt.trim())){
                            return defaultValue;
                        }else{
                            return txt;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return "";
        }
    };

    /**
     *日期格是转换
     */
    Function dateFormat = new CommonFunction(){
        @Override
        public String getName(){
            return "dateFormat";
        }
        @Override
        public Object call(Object[]arguments){
            Object msg="";
            DateFormat format;
            DateFormat oldformat;
            DateFormat newFormat;
            Date date;
            String oldFormatStr="";
            String newFormatStr="";
            if(arguments!=null&&arguments.length>0){
                try{
                    String dateString = arguments[0].toString();
                    if(dateString==null||dateString.equals("")){
                        return "";
                    }
                    if(arguments.length==1){

                        /***当参数一个时默认输出格式yyyy年MM月dd日*/
                        format    = new SimpleDateFormat("yyyy-MM-dd");
                        newFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        date=format.parse(dateString);
                        msg =newFormat.format(date);
                    }else if(arguments.length==2){
                        /***当参数为2个时，默认第二个参数为需要的日期格式*/
                        format       = new SimpleDateFormat("yyyy-MM-dd");
                        newFormatStr = arguments[1].toString();
                        newFormatStr=newFormatStr.replace("Y","y");
                        newFormatStr=newFormatStr.replace("m","M");
                        newFormatStr=newFormatStr.replace("D","d");
                        newFormat    = new SimpleDateFormat(newFormatStr);
                        date=format.parse(dateString);
                        msg =newFormat.format(date);
                    }else if(arguments.length==3){
                        /**当参数为3个时，默认第二个参数为输入的日期格式，第三个格式为输出的日期格式*/
                        oldFormatStr = arguments[1].toString();
                        oldFormatStr=oldFormatStr.replace("Y","y");
                        oldFormatStr=oldFormatStr.replace("m","M");
                        oldFormatStr=oldFormatStr.replace("D","d");
                        newFormatStr = arguments[2].toString();
                        newFormatStr=newFormatStr.replace("Y","y");
                        newFormatStr=newFormatStr.replace("m","M");
                        newFormatStr=newFormatStr.replace("D","d");
                        oldformat = new SimpleDateFormat(oldFormatStr);
                        newFormat = new SimpleDateFormat(newFormatStr);
                        date=oldformat.parse(dateString);
                        msg =newFormat.format(date);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return msg;
        }
    };

    /**
     *日期格是转换
     */
    Function sysDate = new CommonFunction(){
        @Override
        public String getName(){
            return "sysDate";
        }
        @Override
        public Object call(Object[]arguments){
            Object msg="";
            if(arguments==null||arguments.length==0){
                try{
                    DateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
                    Date date =new Date();
                    return format.format(date);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return msg;
        }
    };
    /**
     *获取系统年份
     */
    Function sysYear = new CommonFunction(){
        @Override
        public String getName(){
            return "sysYear";
        }
        @Override
        public Object call(Object[]arguments){
            Object msg="";
            try{
                DateFormat format=new SimpleDateFormat("yyyy");
                Date date =new Date();
                return format.format(date);
            }catch (Exception e){
                e.printStackTrace();
            }
            return msg;
        }
    };

    /**
     *获取系统月份
     */
    Function sysMonth = new CommonFunction(){
        @Override
        public String getName(){
            return "sysMonth";
        }
        @Override
        public Object call(Object[]arguments){
            Object msg="";
            try{
                DateFormat format=new SimpleDateFormat("MM");
                Date date =new Date();
                return format.format(date);
            }catch (Exception e){
                e.printStackTrace();
            }
            return msg;
        }
    };
    /**
     *获取系统日
     */
    Function sysDay = new CommonFunction(){
        @Override
        public String getName(){
            return "sysDay";
        }
        @Override
        public Object call(Object[]arguments){
            Object msg="";
            try{
                DateFormat format=new SimpleDateFormat("dd");
                Date date =new Date();
                return format.format(date);
            }catch (Exception e){
                e.printStackTrace();
            }
            return msg;
        }
    };

    /**
     *码值转换
     */
    Function tagDisp = new CommonFunction(){
        @Override
        public String getName(){
            return "tagDisp";
        }
        @Override
        public Object call(Object[]arguments){
            Object msg="";
            if(arguments!=null&&arguments.length==1){
                try{
                    msg = arguments[0].toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return msg;
        }
    };

    /**
     *千分符转换
     */
    Function currencyFormat = new CommonFunction(){
        @Override
        public String getName(){
            return "currencyFormat";
        }
        @Override
        public Object call(Object[]arguments){
            Object currency="";
            if(arguments!=null&&arguments.length==1){
                try{
                    BigDecimal number;
                    if(arguments[0] instanceof BigDecimal){
                        number =(BigDecimal)arguments[0];
                    }else{
                        String data = arguments[0].toString();
                        if(StringUtils.isNotEmpty(data)){
                            number = new BigDecimal(data);
                        }else{
                            return currency;
                        }
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
                    currency=decimalFormat.format(number);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return currency;
        }
    };


    /**
     *百分比转换
     */
    Function percentage = new CommonFunction(){
        @Override
        public String getName(){
            return "percentage";
        }
        @Override
        public Object call(Object[]arguments){
            //默认转成百分比
            if(arguments!=null&&arguments.length==1){
                try{
                    String data = arguments[0].toString();
                    if(data==null||data.equals("")){
                        return "";
                    }
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    nt.setMinimumFractionDigits(0);
                    return nt.format(Double.parseDouble(data));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(arguments!=null&&arguments.length==2){
                try{
                    String data = arguments[0].toString();//传入的数字
                    if(data==null||data.equals("")){
                        return "";
                    }
                    int level = (int)arguments[1];//位数
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    //设置百分数精确度2即保留两位小数
                    nt.setMinimumFractionDigits(level);
                    return nt.format(Double.parseDouble(data));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return "";
        }
    };

    Function toNumber  =new CommonFunction() {
        @Override
        public Object call(Object[]arguments) {
            if(arguments!=null&&arguments.length==1){
                String data = arguments[0].toString();
                return new BigDecimal(data);
            }
            return null;
        }

        @Override
        public String getName() {
            return "toNumber";
        }
    };

    /**
     *万元近似转换，并千分位表示  参照修改
     */
    Function roundNumber = new CommonFunction(){
        @Override
        public String getName(){
            return "roundNumber";
        }
        @Override
        public Object call(Object[]arguments){
            String rs="";
            if(arguments!=null&&arguments.length>=1){
                String data = arguments[0].toString();
                if(data==null&&data.equals("")){
                    return "";
                }
                BigDecimal bigDecimal = new BigDecimal(data);
                //默认除以10000并四舍五入
                BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
                // 默认保留两位小数
                String pattern = "###,##0.00";
                //第二个参数为需要保留的位数，默认保留2位小数点
                if(arguments.length==2){
                    Integer num = Integer.valueOf(arguments[1].toString());
                    pattern = "###,##0";//小于等于零时，默认整数
                    if(num>0){
                        pattern = "###,##0.";
                        for(int i=0;i<num;i++){
                            pattern = pattern +"0";
                        }
                    }
                }
                DecimalFormat formater = new DecimalFormat(pattern);
                // 四舍五入
                formater.setRoundingMode(RoundingMode.HALF_UP);
                // 格式化完成之后得出结果
                rs = formater.format(decimal);
            }
            else{

            }
            return rs;
        }
    };

    /**
     *面积近似转换，并千分位表示
     */
    Function roundArea = new CommonFunction(){
        @Override
        public String getName(){
            return "roundArea";
        }
        @Override
        public Object call(Object[]arguments){
            String rs="";
            if(arguments!=null&&arguments.length>=1){
                String data = arguments[0].toString();
                if(data==null&&data.equals("")){
                    return "";
                }
                BigDecimal decimal = new BigDecimal(data);
                // 默认保留两位小数
                String pattern = "###,##0.00";
                //第二个参数为需要保留的位数，默认保留2位小数点
                if(arguments.length==2){
                    Integer num = Integer.valueOf(arguments[1].toString());
                    pattern = "###,##0";//小于等于零时，默认整数
                    if(num>0){
                        pattern = "###,##0.";
                        for(int i=0;i<num;i++){
                            pattern = pattern +"0";
                        }
                    }
                }
                DecimalFormat formater = new DecimalFormat(pattern);
                // 四舍五入
                formater.setRoundingMode(RoundingMode.HALF_UP);
                // 格式化完成之后得出结果
                rs = formater.format(decimal);
            }
            else{

            }
            return rs;
        }
    };


}
