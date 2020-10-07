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

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet{

    private Model model = Model.getInstance();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UtilRequest.setJSONContentTypeAndReqEncode(request, response);
        JsonObject jsonObject = UtilRequest.getJsonObject(request, gson);

        int id = jsonObject.get("ID").getAsInt();

        PrintWriter printWriter = response.getWriter();
        if(model.getFromList().size() == 0){
            printWriter.print(gson.toJson(new InfoResponse("В базе данных нет пользователей!")));
        } else if(id == 0){
                model.getFromList().clear();
                printWriter.print(gson.toJson(new InfoResponse("Все пользователи удалены!")));
        } else if(id > 0){
            if (model.getFromList().containsKey(id) != true) {
                printWriter.print(gson.toJson(new InfoResponse("Такого пользователя не существует!")));
            } else {
                model.getFromList().remove(id);
                printWriter.print(gson.toJson(model.getFromList()));
            }
        } else {
            printWriter.print(gson.toJson(new InfoResponse("ID должен быть больше 0!")));
        }
    }
}
