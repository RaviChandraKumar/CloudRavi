package com.ravi.app.controller;

import com.ravi.app.model.Image;
import com.ravi.app.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ImageService imgService;

    @RequestMapping("/")
    public ModelAndView getHomePage(){
        ModelAndView mv = new ModelAndView("loginPage");
        return mv;
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("userName") String userName){
        ModelAndView mv = new ModelAndView("welcomePage");
        List<Image> images = imgService.getAllImagesInDB();
        mv.addObject("images",images);
        mv.addObject("userName",userName);
        return mv;
    }

    @RequestMapping("/userHome")
    public ModelAndView userHomePage(@RequestParam("userName") String userName,
                              @RequestParam("message") String message){
        ModelAndView mv = new ModelAndView("welcomePage");
        List<Image> images = imgService.getAllImagesInDB();
        mv.addObject("images",images);
        mv.addObject("userName",userName);
        mv.addObject("message",message);
        return mv;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file")MultipartFile file,
                                    @RequestParam("userName") String userName,
                                    @RequestParam("title") String title,
                                    RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("userName", userName);
        int x = imgService.uploadNewImgToDB(title,userName,file);
        if (x == 1){
            redirectAttributes.addAttribute("message", "upload successful!");
        }
        else if (x == -1){
            redirectAttributes.addAttribute("message", "File size is greater than 1MB, upload failed!");
        }
        else {
            redirectAttributes.addAttribute("message", "upload failed!!!");
        }
        return "redirect:/userHome";

    }

    @RequestMapping(value = "/updateImage", method = RequestMethod.GET)
    public String updateImage(@RequestParam("userName") String userName,
                              @RequestParam("oldtitle") String oldtitle,
                              @RequestParam("newtitle") String newtitle,
                              RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("userName", userName);
        int x = imgService.updateImgTitle(newtitle, userName, oldtitle);
        if(x == 1){
            redirectAttributes.addAttribute("message", "title was updated successfully");
        }
        else {
            redirectAttributes.addAttribute("message", "title not updated successfully");
        }
        return "redirect:/userHome";
    }

    @RequestMapping(value = "/likeImage", method = RequestMethod.GET)
    public String updateNoofLikes(@RequestParam("userName") String userName,
                              @RequestParam("title") String title,
                              @RequestParam("nooflikes") String nooflikes,
                              RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("userName", userName);
        int x = imgService.updateImgNoOfLikes(userName, title, Long.valueOf(nooflikes));

        if(x == 1){
            redirectAttributes.addAttribute("message", "you liked image successfully");
        }
        else {
            redirectAttributes.addAttribute("message", "your like was not updated successfully");
        }
        return "redirect:/userHome";
    }

    @RequestMapping(value = "/rateImage", method = RequestMethod.GET)
    public String updateImgScore(@RequestParam("userName") String userName,
                              @RequestParam("title") String title,
                              @RequestParam("noofratings") String noofratings,
                              @RequestParam("ratingscore") String ratingscore,
                              @RequestParam("newscore") String newscore,
                              RedirectAttributes redirectAttributes) {

        float score = Float.valueOf(ratingscore) + Float.valueOf(newscore);
        redirectAttributes.addAttribute("userName", userName);
        int x = imgService.updateImgscore(userName, title, score, Long.valueOf(noofratings) + 1);

        if (x == 1) {
            redirectAttributes.addAttribute("message", "you scored the image successfully");
        } else {
            redirectAttributes.addAttribute("message", "your score was not updated successfully");
        }
        return "redirect:/userHome";
    }

}
