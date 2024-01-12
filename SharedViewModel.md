# Fragment와 Activity사이에서 데이터를 전달하는 방법
  * Bundle 사용
  * SharedViewModel 사용
  * setFragmentListener 사용

## Fragment & Activity간 데이터전달을 위해서 setFragmentListener를 사용할 수 있다.
~~~kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
                .setFragmentResultListener("requestKey", this) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            val result = bundle.getString("bundleKey")
            // Do something with the result.
        }
    }
}
~~~

공식문서에서는 이렇게 사용하라고 나오지만 아직까지 기능구현을 해내지 못했다.

