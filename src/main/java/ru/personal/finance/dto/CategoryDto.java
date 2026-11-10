package ru.personal.finance.dto;

import ru.personal.finance.domain.CategoryType;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    public String name;
    public CategoryType type;
    public Double budget;
    public List<OperationDto> operations = new ArrayList<OperationDto>();
}
