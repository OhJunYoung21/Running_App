package com.test.running_beta

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {
    @Query("SELECT id FROM userInfo")
    fun getIdList(): List<String>    // 등록된 회원인지 확인

    @Query("SELECT password FROM userinfo WHERE id = :id")    // 전화번호에 따른 비밀번호 반환
    fun getPasswordByEmail(id: String): String

    @Insert
    fun insertUser(userInfo: UserEntity)    // 회원 등록

    @Query("DELETE FROM userinfo WHERE id = :id AND password = :password")
    fun deleteUser(id: String, password: String)    // 회원 삭제
}