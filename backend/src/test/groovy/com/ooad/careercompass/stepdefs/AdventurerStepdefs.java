package csci.ooad.arcane.stepdefs;

import csci.ooad.arcane.entity.Adventurer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

public class AdventurerStepdefs {
    private Adventurer adventurer;

    @Given("an Adventurer with a health of {int}")
    public void anAdventurerWithAHealthOf(int health) {
        String name = "Adventurer" + LocalDateTime.now().getNano();
        adventurer = new Adventurer(name, health);
    }

    @When("the Adventurer loses {int} health points")
    public void adventurerLosesHealth(int healthLoss) {
        adventurer.subtractHealth(healthLoss);
    }

    @Then("the Adventurer is still alive")
    public void theAdventurerIsStillAlive() {
        assert adventurer.isAlive();
    }

    @Then("the Adventurer is dead")
    public void theAdventurerIsDead() {
        assert !adventurer.isAlive();
    }
}
