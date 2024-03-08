package com.oaosalty.homework2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Homework2 {

    public static int licenseNumberInputChecker(String licenseNumber) {
        Pattern pattern = Pattern.compile("[a-zA-Z]{1}\\d\\d\\d[a-zA-Z][a-zA-Z]");
        Matcher matcher = pattern.matcher(licenseNumber);
        if (matcher.find()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int yearInputChecker(String year) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(year);
        if (matcher.find()) {
            return 1;
        } else {
            int intYear = Integer.parseInt(year);
            if ((intYear > 1950) && (intYear < 2025)) {
                return 0;
            }
            return 1;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        System.out.println("Белин Георгий Алексеевич, РИБО-01-22, Вариант 1");
        System.setProperty("console.encoding", "UTF-8");

        HashMap<String, Car> allCars = new HashMap<>();

        allCars.put("X001XX", new Car("Volvo s90", 2015));
        allCars.put("X002XX", new Car("Lada Vesta", 2020));
        allCars.put("X003XX", new Car("Kia Rio", 2012));

        Scanner scan = new Scanner(System.in, "UTF-8");

        while (true) {
            System.out.println("Какое действие с базой Вы хотите совершить?\nДобавить сведения о новом ТС(1), удалить имеющиеся сведения о ТС(2), очистить базу данных(3), завершить программу(exit)");
            String inAppAction = scan.nextLine();

            switch (inAppAction) {
                case ("1") /*+ТС в базу*/ -> {
                    int dataToBePassed = 1;

                    while (dataToBePassed == 1) {
                        System.out.println("Разделяя данные с помощью клавиши Ввод, введите:");
                        System.out.print("Название машины: ");
                        String typedInName = scan.nextLine();
                        System.out.print("Год её выпуска: ");
                        String typedInManufactureYearString = scan.nextLine();
                        int typedInManufactureYear;

                        if (yearInputChecker(typedInManufactureYearString) == 0) {
                            typedInManufactureYear = Integer.parseInt(typedInManufactureYearString);
                        } else {
                            System.out.println("Год был введён неверно. Повторите ввод.\n");
                            continue;
                        }

                        System.out.print("Регистрационный номер (все буквы АНГЛИЙСКИЕ, русские не работают): ");
                        String typedInLicenceNumber = scan.nextLine().toUpperCase();
                        //Из-за давнего бага NetBeans нормальный ввод русского в консоль невозможен. Все номера вводить на английском

                        if (allCars.containsKey(typedInLicenceNumber)) {
                            System.out.println("В базе уже присутствует другое ТС с данным регистрационным номером. \nДля добавления нового ТС с этим номером удалите запись о предыдущем автомобиле \nс этим номером.");
                        } else {
                            if (licenseNumberInputChecker(typedInLicenceNumber) == 0) {
                                System.out.println("Номер был введён неверно (несоотвествие стандарту). Повторите ввод.\n");
                                continue;
                            } else {
                                allCars.put(typedInLicenceNumber, new Car(typedInName, typedInManufactureYear));
                            }
                        }

                        System.out.println("\nДля добавления записи об ещё одном траснпортном средстве введите (1), для прекращения ввода введите (0)");

                        dataToBePassed = scan.nextInt();

                        System.out.println();
                        scan.nextLine();
                    }
                    for (Map.Entry<String, Car> iter : allCars.entrySet()) {
                        String key = iter.getKey();
                        Car value = iter.getValue();
                        System.out.println(key + " " + value.getName() + " " + value.getManufactureYear());
                    }
                    System.out.println();
                    continue;
                }
                case ("2") /*-ТС из базы удалить*/ -> {
                    int dataToBePassed = 1;

                    while (dataToBePassed == 1) {
                        System.out.println("Введите номер автомобиля для удаления сведений о последнем или (exit) для выхода из режима: ");
                        String typedInLicenceNumber = scan.nextLine();

                        if ("exit".equals(typedInLicenceNumber)) //если команда на выход из режима
                        {
                            dataToBePassed = 0;
                        } else {
                            if (allCars.containsKey(typedInLicenceNumber)) //если введённый номер есть в базе
                            {
                                allCars.remove(typedInLicenceNumber); //..., то удалить его
                                System.out.println("\nЭлемент удалён.");
                            } else {
                                if (licenseNumberInputChecker(typedInLicenceNumber) == 0) {
                                    System.out.println("\nНомер был введён неверно (несоотвествие стандарту). Повторите ввод.");
                                } else {
                                    System.out.println("\nТС с таким номером в базе нет.");
                                } 
                            }
                        }
                    }
                    System.out.println();
                    for (Map.Entry<String, Car> iter : allCars.entrySet()) {
                        String key = iter.getKey();
                        Car value = iter.getValue();
                        System.out.println(key + " " + value.getName() + " " + value.getManufactureYear());
                    }
                    System.out.println();
                    continue;
                }
                case ("3") /*Очистить всю базу*/ -> {
                    allCars.clear();
                    System.out.println("Существовавшая база была удалена.\n");
                    continue;
                }
                case ("exit") /*Завершение работы*/ -> {
                    break;
                }
                default -> {
                    System.out.println("Такого режима программы нет. Повторите ввод.\n");
                    continue;
                }
            }

            System.out.println("\nТекущая база данных: ");
            if (allCars.isEmpty()) {
                System.out.println("[ничего]");
            } else {
                for (Map.Entry<String, Car> iter : allCars.entrySet()) {
                    String key = iter.getKey();
                    Car value = iter.getValue();
                    System.out.println(key + " " + value.getName() + " " + value.getManufactureYear());
                }
            }
            break;
        }
    }
}
