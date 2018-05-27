package diploma.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diploma.controllers.UserController;
import diploma.mappers.JsonUserMapper;
import diploma.model.User;
import java.io.File;
import org.json.JSONObject;


@WebServlet(name = "DeleteUserById", urlPatterns = {"/DeleteUserById"})
public class DeleteUserById extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        try (PrintWriter out = response.getWriter()) 
        {
            UserController userController = new UserController();
            User photos = userController.getUserById(id);
            String uJson=JsonUserMapper.toJSON(photos);
            JSONObject jsonObj = new JSONObject(uJson);
            int uId = (int)jsonObj.get("id");
            String login = (String) jsonObj.get("login");
            for(int i=1;i<21;i++){
            File file = new File("C:\\trainingImages\\"+uId+"-"+login+"-"+i+".png");
        if(file.delete()) {
            System.out.println("Файл удален");
        } else {
            System.out.println("Файл удалить не получилось");
        }
            }
             User user= userController.deleteUserById(id);
             String json=JsonUserMapper.toJSON(user);
             out.println(json);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}