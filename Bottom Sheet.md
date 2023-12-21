## BottomSheet이란?

<div>BottomSheet의 종류에는 크게는 BottomSheetDialog, BottomSheetDialogFragment가 있다. 대개 많은 경우에 하단에서 프래그먼트를 불러온후 , 해당 프래그먼트 위에서 다양한 기능을 수행하는 것이 목적이기 때문에 후자를 많이 사용하는 편이다.</div>

### 🤬BottomSheet 모서리둥글게 하기🤬

~~~xml
<style name="CustomBottomSheetDialogTheme" parent="Theme.Material3.DayNight.BottomSheetDialog">
        <item name="bottomSheetStyle">@style/CustomRoundedBackgroundBottomSheet</item>
    </style>


    <style name="CustomRoundedBackgroundBottomSheet" parent="Widget.Design.BottomSheet.Modal">
        <item name="android:background">@drawable/bottom_sheet_border</item>
    </style>
~~~

모서리 둥글게 하기를 google에 검색하면 안드로이드가 제공하는 BottomSheet.Modal 이라는 객체의 속성을 바꾼 다음, Theme.Material3.DayNight.NoActionBar라는 앱 내의 모든 스타일을 관장하는 객체에게 알려주는 코드가 등장한다.


하지만 필자는 그저 Fragment.xml에서 최상위 뷰 객체의 background속성을 변경함으로써 동일한 기능을 구현했다. 


물론 style자체를 바꾸면 앞으로 만들 모든 bottomSheet에 동일한 스타일이 적용되겠지만, 해도해도 안돼서 될대라 라는 마인드로 android:background속성을 건드렸더니 원하는 대로 bottomsheet가 나왔다.

~~~xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">

    <corners
        android:topLeftRadius="30dp"
        android:topRightRadius="30dp">
    </corners>
    <solid android:color="#C8A59F" />
</shape>

위의 코드를 아래의 bottom_Sheet_Fragment의 최상의 뷰의 백그라운드 속성에 넣어준다.

<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bottom_sheet_border"
    app:layout_behavior="com.google.android.material.bottomSheet.BottomSheetBehavior"
    tools:context=".RunActivity">
~~~
