package com.example.demo7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML
    private TextField display;
    @FXML
    private Label resultField;

    private double firstOperand;
    private String currentOperator;
    private boolean startNewInput = true;
    private boolean hasOperator = false;
    private boolean errorState = false;

    @FXML
    private void handleClear() {
        firstOperand = 0;
        display.setText("");
        resultField.setText("");
        currentOperator = "";
        startNewInput = true;
        hasOperator = false;
        errorState = false;
    }

    @FXML
    private void handleEquals() {
        if (errorState) {
            return;
        }

        if (!currentOperator.isEmpty() && hasOperator) {
            String input = display.getText();
            String[] parts = input.split("[-+*/]");
            if (parts.length == 2) {
                try {
                    double secondOperand = Double.parseDouble(parts[1]);
                    double result = calculate(firstOperand, secondOperand, currentOperator);
                    if (!Double.isFinite(result)) {
                        showError("Error: Division by zero");
                        return;
                    }
                    display.setText(formatResult(result));
                    resultField.setText("");
                    firstOperand = result;
                    currentOperator = "";
                    hasOperator = false;
                    startNewInput = true;
                } catch (NumberFormatException e) {
                    showError("Invalid input");
                }
            }
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.format("%s", result);
        }
    }

    @FXML
    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b; // Division by zero check is handled by Double.isFinite()
            default:
                return b;
        }
    }

    private void showError(String message) {
        resultField.setText(message);
        errorState = true;
        startNewInput = true;
    }

    @FXML
    private void handleBackSpace() {
        if (errorState) {
            handleClear();
            return;
        }

        String currentText = display.getText();
        if (!currentText.isEmpty()) {
            // If deleting an operator, update flags
            if ("+-*/".contains(currentText.substring(currentText.length()-1))) {
                hasOperator = false;
                currentOperator = "";
            }
            display.setText(currentText.substring(0, currentText.length()-1));
        }
    }

    @FXML
    private void handleDecimal() {
        if (errorState) {
            handleClear();
            return;
        }

        String currentText = display.getText();
        // Only allow decimal if the current number doesn't already have one
        if (currentText.isEmpty() || startNewInput) {
            display.setText("0.");
            startNewInput = false;
        } else if (!currentText.contains(".") ||
                (hasOperator && !currentText.substring(currentText.indexOf(currentOperator)).contains("."))) {
            display.setText(display.getText() + ".");
        }
    }

    @FXML
    private void handleOperator(ActionEvent event) {
        if (errorState) {
            return;
        }

        Button button = (Button) event.getSource();
        String newOperator = button.getText();
        String currentText = display.getText();

        if (!currentText.isEmpty()) {
            // If we already have an operator, calculate intermediate result
            if (hasOperator) {
                handleEquals();
                if (errorState) {
                    return;
                }
            }

            // Set the new operator
            try {
                firstOperand = Double.parseDouble(display.getText());
                currentOperator = newOperator;
                display.setText(display.getText() + currentOperator);
                hasOperator = true;
                startNewInput = true;
            } catch (NumberFormatException e) {
                showError("Invalid number");
            }
        }
    }

    @FXML
    private void handleDigit(ActionEvent event) {
        if (errorState) {
            handleClear();
        }

        Button button = (Button) event.getSource();
        String digit = button.getText();

        if (startNewInput) {
            if (hasOperator) {
                // Continuing expression after operator
                display.setText(display.getText() + digit);
            } else {
                // Starting new number
                display.setText(digit);
            }
            startNewInput = false;
        } else {
            display.setText(display.getText() + digit);
        }

        // Update result field with current calculation if we have an operator
        if (hasOperator && !errorState) {
            String[] parts = display.getText().split("[-+*/]");
            if (parts.length == 2) {
                try {
                    double secondOperand = Double.parseDouble(parts[1]);
                    double result = calculate(firstOperand, secondOperand, currentOperator);
                    if (!Double.isFinite(result)) {
                        resultField.setText("Error: Division by zero");
                    } else {
                        resultField.setText(formatResult(result));
                    }
                } catch (NumberFormatException e) {
                    // Ignore incomplete numbers
                }
            }
        }
    }
}