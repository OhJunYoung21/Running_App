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


db객체에 접근해서 UserDAO를 사용, getIdList함수에 접근해서 userIndo테이블에 있는 아이디를 전부 가져온다. 해당 아이디들이 만일 입력받은 아이디를 포함하고 있다면, 사용자가 입력한 아이디가 이미 데이터테이블에 존재한다는
의미이므로 중복되었다는 토스트 메세지를 출력한다. 굳이 어려운 부분은 아닌듯 하나, 데이터테이블에 접근할때 직접 접근하지 않고 객체를 만들어서 간접적으로 접근하는 이유가 뭔지 알면 좋을 듯 싶다.
