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


## 왜 GSON을 사용하는가?

일단, gson은 객체를 JSON문자열 형태로 바꿔주는 라이브러리(도구)이다. 커스텀 클래스(개발자가 데이터를 저장하기 위해 사용하는 클래스)를 room DB에 바로 저장할 수는 없다. 그렇기 때문에 해당 객체를 JSON 문자열로 바꿔서 저장한다.

이때 사용되는 것이 GSON이다. 데이터가 생성되고 저장될 때는 [객체(데이터 클래스) -> JSON문자열]의 작업이 필요하고, 데이터를 읽어올때에는 [JSON문자열->객체(데이터클래스)] 작업이 필요하다. 위 과정을 수행하기 위해 GSON이 필요하다. 갑자기 천지창조의 수준으로 객체가 JSON으로 변하는 것은 아니기 때문에 변환을 도와주는 도구 정도로 생각하자.😀


<div>위에서 말한 것처럼 데이터를 읽어올때와 저장할때 두가지 작업이 필요하므로 converter도 두개 선언해준다.</div>

~~~kotlin
@ProvidedTypeConverter
class RecordsTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: Records): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Records {
        return gson.fromJson(value, Records::class.java)
    }
}
~~~

