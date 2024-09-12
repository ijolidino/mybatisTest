package com.blackmagicwoman.docTplEexcutor.txbx;

import com.docTplEexcutor.pojo.ImageByte;
import com.docTplEexcutor.util.QNameUtils;
import com.docTplEexcutor.util.RegexUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlToken;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class TxBxRun {
    /**所属文档对象*/
    private XWPFDocument doc;
    /**段落集合*/
    private XmlObject run;

    /**非公共构造函数*/
    protected TxBxRun(XmlObject run,XWPFDocument doc){
        this.doc = doc;
        this.run = run;
    }

    /**获取文本值*/
    public String getText(){
        XmlCursor cursor = run.newCursor();
        if(cursor.toFirstChild()){
            if(QNameUtils.isText(cursor.getName())){
                String textValue = cursor.getTextValue();
                cursor.dispose();
                return textValue;
            }
        }
        while (cursor.toNextSibling()){
            if(QNameUtils.isText(cursor.getName())){
                String textValue = cursor.getTextValue();
                cursor.dispose();
                return textValue;
            }
        }
        return null;
    }

    /**获取文本值*/
    public void setText(String textValue){
        XmlCursor cursor = run.newCursor();
        if(cursor.toFirstChild()){
            if(QNameUtils.isText(cursor.getName())){
                cursor.setTextValue(textValue);
                cursor.dispose();
                return;
            }
        }
        while (cursor.toNextSibling()){
            if(QNameUtils.isText(cursor.getName())){
                cursor.setTextValue(textValue);
                cursor.dispose();
                return;
            }
        }
    }

    public String toString(){
        return run.toString();
    }

    /***
     * 参数输入映射计算结果集
     * @param tagExp 标签表达式
     * @param map 标签值
     */
    public void runImageExp(String tagExp, Map<String,Object> map){
        String text = this.getText();
        /**去除@{和}*/
        String formula = tagExp.replace("@{","").replace("}","").trim();
        /**标签变量合集*/
        Set<String> set = RegexUtils.imgeMatcher(formula);
        for(String imgTagName : set){
            try{
                String widthAndHeight = RegexUtils.imageWidthAndHeightMatcher(imgTagName);
                imgTagName = imgTagName.replace(widthAndHeight,"");
                ImageByte docImage =(ImageByte) map.get(imgTagName);
                InputStream inputStream = new ByteArrayInputStream(docImage.getImageByte());
                int width = 0;
                int height = 0;
                if(StringUtils.isNotEmpty(widthAndHeight)){
                    String []str = widthAndHeight.replace("(","").replace(")","").split(",");
                    width = Integer.valueOf(str[0]);
                    height= Integer.valueOf(str[1]);
                }
                //否则图片默认大小
                else{
                    BufferedImage image = ImageIO.read(inputStream);
                    width = image.getWidth();//图片像素转成英制
                    height = image.getHeight();//图片像素转成英制
                    inputStream.reset();
                }
                this.addPicture(inputStream,docImage.getFileType(), Units.pixelToEMU(width),Units.pixelToEMU(height));
            }catch (InvalidFormatException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        this.setText(text.replace(tagExp,""));
    }
    /**添加图片*/
    public void addPicture(InputStream pictureData,int pictureType,int width,int height) throws InvalidFormatException {
        String relationId = doc.addPictureData(pictureData,pictureType);
        this.createPicture(relationId,width,height);
    }

    public void createPicture(String relationId,int width,int height){
        String bligpId ="1";
        String picXml="" +
                "<w:drawing xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"" +
                " xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\"" +
                " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">" +
                "    <wp:inline distT=\"0\" distB=\"0\" distL=\"114300\" distR=\"114300\">" +
                "        <wp:extent cx=\"4083685\" cy=\"2906395\"/>" +
                "        <wp:effectExtent l=\"0\" t=\"0\" r=\"5715\" b=\"1905\"/>" +
                "        <wp:docPr id=\"1\" name=\"图片 1\" descr=\"myImage\"/>" +
                "        <wp:cNvGraphicFramePr>" +
                "            <a:graphicFrameLocks xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" noChangeAspect=\"1\"/>" +
                "        </wp:cNvGraphicFramePr>" +
                "        <a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "            <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "                <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "                    <pic:nvPicPr>" +
                "                        <pic:cNvPr id=\""+bligpId+"\" name=\"图片 1\" descr=\"myImage\"/>" +
                "                        <pic:cNvPicPr>" +
                "                            <a:picLocks noChangeAspect=\"1\"/>" +
                "                        </pic:cNvPicPr>" +
                "                    </pic:nvPicPr>" +
                "                    <pic:blipFill>" +
                "                        <a:blip r:embed=\""+relationId+"\"/>" +
                "                        <a:stretch>" +
                "                            <a:fillRect/>" +
                "                        </a:stretch>" +
                "                    </pic:blipFill>" +
                "                    <pic:spPr>" +
                "                        <a:xfrm>" +
                "                            <a:off x=\"0\" y=\"0\"/>" +
                "                            <a:ext cx=\""+width+"\" cy=\""+height+"\"/>" +
                "                        </a:xfrm>" +
                "                        <a:prstGeom prst=\"rect\">" +
                "                            <a:avLst/>" +
                "                        </a:prstGeom>" +
                "                    </pic:spPr>" +
                "                </pic:pic>" +
                "            </a:graphicData>" +
                "        </a:graphic>" +
                "    </wp:inline>" +
                "</w:drawing>";
        try{
            XmlToken xmlToken = XmlToken.Factory.parse(picXml);
            run.set(xmlToken);
        }catch (XmlException e){
            e.printStackTrace();
        }
    }


}
