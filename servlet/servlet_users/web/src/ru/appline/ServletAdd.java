package ru.appline;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private AtomicInteger counter = new AtomicInteger(4);

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//        request.setCharacterEncoding("UTF-8");
//        PrintWriter printWriter = response.getWriter();
//
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        User user = new User(name, surname, salary);
//        model.addUser(user,counter.getAndIncrement());
//
//        printWriter.print("<html>" +
//                            "<h3>Пользователь " + name + " " + surname +
//                            " с зарплатой " + salary + " рублей " +
//                            " успешно создан.</h3>" +
//                            "<a href=\"addUser.html\">Создать нового пользователя</a></br>" +
//                            "<a href=\"index.jsp\">Домой</a>" +
//                            "</html>");
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }catch (Exception exception){
            System.out.println("Error");
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(stringBuffer),JsonObject.class);
        request.setCharacterEncoding("UTF-8");

        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        double salary = jsonObject.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.addUser(user,counter.getAndIncrement());

        response.setContentType("application/json:charset=utf-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.print(gson.toJson(model.getFromList()));
    }
}
