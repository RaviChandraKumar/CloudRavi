package com.ravi.app.repository;

import com.ravi.app.model.Image;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface ImageRepository extends CrudRepository<Image, String>{

    @Query(value = "SELECT * FROM images ORDER BY modifiedtime DESC", nativeQuery = true)
    List<Image> getAllImages();


    @Query(value = "SELECT img FROM Image img WHERE img.title = ?1", nativeQuery = false)
    Image getImgByTitle(String title);

    @Query(value = "select img from Image img where img.title like ?1% ", nativeQuery = false)
    List<Image> getImgsLikeTitle(String title);

    @Modifying
    @Query(value = "Insert into images (title, uploadedby, modifiedby, nooflikes, ratingscore, noofratings, imgname, createdtime, " +
            "modifiedtime, imgdata, size) VALUES (:title, :uploadedby, :modifiedby,  :nooflikes, :ratingscore, :noofratings,:imgname, " +
            ":createdtime, :modifiedtime, :imgdata, :size)", nativeQuery = true)
    @Transactional
    int modifyingQueryInsertImage(@Param("title") String title,
                                  @Param("uploadedby") String uploadedby,
                                  @Param("modifiedby") String modifiedby,
                                  @Param("nooflikes") long nooflikes,
                                  @Param("ratingscore") float ratingscore,
                                  @Param("noofratings") long noofratings,
                                  @Param("imgname") String imgname,
                                  @Param("createdtime") Timestamp createdtime,
                                  @Param("modifiedtime") Timestamp modifiedtime,
                                  @Param("imgdata") byte[] imgdata,
                                  @Param("size") long size);

    @Modifying
    @Query(value = "update images set title = :newtitle, modifiedby = :modifiedby, modifiedtime=:modifiedtime" +
            " where title = :oldtitle", nativeQuery = true)
    @Transactional
    int modifyingQueryUpdateImage(@Param("newtitle") String newTitle,
                                  @Param("modifiedby") String modifiedby,
                                  @Param("modifiedtime") Timestamp modifiedtime,
                                  @Param("oldtitle") String oldTitle);

    @Modifying
    @Query(value = "update images set nooflikes = :likes, modifiedby = :modifiedby, modifiedtime=:modifiedtime" +
            " where title = :title", nativeQuery = true)
    @Transactional
    int modifyingQueryUpdateImageNoOfLikes(@Param("likes") long noloflikes,
                                  @Param("modifiedby") String modifiedby,
                                  @Param("modifiedtime") Timestamp modifiedtime,
                                  @Param("title") String title);

    @Modifying
    @Query(value = "update images set ratingscore = :ratingscore, noofratings = :noofratings, " +
            "modifiedby = :modifiedby, modifiedtime=:modifiedtime" +
            "  where title = :title", nativeQuery = true)
    @Transactional
    int modifyingQueryUpdateImageRatingScore(@Param("title") String title,
                                           @Param("ratingscore") float ratingscore,
                                           @Param("noofratings") long noofratings,
                                           @Param("modifiedby") String modifiedby,
                                           @Param("modifiedtime") Timestamp modifiedtime);

    @Modifying
    @Query(value = "delete from Image img where img.title = ?1", nativeQuery = false)
    @Transactional
    int deleteImageByTitle(String title);
}

