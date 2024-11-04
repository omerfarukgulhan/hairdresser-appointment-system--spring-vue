package com.ofg.barber.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.ofg.barber.service.abstact.FileService;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {
    private final FileService fileService;
    private String[] fileType;

    @Autowired
    public FileTypeValidator(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void initialize(FileType fileType) {
        this.fileType = fileType.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;

        String type = fileService.detectType(value);

        for (String validType : fileType) {
            if (type.contains(validType)) return true;
        }

        String validTypes = Arrays.stream(fileType).collect(Collectors.joining(", "));
        context.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
        hibernateConstraintValidatorContext.addMessageParameter("types", validTypes);
        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();

        return false;
    }
}