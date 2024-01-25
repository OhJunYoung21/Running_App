# Fragment와 Activity사이에서 데이터를 전달하는 방법
  * Bundle 사용
  * SharedViewModel 사용
  * setFragmentListener 사용

## Fragment & Activity간 데이터전달을 위해서 setFragmentResultListener,setFragmentResult를 사용할 수 있다.

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

---

### setFragmentResultListener는 데이터를 수신하는 액티비티 또는 프래그먼트에서 사용한다.

~~~kotlin
supportFragmentManager.setFragmentResultListener("requestKey", this) { requestKey, bundle ->
            id = bundle.getString("findId").toString()
            password = bundle.getString("findPassword").toString()
        }
~~~

setFragmentResult에서 넘겨주는 bundle 객체를 supportFragmentManager를 사용해서 id,password에 넣어주고 있다.


