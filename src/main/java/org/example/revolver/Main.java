package org.example.revolver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Программа для выполнения экспериментов.
 * Позволяет пользователю вводить количество экспериментов и затем выполнять
 * соответствующие действия. В программе реализована валидация ввода для
 * получения положительного целого числа.

 * Теоретические расчеты:
 * Сразу нажать на спусковой крючок:
 * - вероятность остаться живым после первого нажатия на курок,
 *   соответствует 4 пустых каморы из 6: 4/6 = 2/3 = 0,(6)
 * - вероятность 2-го игрока при прокрутке барабана такая же, как и при первом нажатии на курок Игроком1,
 *   что соответствует вероятности: 4/6 = 2/3 = 0,(6)
 *  - если после выстрела Игрока1 ничего не произошло,
 *  значит имеем такие варианты расположения патронов в барабане:
 *  ("о" - пустая камора; "Х" - в каморе находится патрон)
 *  ХХоооо
 *  оХХооо
 *  ооХХоо
 *  оооХХо
 *  три из четырёх камор гарантируют второму игроку выживание с вероятностью: 3/4 = 0,75
 *  такая вероятность получается из-за последовательного расположения патронов в барабане

 * Ответ на задачу:
 * Нажатие на спусковой крючок дает шанс успеха с вероятностью 75%, а прокрутка барабана - с вероятностью 66,66%.
 * Делаем вывод, что более выгодно сразу нажать на спусковой крючок.
 *
 * @author Москальков Павел
 * @version 1.0
 */
public class Main {
    public static class Revolver {
        public LinkedList<Integer> chamber;

        //создаем барабан с 2мя заряженными и 4мя пустыми каморами
        public Revolver() {
            this.chamber = new LinkedList<>(Arrays.asList(1, 1, 0, 0, 0, 0));
            shiftRandom();
        }

        //прокручиваем барабан на 1 камору
        public void shift() {
            Integer lastElement = chamber.removeLast();
            chamber.addFirst(lastElement);
        }

        //прокручиваем барабан на случайное кол-во раз
        public void shiftRandom() {
            int randomShifts = (int) (Math.random() * 30);
            for (int i = 0; i < randomShifts; i++) {
                shift();
            }
        }

        //проверка, есть ли в каморе патрон
        public boolean hasNoBulletInAMuzzle() {
            return chamber.getFirst() != 1;
        }
    }
    public static void main(String[] args) {

        //ввод начальных данных с валидацией
        System.out.println("Введите количество экспериментов: ");
        Scanner scanner = new Scanner(System.in);
        int tries;
        while (true) {
            if (scanner.hasNextInt()) {
                tries = scanner.nextInt();
                if (tries > 0) {
                    break;
                } else {
                    System.out.println("Ошибка! Введите положительное целое число");
                }
            } else {
                System.out.println("Ошибка! Введите положительное целое число");
                scanner.next();
            }
            System.out.print("Повторите ввод: \n");
        }

        //инициализация переменных - счетчиков вероятностей
        float firstPlayerSurvived = 0;
        float secondPlayerSurvivedIfHeShoots = 0;
        float secondPlayerSurvivedIfHeSpins = 0;

        //инициализация переменных - счетчиков вероятностей
        for (int i = 0; i < tries; i++) {
            Revolver revolver = new Revolver();

            if (revolver.hasNoBulletInAMuzzle()) {
                firstPlayerSurvived++;
            } else {
                continue;
            }

            revolver.shift();

            if (revolver.hasNoBulletInAMuzzle()) {
                secondPlayerSurvivedIfHeShoots++;
            }

            revolver.shiftRandom();

            if (revolver.hasNoBulletInAMuzzle()) {
                secondPlayerSurvivedIfHeSpins++;
            }
        }

        System.out.println("Количество экспериментов: " + tries);
        System.out.println("Игрок 1, количество успешных исходов: " + firstPlayerSurvived +
                ", процент: " + (firstPlayerSurvived*100/tries) + "%");
        System.out.println("Игрок 2, количество успешных исходов, если выстрелит сразу: " + secondPlayerSurvivedIfHeSpins +
                ", процент: " + secondPlayerSurvivedIfHeShoots*100/(firstPlayerSurvived) + "%");
        System.out.println("Игрок 2, количество успешных исходов, если прокрутит барабан: " + secondPlayerSurvivedIfHeSpins +
                ", процент: " + (secondPlayerSurvivedIfHeSpins*100/(firstPlayerSurvived)) + "%");
    }

}
