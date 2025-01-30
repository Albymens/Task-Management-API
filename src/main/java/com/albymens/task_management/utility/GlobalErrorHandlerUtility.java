package com.albymens.task_management.utility;

import com.albymens.task_management.entity.Priority;
import com.albymens.task_management.entity.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@Component
public class GlobalErrorHandlerUtility {

    public static String getEnumValues(MethodArgumentTypeMismatchException ex) {
        if(ex.getName().equals("status")){
            String delimiterSeparator = String.join(", ", Arrays.toString(Status.values()));
            return String.format("Expected one of: %s", delimiterSeparator);
        }
        if(ex.getName().equals("priority")){
            String delimiterSeparator = String.join(", ", Arrays.toString(Priority.values()));
            return String.format("Expected one of: %s", delimiterSeparator);
        }
        if(ex.getName().equals("deadline")){
            return "Expected date format [yyyy-dd-mm]" ;
        }

        return "";
    }

}
