package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/home")
    String getHome() {
        return "home";
    }


    @GetMapping("/calc")
    String getCalc() {
        return "calc";
    }

    @GetMapping("/conv")
    String getConverter() {
        return "conv";
    }


    @PostMapping("/calculate")
    String calculate(
            @RequestParam("first_number") double first_number,
            @RequestParam("second_number") double second_number,
            @RequestParam("operation") String operation,
            Model model
    ) {
        double result = 0.0;

        switch (operation) {
            case "+" -> result = first_number + second_number;
            case "-" -> result = first_number - second_number;
            case "*" -> result = first_number * second_number;
            case "/" -> {
                if (second_number != 0) result = first_number / second_number;
                else result = second_number;
            }
            case "%" -> result = (first_number / 100) * second_number;
        }
        model.addAttribute("result", result);
        return "resultpage";
    }

    double RUB_TO_USD = 96.4;
    double RUB_TO_EUR = 104.09;
    double RUB_TO_CNY = 13.49;
    double RUB_TO_SEK = 0.23;

    @PostMapping("/convert")
    String convert(
            @RequestParam("current_amount") double current_amount,
            @RequestParam("currency1") String currency1,
            @RequestParam("currency2") String currency2,
            Model model
    ) {
        double await_amount = 0.0;
        switch (currency1) {
            case "RUB" -> {
                switch (currency2) {
                    case "RUB" -> await_amount = current_amount;
                    case "USD" -> await_amount = current_amount / RUB_TO_USD;
                    case "EUR" -> await_amount = current_amount / RUB_TO_EUR;
                    case "CNY" -> await_amount = current_amount / RUB_TO_CNY;
                    case "SEK" -> await_amount = current_amount * RUB_TO_SEK;
                }
            }
            case "USD" -> await_amount = switch (currency2) {
                case "RUB" -> current_amount * RUB_TO_USD;
                case "USD" -> current_amount;
                case "EUR" -> current_amount * RUB_TO_USD / RUB_TO_EUR;
                case "CNY" -> current_amount * RUB_TO_USD / RUB_TO_CNY;
                case "SEK" -> current_amount * RUB_TO_USD * RUB_TO_SEK;
                default -> await_amount;
            };
            case "EUR" -> await_amount = switch (currency2) {
                case "RUB" -> current_amount * RUB_TO_EUR;
                case "USD" -> current_amount * RUB_TO_EUR / RUB_TO_USD;
                case "EUR" -> current_amount;
                case "CNY" -> current_amount * RUB_TO_EUR / RUB_TO_CNY;
                case "SEK" -> current_amount * RUB_TO_EUR * RUB_TO_SEK;
                default -> await_amount;
            };
            case "CNY" -> await_amount = switch (currency2) {
                case "RUB" -> current_amount * RUB_TO_CNY;
                case "USD" -> current_amount * RUB_TO_CNY / RUB_TO_USD;
                case "EUR" -> current_amount * RUB_TO_CNY / RUB_TO_EUR;
                case "CNY" -> current_amount;
                case "SEK" -> current_amount * RUB_TO_CNY * RUB_TO_SEK;
                default -> await_amount;
            };
            case "SEK" -> await_amount = switch (currency2) {
                case "RUB" -> current_amount / RUB_TO_SEK;
                case "USD" -> current_amount / RUB_TO_SEK / RUB_TO_USD;
                case "EUR" -> current_amount / RUB_TO_SEK / RUB_TO_EUR;
                case "CNY" -> current_amount / RUB_TO_SEK / RUB_TO_CNY;
                case "SEK" -> current_amount;
                default -> await_amount;
            };
        }
        double roundedAmount = Math.round(await_amount * 1000.0) / 1000.0;
        model.addAttribute("await_amount", roundedAmount);
        return "conv";
    }
}
