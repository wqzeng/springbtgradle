package com.wqzeng.springbtgradle.pattern;

import com.wqzeng.springbtgradle.model.dto.Animal;
import com.wqzeng.springbtgradle.model.dto.Cat;
import com.wqzeng.springbtgradle.model.dto.Tiger;
import org.springframework.util.StringUtils;

/**
 * 工厂模式
 */
public class AnimalFactory {
    public Animal getAnimal(String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }else if(name.equalsIgnoreCase("Tiger")){
            return new Tiger("景阳冈虎",2,"");
        }else if(name.equalsIgnoreCase("Kitty")){
            return new Cat("helloKitty",2,"");
        }else {
            return null;
        }
    }
}
