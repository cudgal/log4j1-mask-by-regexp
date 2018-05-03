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
    private boolean maskConfCorrect = false;
    private List<Pattern> findRegExpListAsObj;
    private List<String> maskRegExpListAsObj;

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

        if (!maskConfCorrect) {
            return result;
        }

        for (int i = 0; i < findRegExpListAsObj.size(); i++) {
            result = mask(
                result,
                findRegExpListAsObj.get(i),
                maskRegExpListAsObj.get(i)
            );
        }

        return result;
    }

    private List<Pattern> prepareFindRegExpList() {
        List<Pattern> result = new ArrayList<Pattern>();

        if (regExpListDelimiter == null || regExpListDelimiter.equals("")) {
            result.add(
                Pattern.compile(findRegExpList)
            );
            return result;
        }


        for (String curPatternAsString : Arrays.asList(findRegExpList.split(regExpListDelimiter))) {
            result.add(
                Pattern.compile(curPatternAsString)
            );
        }


        return result;
    }

    private List<String> prepareMaskRegExpList() {
        List<String> result = new ArrayList<String>();

        if (regExpListDelimiter == null || regExpListDelimiter.equals("")) {
            result.add(
                maskRegExpList
            );
            return result;
        }


        for (String curPatternAsString : Arrays.asList(maskRegExpList.split(regExpListDelimiter))) {
            result.add(
                curPatternAsString
            );
        }


        return result;
    }


    private String mask(String stringForMask, Pattern findRegExp, String maskRegExp) {
        Matcher matcher = findRegExp.matcher(stringForMask);
        return matcher.replaceAll(maskRegExp);
    }

    @Override
    public void activateOptions() {
        super.activateOptions();

        this.findRegExpListAsObj = prepareFindRegExpList();
        this.maskRegExpListAsObj = prepareMaskRegExpList();

        maskConfCorrect = findRegExpListAsObj != null
            && maskRegExpListAsObj != null
            && findRegExpListAsObj.size() > 0
            && findRegExpListAsObj.size() == maskRegExpListAsObj.size();
    }
}
