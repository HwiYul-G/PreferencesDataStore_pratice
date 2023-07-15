## PreferencesDataStore_pratice
[geeks for geeks's Preferences DataStore in Android](https://www.geeksforgeeks.org/preferences-datastore-in-android/) 이 링크에 있는 것을 따라서 만들며 SharedPreference의 next라 할 수 있는 PreferencesDataStore에 대해 학습하고자 한다.

## Preference DataStore
Preference DataStore는 안드로이드에 영구적으로 data를 저장하는데 사용된다. DataStore는 코루틴과 flow를 사용해 비동기적이고 일관된 트랜잭션 방식으로 데이터를 저장한다.<br>

### Preferences DataStore and Proto DataStore
- Preferences DataStore : key를 사용해 데이터를 저장, 엑세스함. 유형 안전성을 제공하지 않고 사전 정의된 스키마 필요 없음
- Proto DataStore : 맞춤 데이터 유형의 인스턴스로 데이터 저장. 유형 안전성 제공 + 프로토콜 버퍼를 사용해 스키마 정의
이 중 Preferences DataStore에 대해 볼 것이다.

#### Preferences DataStore 만들기
`preferencesDataStore`로 만든 속성 위임을 사용해서 `Datastore<Preferences>`의 instance를 만든다. 이때, kotlin 파일의 최상위 수준에서 인스턴스를 '한 번' 호출한 후, 앱의 나머지 부분에선 이 속성을 통해 인스턴스를 액세스한다.
이렇게 하면 더 간편하게 `DataStore`를 싱글톤으로 유지할 수 있다.
```kotlin
  // At the top level of your kotlin file:
  val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name="Preferences Datastore의 이름")
```
#### Preferences DataStore에서 읽기
Preferences Datastore는 사전 정의된 스키마를 사용하지 않으므로 `DataStore<Preferences>` 인스턴스에 저장해야하는 각 값의 키를 정의하려면 상응하는 키 유형 함수를 사용해야 한다.
```kotlin
val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
val exampleCounterFlow : Flow<Int> = context.dataStore.data
  .map{ prefs->
    // No type safety.
    prefs[EXAMPLE_COUNTER] ?: 0
  }
```
#### Preferences DataStore에 쓰기
Preferences Datastore는 `DataStore`의 데이터를 트랜잭션 방식으로 업데이트하는 `edit()` 함수 제공 함수의 `transform` 매개변수는 필요에 따라 값을 업뎃할 수 있는 코드 블록을 허용한다. 변환 블록의 모든 코드는 단일 트랜잭션으로 취급된다.
```kotlin
suspend fun incrementCounter(){
  context.dataStore.edit{ Prefereces_Datastore의 이름->
    val currentCounterValue = 이름[key] ?: 0
    이름[key] = currentCounterValue + 1
  } 
}
```
#### 참고자료
[Android DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=ko)

## ViewModel
activity나 fragment같은 UI 컨트롤러의 목적은 UI data를 표시하거나 사용자 작업에 반응하거나 권한 요청 같은 OS 커뮤니케이션을 처리하는 것이다.
UI 컨트롤러에 DB, Retrofit 등 데이터 로드 작업까지 시키면, 하나의 클래스에서 앱의 모든 작업을 처리하게 된다. 이렇게 되면 테스트도 어려워진다. 
<b>→ 다른 클래스로 작업의 위임이 필요하다.⇒ UI 컨트롤러 로직에서 뷰 데이터 소유권을 분리하고자 ViewModel을 사용한다.</b><br>

ViewModel 객체는 구성이 변경되는 동안 자동 보관된다. VM 객체가 보유한 데이터는 재개된 activitiy나 fragment 인스턴스에서 즉시 사용할 수 있다.
활동이 다시 생성되면 첫 번째 활동에서 UI controller에서 생성된 동일한 VM 인스턴스를 받는다. UI Controller의 활동이 완료되면 프레임워크는 리소스를 정리할 수 있도록 VM 객체의 onCleared() 메서드를 호출한다.<br>

<b>ViewModel은 View, Lifecycle, active context 참조를 포함하는 클래스를 참조해서는 안된다.</b><br>

`ViewModel` 객체는 View, `LifecyclerOwners`의 특정 인스턴스화보다 오래 지속되게 설계되었다. 그래서 VM이 위의 참조를 포함하는 클래스를 참조하면 참조하고 있던게 사라지기 때문에 문제가 된다. 어쨌건, 이 설계로 VM은 View와 Lifecycler 객체를 모른다. 따라서 개발자는 VM을 다루는 테스트를 더 쉽게 작성할 수 있다. (왜?) VM 객체에는 LiveData같은 LifecyclerObservers가 포함될 수 있다. 그러나 VM 객체는 LifeData 객체와 같이 수명 주기를 인식하는 Observable의 변경사항을 관찰해선 안된다.(참조하면 안되기 때문이다.)<br>
예를 들어, `ViewModel`이 시스템 서비스를 찾는데 `Application`같은 context가 필요하면 이럴 때는 `ViewModel`클래스가 아니라 `AndroidViewModel`클래스를 상속받아 생성자에 Application을 받는 생성자를 포함할 수 있다. 이는 Application 클래스가 Context를 확장하기 때문이다. 그래서 참조해도 문제가 되지 않는다.

#### 참고자료
[Android ViewModel 개요](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ko)

