package com.cudgal.utils.log4j;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author alexandr_lenkov@epam.com
 * Date: 28.04.2018
 * Time: 14:08
 */
public class Main {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class);
        logger.debug("this is a debug log message password=dfgfgfdfgfd, field=field_value");
        logger.info("{password: qqqqqq, password:dfdfdfdfd}");
        logger.warn("this is a warning log message");



    }
}
