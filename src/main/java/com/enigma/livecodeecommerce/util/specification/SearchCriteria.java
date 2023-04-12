package com.enigma.livecodeecommerce.util.specification;

import com.enigma.livecodeecommerce.util.constants.FindOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private FindOperator operator;
    private String value;
}
