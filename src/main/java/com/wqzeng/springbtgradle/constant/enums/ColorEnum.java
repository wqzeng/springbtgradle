package com.wqzeng.springbtgradle.constant.enums;

import java.util.Optional;

public enum ColorEnum {
    RED((byte)1,"RED"),
    GREEN((byte)2,"GREEN"),
    BLUE((byte)1,"BLUE");
    private Byte value;
    private String name;
    ColorEnum(Byte value,String name){
        this.value=value;
        this.name=name;
    }

    public static Optional<ColorEnum> getColorEnum(String name ) throws IllegalArgumentException{
        for( ColorEnum enums : values() ) {
            if( enums.getName().equals( name ) ) {
                return Optional.of(enums);
            }
        }
        throw new IllegalArgumentException("unsupported ColorEnum name");
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
