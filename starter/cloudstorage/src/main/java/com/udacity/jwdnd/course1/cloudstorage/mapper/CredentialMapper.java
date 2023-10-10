package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
// comment out by ThanhTLN
public interface CredentialMapper {
    // query credential list
    // credentialid url username key password userid
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialList(Integer userid);

    // insert new credential ( credentialid url username password userid )
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    // for auto  increment
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int createNewCredential(Credential credential);

    // update credential ( credentialid url username password userid )
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, userid = #{userid}, password = #{password} WHERE credentialid = #{credentialid}")
    int updateCredential(Credential credential);

    // delelte credential
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{deleteId}")
    void deleteCredential(String deleteId);
}
