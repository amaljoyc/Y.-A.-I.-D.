/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yaid.user;

import com.yaid.db.DbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TTT
 */
public class Login extends HttpServlet {

    public Login() {
        super();
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HttpSession session = request.getSession(true);
        Connection connect = null;
        PrintWriter pw = null;

        pw = response.getWriter();

        response.setContentType("text/html");
        try {
            String email = request.getParameter("emailid");
            String psw = request.getParameter("password");
            String userid = email.substring(0, email.indexOf("@"));
            //udb.setUserPassword(psw);

            //System.out.println("hello...." + email.substring(0, email.indexOf("@")));
            System.out.println("user id : " + userid);

            connect = DbConnection.getDbConnection();
            preparedStatement = connect.prepareStatement("select userid,password,email from yaid.users where email=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(1, request.getParameter("emailid"));
            resultSet = preparedStatement.executeQuery();
            System.out.println("\t Query : " + preparedStatement);
            if (resultSet.next()) {
                System.out.println("\t DB : " + resultSet.getString("password") + "\t Entered : " + psw);
                if (resultSet.getString("password").equals(psw)) {
                    //setting up session
                    int si = resultSet.getInt("userid");
                    session.setAttribute("userID", userid);
                    session.setAttribute("userSerial", Integer.toString(si));
                    String temp = (String) session.getAttribute("userID");

                    String ser = (String) session.getAttribute("userSerial");
                    System.out.println("session : " + temp);
                    System.out.println("serial : " + ser);
                    if (temp.equals("admin")) {
                        //response.sendRedirect("administratorViewUsers.jsp");
                        pw.print("admin");
                    } else {
                        //pw.println("Valid user ID and password !!!");
                        //response.sendRedirect("desktop.jsp");
                        pw.print("ordinaryUser");
                    }
                } else {

                    pw.print("invalidUser");

                }

            } else {

                pw.print("invalidUser");

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Login Error : " + e);
        }


        /** 
         * Returns a short description of the servlet.
         * @return a String containing servlet description
         */
    }

    public void setSession(String userid) {
    }
}