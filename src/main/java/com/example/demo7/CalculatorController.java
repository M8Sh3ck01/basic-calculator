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

    private static final String OPERATORS = "+-*/";

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
        if (errorState) return;

        if (!currentOperator.isEmpty() && hasOperator) {
            try {
                double[] operands = parseOperands(display.getText());
                double result = calculate(operands[0], operands[1], currentOperator);

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

            } catch (Exception e) {
                showError("Invalid input");
            }
        }
    }

    private double[] parseOperands(String input) throws NumberFormatException {
        int opIndex = -1;

        for (int i = 1; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (OPERATORS.contains(Character.toString(ch))) {
                opIndex = i;
                break;
            }
        }

        if (opIndex == -1 || opIndex == input.length() - 1) {
            throw new NumberFormatException("Incomplete expression");
        }

        double a = Double.parseDouble(input.substring(0, opIndex));
        double b = Double.parseDouble(input.substring(opIndex + 1));

        return new double[]{a, b};
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
                return a / b;
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
            if ("+-*/".contains(currentText.substring(currentText.length() - 1))) {
                hasOperator = false;
                currentOperator = "";
            }
            display.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    @FXML
    private void handleDecimal() {
        if (errorState) {
            handleClear();
            return;
        }

        String currentText = display.getText();
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

        if (!currentText.isEmpty() || (currentText.isEmpty() && newOperator.equals("-"))) {

            if (currentText.isEmpty() && newOperator.equals("-")) {
                display.setText("-");
                startNewInput = false;
                return;
            }

            if (hasOperator && "+-*/".contains(currentText.substring(currentText.length() - 1))) {
                display.setText(currentText.substring(0, currentText.length() - 1) + newOperator);
                currentOperator = newOperator;
                return;
            }

            if (hasOperator) {
                handleEquals();
                if (errorState) return;
            }

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

        if (startNewInput && !hasOperator) {
            display.setText(digit);
        } else {
            display.setText(display.getText() + digit);
        }
        startNewInput = false;

        if (hasOperator && !errorState) {
            try {
                double[] operands = parseOperands(display.getText());
                double result = calculate(operands[0], operands[1], currentOperator);

                if (!Double.isFinite(result)) {
                    resultField.setText("Error: Division by zero");
                } else {
                    resultField.setText(formatResult(result));
                }
            } catch (Exception e) {
                // Ignore incomplete numbers
            }
        }
    }
}
