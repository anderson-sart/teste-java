package com.softline.api.controller;

import com.softline.api.dto.LoginRequest;
import com.softline.api.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final AuthService authService;

    @GetMapping("/")
    public String root(HttpServletRequest request) {
        return hasToken(request) ? "redirect:/menu" : "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        return hasToken(request) ? "redirect:/menu" : "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletResponse response,
                          Model model) {
        try {
            var result = authService.login(new LoginRequest(username, password));
            var cookie = new Cookie("auth_token", result.token());
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            response.addCookie(cookie);
            return "redirect:/menu";
        } catch (Exception e) {
            model.addAttribute("error", "Usuário ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        var cookie = new Cookie("auth_token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/login";
    }

    @GetMapping("/menu")
    public String menu(HttpServletRequest request) {
        return hasToken(request) ? "menu" : "redirect:/login";
    }

    @GetMapping("/produtos")
    public String produtos(HttpServletRequest request) {
        return hasToken(request) ? "produtos/index" : "redirect:/login";
    }

    @GetMapping("/produtos/create")
    public String produtosCreate(HttpServletRequest request) {
        return hasToken(request) ? "produtos/form" : "redirect:/login";
    }

    @GetMapping("/produtos/edit/{id}")
    public String produtosEdit(HttpServletRequest request) {
        return hasToken(request) ? "produtos/form" : "redirect:/login";
    }

    @GetMapping("/clientes")
    public String clientes(HttpServletRequest request) {
        return hasToken(request) ? "clientes/index" : "redirect:/login";
    }

    @GetMapping("/clientes/create")
    public String clientesCreate(HttpServletRequest request) {
        return hasToken(request) ? "clientes/form" : "redirect:/login";
    }

    @GetMapping("/clientes/edit/{id}")
    public String clientesEdit(HttpServletRequest request) {
        return hasToken(request) ? "clientes/form" : "redirect:/login";
    }

    private boolean hasToken(HttpServletRequest request) {
        if (request.getCookies() == null) return false;
        return Arrays.stream(request.getCookies())
                .anyMatch(c -> "auth_token".equals(c.getName()) && !c.getValue().isBlank());
    }
}
