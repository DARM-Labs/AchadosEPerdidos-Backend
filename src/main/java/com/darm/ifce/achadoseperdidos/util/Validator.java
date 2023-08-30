package com.darm.ifce.achadoseperdidos.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import com.darm.ifce.achadoseperdidos.exceptions.BadRequestException;

public class Validator {

    public static void validateName(String name) {
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new BadRequestException("Invalid name. Name cannot contain numbers or special characters");
        }
    }

    public static LocalDate validateDate(String date) {
        try {

            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        } catch (DateTimeParseException e) {
            throw new BadRequestException(
                    "The date format does not conform to the format: dd/MM/yyyy. " + e.getMessage());
        }
    }

    public static LocalDate ageOfMajorityValidation(String date) {
        LocalDate localDate = validateDate(date);
        LocalDate dateOfBirth = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateOfRegister = LocalDate.now();
        long age = ChronoUnit.YEARS.between(dateOfBirth, dateOfRegister);
        if (age < 18){
            throw new BadRequestException("This type of user must be over 18 years of age to be registered.");
        }
        return localDate;

    }

    public static String validateCPF(String CPF) {
        boolean cpfIsValid = CPF.matches("^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$");
        if (!cpfIsValid) {
            throw new BadRequestException("The CPF does not conform to the standard: XXX.XXX.XXX-XX");
        }
        return CPF;
    }

    public static String validatePhoneNumber(String phone) {
        boolean isValid = phone.matches("^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$");
        if (!isValid) {
            throw new BadRequestException("Phone number does not match pattern: (XX) XXXXX-XXXX");
        }
        return phone;
    }
    public static String validateCivilRegistry(String civilRegistry) {
        boolean civilRegistryIsValid = civilRegistry
                .matches("^\\d{6}[.]\\d{2}[.]\\d{2}[.]\\d{4}[.]\\d{1}[.]\\d{5}[.]\\d{3}[.]\\d{7}-\\d{2}$");
        if (!civilRegistryIsValid) {
            throw new BadRequestException("The Civil Registry does not conform to the standard: " +
                    "XXXXXX.XX.XX.XXXX.X.XXXXX.XXX.XXXXXXX-XX");
        }
        return civilRegistry;
    }

    public static LocalDate validatesIfDateIsBeforeCurrentDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new BadRequestException("The notice date cannot be earlier than the notice creation date!");
        }
        return date;
    }

    public static String validateTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        if (!time.matches("^(?:[01]\\d|2[0-3]):[0-5]\\d$")) {
            throw new BadRequestException(
                    "The start_time or end_time attributes are not following the pattern (HH:mm)!");
        }
        return time;
    }


    public static String validateEmail(String email) {
        if (!email.matches("^[^0-9].*")) {
            throw new BadRequestException("Invalid email. email cannot starts with a number");
        }

        return email;
    }

    public static LocalDate verifyParentOlderThanSixteen(String date) {
        LocalDate localDate = validateDate(date);
        int year = LocalDate.now().getYear() - localDate.getYear();
        if (year < 16) {
            throw new BadRequestException("This type of user must be over 16 years of age to be registered.");
        }
        return localDate;

    }

    public static void checkPasswordIsValid(String password) {
        if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!|()*?&.~]).+")) {
            throw new IllegalArgumentException(
                    "Invalid password. The password must contain: Uppercase letter, lowercase letter, number (0-9), special character!");
        }
    }

    public static String validateClassName(String name) {
        if (!name.matches("^[a-zA-Z0-9\\s]+$")) {
            throw new BadRequestException("Invalid name. Name cannot contain special characters");
        }

        return name;
    }

    public static void validateSubjectName(String name){
        if (name.matches("^\\d+.*") || name.matches("(?=.*[@#$%!|()*?&.~=+ªº{}~´^/]).+")){
            throw new BadRequestException("Invalid subject name. The name cannot start with numbers or contain special characters!");
        }
    }

}
