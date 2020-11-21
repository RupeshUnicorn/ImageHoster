package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class CommentController {

    @Autowired
    private CommentService  commentService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request pattern is of type '/image/{imageId}/{imageTitle}/comments'
    // and also the incoming request is of POST type
    //The method receives all the details of the image along with the comment to be stored in the database,
    //After getting the comment, set the user of the comment by getting the logged in user from the Http Session
    //Set the local date on which the comment is posted
    //After storing the comment, this method directs to the same image page displaying all the comments

    @RequestMapping(value = "/image/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer imageId, @RequestParam("comment") String description,
                             HttpSession session) {

        Image image = imageService.getImage(imageId);
        User loggedInUser = (User) session.getAttribute("loggeduser");
        Comment comment = new Comment();
        comment.setImage(image);
        comment.setUser(loggedInUser);
        comment.setText(description);
        comment.setCreatedDate(LocalDate.now());
        commentService.addComment(comment);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
