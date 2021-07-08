Repository which shows iOS issue when integrating koin in KMM project. iOS koin injection is based on:
https://johnoreilly.dev/posts/kotlinmultiplatform-koin/

* This repo focuses on iOS only
* There are 3 shared modules: `config` - supposed to be koin start point, `domain`, `data`
* Module's dependency is simplified (domain is not isolated) compared to clean architecture and has following graph: 
  * `presentation-->domain-->data` and `presentation-->config-->[domain, data]`
* In `AppDelegate.swift` we have:
```
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        //KoinDependencyKt.doInitKoin() // koin started in config module, DOES NOT WORK "KoinApplication has not been started" error
        KoinDependencyFromDomainKt.doInitKoin() // koin started in domain module, WORKS
        return true
    }
```

When using `KoinDependencyFromDomain` which is inside `domain` module everything works fine, but when we try to use `KoinDependency` we get error "KoinApplication has not been started". Also see koin jvm library class GlobalContext:L36


Some extra finding. If we check class definitions for `TestUseCaseImpl` in header `domain.h` we see:
```
__attribute__((swift_name("TestUseCaseImpl")))
@interface DomainTestUseCaseImpl : DomainBase <DomainTestUseCase, DomainKoin_coreKoinComponent>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
```
after moving `TestUseCaseImpl` to `config` module leaving `TestUseCase` interface  in `domain` we discover in `config.h`
```
__attribute__((swift_name("TestUseCaseImpl")))
@interface ConfigTestUseCaseImpl : ConfigBase <ConfigDomainTestUseCase, ConfigKoin_coreKoinComponent>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
```
and interface in `domain.h` is
```
__attribute__((swift_name("TestUseCase")))
@protocol DomainTestUseCase
```

So definitions for `TestUseCaseImpl` and interfaces are wrong and in the end we will get error:
```
Could not cast value of type 'ConfigTestUseCaseImpl' (0x10e32e5c8) to 'DomainTestUseCase' (0x10e063750).
2021-07-07 16:05:05.129752+0200 iosApp[21567:1072370] Could not cast value of type 'ConfigTestUseCaseImpl' (0x10e32e5c8) to 'DomainTestUseCase' (0x10e063750).
```
if we try to
```
struct ContentView: View {

    @ObservedObject private(set) var testViewModel =
        TestViewModel(testUseCase: TestUseCaseImpl() as! TestUseCase)
        
```
