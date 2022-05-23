package com.jpmc.salesreport.util;

public final class PatternsDefinition {

    public static final String MSG_PATTERN_1 = "^(?<product>[a-z]+)+.*\\s(?<price>[0-9]+)[a-z]{1}$";


    public static final String MSG_PATTERN_2 = "^(?<salesquantity>[0-9]+)\\s[a-z]+\\s[a-z]{2}\\s(?<product>[a-z]+)\\s[a-z]{2}\\s(?<price>[0-9]+)[a-z]{1}.*$";


    public static final String MSG_PATTERN_3 = "^(?<operation>[A-za-z]+)\\s(?<operationprice>[0-9]+)[a-z]{1}\\s(?<product>[A-za-z]+).*$";

}
