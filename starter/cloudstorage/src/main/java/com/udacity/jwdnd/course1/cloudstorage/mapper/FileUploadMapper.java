package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.FileUploads;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileUploadMapper {
    // query all files
    @Select("SELECT fileId, filename FROM FILES WHERE userid = #{userid}")
    List<FileUploads> getFileUpload(Integer userid);

    // delete file exist
    @Select("DELETE FROM FILES WHERE fileId = #{deleteId}")
    void deleteFile(String deleteId);

    // get file name to delete
    @Select("SELECT filename FROM FILES WHERE fileId = #{deleteId}")
    String getFileName(String deleteId);

    // get contenttype by file name
    @Select("SELECT contenttype FROM FILES WHERE filename = #{filename}")
    String getContenttypeByFileName(String filename);

    // insert new file on Db
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    // insert new record
    // comment out by ThanhTLN
    int insert(FileUploads fileUploads);
}
