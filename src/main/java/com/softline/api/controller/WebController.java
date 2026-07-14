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
        return getToken(request) != null ? "redirect:/menu" : "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        return getToken(request) != null ? "redirect:/menu" : "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletResponse response,
                          Model model) {
        try {
            var result = authService.login(new LoginRequest(username, password));
            var cookie = new Cookie("auth_token", result.token());
            cookie.setHttpOnly(true);
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
    public String menu(HttpServletRequest request, Model model) {
        return withAuth(request, model, "menu");
    }

    @GetMapping("/produtos")
    public String produtos(HttpServletRequest request, Model model) {
        return withAuth(request, model, "produtos/index");
    }

    @GetMapping("/produtos/create")
    public String produtosCreate(HttpServletRequest request, Model model) {
        return withAuth(request, model, "produtos/form");
    }

    @GetMapping("/produtos/edit/{id}")
    public String produtosEdit(HttpServletRequest request, Model model) {
        return withAuth(request, model, "produtos/form");
    }

    @GetMapping("/clientes")
    public String clientes(HttpServletRequest request, Model model) {
        return withAuth(request, model, "clientes/index");
    }

    @GetMapping("/clientes/create")
    public String clientesCreate(HttpServletRequest request, Model model) {
        return withAuth(request, model, "clientes/form");
    }

    @GetMapping("/clientes/edit/{id}")
    public String clientesEdit(HttpServletRequest request, Model model) {
        return withAuth(request, model, "clientes/form");
    }

    private String withAuth(HttpServletRequest request, Model model, String view) {
        String token = getToken(request);
        if (token == null) return "redirect:/login";
        model.addAttribute("authToken", token);
        return view;
    }

    private String getToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(c -> "auth_token".equals(c.getName()) && !c.getValue().isBlank())
                .map(Cookie::getValue)
                .findFirst().orElse(null);
    }
}
