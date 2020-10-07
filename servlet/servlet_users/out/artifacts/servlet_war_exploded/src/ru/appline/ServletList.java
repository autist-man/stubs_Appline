package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.util.InfoResponse;
import ru.appline.util.UtilRequest;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet (urlPatterns = "/get")
public class ServletList extends HttpServlet {

    private Model model = Model.getInstance();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter printWriter = response.getWriter();
//        int id = Integer.parseInt(request.getParameter("ID"));
//        if (id == 0) {
//            printWriter.print("<html>" +
//                    "<h3>Доступные пользователи:</h3></br>" +
//                    "ID пользователя:" +
//                    "<ul>");
//            for (Map.Entry<Integer, User> entry : model.getFromList().entrySet()) {
//                printWriter.print("<li>" + entry.getKey() + "</li>" +
//                        "<ul>" +
//                        "<li>Имя:" + entry.getValue().getName() + "</li>" +
//                        "<li>Фамилия:" + entry.getValue().getSurname() + "</li>" +
//                        "<li>Зарплата:" + entry.getValue().getSalary() + "</li>" +
//                        "</ul>");
//            }
//            printWriter.print("</ul>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//        } else if (id > 0) {
//            if (id > model.getFromList().size()) {
//                printWriter.print("<html>" +
//                        "<h3>Такого пользователя нет!</h3>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//            } else {
//                printWriter.print("<html>" +
//                        "<h3>Запрошеный пользователь:</h3>" +
//                        "</br>" +
//                        "Имя: " + model.getFromList().get(id).getName() + "</br>" +
//                        "Фамилия: " + model.getFromList().get(id).getSurname() + "</br>" +
//                        "Зарплата: " + model.getFromList().get(id).getSalary() + "</br>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//            }
//
//        } else {
//            printWriter.print("<html>" +
//                    "<h3>ID должен быть больше 0!</h3>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UtilRequest.setJSONContentTypeAndReqEncode(request, response);
        JsonObject jsonObject = UtilRequest.getJsonObject(request, gson);

        int id = jsonObject.get("ID").getAsInt();

        PrintWriter printWriter = response.getWriter();

        if(model.getFromList().size() == 0){
            printWriter.print(gson.toJson(new InfoResponse("В базе данных нет пользователей!")));
        } else if (id == 0) {
                printWriter.print(gson.toJson(model.getFromList()));
        } else if (id > 0) {
            if (model.getFromList().containsKey(id) != true) {
                printWriter.print(gson.toJson(new InfoResponse("Такого пользователя не существует!")));
            } else {
                printWriter.print(gson.toJson(model.getFromList().get(id)));
            }
        } else {
            printWriter.print(gson.toJson(new InfoResponse("ID должен быть больше 0!")));
        }
    }
}
