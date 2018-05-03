package com.cudgal.utils.log4j.layout;


import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author alexandr_lenkov@epam.com
 * Date: 28.04.2018
 * Time: 13:36
 */
public class SCILayout extends PatternLayout{
    private String findRegExpList;
    private String maskRegExpList;
    private String regExpListDelimiter;

    public String getFindRegExpList() {
        return findRegExpList;
    }

    public void setFindRegExpList(String findRegExpList) {
        this.findRegExpList = findRegExpList;
    }

    public String getMaskRegExpList() {
        return maskRegExpList;
    }

    public void setMaskRegExpList(String maskRegExpList) {
        this.maskRegExpList = maskRegExpList;
    }

    public String getRegExpListDelimiter() {
        return regExpListDelimiter;
    }

    public void setRegExpListDelimiter(String regExpListDelimiter) {
        this.regExpListDelimiter = regExpListDelimiter;
    }

    @Override
    public String format(LoggingEvent event) {
        String result = super.format(event);

        List<String> findRegExpListAsObj = prepareFindRegExpList();
        List<String> maskRegExpListAsObj = prepareMaskRegExpList();

        if (findRegExpListAsObj != null
            && maskRegExpListAsObj != null
            && findRegExpListAsObj.size() > 0
            && findRegExpListAsObj.size() == maskRegExpListAsObj.size()) {
            for (int i = 0; i < findRegExpListAsObj.size(); i++) {
                result = mask(
                    result,
                    findRegExpListAsObj.get(i),
                    maskRegExpListAsObj.get(i)
                );
            }
        }
        return result;
    }

    private List<String> prepareFindRegExpList() {
        return prepareRegExpList(findRegExpList);
    }

    private List<String> prepareMaskRegExpList() {
        return prepareRegExpList(maskRegExpList);
    }

    private List<String> prepareRegExpList(String regExpAsString) {
        List<String> result = new ArrayList<String>();

        if (regExpListDelimiter == null || regExpListDelimiter.equals("")) {
            result.add(regExpAsString);
            return result;
        }

        result.addAll(
            Arrays.asList(
                regExpAsString.split(regExpListDelimiter)
            )
        );

        return result;
    }

    public String mask(String stringForMask, String findRegExp, String maskRegExp) {
        StringBuffer buffer = new StringBuffer();

        Matcher matcher = Pattern.compile(findRegExp).matcher(stringForMask);
        while (matcher.find()) {
            matcher.appendReplacement(buffer, maskRegExp);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
