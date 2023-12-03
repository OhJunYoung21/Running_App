# 회원가입 로직


<div>1.아이디 중복확인</div>

~~~kotlin
lateinit var binding: ActivityJoinBinding
    lateinit var id: String
    lateinit var password: String
    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        binding = ActivityJoinBinding.inflate(layoutInflater)

        db = AppDatabase.getInstance(this)

        //아이디 중복확인 버튼 클릭시 발생하는 이벤트 처리
        binding.checkId.setOnClickListener {
            id = binding.userId.text.toString()
            if (db.getUserDAO().getIdList().contains(id)) {
                Toast.makeText(
                    this, "이미 사용중인 아이디입니다.\n 다시 입력하세요", Toast.LENGTH_SHORT
                )
            }
        }
    }
~~~


db객체에 접근해서 UserDAO를 사용, getIdList함수에 접근해서 userIndo테이블에 있는 아이디를 전부 가져온다. 


해당 아이디들이 만일 입력받은 아이디를 포함하고 있다면, 사용자가 입력한 아이디가 이미 데이터테이블에 존재한다는
의미이므로 중복되었다는 토스트 메세지를 출력한다. 


굳이 어려운 부분은 아닌듯 하나, 데이터테이블에 접근할때 직접 접근하지 않고 객체를 만들어서 간접적으로 접근하는 이유가 뭔지 알면 좋을 듯 싶다.

# 스레드 관련 오류

메인쓰레드에서 데이터베이스에 접근하려고 하니, 다음과 같은 오류가 발생하였다.


Cannot access database on the main thread since it may potentially lock the UI for a long period of time

<div>위 오류의 뜻은 무엇인가?</div>

구글링을 해보면, 비동기작업을 메인쓰레드에서 실행하기 때문이라고 한다. 이렇게만 설명하면 이해하기 어렵기 떄문에, 좀 더 자세히 설명하도록 하겠다.


안드로이드에서 MainThread는 UI Thread이다. 즉, 주된 업무는 사용자와 상호작용하는 화면을 보여주는 것이 주된 업무라는 것이다.


근데, 만약 이 Thread에 또 다른 업무를 주면 어떻게 될까?

MainThread는 여러가지 작업을 동시에 실행해야 하기 때문에 다른 작업을 하는 동안 UI Thread가 실행되지 않을 수도 있다. 그렇다면 사용자는 빈화면을 오래보게 되는 에러가 발생할 수도 있다.

우리가 가끔 앱이 렉이 걸리면 빈화면이 계속 보이는데, 아마 이때도 메인 쓰레드에 오류가 생겨서 UI를 보여주는 기능을 제때 실행하지 못해서 그러는 듯 싶다.(추가 공부필요🔖)

그렇기 때문에 우리는 비동기 방식을 사용해서 데이터베이스에 접근을 시도한다.





