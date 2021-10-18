package com.d4.codefellowship.controllers;

import com.d4.codefellowship.models.ApplicationUser;
import com.d4.codefellowship.models.FakeFace;
import com.d4.codefellowship.repos.AppUserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class MainController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepo appUserRepo;

    @ModelAttribute("user")
    public ApplicationUser user() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == ApplicationUser.class) {
            ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(user.getUsername());
            System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            return user;
        } else {
            return null;
        }

    }


    @GetMapping("/")
    public String splashPage() {
        return "index";
    }

    @GetMapping("/login")
    public String signInPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @GetMapping("/profile/{id}")
    public String profileView(@PathVariable Long id, Model model) {
        ApplicationUser user = appUserRepo.findById(id).orElseThrow();
        model.addAttribute("profileUser", user);
        try{
            URL url = new URL("https://fakeface.rest/face/json?minimum_age=16&maximum_age=25");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            InputStreamReader responseStream = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(responseStream);
            String data = bufferedReader.readLine();
            bufferedReader.close();

            Gson gson = new Gson();
            FakeFace fakeFace = gson.fromJson(data,FakeFace.class);
            model.addAttribute("image",fakeFace.getImage_url());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "profile";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(@ModelAttribute ApplicationUser user) {
        System.out.println(user.getPassword());
        ApplicationUser newUser = new ApplicationUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getBio());
        appUserRepo.save(newUser);
        return new RedirectView("/");
    }
}
