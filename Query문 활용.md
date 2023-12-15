## 🧑‍💻아이디와 비밀번호 찾기🧑‍💻

* 먼저, DAO문에서 Query문을 추가해준다. 나는 입력받은 name,number를 기준으로 id값을 얻어올 것이다.
~~~kotlin
@Query("SELECT id FROM userinfo WHERE name = :name AND phoneNumber = :number")
    fun getIdByName(name: String, number: String):String
~~~
* 아이디 찾기 액티비티에서 해당 쿼리문을 실행한다. 데이터베이스에 접근하므로 코루틴내부에서 실행한다. Main Thread에서 실행하면 안되는 이유는...알지?
* 아래는 메서드 코드이다. 코루틴에서 실행하므로 suspend 키워드를 붙여주었다.
~~~kotlin
private suspend fun findId(name: String, number: String): String {

        val application = application as MyApplication

        return application.db.getUserDAO().getIdByName(name, number)

    }
~~~
* 아래는 전체 실행코드이다.(현재로써는 id에 null이 들어가는 에러가 발생하고 있음)
~~~kotlin
binding.searchBtn.setOnClickListener {

            name = binding.searchName.toString().trim()

            number = binding.searchNumber.toString().trim()

            CoroutineScope(Dispatchers.IO).launch {

                id = findId(name,number)

                runOnUiThread {

                    Toast.makeText(this@SearchIdActivity,"회원님의 아이디는 ${id}입니다.",Toast.LENGTH_SHORT).show()

                }
4
            }

        }
~~~
