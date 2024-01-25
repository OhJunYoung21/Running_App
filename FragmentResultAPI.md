# Fragment와 Activity사이에서 데이터를 전달하는 방법
  * Bundle 사용
  * SharedViewModel 사용
  * FragmentResultAPI 사용


### setFragmentResult는 송신하는 프래그먼트에서 사용한다.

~~~kotlin
CoroutineScope(Dispatchers.IO).launch {
                val id = findIdAsync(name, number).toString()

                CoroutineScope(Dispatchers.Main).launch {
                    val dialogFragment = ConfirmDialog(title, content_1, id, 0)
                    dialogFragment.isCancelable = false
                    dialogFragment.show(requireFragmentManager(), "findIdProcess")
                }

                setFragmentResult("requestKey", bundleOf("findId" to id))

                cancel()
            }
~~~

위 코드의 cancel()위 부분을 보면 setFragmentResult를 사용하고 있다.

setFragmentResult는 개발자가 지정한 bundle객체에 값을 넣는 메서드입니다.

매개변수는 requestKey와 bundle객체가 있습니다. requestKey는 수신하는 프래그먼트,또는 액티비티에서 값을 전달받을때 결과를 분기하기 위해서 사용합니다.

예를 들어서 여러개의 프래그먼트에서 setFragemtResult()를 사용할때, 각각의 요청을 구별할 수 있어야 하는데, 위 역할을 requestKey가 수행합니다.

각각의 요청을 구별하지 못하면, 소프트웨어 입장에서 어떤 요청에 어떤 응답을 해야 할지 모르기 때문에 requestKey같은 식별자는 필수입니다.

bundle객체는 일반적으로 프래그먼트 사이에서 데이터를 전달할때 사용되는 하나의 객체종류입니다.

---

### setFragmentResultListener는 데이터를 수신하는 액티비티 또는 프래그먼트에서 사용한다.

~~~kotlin
supportFragmentManager.setFragmentResultListener("requestKey", this) { requestKey, bundle ->
            id = bundle.getString("findId").toString()
            password = bundle.getString("findPassword").toString()
        }
~~~

setFragmentResult에서 넘겨주는 bundle 객체를 supportFragmentManager를 사용해서 id,password에 넣어주고 있다.

setFragmentResultListener{}는 콜백함수를 호출합니다. 위 콜백함수는 파라미터가 2개가 있는데, 바로 requestKey와 bundle입니다.

위 코드에서 requestKey는 사용하지 않고 있으므로 '_'로 대체할 수 있습니다.

파라미터는 콜백함수에서 마치 일반함수의 매개변수와 같은 역할을 수행한다고 볼 수 있겠습니다.

위 부분은 콜백함수 부분에서 좀 더 자세히 다뤄볼 예정입니다.



