import SwiftUI
import config
import domain

struct ContentView: View {

    @ObservedObject private(set) var testViewModel =
        TestViewModel(testUseCase: TestUseCaseImpl())

    private func listView() -> AnyView {
        return AnyView(Text(testViewModel.testValue))
    }
    
    var body: some View {
        NavigationView {
            listView()
        }
    }
}

class TestViewModel: ObservableObject {
        
    private var testUseCase: TestUseCase
    @Published var testValue = ""

    init(testUseCase: TestUseCase) {
        self.testUseCase = testUseCase
        loadStringValue()
    }
        
    func loadStringValue() {
        testUseCase.invoke(completionHandler: { value, error in
            if let value = value {
                self.testValue = value
            } else {
                self.testValue = error?.localizedDescription ?? "error"
            }
        })
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
	ContentView()
	}
}
