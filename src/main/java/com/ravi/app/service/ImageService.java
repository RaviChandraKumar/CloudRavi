package com.ravi.app.service;

import com.ravi.app.model.Image;
import com.ravi.app.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    ImageRepository imgRepo;

    public List<Image> getAllImagesInDB(){
        List<Image> listOfAllImages = imgRepo.getAllImages();
        return listOfAllImages;
    }

    public Image getImageWithTitle(String title){
        Image img = imgRepo.getImgByTitle(title);
        return img;
    }

    public  int uploadNewImgToDB(String title, String usrName, MultipartFile file){

        int x =0;
        String filename = null;
        byte imagedata[] = null;
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        long filelength = 0;
        try {
            filename = file.getOriginalFilename();
            System.out.println("Begin method: uploadNewImgToDB  " + filename);

            File imgfile= new File(filename);
            imgfile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imgfile);
            fos.write(file.getBytes());
            fos.close();

            filelength = imgfile.length();

            if((filelength/1024) > 1000){
                return -1;
            }
            imagedata = new byte[(int)filelength];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(imgfile);
            int read = 0;
            while ((read = ios.read(imagedata)) != -1) {
                ous.write(imagedata, 0, read);
            }

            System.out.println("size of input file::::"+filelength);

            System.out.println("finished reading file to byte array ::: " + imagedata.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        x = imgRepo.modifyingQueryInsertImage(title, usrName, usrName, 0,0,0, filename, currentTimeStamp,
                currentTimeStamp, imagedata, filelength);

        return x;
    }

    public int updateImgTitle(String newtitle, String usrName, String oldtitle){
        int x = 0;
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        x = imgRepo.modifyingQueryUpdateImage(newtitle, usrName, currentTimeStamp, oldtitle);
        return x;
    }

    public int updateImgNoOfLikes(String usrName, String title, long nooflikes){
        int x =0;
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        x = imgRepo.modifyingQueryUpdateImageNoOfLikes(nooflikes+1, usrName, currentTimeStamp, title);
        return x;
    }

    public int updateImgscore(String usrName, String title, float ratingscore, long noofratings){
        int x =0;
        Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
        x = imgRepo.modifyingQueryUpdateImageRatingScore(title,ratingscore,noofratings,usrName,currentTimeStamp);
        return x;
    }
}
