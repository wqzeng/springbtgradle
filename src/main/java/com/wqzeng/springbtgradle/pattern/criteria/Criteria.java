package com.wqzeng.springbtgradle.pattern.criteria;

import java.util.List;

public interface Criteria {
    List<Person> meetCriteria(List<Person> persons);
}