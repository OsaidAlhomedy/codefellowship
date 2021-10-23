package com.d4.codefellowship.controllers;

import com.d4.codefellowship.models.ApplicationUser;
import com.d4.codefellowship.models.FakeFace;
import com.d4.codefellowship.models.Post;
import com.d4.codefellowship.models.Role;
import com.d4.codefellowship.repos.AppUserRepo;
import com.d4.codefellowship.repos.PostRepo;
import com.d4.codefellowship.repos.RoleRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PostRepo postRepo;

    @ModelAttribute("user")
    public ApplicationUser user() {
        Object priniple = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (priniple instanceof ApplicationUser) {
            return (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @GetMapping("/profile")
    public String profileView(Model model) {
        model.addAttribute("posts",postRepo.findAllByApplicationUserId(user().getId()));
        return "profile";
    }

    @GetMapping("/profile/{user_profile_id}")
    public String profileUserView(@PathVariable Long user_profile_id, Model model) {
        ApplicationUser profileUser = appUserRepo.findById(user_profile_id).orElseThrow();
        List<Post> posts = postRepo.findAllByApplicationUserId(user_profile_id);
        model.addAttribute("profileUser",profileUser);
        model.addAttribute("profilePosts",posts);
        return "oneprofile";
    }


    @PostMapping("/signup")
    public RedirectView signUpUser(ApplicationUser user) {
        System.out.println(user.getPassword());
        ApplicationUser newUser = new ApplicationUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getBio());
        Role role = new Role("USER");
        roleRepo.save(role);
        newUser.setRole(role);
        appUserRepo.save(newUser);

        return new RedirectView("/");
    }

    @PostMapping("/post/{userid}")
    public RedirectView postCreator(@PathVariable String userid,Post post){
        System.out.println(userid);
        System.out.println(post.getId());
        System.out.println(post.getBody());
        ApplicationUser user = appUserRepo.findById(Long.parseLong(userid)).orElseThrow();
        Post newPost = new Post(post.getBody(),user);
        postRepo.save(newPost);
        return new RedirectView("/profile");
    }


//    @GetMapping("/feed")
//    public String feedView() {
//
//
//    }

    @Transactional
    @PostMapping("/follow/{profileId}")
    public RedirectView follow(@PathVariable Long profileId){

        System.out.println(profileId);

        ApplicationUser loggedInUser = user();
        ApplicationUser aboutToBeFollowedUser = appUserRepo.findById(profileId).orElseThrow();


        loggedInUser.getFollowings().add(aboutToBeFollowedUser);
        appUserRepo.save(loggedInUser);
        System.out.println(loggedInUser.getFollowings());

        return new RedirectView("/profile/"+aboutToBeFollowedUser.getId());
    }




//    private void fakePics(Model model) {
//        try {
//            URL url = new URL("https://fakeface.rest/face/json?minimum_age=16&maximum_age=25");
//
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(5000);
//            InputStreamReader responseStream = new InputStreamReader(connection.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(responseStream);
//            String data = bufferedReader.readLine();
//            bufferedReader.close();
//
//            Gson gson = new Gson();
//            FakeFace fakeFace = gson.fromJson(data, FakeFace.class);
//            model.addAttribute("image", fakeFace.getImage_url());
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }
//    }
}
