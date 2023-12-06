# 커스텀 클래스를 roomDB에 저장하자.

개발을 하던 도중, 유저의 달리기 기록을 저장해야 할 일이 생겼다. 현재 사용하는 데이터베이스는 roomDB였기 때문에, 당연히 roomDB에 저장하려고 했다.


저장은 Records 라는 data class를 선언해주고, Entitiy에 넣어서 테이블에 넣어주려고 하였다.

~~~kotlin
@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0L,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val phoneNumber: String,
    @ColumnInfo
    val id: String,
    @ColumnInfo
    val password: String,
    @ColumnInfo
    val gender: String,
    @ColumnInfo
    val record: Records

)

data class Records(
    val time: Long,
    val speed: Long,
    val steps: Long
)
~~~

하지만 이렇게 하면 오류가 발생한다. roomDB는 커스텀 클래스 형태의 데이터를 테이블에 저장할 수가 없는 것이다. 위 문제를 해결하기 위해서는 gson을 사용해야먄 했다.
