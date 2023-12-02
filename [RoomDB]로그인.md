# RoomDB 로그인 구현

* RoomDB란?
<div>데이터를 저장하는 방법에는 크게 두가지가 있습니다.</div>
<div>1.서버에 데이터를 저장하고, 데이터가 필요할떄마다 서버와 연결하여 데이터를 가져오는 방식</div>
<div>2.기기내의 저장공간에 데이터를 저장하는 방식</div>

2번의 방법을 사용하면 어마어마한 양의 데이터를 저장할 수는 없겠지만, 1번보다 빠른 속도로 데이터를 가져올 수 있다는 장점이 있으며 RoomDB는 2번 방식을 사용합니다.

---

# RoomDB의 사용해서 회원정보 저장하기

* Entity 선언

~~~kotlin
@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val name: String,
    @ColumnInfo
    val phoneNumber: String,
    @ColumnInfo
    val id: Long,
    @ColumnInfo
    val password: String,
    @ColumnInfo
    val gender: String
)
~~~

Entity는 개발자가 각 사용자의 정보를 저장하는 테이블의 형식이다. 위 코드를 보면 테이블의 이름은 userInfo이며, name,phoneNumber,id,password,gender의 열을 가지고 있다.

---

* DAO(Data Accesion Object)

~~~kotlin
@Dao
interface LoginDAO {
    @Query("SELECT id FROM userInfo")
    fun getEmailList(): List<String>    // 등록된 회원인지 확인

    @Query("SELECT password FROM userinfo WHERE id = id")    // 전화번호에 따른 비밀번호 반환
    fun getPasswordByEmail(email: String): String

    @Insert
    fun insertUser(userInfo: UserEntity)    // 회원 등록

    @Query("DELETE FROM userinfo WHERE phoneNumber = phoneNumber AND password = :password")
    fun deleteUser(id: String, password: String)    // 회원 삭제
}
~~~

RoomDB를 사용하면 userInfo라는 데이터테이블에 직접 접근하는 것이 아니고, DAO객체를 통해서 간접적으로 접근합니다. 그래서 DAO객체를 선언하고, CRUD메소드를 선언해줘야 합니다.

<div>Create,Read,Update,Delete가 roomDB에는 @Insert, @Query,@Update,@Delete입니다.</div>

CRUD는 말은 거창하지만, 데이터에 접근해서 어떤 작업을 수행할지를 정하는 것입니다. 예를 들어서 내가 데이터 테이블에 접근해서 id가 junyoung인 객체의 password를 알아오고 싶다면, Query문을 사용해서 데이터를 읽어오면 되겠죠? Query는 Read와 동일한 기능입니다.





