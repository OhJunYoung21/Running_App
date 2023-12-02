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






