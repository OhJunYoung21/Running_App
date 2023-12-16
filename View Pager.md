## 😀아이디, 비밀번호 찾기를 View Pager로 구현해볼까😀
<div>
<img width="1000" alt="스크린샷 2023-12-16 오후 3 41 35" src="https://github.com/OhJunYoung21/Running_App/assets/81908471/0c6b8215-d4b7-4157-a54d-2b6133a479d2">
</div>


### 🧑‍💻ViewPager2란?🧑‍💻
-------
ViewPager2는 ViewPager에 이어서 나온 버전으로, 프래그먼트간 전환동작을 사용할때 자주 사용되는 위젯이다. 안드로이드 개발에서 화면전환을 위해 사용되는 강력한 도구이며,Jetpack Library의 일부이다.

### 🧑‍💻ViewPager2Adapter🧑‍💻
-------
~~~kotlin
class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchID_Fragment()
            else -> SearchPW_Fragment()
        }
    }
}
~~~

ViewPager2Adapter는 기본적으로 FragmentStateAdapter를 상속해서 만든다. override(내 입맛대로 바꿔주는)해주는 메서드는 createFragement와 getItemCount()이다.


* getItemCount로 얼마나 많은 수의 프래그먼트를 보여줘야 하는지 미리 알수있어, 그에 맞는 메모리 공간을 마련할 수 있다.
* createFragment()로 어떤 프래그먼트를 보여줄지 설정할수 있다.


### 🧑‍💻TabLayout는 ViewPager2와 같이 사용한다.🧑‍💻
-------
ViewPager2는 홀로 쓰이는 경우보다 TabLayout과 같이 쓰인다. TabLayout은 사용자경험을 위해 어떤 프래그먼트를 보여줄 것인지 알려주는 메뉴판이라고 생각할 수 있다.

TabLayout과 ViewPager2를 연결해줘야 하는데, 이때는 TabMediator를 사용한다.

~~~kotlin
TabLayoutMediator(TabLayout, ViewPager2) { tab, position ->

            tab.text = tabTitleArray[position]

        }.attach()
~~~




