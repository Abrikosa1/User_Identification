package diploma.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import diploma.controllers.UserController;
import diploma.controllers.detector;
import diploma.controllers.updater;
import diploma.mappers.JsonUserMapper;
import diploma.model.User;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Core;





@WebServlet(name = "InsertUser", urlPatterns = {"/InsertUser"})
public class InsertUser extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * -Djava.library.path="C:\opencv\build\java\x64" 
     * 

     */

    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            long startTime = System.currentTimeMillis();
            String opencvpath = "C:\\opencv\\build\\java\\x64\\";
            System.load(opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll");
            StringBuffer sb = new StringBuffer();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            try {
                String str = sb.toString();
                System.out.println("Hi man");
                JSONObject jsonObj = new JSONObject(str);
                String image = (String) jsonObj.get("image");
                String imgArr = (String) jsonObj.get("imgArray");
                String login = (String) jsonObj.get("login");
                String firstName = (String) jsonObj.get("firstName"); 
                String lastName = (String) jsonObj.get("lastName"); 
                JSONArray jsonArr = new JSONArray(imgArr);
                //*последний id
                UserController userController = new UserController();
                User user2 =  userController.getLastId();
                String json=JsonUserMapper.toJSON(user2);
                JSONObject jsonObj2 = new JSONObject(json);
                int idFromJson = (int) jsonObj2.get("id");
                int id = idFromJson + 1;
                //last id + 1
                //for(int i=0;i<jsonArr.length();i++){
                    //String strIm = jsonArr.get(i).toString();
                    //BufferedImage bufIm2 = detector.String2BufferedImage(strIm);
                    detector.detect(jsonArr, login,id);
                    updater.updateRec();
               // }
                String userLogin = "{\"login\": \""+login+"\",\"id\":"+id+",\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\" }";
                //System.out.println(userLogin);               
                User user = JsonUserMapper.fromJSON(userLogin);
                //System.out.println("user: "+user);
                userController.insertUser(user);
                long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");

            } catch (JSONException e) {
                //throw new IOException("Error parsing JSON request string");
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