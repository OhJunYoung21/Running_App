# 네이버 API를 사용할 때, 해당 API를 사용해서 현 위치를 보여줘보자.

## 인터페이스를 가져오자.

~~~kotlin
class InsideActivity : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var naverMap: NaverMap

    private lateinit var binding: ActivityInsideBinding

    private lateinit var mapview:MapView
          }
~~~

위의 코드 아래 oncreate문이 생략되어 있지만, 위 코드의 의미는 중요하기에 알아보자.😀

OnMapReadyCallback은 Naver Map API가 제공하는 인터페이스이다. 인터페이스는 개발자가 함수를 편하게 사용하기 위해 이미 만들어져있는 메서드(함수)들의 집합이다.

만일 인터페이스가 없다면, 개발자는 일일이 함수를 구현해야 하고, 이는 개발자 입장에서 정말 빡치는 일이 될 수 밖에 없다.🤬

## 인터페이스는 일단 가져왔고, mapView를 초기화하고 생성해주자.

~~~kotlin

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapview = binding.mapView
        //사용자 경험을 위해 만들어진 bundle 객체입니다. 만일
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync(this)
        
    }
~~~

mapView는 뷰 바인딩을 통해서 지목해주었다. mapView.oncreate(savedInstance)는 사용자의 편의를 위해 제공한 것이다.

savedInstance는 사용자가 앱을 껏다가 켰을 때, 가장 최근의 상태를 보여주기 위해 만들어진 것이다. 예를 들어서 내가 마지막으로 방문한 장소가 경기도 과천이라면, 경기도 과천이 위치에 찍혀져 있을 것이다.

하지만 나의 목적은 실시간으로 나의 위치가 찍혀야 하기 때문에 굳이 onCreate(savedInstance)를 사용하지 않아도 된다고 생각하였다. 사용하지 않아도 지도를 보여주는 데에는 지장이 없다.

mapView.getMapAsync(this)는 지도 뷰가 비동기적으로 로딩되는 작업이 완료되면, OnMapReady라는 콜백함수를 호출하는 메서드이다. 

왜 비동기적으로 처리하느냐면, Main Thread는 UI Thread작업을 처리해야 하는데 지도를 보여주는 작업까지 동기적으로 처리하면 지도 작업을 Main Thread에서 처리하느라,
앱이 정지된 것처럼 보이거나, 비정상적으로 작동하는 것처럼 보여서 사용자에게 좋지 않다.(UX가 좋지 않다) 😂


## FusedLocationProviderClient를 사용해서 현재 위치 저장하기😀




