package controller;

import config.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
//@RequestMapping(value = "/saveAudio")
public class FileAccess {

    private AmazonClient amazonClient;

    @Autowired
    FileAccess(AmazonClient amazonClient){
        this.amazonClient = amazonClient;
    }

    @PostMapping("/saveAudio")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

}
