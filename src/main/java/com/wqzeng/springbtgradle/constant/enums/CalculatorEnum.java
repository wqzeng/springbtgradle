package com.wqzeng.springbtgradle.constant.enums;

public enum CalculatorEnum {
    ADDITION {
        public Double execute( Double x, Double y ) {
            return x + y; // 加法
        }
    },

    SUBTRACTION {
        public Double execute( Double x, Double y ) {
            return x - y; // 减法
        }
    },

    MULTIPLICATION {
        public Double execute( Double x, Double y ) {
            return x * y; // 乘法
        }
    },


    DIVISION {
        public Double execute( Double x, Double y ) {
            return x/y;  // 除法
        }
    };

    public abstract Double execute(Double x, Double y);
}
