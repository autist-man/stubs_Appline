package ru.calculator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calculate")
public class ServletCalculator extends HttpServlet {
    enum FLAGS {
        SUCCESSFUL,
        FALSE,
        NAN,
    }

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }catch (Exception e){
            System.out.println("Error!");
            e.printStackTrace();
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(stringBuffer), JsonObject.class);

        double a = jsonObject.get("a").getAsDouble();
        double b = jsonObject.get("b").getAsDouble();
        char math = jsonObject.get("math").getAsString().charAt(0);

        PrintWriter printWriter = response.getWriter();
        Calculator calculator = new Calculator();
        FLAGS res = calculator.calculate(a, b, math);
        if(res == FLAGS.SUCCESSFUL){
            printWriter.print(gson.toJson(calculator));
        } else if(res == FLAGS.FALSE){
            printWriter.print(gson.toJson(new Error("Неверно введен оператор работы над числами!")));
        } else {
            printWriter.print(gson.toJson(new Error("Деление на ноль запрещено!")));
        }

    }

    private class Calculator{
        private double result;

        public FLAGS calculate(double a, double b, char op){
            switch (op){
                case '+': result = a + b; return  FLAGS.SUCCESSFUL;
                case '-': result = a - b; return  FLAGS.SUCCESSFUL;
                case '*': result = a * b; return  FLAGS.SUCCESSFUL;
                case '/':
                    if (Double.compare(b, 0.0d) == 0){
                        return FLAGS.NAN;
                    }
                    result = a / b;
                    return  FLAGS.SUCCESSFUL;
                default: return FLAGS.FALSE;
            }
        }
    }

    private class Error{
        private final String error;
        public Error(String description){
            this.error = description;
        }
    }
}
