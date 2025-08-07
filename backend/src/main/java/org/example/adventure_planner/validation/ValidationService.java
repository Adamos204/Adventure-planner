package org.example.adventure_planner.validation;

import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public void requireNotNull(Object obj, String message){
        if (obj == null){
            throw new IllegalArgumentException(message);
        }
    }

    public void requireId(Long id){
        if (id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    public void requireEntityExists(boolean exists , String message){
        if(!exists){
            throw new RuntimeException(message);
        }
    }
}
