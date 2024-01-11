# Fragment와 Activity사이에서 데이터를 전달하는 방법
  * Bundle 사용
  * SharedViewModel 사용

## Bundle/Intent 객체 사용

주로 데이터를 전달할 때 bundle 객체를 사용해서 전달한다고 한다.

~~~kotlin
내가 원하는 기능: a Fragment에서 생성된 변수를 a Frament의 부모 액티비티(A)에 전달 -> A액티비티에서 B액티비티에 변수를 전달(Intent 객체 사용하면 간단)
[이슈사항]
🤬Frament에서 Activity로 데이터를 전달하는 부분을 못 만들고 있음 Tlqkf🤬
~~~
