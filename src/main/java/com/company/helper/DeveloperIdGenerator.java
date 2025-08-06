package com.company.helper;

import com.company.entity.Developer;

public class DeveloperIdGenerator {

public static String generateId(Developer developer){

    String fName = developer.getfName();
    String lName = developer.getlName();
    int yearofbirth = developer.getYearOfBirth();


    char getFirstchar = lName.charAt(0);

    // 1. Use String.format to ensure the year is always 2 digits (e.g., "00", "07", "95")
    String a = String.format("%02d", yearofbirth % 100);

    // 2. Use the variables from the developer object, NOT hardcoded text
    String s = getFirstchar + fName + a;

    return s;

}
}
