package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    // query notes list
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getNotesList(Integer userid);

    // insert new note ( noteid, notetitle, notedescription, userid )
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    // for auto  increment
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int createNewNote(Notes notes);

    // insert new note ( noteid, notetitle, notedescription, userid )
    @Update("UPDATE NOTES  SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
    int updateNote(Notes notes);

    // delelte note
    @Delete("DELETE FROM NOTES WHERE noteid = #{deleteId}")
    void deleteNote(String deleteId);
}
