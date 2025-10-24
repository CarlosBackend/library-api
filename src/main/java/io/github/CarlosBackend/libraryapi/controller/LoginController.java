package io.github.CarlosBackend.libraryapi.controller;
import io.github.CarlosBackend.libraryapi.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        if( authentication instanceof CustomAuthentication customAuthentication){
            System.out.println(customAuthentication.getUsuario());
        }
        return "Olá " + authentication.getName();
    }
}
