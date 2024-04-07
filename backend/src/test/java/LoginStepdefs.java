import com.ooad.careercompass.rest.controller.AuthController;
import com.ooad.careercompass.rest.dto.AuthResponse;
import com.ooad.careercompass.rest.dto.LoginRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginStepdefs {


    @Autowired
    AuthController authController;
    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
    }

    @When("I enter valid login credentials")
        public void iEnterValidLoginCredentials(io.cucumber.datatable.DataTable dataTable) {
            // Get the email and password from the DataTable
            String email = dataTable.cell(1, 0);
            String password = dataTable.cell(1, 1);
        try {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(email);
            loginRequest.setPassword(password);
            AuthResponse authResponse = authController.login(loginRequest);
            System.out.println(authResponse.accessToken());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @And("I submit the login form")
    public void iSubmitTheLoginForm() {
    }

    @Then("I receive a response containing an access token and my profile details")
    public void iReceiveAResponseContainingAnAccessTokenAndMyProfileDetails() {
    }

    @When("I enter invalid login credentials")
    public void iEnterInvalidLoginCredentials() {
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
    }
}
