package com.example.workflow.helper;

import java.util.HashMap;

public class TempHelper {

    public static int getFound(HashMap<String, Boolean> o) {
        int found = 0;
        for(Boolean value : o.values())
        {
            found = value ? found + 1: found;
        }
        return found;
    }
}
