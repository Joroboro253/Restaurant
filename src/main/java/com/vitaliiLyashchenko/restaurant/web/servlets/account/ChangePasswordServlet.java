package com.vitaliiLyashchenko.restaurant.web.servlets.account;

import com.vitaliiLyashchenko.restaurant.db.Dao;
import com.vitaliiLyashchenko.restaurant.db.entity.User;
import com.vitaliiLyashchenko.restaurant.exceptions.AppException;
import com.vitaliiLyashchenko.restaurant.exceptions.DbException;
import com.vitaliiLyashchenko.restaurant.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/change-password")
public class ChangePasswordServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ChangePasswordServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/change_pass.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String oldPass = req.getParameter("old-pass");
        String newPass = req.getParameter("new-pass");
        User currentUser = (User) req.getSession().getAttribute("user");
        try {
            User user = Dao.getDao().getUserDao().logIn(currentUser.getLogin(), oldPass.toCharArray());
            if (user == null) {
                req.setAttribute("err", "true");
                res.sendRedirect(req.getContextPath() + "/account/change-password?err=");
            } else {
                Dao.getDao().getUserDao().changePassword(user.getId(), newPass.toCharArray());
                res.sendRedirect(req.getContextPath() + "/account");
            }
        } catch (DbException e) {
            log.error(Utils.getErrMessage(e));
            throw new AppException(e);
        }
    }
}

